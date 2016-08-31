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

    public SchemaMap()
    {
        classToSchema = new HashMap<>();
        nameToSchema = new HashMap<>();
        groupMap = new HashMap<>();
        log = LoggerFactory.getLogger(getClass());
        try
        {
            initializeGroupsMap();
            
            addElement(CrestOptions.class, "-v1+json");
            addElement(ConstellationCollection.class, "-v1+json");
            addElement(ItemGroupCollection.class, "-v1+json");
            addElement(CorporationCollection.class, "-v1+json");        // has options version but not corp 
            addElement(AllianceCollection.class, "-v2+json");
            addElement(ItemTypeCollection.class, "-v1+json");
            addElement(TokenDecode.class, "-v1+json");                  // swapped in second representation
            addElement(MarketTypePriceCollection.class, "-v1+json");
            addElement(OpportunityTasksCollection.class, "-v1+json");
            addElement(OpportunityGroupsCollection.class, "-v1+json");
            addElement(ItemCategoryCollection.class, "-v1+json");
            addElement(RegionCollection.class, "-v1+json");
            addElement(BloodlineCollection.class, "-v2+json");
            addElement(MarketGroupCollection.class, "-v1+json");
            addElement(SystemCollection.class, "-v1+json");
            addElement(SovCampaignsCollection.class, "-v1+json");
            addElement(SovStructureCollection.class, "-v1+json");
            addElement(TournamentCollection.class, "-v1+json");
            addElement(VirtualGoodStore.class, "-v1+json");             // group: virtualGoodStore href: https://vgs-tq.eveonline.com/ HTTP/1.1 405 Method Not Allowed
            addElement(WarsCollection.class, "-v1+json");
            addElement(IncursionCollection.class, "-v1+json");
            addElement(DogmaAttributeCollection.class, "-v1+json");
            addElement(DogmaEffectCollection.class, "-v1+json");
            addElement(RaceCollection.class, "-v3+json");
            addElement(InsurancePricesCollection.class, "-v1+json");
            // addElement(AuthenticationEndpoint.class,"-v1+json");
            addElement(IndustryFacilityCollection.class, "-v1+json");
            addElement(IndustrySystemCollection.class, "-v1+json");
            addElement(NPCCorporationsCollection.class, "-v1+json");
            addElement(Time.class, "-v1+json");
            addElement(MarketTypeCollection.class, "-v1+json");
        } catch (Exception e)
        {
            log.warn("Schema initialization failed", e);
        }
    }

    private void initializeGroupsMap()
    {
        groupMap.put("constellations", "constellations");
        groupMap.put("itemGroups", "itemGroups");
        groupMap.put("corporations", "corporations");
        groupMap.put("alliances", "alliances");
        groupMap.put("itemTypes", "itemTypes");
        groupMap.put("decode", "decode");
        groupMap.put("marketPrices", "marketPrices");
        groupMap.put("opportunities", "opportunities");
        groupMap.put("itemCategories", "itemCategories");
        groupMap.put("regions", "regions");
        groupMap.put("bloodlines", "bloodlines");
        groupMap.put("marketGroups", "marketGroups");
        groupMap.put("systems", "systems");
        groupMap.put("sovereignty", "sovereignty");
        groupMap.put("tournaments", "tournaments");
        groupMap.put("virtualGoodStore", "virtualGoodStore");
        groupMap.put("wars", "wars");
        groupMap.put("incursions", "incursions");
        groupMap.put("dogma", "dogma");
        groupMap.put("races", "races");
        groupMap.put("insurancePrices", "insurancePrices");
        groupMap.put("authEndpoint", "authEndpoint");
        groupMap.put("industry", "industry");
        groupMap.put("npcCorporations", "npcCorporations");
        groupMap.put("time", "time");
        groupMap.put("marketTypes", "marketTypes");
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
        Representations representations = cache.getCrestOptions(null).getRepresentations();
        List<EndpointGroup> groups = cache.getCrestCallList().getCallGroups();
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
//        for (CcpType type : endpointSchema.acceptType.ccpType.children)
//        {
//        }
        
        for (EndpointGroup group : groups)
        {
            groupsSeen.put(group.name, group.name);
            if(groupMap.get(group.name) == null)
                list.add("New group seen: " + group.name);
            
            if ("virtualGoodStore".equals(group.name))
            {
                versionBasesSeen.put(VirtualGoodStore.VersionBase, "-v1+json");
                continue; // FIXME when fixed
            }
            if ("authEndpoint".equals(group.name))
                continue;   // there is no schema on the auth endpoint, just skip it.
            
            for (Endpoint endpoint : group.getEndpoints())
            {
                Representations reps = cache.getCrestOptions(endpoint.uri).getRepresentations();
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
                if(group.name.equals("corporations"))
                {
                    // FIXME: remove when fixed
                    if(size > 1)
                        list.add("It appears CorporationCollection schema may have been added");
                    versionBasesSeen.put(CorporationCollection.VersionBase, "-v1+json");
                    continue;
                }
                if(group.name.equals("decode"))
                {
                    if(rep1Version.contains("Option"))
                        list.add("It appears DecodeToken schema may have been fixed");
                    rep0Version = rep1Version;
                }
                idx = rep0Version.indexOf("-v");
                base = rep0Version.substring(0, idx);
                rev = rep0Version.substring(idx);
                versionBasesSeen.put(base, rev);
                SchemaMapElement element = nameToSchema.get(base);
                if(element == null)
                    list.add("a new group: " + group.name + " rev: " + rep0Version + " seems to have been added.");
            }
        }
        if(nameToSchema.size() != versionBasesSeen.size())
            list.add("Existing number of version bases: " + nameToSchema.size() + " does not match ccp latest: " + versionBasesSeen.size());
        for(Entry<String, SchemaMapElement> entry : nameToSchema.entrySet())
        {
            String version = versionBasesSeen.get(entry.getKey());
            if(version == null)
                list.add("Current ccp lastest does not contain version base: " + entry.getKey());
            else
            {
                if(!entry.getValue().currentVersion.equals(version))
                    list.add("There is a newer endpoint version for base: " + entry.getValue().getVersion() + " " + entry.getKey() + entry.getValue());
            }
        }
        if(list.size() == 0)
            list.add("crestj's endpoint versions are up to date");
        StringBuilder sb = new StringBuilder();
        sb.append("\ncrestj endpoint version check:\n");
        for(String msg : list)
            sb.append("\t" + msg + "\n");
        log.info(sb.toString());
        return list;
    }

    private void addElement(Class<? extends EveData> clazz, String currentVersion) throws Exception
    {
        Field field = clazz.getDeclaredField("VersionBase");
        String applicationName = (String) field.get(clazz);
        SchemaMapElement element = new SchemaMapElement(clazz.getName(), applicationName, currentVersion);
        classToSchema.put(element.clazz, element);
        nameToSchema.put(element.applicationName, element);
    }

    public class SchemaMapElement
    {
        public final String clazz;
        public final String applicationName;
        public final String currentVersion;

        public SchemaMapElement(String clazz, String applicationName, String currentVersion)
        {
            this.clazz = clazz;
            this.applicationName = applicationName;
            this.currentVersion = currentVersion;
        }

        public String getVersion()
        {
            return applicationName + currentVersion;
        }
    }
}
