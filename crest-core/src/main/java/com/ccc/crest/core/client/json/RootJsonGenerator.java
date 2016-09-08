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
package com.ccc.crest.core.client.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

@SuppressWarnings("javadoc")
public class RootJsonGenerator
{
    public Endpoints endpoints;
    public String json;
    
    public RootJsonGenerator()
    {
        init();
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        json = gson.create().toJson(endpoints);
    }
    
    public class Endpoints
    {
        public long userCount;
        public String serverVersion;
        public String serverName;
        public String serverStatus;
        public Endpoint root;
        
        public Endpoints()
        {
            userCount = 63;
            serverVersion = "EVE-2016-HERMINE 14.08.1071672.1071672";
            serverName = "SINGULARITY";
            serverStatus = "online";
            root = new Endpoint("root", "application/vnd.ccp.eve.Api-v5+json", "https://api-sisi.testeveonline.com/", "");
        }
    }
    
    public class Endpoint
    {
        public String name;
        public String uid;
        public String url;
        public String relative;
        public List<Endpoint> children;
        
        public Endpoint(String name, String uid, String url, String relative)
        {
            this.name = name;
            this.uid = uid;
            this.url = url;
            this.relative = relative;
            children = new ArrayList<>();
        }
    }
    
    public static void main(String[] args)
    {
        RootJsonGenerator rjg = new RootJsonGenerator();
        LoggerFactory.getLogger(RootJsonGenerator.class).info("\n"+rjg.json);
        System.out.println("look here");
    }
    
    private void init()
    {
        endpoints = new Endpoints();
        Endpoint root = endpoints.root;
        addChild(root, "character", "application/vnd.ccp.eve.Character", null, null);
        Endpoint child = addChild(root, "corporation", "application/vnd.ccp.eve.Corporation", null, null);
        addChild(child, "corporation", "application/vnd.ccp.eve.CorporationCollection", "https://api-sisi.testeveonline.com/corporations/", "/corporations/");
        addChild(child, "corporation", "application/vnd.ccp.eve.NPCCorporationsCollection", "https://api-sisi.testeveonline.com/corporations/npccorps/", "/corporations/npccorps/");
        addChild(root, "dogma", "application/vnd.ccp.eve.Dogma", null, null);
        addChild(root, "fleet", "application/vnd.ccp.eve.Fleet", null, null);
        addChild(root, "industry", "application/vnd.ccp.eve.Industry", null, null);
        addChild(root, "inventory", "application/vnd.ccp.eve.Inventory", null, null);
        addChild(root, "market", "application/vnd.ccp.eve.MarketGroupCollection", null, null);
        addChild(root, "misc", "application/vnd.ccp.eve.Misc", null, null);
        addChild(root, "opportunity", "application/vnd.ccp.eve.OpportunityGroupsCollection", null, null);
        addChild(root, "sovereignty", "application/vnd.ccp.eve.Sov", null, null);
        addChild(root, "tournament", "application/vnd.ccp.eve.Tournament", null, null);
    }
    
    private Endpoint addChild(Endpoint ep, String name, String uid, String url, String relative)
    {
        Endpoint child = new Endpoint(name, uid, url, relative);
        ep.children.add(child);
        return child;
    }
}

