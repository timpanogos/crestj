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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@SuppressWarnings("javadoc")
public class RootJsonGenerator
{
    public static final String DefaultLogPath = "/var/opt/ccc/crestj/log/rootJsonGen.log";
    public static final String LogFilePathKey = "ccc.tools.log-file-path";
    public static final String LogFileBaseKey = "ccc.tools.log-file-base";
    private final boolean UseSisi = true;
    public static final String UidBase = "application/vnd.ccp.eve.";
    public static final String SisiUrlBase =  "https://api-sisi.testeveonline.com";
    public static final String TqUrlBase =  "https://crest-tq.eveonline.com";
    public static final String Fixme = "(TBD)";
    public Endpoints endpoints;
    public String json;

    private final String rootUrl;
    private final Logger log;

    public RootJsonGenerator()
    {
        String logPath = DefaultLogPath;
        System.setProperty(LogFilePathKey, logPath);
        String base = logPath;
        int idx = logPath.lastIndexOf('.');
        if(idx != -1)
            base = logPath.substring(0, idx);
        System.setProperty(LogFileBaseKey, logPath);
        log = LoggerFactory.getLogger(getClass());
        if(log.isDebugEnabled())
        {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            // TODO: StatusPrinter.setPrintStream
            StatusPrinter.print(lc);
        }
        if(UseSisi)
            rootUrl = SisiUrlBase;
        else
            rootUrl = TqUrlBase;
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
            root = new Endpoint("root", UidBase+"Api-v5+json", rootUrl+"/", "");
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
        addChild(root, "alliances", UidBase+"AllianceCollection", rootUrl+"/alliances/", "/alliances/");
        addChild(root, "bloodlines", UidBase+"BloodlineCollection", rootUrl+"/bloodlines/", "/bloodlines/");
        addChild(root, "character", UidBase+"Character", rootUrl, Fixme);
        addChild(root, "constellations", UidBase+"ConstellationCollection", rootUrl+"/constellations/", "/constellations/");

        Endpoint child = addChild(root, "corporation", UidBase+"Corporation", rootUrl, "");
        Endpoint gchild = addChild(child, "corporation", UidBase+"CorporationCollection", rootUrl+"/corporations/", "/corporations/");
        addChild(gchild, "npcCorporation", UidBase+"NPCCorporationsCollection", rootUrl+"/corporations/npccorps/", "/npccorps/");

        child = addChild(root, "dogma", UidBase+"Dogma", rootUrl+"/dogma/", "/dogma/");
        addChild(child, "dogma", UidBase+Fixme, rootUrl+"/dogma/attributes/", "/attributes/");
        addChild(child, "dogma", UidBase+Fixme, rootUrl+"/dogma/effects/", "/effects/");

        child = addChild(root, "fleet", UidBase+"Fleet", rootUrl+"fleets", "");
        gchild = addChild(child, "fleets", UidBase+"Fleet", rootUrl+"fleets/0/", "/0/");
        Endpoint ggchild = addChild(gchild, "fleetWings", UidBase+"FleetWings", rootUrl+"/fleets/0/wings/", "/wings/");
        addChild(ggchild, "fleetSquads", UidBase+"FleetSquads"+Fixme, rootUrl+"/fleets/0/wings/0/squads/", "/squads/");
        addChild(gchild, "fleetMembers", UidBase+"FleetMembers", rootUrl+"/fleets/0/members/", "/members/");

        addChild(root, "incursions", UidBase+"IncursionCollection", rootUrl+"/incursions/", "/incursions/");

        child = addChild(root, "industry", UidBase+"Industry", rootUrl + "/industry/", "/industry/");
        addChild(child, "facility", UidBase+"IndustryFacilityCollection", rootUrl+"/industry/facility/", "/facility/");
        addChild(child, "system", UidBase+"IndustrySystemCollection", rootUrl+"/industry/system/", "/system/");

        child = addChild(root, "inventory", UidBase+"Inventory"+Fixme, rootUrl + "/inventory/", "/inventory/");
        addChild(child, "categories", UidBase+"ItemCategoryCollection", rootUrl+"/inventory/categories/", "/categories/");
        addChild(child, "groups", UidBase+"ItemGroupCollection", rootUrl+"/inventory/groups/", "/groups/");
        addChild(child, "types", UidBase+"ItemGroupCollection", rootUrl+"/inventory/types/", "/types/");

        child = addChild(root, "market", UidBase+"Market"+Fixme, rootUrl + "/market/", "/market/");
        addChild(child, "groups", UidBase+"MarketGroupCollection", rootUrl+"/market/groups/", "/groups/");
        addChild(child, "prices", UidBase+"MarketTypePriceCollection", rootUrl+"/market/prices/", "/prices/");
        addChild(child, "types", UidBase+"MarketTypeCollection", rootUrl+"/market/types/", "/types/");

        child = addChild(root, "opportunity", UidBase+"Opportunity"+Fixme, rootUrl + "/opportunities/", "/opportunities/");
        addChild(child, "groups", UidBase+"OpportunityGroupsCollection", rootUrl+"/opportunities/groups/", "/groups/");
        addChild(child, "tasks", UidBase+"OpportunityTasksCollection", rootUrl+"/opportunities/tasks/", "/tasks/");

        child = addChild(root, "sovereignty", UidBase+"Sovereignty"+Fixme, rootUrl + "/sovereignty/", "/sovereignty/");
        addChild(child, "campaigns", UidBase+"SovCampaignsCollection", rootUrl+"/sovereignty/campaigns/", "/campaigns/");
        addChild(child, "structures", UidBase+"SovStructureCollection", rootUrl+"/sovereignty/structure/", "/structure/");

        addChild(root, "races", UidBase+"RaceCollection", rootUrl+"/races/", "/races/");
        addChild(root, "regions", UidBase+"RegionCollection", rootUrl+"/regions/", "/regions/");

        child = addChild(root, "schema", UidBase+"Schema"+Fixme, rootUrl + "/schema/", "/schema/");
        addChild(child, "endpoints", UidBase+"SchemaEndpointCollection"+Fixme, rootUrl+"/schema/endpoints/", "/endpoints/");
        addChild(child, "options", UidBase+"SchemaOptionCollection", rootUrl+"/schema/options/", "/options/");

        child = addChild(root, "tournaments", UidBase+"Tournaments"+Fixme, rootUrl + Fixme, "");
        gchild = addChild(child, "tournaments", UidBase+"TournamentCollection", rootUrl+"/tournaments/", "/tournaments/");
        ggchild = addChild(gchild, "tournament", UidBase+"Tournament", rootUrl+"/tournaments/0/", "/0/");

        Endpoint gggchild = addChild(ggchild, "series", UidBase+"TournamentSeries", rootUrl+"/tournaments/0/series/", "/series/");
        addChild(gggchild, "bans", UidBase+"TournamentSeries", rootUrl+"/tournaments/0/series/0/bans/", "/bans/");
        Endpoint ggggchild = addChild(gggchild, "matches", UidBase+"TournamentMatchCollection"+Fixme, rootUrl+"/tournaments/0/series/0/matches/", "/matches/");
        Endpoint gggggchild = addChild(ggggchild, "match", UidBase+"TournamentMatch"+Fixme, rootUrl+"/tournaments/0/series/0/matches/0/", "/0/");
        addChild(gggggchild, "static", UidBase+"TournamentMatchStatic"+Fixme, rootUrl+"/tournaments/0/series/0/matches/0/static/", "/static/");
        addChild(gggggchild, "pilotStats", UidBase+"TournamentMatchPilotStats"+Fixme, rootUrl+"/tournaments/0/series/0/matches/0/pilotstats/", "/pilotstats/");
        Endpoint ggggggchild = addChild(gggggchild, "realtime", UidBase+"TournamentMatchRealtime"+Fixme, rootUrl+"/tournaments/0/series/0/matches/0/realtime/", "/realtime/");
        addChild(ggggggchild, "pilotStats", UidBase+"TournamentMatchRealtime"+Fixme, rootUrl+"/tournaments/0/series/0/matches/0/realtime/0/", "/0/");

        gggchild = addChild(ggchild, "teams", UidBase+"TournamentTeams"+Fixme, rootUrl+"/tournaments/0/teams/", "/teams/");
        addChild(gggchild, "team", UidBase+"TournamentTeam"+Fixme, rootUrl+"/tournaments/0/teams/0/", "/0/");

        ggchild = addChild(gchild, "teams", UidBase+"TournamentTeams2"+Fixme, rootUrl+"/tournaments/teams/", "/teams/");
        gggchild = addChild(ggchild, "team", UidBase+"TournamentTeam2"+Fixme, rootUrl+"/tournaments/teams/0/", "/0/");
        addChild(gggchild, "members", UidBase+"TournamentTeam2Members"+Fixme, rootUrl+"/tournaments/teams/0/members/", "/members/");

        addChild(root, "solarsystems", UidBase+"SystemCollection", rootUrl+"/solarsystems/", "/solarsystems/");
        addChild(root, "time", UidBase+"Time", rootUrl+"/time/", "/time/");
        addChild(root, "decode", UidBase+"BloodlineCollection", rootUrl+"/decode/", "/decode/");

        addChild(root, "virtualGoodStore", UidBase+"BloodlineCollection", rootUrl+"/virtualGoodStore/", "/virtualGoodStore/");
        addChild(root, "wars", UidBase+"BloodlineCollection", rootUrl+"/wars/", "/wars/");

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
