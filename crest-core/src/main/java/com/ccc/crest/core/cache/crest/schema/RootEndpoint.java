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

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.client.CrestClient;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@SuppressWarnings("javadoc")
public class RootEndpoint
{
    public static final String DefaultLogPath = "/var/opt/ccc/crestj/log/rootJsonGen.log";
    public static final String LogFilePathKey = "ccc.tools.log-file-path";
    public static final String LogFileBaseKey = "ccc.tools.log-file-base";
    public static final String UidBase = "application/vnd.ccp.eve.";
    public static final String Fixme = "(TBD)";

    public Endpoints endpoints;
    public String json;

    private final String rootUrl;
    private final Logger log;

    public RootEndpoint()
    {
        String logPath = DefaultLogPath;
        System.setProperty(LogFilePathKey, logPath);
        String base = logPath;
        int idx = logPath.lastIndexOf('.');
        if (idx != -1)
            base = logPath.substring(0, idx);
        System.setProperty(LogFileBaseKey, logPath);
        log = LoggerFactory.getLogger(getClass());
        if (log.isDebugEnabled())
        {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            // TODO: StatusPrinter.setPrintStream
            StatusPrinter.print(lc);
        }
        rootUrl = CrestClient.getCrestBaseUri();
        init();
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        json = gson.create().toJson(endpoints);
    }

