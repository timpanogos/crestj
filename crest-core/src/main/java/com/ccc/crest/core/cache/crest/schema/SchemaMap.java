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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.BaseEveData.VersionType;
import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.cache.EveJsonData;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.cache.crest.schema.option.Representation;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.tools.StrH;
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
        DataCache cache = ((CrestController) CrestController.getController()).dataCache;
        Representation rep = cache.getOptions(null).getRepresentations().representations.get(1);
        if(!rep.acceptType.name.equals(schemaMap.rootEndpoint.endpoints.root.ruid))
        {
            String msg = "This framework is currently coded to " + schemaMap.rootEndpoint.endpoints.root.ruid + " CREST is reporting: " + rep.acceptType.name;
            schemaMap.log.warn(msg);
            CrestController.getCrestController().fireEndpointDeprecatedEvent(msg);
        }
    }

    private void initializeVersions() throws Exception
    {
        AtomicInteger level = new AtomicInteger(0);
        String path = new String("");
        traverse(rootEndpoint.endpoints.root, level, path);
    }

    private void traverse(RootEndpoint.Endpoint endpoint, AtomicInteger level, String url) throws Exception
    {
        if(endpoint.relative != null)
            url = StrH.combinePathSegments(url, endpoint.relative, '/');
        if(level.get() != 0)
        {
            if(endpoint.cuid != null)
                addElement(endpoint.eveDataClass, endpoint.cuidVersion, url.toString(), VersionType.Post);
            if(endpoint.ruid != null)
                addElement(endpoint.eveDataClass, endpoint.ruidVersion, url.toString(), VersionType.Get);
            if(endpoint.uuid != null)
                addElement(endpoint.eveDataClass, endpoint.uuidVersion, url.toString(), VersionType.Put);
            if(endpoint.duid != null)
                addElement(endpoint.eveDataClass, endpoint.duidVersion, url.toString(), VersionType.Delete);
        }
        level.incrementAndGet();
        for (RootEndpoint.Endpoint child : endpoint.children)
            traverse(child, level, url);
        level.decrementAndGet();
    }

    public SchemaMapElement getSchemaFromVersionBase(String versionBase)
    {
        synchronized (uidToSchema)
        {
            return uidToSchema.get(versionBase);
        }
    }

    public String getUrlStripAtomic(String versionBase)
    {
        // rootUrl + "/alliances/99000006/"
        String url = getSchemaFromVersionBase(versionBase).getUri();
        int idx = url.lastIndexOf('/');
        do
            if(!Character.isDigit(url.charAt(--idx)))
                break;
        while(true);
        return url.substring(0, idx);
    }

    private void addElement(Class<? extends EveJsonData> clazz, String version, String uri, VersionType uidType) throws Exception
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
        @SuppressWarnings("null")
        String uid = (String) field.get(clazz);
        SchemaMapElement element = new SchemaMapElement(clazz.getName(), uid, version, uri, uidType);
        uidToSchema.put(element.uid, element);
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
