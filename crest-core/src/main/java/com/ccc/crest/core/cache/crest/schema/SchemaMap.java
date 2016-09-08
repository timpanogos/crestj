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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.cache.crest.alliance.AllianceCollection;
import com.ccc.crest.core.cache.crest.bloodline.BloodlineCollection;
import com.ccc.crest.core.cache.crest.constellation.ConstellationCollection;
import com.ccc.crest.core.cache.crest.corporation.CorporationCollection;
import com.ccc.crest.core.cache.crest.decode.TokenDecode;
import com.ccc.crest.core.cache.crest.dogma.DogmaAttributeCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaEffectCollection;
import com.ccc.crest.core.cache.crest.incursion.IncursionCollection;
import com.ccc.crest.core.cache.crest.industry.IndustryFacilityCollection;
import com.ccc.crest.core.cache.crest.industry.IndustrySystemCollection;
import com.ccc.crest.core.cache.crest.insurancePrice.InsurancePricesCollection;
import com.ccc.crest.core.cache.crest.itemCategory.ItemCategoryCollection;
import com.ccc.crest.core.cache.crest.itemGroup.ItemGroupCollection;
import com.ccc.crest.core.cache.crest.itemType.ItemTypeCollection;
import com.ccc.crest.core.cache.crest.marketGroup.MarketGroupCollection;
import com.ccc.crest.core.cache.crest.marketPrice.MarketTypeCollection;
import com.ccc.crest.core.cache.crest.marketType.MarketTypePriceCollection;
import com.ccc.crest.core.cache.crest.npcCorporation.NPCCorporationsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityGroupsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityTasksCollection;
import com.ccc.crest.core.cache.crest.race.RaceCollection;
import com.ccc.crest.core.cache.crest.region.RegionCollection;
import com.ccc.crest.core.cache.crest.schema.endpoint.Endpoint;
import com.ccc.crest.core.cache.crest.schema.endpoint.EndpointGroup;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.cache.crest.schema.option.Representation;
import com.ccc.crest.core.cache.crest.schema.option.Representations;
import com.ccc.crest.core.cache.crest.sovereignty.SovCampaignsCollection;
import com.ccc.crest.core.cache.crest.sovereignty.SovStructureCollection;
import com.ccc.crest.core.cache.crest.system.SystemCollection;
import com.ccc.crest.core.cache.crest.time.Time;
import com.ccc.crest.core.cache.crest.tournament.TournamentCollection;
import com.ccc.crest.core.cache.crest.virtualGoodStore.VirtualGoodStore;
import com.ccc.crest.core.cache.crest.war.WarsCollection;
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

    private final HashMap<String, SchemaMapElement> classToSchema;
    private final HashMap<String, SchemaMapElement> nameToSchema;
    private final HashMap<String, String> groupMap;
    private final Logger log;
    private final HashMap<String, SchemaMapElement> uriToSchema;

    public SchemaMap()
    {
        classToSchema = new HashMap<>();
        nameToSchema = new HashMap<>();
        groupMap = new HashMap<>();
        uriToSchema = new HashMap<>();
        log = LoggerFactory.getLogger(getClass());
        try
        {
            initializeGroupsMap();
            initializeVersions();
            initializeEndpoints();
        } catch (Exception e)
        {
            log.warn("Schema initialization failed", e);
        }
    }
    
    public static void init()
    {
        // just need to reference this class and cause it to load
        // nothing to do static initializer will cause everything to setup
    }
    
    private void initializeVersions() throws Exception
    {
        addElement(AllianceCollection.class,            "-v2+json", "/alliances/",              "alliances.href");
//      addElement(AuthenticationEndpoint.class,        "-v1+json", "/oauth/token/",            "authEndpoint.href");
        addElement(BloodlineCollection.class,           "-v2+json", "/bloodlines/",             "bloodlines.href");
        addElement(ConstellationCollection.class,       "-v1+json", "/constellations/",         "constellations.href");
        addElement(CorporationCollection.class,         "-v1+json", "/corporations/",           "corporations.href"); // has options version but not corp 
        addElement(CrestOptions.class,                  "-v1+json", "",                         "");
        addElement(DogmaAttributeCollection.class,      "-v1+json", "/dogma/attributes/",       "dogma.attributes");
        addElement(DogmaEffectCollection.class,         "-v1+json", "/dogma/effects/",          "dogma.effects");
        addElement(IncursionCollection.class,           "-v1+json", "/incursions/",             "incursions.href");
        addElement(IndustryFacilityCollection.class,    "-v1+json", "/industry/facilities/",    "industry.facilities");
        addElement(IndustrySystemCollection.class,      "-v1+json", "/industry/systems/",       "industry.systems");
        addElement(InsurancePricesCollection.class,     "-v1+json", "/insuranceprices/",        "insurancePrices.href");
        addElement(ItemCategoryCollection.class,        "-v1+json", "/inventory/categories/",   "itemCategories.href");
        addElement(ItemGroupCollection.class,           "-v1+json", "/inventory/groups/",       "itemGroups.href");
        addElement(ItemTypeCollection.class,            "-v1+json", "/inventory/types/",        "itemTypes.href");
        addElement(MarketGroupCollection.class,         "-v1+json", "/market/groups/",          "marketGroups.href");
        addElement(MarketTypePriceCollection.class,     "-v1+json", "/market/prices/",          "marketPrices.href");
        addElement(MarketTypeCollection.class,          "-v1+json", "/market/types/",           "marketTypes.href");
        addElement(NPCCorporationsCollection.class,     "-v1+json", "/corporations/npccorps/",  "npcCorporations.href");
        addElement(OpportunityGroupsCollection.class,   "-v1+json", "/opportunities/groups/",   "opportunities.groups");
        addElement(OpportunityTasksCollection.class,    "-v1+json", "/opportunities/tasks/",    "opportunities.tasks");
        addElement(RaceCollection.class,                "-v3+json", "/races/",                  "races.href");
        addElement(RegionCollection.class,              "-v1+json", "/regions/",                "regions.href");
        addElement(SovCampaignsCollection.class,        "-v1+json", "/sovereignty/campaigns/",  "sovereignty.campaigns");
        addElement(SovStructureCollection.class,        "-v1+json", "/sovereignty/structures/", "sovereignty.structures");
        addElement(SystemCollection.class,              "-v1+json", "/solarsystems/",           "systems.href");
        addElement(Time.class,                          "-v1+json", "/time/",                   "time.href");
        addElement(TokenDecode.class,                   "-v1+json", "/decode/",                 "decode.href"); // swapped in second representation
        addElement(TournamentCollection.class,          "-v1+json", "/tournaments/",            "tournaments.href");
        addElement(VirtualGoodStore.class,              "-v1+json", "/virtualGoodStore/",       "virtualGoodStore.href"); // group: virtualGoodStore href: https://vgs-tq.eveonline.com/ HTTP/1.1 405 Method Not Allowed
        addElement(WarsCollection.class,                "-v1+json", "/wars/",                   "wars.href");
    }
    
    private void initializeGroupsMap()
    {
        groupMap.put("alliances", "alliances");
        groupMap.put("authEndpoint", "authEndpoint");
        groupMap.put("bloodlines", "bloodlines");
        groupMap.put("constellations", "constellations");
        groupMap.put("corporations", "corporations");
        groupMap.put("decode", "decode");
        groupMap.put("dogma", "dogma");
        groupMap.put("incursions", "incursions");
        groupMap.put("industry", "industry");
        groupMap.put("insurancePrices", "insurancePrices");
        groupMap.put("itemCategories", "itemCategories");
        groupMap.put("itemGroups", "itemGroups");
        groupMap.put("itemTypes", "itemTypes");
        groupMap.put("marketGroups", "marketGroups");
        groupMap.put("marketPrices", "marketPrices");
        groupMap.put("marketTypes", "marketTypes");
        groupMap.put("npcCorporations", "npcCorporations");
        groupMap.put("opportunities", "opportunities");
        groupMap.put("races", "races");
        groupMap.put("regions", "regions");
        groupMap.put("sovereignty", "sovereignty");
        groupMap.put("systems", "systems");
        groupMap.put("time", "time");
        groupMap.put("tournaments", "tournaments");
        groupMap.put("virtualGoodStore", "virtualGoodStore");
        groupMap.put("wars", "wars");
    }

    private void initializeEndpoints() throws SourceFailureException
    {
        HashMap<String, String> checkMap = new HashMap<>();
        DataCache cache = ((CrestController) CrestController.getController()).dataCache;
        List<EndpointGroup> groups = cache.getEndpointCollection().getEndpointGroups();
        for (EndpointGroup group : groups)
        {
            List<Endpoint> endpoints = group.getEndpoints();
            for(Endpoint endpoint : endpoints)
            {
                String turi = checkMap.get(endpoint.uri);
                if(turi != null)
                    log.warn("two endpoints reported with same uri: " + group.toString());
                checkMap.put(endpoint.uri, endpoint.uri);
//                constellations.href
                String key = new StringBuilder().append(group.name).append(".").append(endpoint.name).toString();
                SchemaMapElement e = uriToSchema.get(key);
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
        synchronized (nameToSchema)
        {
            return nameToSchema.get(versionBase);
        }
    }

    public SchemaMapElement getSchemaFromClass(EveData dataObject)
    {
        synchronized (classToSchema)
        {
            return classToSchema.get(dataObject.getClass().getName());
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
        if (!CrestOptions.getVersion().equals(schemaSchema.acceptType.name))
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
                    versionBasesSeen.put(CorporationCollection.VersionBase, "-v1+json");
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
                SchemaMapElement element = nameToSchema.get(base);
                if (element == null)
                    list.add("a new group: " + group.name + " rev: " + rep0Version + " seems to have been added.");
            }
        }
        if (nameToSchema.size() != versionBasesSeen.size())
            list.add("Existing number of version bases: " + nameToSchema.size() + " does not match ccp latest: " + versionBasesSeen.size());
        for (Entry<String, SchemaMapElement> entry : nameToSchema.entrySet())
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

    private void addElement(Class<? extends EveData> clazz, String version, String uri, String key) throws Exception
    {
        Field field = clazz.getDeclaredField("VersionBase");
        String applicationName = (String) field.get(clazz);
        SchemaMapElement element = new SchemaMapElement(clazz.getName(), applicationName, version, uri, key);
        classToSchema.put(element.clazz, element);
        nameToSchema.put(element.applicationName, element);
        uriToSchema.put(element.key, element);
    }

    public class SchemaMapElement
    {
        public final String clazz;
        public final String applicationName;
        public final String currentVersion;
        private String currentUri;
        private String key;

        public SchemaMapElement(String clazz, String applicationName, String version, String uri, String key)
        {
            this.clazz = clazz;
            this.applicationName = applicationName;
            this.currentVersion = version;
            this.currentUri = uri;
            this.key = key;
        }

        public String getVersion()
        {
            return applicationName + currentVersion;
        }
        
        public synchronized String getKey()
        {
            return key;
            
        }
        
        public synchronized void setKey(String key)
        {
            this.key = key;
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
            format.ttl("applicationName: ", applicationName);
            format.ttl("currentVersion: ", currentVersion);
            format.ttl("currentUri: ", currentUri);
            format.ttl("key: ", key);
            return format;
        }
    }
}