    private String readFile(String path) throws Exception
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
    }

    private void writeFile(File parent, Endpoint endpoint) throws Exception
    {
        String content = null;
        if(endpoint.children.size() != 0)
            content = groupPage;
        else
            content = leafPage;

       content = content.replace("$title", endpoint.uid);
       content = content.replace("$url", endpoint.url);

       String path = parent.getPath() + "/" + endpoint.name + ".md";
        PrintWriter out = new PrintWriter(path);
        out.println(content);
        out.close();
    }

    private String groupPage;
    private String leafPage;

    public void dumpTree(File fbase) throws Exception
    {
        groupPage = readFile(fbase.getPath() + "/groupPage.md");
        leafPage = readFile(fbase.getPath() + "/leafPage.md");
        traverse(endpoints.root, fbase);
    }

    private void traverse(Endpoint endpoint, File fbase) throws Exception
    {
        File nfbase = null;
        if (endpoint.relative == null)
        {
            nfbase = new File(fbase.getPath() + "/" + "root");
            nfbase.mkdirs();
        }else
        {
            String path = fbase.getPath() + endpoint.relative;
            if(path.endsWith("/0/"))
                path = path.substring(0, path.length()-3);
            nfbase = new File(path);
            nfbase.mkdirs();
        }
        if (endpoint.relative != null)
            writeFile(nfbase, endpoint);
        for (Endpoint child : endpoint.children)
            traverse(child, nfbase);
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
            root = new Endpoint("root", UidBase + "Api-v5+json", rootUrl + "/", null);
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

        @Override
        public String toString()
        {
            return "name: " + name + " uid: " + uid + " relative: " + relative + " url: " + url;
        }
    }

    public static void main(String[] args)
    {
        RootEndpoint rjg = new RootEndpoint();
        LoggerFactory.getLogger(RootEndpoint.class).info("\n" + rjg.json);
    }

    private void init()
    {
        endpoints = new Endpoints();
        Endpoint root = endpoints.root;
        addChild(root, "alliances", UidBase + "AllianceCollection", rootUrl + "/alliances/", "/alliances/");
        addChild(root, "bloodlines", UidBase + "BloodlineCollection", rootUrl + "/bloodlines/", "/bloodlines/");
        Endpoint child = addChild(root, "characters", UidBase + "Character", rootUrl + "/characters/0/", "/characters/0/");
        Endpoint gchild = addChild(child, "contacts", UidBase + "CharacterContacts", rootUrl + "/characters/0/contacts/", "/contacts/");
        addChild(gchild, "contact", UidBase + "CharacterContact", rootUrl + "/characters/0/contacts/0/", "/0/");
        gchild = addChild(child, "fittings", UidBase + "CharacterFittings", rootUrl + "/characters/0/fittings/", "/fittings/");
        addChild(gchild, "fitting", UidBase + "CharacterFitting", rootUrl + "/characters/0/fittings/0/", "/0/");
        addChild(child, "opportunities", UidBase + "CharacterOpportunities", rootUrl + "/characters/0/characterOpportunitiesRead/", "/characterOpportunitiesRead/");
        addChild(child, "waypoints", UidBase + "CharacterWaypoints", rootUrl + "/characters/0/ui/autopilot/waypoints/", "/ui/autopilot/waypoints/");
        addChild(child, "location", UidBase + "CharacterLocation", rootUrl + "/characters/0/location/", "/location/");
        addChild(child, "openWindow", UidBase + "CharacterOpenWindow", rootUrl + "/characters/0/ui/openwindow/marketdetails/", "/ui/autopilot/marketdetails/");

        addChild(root, "constellations", UidBase + "ConstellationCollection", rootUrl + "/constellations/", "/constellations/");

        child = addChild(root, "corporation", UidBase + "Corporation", rootUrl, "corporations");
        gchild = addChild(child, "corporation", UidBase + "CorporationCollection", rootUrl + "/corporations/", "");
        addChild(gchild, "npcCorporation", UidBase + "NPCCorporationsCollection", rootUrl + "/corporations/npccorps/", "/npccorps/");

        child = addChild(root, "dogma", UidBase + "Dogma", rootUrl + "/dogma/", "/dogma/");
        addChild(child, "dogma", UidBase + Fixme, rootUrl + "/dogma/attributes/", "/attributes/");
        addChild(child, "dogma", UidBase + Fixme, rootUrl + "/dogma/effects/", "/effects/");

        child = addChild(root, "fleet", UidBase + "Fleet", rootUrl + "fleets", "fleets");
        gchild = addChild(child, "fleets", UidBase + "Fleet", rootUrl + "fleets/0/", "/0/");
        Endpoint ggchild = addChild(gchild, "fleetWings", UidBase + "FleetWings", rootUrl + "/fleets/0/wings/", "/wings/");
        addChild(ggchild, "fleetSquads", UidBase + "FleetSquads" + Fixme, rootUrl + "/fleets/0/wings/0/squads/", "/squads/");
        addChild(gchild, "fleetMembers", UidBase + "FleetMembers", rootUrl + "/fleets/0/members/", "/members/");

        addChild(root, "incursions", UidBase + "IncursionCollection", rootUrl + "/incursions/", "/incursions/");

        child = addChild(root, "industry", UidBase + "Industry", rootUrl + "/industry/", "/industry/");
        addChild(child, "facility", UidBase + "IndustryFacilityCollection", rootUrl + "/industry/facility/", "/facility/");
        addChild(child, "system", UidBase + "IndustrySystemCollection", rootUrl + "/industry/system/", "/system/");

        child = addChild(root, "inventory", UidBase + "Inventory" + Fixme, rootUrl + "/inventory/", "/inventory/");
        addChild(child, "categories", UidBase + "ItemCategoryCollection", rootUrl + "/inventory/categories/", "/categories/");
        addChild(child, "groups", UidBase + "ItemGroupCollection", rootUrl + "/inventory/groups/", "/groups/");
        addChild(child, "types", UidBase + "ItemGroupCollection", rootUrl + "/inventory/types/", "/types/");

        child = addChild(root, "market", UidBase + "Market" + Fixme, rootUrl + "/market/", "/market/");
        addChild(child, "groups", UidBase + "MarketGroupCollection", rootUrl + "/market/groups/", "/groups/");
        addChild(child, "prices", UidBase + "MarketTypePriceCollection", rootUrl + "/market/prices/", "/prices/");
        addChild(child, "types", UidBase + "MarketTypeCollection", rootUrl + "/market/types/", "/types/");

        child = addChild(root, "opportunity", UidBase + "Opportunity" + Fixme, rootUrl + "/opportunities/", "/opportunities/");
        addChild(child, "groups", UidBase + "OpportunityGroupsCollection", rootUrl + "/opportunities/groups/", "/groups/");
        addChild(child, "tasks", UidBase + "OpportunityTasksCollection", rootUrl + "/opportunities/tasks/", "/tasks/");

        child = addChild(root, "planets", UidBase + "Planets", rootUrl + "/planets/", "/planets/");
        addChild(child, "planet", UidBase + "Planets", rootUrl + "/planets/0/", "/planets/0/");

        child = addChild(root, "sovereignty", UidBase + "Sovereignty" + Fixme, rootUrl + "/sovereignty/", "/sovereignty/");
        addChild(child, "campaigns", UidBase + "SovCampaignsCollection", rootUrl + "/sovereignty/campaigns/", "/campaigns/");
        addChild(child, "structures", UidBase + "SovStructureCollection", rootUrl + "/sovereignty/structure/", "/structure/");

        child = addChild(root, "stargate", UidBase + "Stargates", rootUrl + "/stargate/", "/stargate/");
        addChild(child, "stargate", UidBase + "Stargate", rootUrl + "/stargate/0/", "/stargate/0/");

        addChild(root, "races", UidBase + "RaceCollection", rootUrl + "/races/", "/races/");
        addChild(root, "regions", UidBase + "RegionCollection", rootUrl + "/regions/", "/regions/");

        child = addChild(root, "schema", UidBase + "Schema" + Fixme, rootUrl + "/schema/", "/schema/");
        addChild(child, "endpoints", UidBase + "SchemaEndpointCollection" + Fixme, rootUrl + "/schema/endpoints/", "/endpoints/");
        addChild(child, "options", UidBase + "SchemaOptionCollection", rootUrl + "/schema/options/", "/options/");

        child = addChild(root, "tournaments", UidBase + "Tournaments" + Fixme, rootUrl + Fixme, "tournaments");
        gchild = addChild(child, "tournaments", UidBase + "TournamentCollection", rootUrl + "/tournaments/", "");
        ggchild = addChild(gchild, "tournament", UidBase + "Tournament", rootUrl + "/tournaments/0/", "/0/");

        Endpoint gggchild = addChild(ggchild, "series", UidBase + "TournamentSeries", rootUrl + "/tournaments/0/series/", "/series/");
        addChild(gggchild, "bans", UidBase + "TournamentSeries", rootUrl + "/tournaments/0/series/0/bans/", "/bans/");
        Endpoint ggggchild = addChild(gggchild, "matches", UidBase + "TournamentMatchCollection" + Fixme, rootUrl + "/tournaments/0/series/0/matches/", "/matches/");
        Endpoint gggggchild = addChild(ggggchild, "match", UidBase + "TournamentMatch" + Fixme, rootUrl + "/tournaments/0/series/0/matches/0/", "/0/");
        addChild(gggggchild, "static", UidBase + "TournamentMatchStatic" + Fixme, rootUrl + "/tournaments/0/series/0/matches/0/static/", "/static/");
        addChild(gggggchild, "pilotStats", UidBase + "TournamentMatchPilotStats" + Fixme, rootUrl + "/tournaments/0/series/0/matches/0/pilotstats/", "/pilotstats/");
        Endpoint ggggggchild = addChild(gggggchild, "realtime", UidBase + "TournamentMatchRealtime" + Fixme, rootUrl + "/tournaments/0/series/0/matches/0/realtime/", "/realtime/");
        addChild(ggggggchild, "pilotStats", UidBase + "TournamentMatchRealtime" + Fixme, rootUrl + "/tournaments/0/series/0/matches/0/realtime/0/", "/0/");

        gggchild = addChild(ggchild, "teams", UidBase + "TournamentTeams" + Fixme, rootUrl + "/tournaments/0/teams/", "/teams/");
        addChild(gggchild, "team", UidBase + "TournamentTeam" + Fixme, rootUrl + "/tournaments/0/teams/0/", "/0/");

        ggchild = addChild(gchild, "teams", UidBase + "TournamentTeams2" + Fixme, rootUrl + "/tournaments/teams/", "/teams/");
        gggchild = addChild(ggchild, "team", UidBase + "TournamentTeam2" + Fixme, rootUrl + "/tournaments/teams/0/", "/0/");
        addChild(gggchild, "members", UidBase + "TournamentTeam2Members" + Fixme, rootUrl + "/tournaments/teams/0/members/", "/members/");

        addChild(root, "solarsystems", UidBase + "SystemCollection", rootUrl + "/solarsystems/", "/solarsystems/");
        addChild(root, "time", UidBase + "Time", rootUrl + "/time/", "/time/");
        addChild(root, "decode", UidBase + "TokenDecode", rootUrl + "/decode/", "/decode/");

        addChild(root, "virtualGoodStore", UidBase + "VirtualGoodStore", rootUrl + "/virtualGoodStore/", "/virtualGoodStore/");
        child = addChild(root, "wars", UidBase + "Wars", rootUrl + "/wars/", "/wars/");
        addChild(child, "war", UidBase + "War", rootUrl + "/wars/0/", "/wars/0/");

    }

    private Endpoint addChild(Endpoint ep, String name, String uid, String url, String relative)
    {
        Endpoint child = new Endpoint(name, uid, url, relative);
        ep.children.add(child);
        return child;
    }
}