/*
 
{"constellations": {"href": "https://api-sisi.testeveonline.com/constellations/"}, "itemGroups": {"href": "https://api-sisi.testeveonline.com/inventory/groups/"}, "corporations": {"href": "https://api-sisi.testeveonline.com/corporations/"}, "alliances": {"href": "https://api-sisi.testeveonline.com/alliances/"}, "itemTypes": {"href": "https://api-sisi.testeveonline.com/inventory/types/"}, "userCount": 63, "decode": {"href": "https://api-sisi.testeveonline.com/decode/"}, "marketPrices": {"href": "https://api-sisi.testeveonline.com/market/prices/"}, "opportunities": {"tasks": {"href": "https://api-sisi.testeveonline.com/opportunities/tasks/"}, "groups": {"href": "https://api-sisi.testeveonline.com/opportunities/groups/"}}, "itemCategories": {"href": "https://api-sisi.testeveonline.com/inventory/categories/"}, "regions": {"href": "https://api-sisi.testeveonline.com/regions/"}, "bloodlines": {"href": "https://api-sisi.testeveonline.com/bloodlines/"}, "marketGroups": {"href": "https://api-sisi.testeveonline.com/market/groups/"}, "systems": {"href": "https://api-sisi.testeveonline.com/solarsystems/"}, "sovereignty": {"campaigns": {"href": "https://api-sisi.testeveonline.com/sovereignty/campaigns/"}, "structures": {"href": "https://api-sisi.testeveonline.com/sovereignty/structures/"}}, "tournaments": {"href": "https://api-sisi.testeveonline.com/tournaments/"}, "virtualGoodStore": {"href": "https://sisivgs.testeveonline.com/"}, "serverVersion": "EVE-2016-HERMINE 14.08.1071672.1071672", "userCount_str": "63", "wars": {"href": "https://api-sisi.testeveonline.com/wars/"}, "incursions": {"href": "https://api-sisi.testeveonline.com/incursions/"}, "dogma": {"attributes": {"href": "https://api-sisi.testeveonline.com/dogma/attributes/"}, "effects": {"href": "https://api-sisi.testeveonline.com/dogma/effects/"}}, "races": {"href": "https://api-sisi.testeveonline.com/races/"}, "insurancePrices": {"href": "https://api-sisi.testeveonline.com/insuranceprices/"}, "authEndpoint": {"href": "https://sisilogin.testeveonline.com/oauth/token/"}, "serviceStatus": "online", "industry": {"facilities": {"href": "https://api-sisi.testeveonline.com/industry/facilities/"}, "systems": {"href": "https://api-sisi.testeveonline.com/industry/systems/"}}, "npcCorporations": {"href": "https://api-sisi.testeveonline.com/corporations/npccorps/"}, "time": {"href": "https://api-sisi.testeveonline.com/time/"}, "marketTypes": {"href": "https://api-sisi.testeveonline.com/market/types/"}, "serverName": "SINGULARITY"}

EndpointCollection:
    userCount: 63
    serverVersion: EVE-2016-HERMINE 14.08.1071672.1071672
    serverName: SINGULARITY
    serviceStatus: online
        name: 
            name: href
                uri: https://api-sisi.testeveonline.com/constellations/
        name: itemGroups
            name: href
                uri: https://api-sisi.testeveonline.com/inventory/groups/
        name: corporations
            name: href
                uri: https://api-sisi.testeveonline.com/corporations/
        name: alliances
            name: href
                uri: https://api-sisi.testeveonline.com/alliances/
        name: itemTypes
            name: href
                uri: https://api-sisi.testeveonline.com/inventory/types/
        name: decode
            name: href
                uri: https://api-sisi.testeveonline.com/decode/
        name: marketPrices
            name: href
                uri: https://api-sisi.testeveonline.com/market/prices/
        name: opportunities
            name: tasks
                uri: https://api-sisi.testeveonline.com/opportunities/tasks/
            name: groups
                uri: https://api-sisi.testeveonline.com/opportunities/groups/
        name: itemCategories
            name: href
                uri: https://api-sisi.testeveonline.com/inventory/categories/
        name: regions
            name: href
                uri: https://api-sisi.testeveonline.com/regions/
        name: bloodlines
            name: href
                uri: https://api-sisi.testeveonline.com/bloodlines/
        name: marketGroups
            name: href
                uri: https://api-sisi.testeveonline.com/market/groups/
        name: systems
            name: href
                uri: https://api-sisi.testeveonline.com/solarsystems/
        name: sovereignty
            name: campaigns
                uri: https://api-sisi.testeveonline.com/sovereignty/campaigns/
            name: structures
                uri: https://api-sisi.testeveonline.com/sovereignty/structures/
        name: tournaments
            name: href
                uri: https://api-sisi.testeveonline.com/tournaments/
        name: virtualGoodStore
            name: href
                uri: https://sisivgs.testeveonline.com/
        name: wars
            name: href
                uri: https://api-sisi.testeveonline.com/wars/
        name: incursions
            name: href
                uri: https://api-sisi.testeveonline.com/incursions/
        name: dogma
            name: attributes
                uri: https://api-sisi.testeveonline.com/dogma/attributes/
            name: effects
                uri: https://api-sisi.testeveonline.com/dogma/effects/
        name: races
            name: href
                uri: https://api-sisi.testeveonline.com/races/
        name: insurancePrices
            name: href
                uri: https://api-sisi.testeveonline.com/insuranceprices/
        name: authEndpoint
            name: href
                uri: https://sisilogin.testeveonline.com/oauth/token/
        name: industry
            name: facilities
                uri: https://api-sisi.testeveonline.com/industry/facilities/
            name: systems
                uri: https://api-sisi.testeveonline.com/industry/systems/
        name: npcCorporations
            name: href
                uri: https://api-sisi.testeveonline.com/corporations/npccorps/
        name: time
            name: href
                uri: https://api-sisi.testeveonline.com/time/
        name: marketTypes
            name: href
                uri: https://api-sisi.testeveonline.com/market/types/
 */
