/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as 
**  published by the Free Software Foundation, either version 3 of the 
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.core.cache.crest.schema;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.BaseEveData.VersionType;
import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.cache.EveJsonData;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.cache.crest.corporation.NpcCorporationsCollection;
import com.ccc.crest.core.cache.crest.schema.RootEndpoint.Endpoint;
import com.ccc.crest.core.cache.crest.schema.endpoint.CrestEndpoint;
import com.ccc.crest.core.cache.crest.schema.endpoint.EndpointGroup;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.cache.crest.schema.option.Representation;
import com.ccc.crest.core.cache.crest.schema.option.Representations;
import com.ccc.crest.core.cache.crest.virtualGoodStore.VirtualGoodStore;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class SchemaMap
{
    public static final String CrestOverallVersion = "application/vnd.ccp.eve.Api-v5+json";
    public static final SchemaMap schemaMap;
    static
    {
        schemaMap = new SchemaMap();
    }

    private final HashMap<String, SchemaMapElement> uidToSchema;
    private final Logger log;
    private final RootEndpoint rootEndpoint;

    public SchemaMap()
    {
        uidToSchema = new HashMap<>();
        rootEndpoint = new RootEndpoint();
        log = LoggerFactory.getLogger(getClass());
        try
        {
            initializeVersions();
        } catch (Exception e)
        {
            log.warn("Schema initialization failed", e);
        }
    }
    
    public static void init() throws SourceFailureException
    {
        // just need to reference this class and cause it to load
        // nothing to do static initializer will cause everything to setup
        DataCache cache = ((CrestController) CrestController.getController()).dataCache;
        Representation rep = cache.getOptions(null).getRepresentations().representations.get(1);
        if(!rep.acceptType.name.equals("application/"+schemaMap.rootEndpoint.endpoints.root.ruid))
            schemaMap.log.warn("This framework is currently coded to " + schemaMap.rootEndpoint.endpoints.root.ruid + " CREST is reporting: " + rep.acceptType.name);
    }
    
    private void initializeVersions() throws Exception
    {
        AtomicInteger level = new AtomicInteger(0);
        traverse(rootEndpoint.endpoints.root, level);
    }
    
    private void traverse(RootEndpoint.Endpoint endpoint, AtomicInteger level) throws Exception
    {
        if(level.get() != 0)
        {
            if(endpoint.cuid != null)
                addElement(endpoint.eveDataClass, endpoint.cuidVersion, endpoint.relative, VersionType.Post);
            if(endpoint.ruid != null)
                addElement(endpoint.eveDataClass, endpoint.ruidVersion, endpoint.relative, VersionType.Get);
            if(endpoint.uuid != null)
                addElement(endpoint.eveDataClass, endpoint.uuidVersion, endpoint.relative, VersionType.Put);
            if(endpoint.duid != null)
                addElement(endpoint.eveDataClass, endpoint.duidVersion, endpoint.relative, VersionType.Delete);
        }
        level.incrementAndGet();
        for (RootEndpoint.Endpoint child : endpoint.children)
            traverse(child, level);
        level.decrementAndGet();
    }

    private void initializeEndpoints() throws SourceFailureException
    {
        HashMap<String, String> checkMap = new HashMap<>();
        DataCache cache = ((CrestController) CrestController.getController()).dataCache;
        List<EndpointGroup> groups = cache.getEndpointCollection().getEndpointGroups();
        for (EndpointGroup group : groups)
        {
            List<CrestEndpoint> endpoints = group.getEndpoints();
            for(CrestEndpoint endpoint : endpoints)
            {
                String turi = checkMap.get(endpoint.uri);
                if(turi != null)
                    log.warn("two endpoints reported with same uri: " + group.toString());
                checkMap.put(endpoint.uri, endpoint.uri);
//                constellations.href
                String key = new StringBuilder().append(group.name).append(".").append(endpoint.name).toString();
                SchemaMapElement e = uidToSchema.get(key);
                if(e == null)
                {
                    if(!"authEndpoint.href".equals(key))
                        log.warn("\nCannot map the endpoint to a schema: \n" + group.toString());
                    continue;
                }
                String reportedUri = endpoint.uri;
                String base = CrestClient.getCrestBaseUri();
                if(!reportedUri.startsWith(base))
                {
                    log.warn("\nCannot map the endpoint to a schema: \n" + group.toString() +e.toString());
                    continue;
                }
                reportedUri = reportedUri.substring(base.length());
                if(!reportedUri.equals(e.currentUri))
                    log.info("A new uri was reported for: \n" + group.toString() + "new: " + reportedUri + "\n" +e.toString());
                e.setUri(reportedUri);
            }
        }
    }
    
    public SchemaMapElement getSchemaFromVersionBase(String versionBase)
    {
        synchronized (uidToSchema)
        {
            return uidToSchema.get(versionBase);
        }
    }

    public List<String> checkSchema() throws SourceFailureException
    {
        HashMap<String, String> groupsSeen = new HashMap<>();
        HashMap<String, String> versionBasesSeen = new HashMap<>();

        List<String> list = new ArrayList<>();
        DataCache cache = ((CrestController) CrestController.getController()).dataCache;
        Representations representations = cache.getOptions(null).getRepresentations();
//log.info("\n"+representations.toString());        
        List<EndpointGroup> groups = cache.getEndpointCollection().getEndpointGroups();
        Representation schemaSchema = representations.representations.get(0);
        String optionsVersion = schemaSchema.acceptType.name;
        int idx = optionsVersion.indexOf("-v");
        String base = optionsVersion.substring(0, idx);
        String rev = optionsVersion.substring(idx);
        versionBasesSeen.put(base, rev);
        Representation endpointSchema = representations.representations.get(1);
        if (!CrestOptions.getVersion(VersionType.Get).equals(schemaSchema.acceptType.name))
            list.add(schemaSchema.acceptType.name);
        if (!SchemaMap.CrestOverallVersion.equals(endpointSchema.acceptType.name))
            list.add(endpointSchema.acceptType.name);
        
        for (EndpointGroup group : groups)
        {
            groupsSeen.put(group.name, group.name);
            if (groupMap.get(group.name) == null)
                list.add("New group seen: " + group.name);

            if ("virtualGoodStore".equals(group.name))
            {
                versionBasesSeen.put(VirtualGoodStore.VersionBase, "-v1+json");
                continue; // FIXME when fixed
            }
            if ("authEndpoint".equals(group.name))
                continue; // there is no schema on the auth endpoint, just skip it.

            for (Endpoint endpoint : group.getEndpoints())
            {
                Representations reps = cache.getOptions(endpoint.uri).getRepresentations();
                int size = reps.representations.size();
                Representation rep0 = null;
                Representation rep1 = null;
                String rep1Version = null;
                String rep0Version = null;
                if (size > 0)
                {
                    rep0 = reps.representations.get(0);
                    rep0Version = rep0.acceptType.name;
                }
                if (size > 1)
                {
                    rep1 = reps.representations.get(1);
                    rep1Version = rep1.acceptType.name;
                }
                if (group.name.equals("corporations"))
                {
                    // FIXME: remove when fixed
                    if (size > 1)
                        list.add("It appears CorporationCollection schema may have been added");
                    versionBasesSeen.put(NpcCorporationsCollection.GetBase, "-v1+json");
                    continue;
                }
                if (group.name.equals("decode"))
                {
                    if (rep1Version.contains("Option"))
                        list.add("It appears DecodeToken schema may have been fixed");
                    rep0Version = rep1Version;
                }
                idx = rep0Version.indexOf("-v");
                base = rep0Version.substring(0, idx);
                rev = rep0Version.substring(idx);
                versionBasesSeen.put(base, rev);
                SchemaMapElement element = uidToSchema.get(base);
                if (element == null)
                    list.add("a new group: " + group.name + " rev: " + rep0Version + " seems to have been added.");
            }
        }
        if (uidToSchema.size() != versionBasesSeen.size())
            list.add("Existing number of version bases: " + uidToSchema.size() + " does not match ccp latest: " + versionBasesSeen.size());
        for (Entry<String, SchemaMapElement> entry : uidToSchema.entrySet())
        {
            String version = versionBasesSeen.get(entry.getKey());
            if (version == null)
                list.add("Current ccp lastest does not contain version base: " + entry.getKey());
            else
            {
                if (!entry.getValue().currentVersion.equals(version))
                    list.add("There is a newer endpoint version for base: " + entry.getValue().getVersion() + " " + entry.getKey() + entry.getValue());
            }
        }
        if (list.size() == 0)
            list.add("crestj's endpoint versions are up to date");
        StringBuilder sb = new StringBuilder();
        sb.append("\ncrestj endpoint version check:\n");
        for (String msg : list)
            sb.append("\t" + msg + "\n");
        log.info(sb.toString());
        return list;
    }

    private void addElement(Class<? extends EveJsonData> clazz, String version, String uri, VersionType uidType) throws Exception
    {
try
{
        Field field = null;
        switch(uidType)
        {
            case Delete:
                field = clazz.getDeclaredField("DeleteBase");
                break;
            case Get:
                field = clazz.getDeclaredField("GetBase");
                break;
            case Post:
                field = clazz.getDeclaredField("PostBase");
                break;
            case Put:
                field = clazz.getDeclaredField("PutBase");
                break;
            default:
                break;
        }
        String uid = (String) field.get(clazz);
        SchemaMapElement element = new SchemaMapElement(clazz.getName(), uid, version, uri, uidType);
        uidToSchema.put(element.uid, element);
}catch(Exception e)
{
    log.info("look here");
}
    }

    public class SchemaMapElement
    {
        public final String clazz;
        public final String uid;
        public final VersionType uidType;
        public final String currentVersion;
        private String currentUri;

        public SchemaMapElement(String clazz, String applicationName, String version, String uri, VersionType uidType)
        {
            this.clazz = clazz;
            this.uid = applicationName;
            this.currentVersion = version;
            this.currentUri = uri;
            this.uidType = uidType;
        }

        public String getVersion()
        {
            return uid + currentVersion;
        }
        
        public synchronized String getUri()
        {
            return CrestClient.getCrestBaseUri() + currentUri;
        }
        
        public synchronized void setUri(String uri)
        {
            currentUri = uri;
        }
        
        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            format.ttl(getClass().getSimpleName());
            format.inc();
            return toString(format).toString();
        }
        
        public TabToLevel toString(TabToLevel format)
        {
            format.ttl("clazz: ", clazz);
            format.ttl("applicationName: ", uid);
            format.ttl("currentVersion: ", currentVersion);
            format.ttl("currentUri: ", currentUri);
            return format;
        }
    }
}
