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
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.client.CrestClient;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@SuppressWarnings("javadoc")
public class RootEndpoint
{
    public static final String HomeBase = "/wsp/";
    public static final String WorkBase = "/wsc/";
    public static final String TemplateBase = HomeBase + "eveonline-third-party-documentation/docs/crest/root/";
    public static final String TechDocTemplate = TemplateBase + "technicalPage.md";
    public static final String GroupDocTemplate = TemplateBase + "groupPage.md";
    public static final String LeafDocTemplate = TemplateBase + "leafPage.md";

    public static final String DefaultLogPath = "/var/opt/ccc/crestj/log/rootJsonGen.log";
    public static final String LogFilePathKey = "ccc.tools.log-file-path";
    public static final String LogFileBaseKey = "ccc.tools.log-file-base";
    public static final String UidBase = "application/vnd.ccp.eve.";
    public static final String Fixme = "(TBD)";

    public Endpoints endpoints;
    public String json;

    private final Logger log;
    private final String rootUrl;
    private final String groupPage;
    private final String leafPage;
    private volatile StringBuilder techPage;
    private final AtomicInteger totalLeafs;
    private final AtomicInteger totalGroups;
    private final AtomicInteger totalUids;
    
    public RootEndpoint() throws Exception
    {
        String logPath = DefaultLogPath;
        System.setProperty(LogFilePathKey, logPath);
        String base = logPath;
        int idx = logPath.lastIndexOf('.');
        if (idx != -1)
            base = logPath.substring(0, idx);
        System.setProperty(LogFileBaseKey, base);
        log = LoggerFactory.getLogger(getClass());
        if (log.isDebugEnabled())
        {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            StatusPrinter.print(lc);
        }
        rootUrl = CrestClient.getCrestBaseUri();
        init();
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        json = gson.create().toJson(endpoints);

        groupPage = readFile(GroupDocTemplate);
        leafPage = readFile(LeafDocTemplate);
        techPage = new StringBuilder(readFile(TechDocTemplate));
        totalLeafs = new AtomicInteger();
        totalGroups = new AtomicInteger();
        totalUids = new AtomicInteger();
    }

    private String readFile(String path) throws Exception
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
    }

    private void pingUrl(Endpoint endpoint) throws SourceFailureException
    {
        try
        {
            log.info("\n"+endpoint.toString());
            DataCache dataCache = CrestController.getCrestController().dataCache;
            CrestOptions copts = dataCache.getOptions(endpoint.url);
        }catch(Exception e)
        {
            log.error("failed: ", e);
        }
    }
    
    private void writeFile(File parent, Endpoint endpoint, AtomicInteger level) throws Exception
    {
        StringBuilder groupContent = null;
        StringBuilder leafContent = null;
        if (endpoint.type == Type.Group || endpoint.type == Type.Mixed)
        {
            groupContent = new StringBuilder(groupPage);
            if (endpoint.type == Type.Mixed)
            {
                leafContent = new StringBuilder(leafPage);
                groupContent.append("* ");
                getRef(groupContent, parent.getPath(), endpoint.name, endpoint.ruid);
            }

            for (int i = 0; i < endpoint.children.size(); i++)
            {
                Endpoint ep = endpoint.children.get(i);
                groupContent.append("* ");
                getRef(groupContent, parent.getPath(), ep.name, ep.ruid);
            }
        } else
            leafContent = new StringBuilder(leafPage);

//        String uid = endpoint.ruid.substring(12);
        int count = 0;
        String leafPage = null;
        String groupPage = null;
        String leafPath = null;
        String groupPath = null;
        switch(endpoint.type)
        {
            case Group:
                groupPage = groupContent.toString();
                doReplacements(groupPage, endpoint);
                groupPath = parent.getPath() + "/" + endpoint.name + "Group.md";
                totalGroups.incrementAndGet();
                break;
            case Leaf:
                leafPage = leafContent.toString();
                doReplacements(leafPage, endpoint);
                leafPath = parent.getPath() + "/" + endpoint.name + ".md";
                totalLeafs.incrementAndGet();
                break;
            case Mixed:
                groupPage = groupContent.toString();
                doReplacements(groupPage, endpoint);
                groupPath = parent.getPath() + "/" + endpoint.name + "Group.md";
                leafPage = leafContent.toString();
                doReplacements(leafPage, endpoint);
                leafPath = parent.getPath() + "/" + endpoint.name + ".md";
                totalGroups.incrementAndGet();
                totalLeafs.incrementAndGet();
                break;
            default:
                throw new Exception("endpoint.type is null");
        }
        if(leafPath != null)
        {
            PrintWriter out = new PrintWriter(leafPath);
            out.println(leafPage);
            out.close();
        }
        if(groupPath != null)
        {
            PrintWriter out = new PrintWriter(groupPath);
            out.println(groupPage);
            out.close();
        }
        
        techPage.append("* ");
        for (int i = 0; i < level.get(); i++)
            techPage.append("......");
        getRef(techPage, parent.getPath(), endpoint.name, endpoint.ruid);
    }

    private String doReplacements(String value, Endpoint endpoint)
    {
        value = value.replace("$title", endpoint.name);
        value.replace("$url", endpoint.url == null ? "n/a" : endpoint.url);
        value.replace("$cuid", endpoint.cuid == null ? "n/a" : endpoint.cuid);
        value.replace("$ruid", endpoint.ruid == null ? "n/a" : endpoint.ruid);
        value.replace("$uuid", endpoint.uuid == null ? "n/a" : endpoint.uuid);
        value.replace("$duid", endpoint.duid == null ? "n/a" : endpoint.duid);
        if(endpoint.cuid != null)
            totalUids.incrementAndGet();
        if(endpoint.ruid != null)
            totalUids.incrementAndGet();
        if(endpoint.uuid != null)
            totalUids.incrementAndGet();
        if(endpoint.duid != null)
            totalUids.incrementAndGet();
        return value;
    }
    
    private String getRef(StringBuilder sb, String parentPath, String endpointName, String endpointUid)
    {
        String ref = parentPath + "/" + endpointName + ".md";
        ref = ref.replace('\\', '/');
        int idx = ref.indexOf("/root/");
        idx += 6;
        ref = ref.substring(idx);
        idx = ref.indexOf('/') + 1;
        ref = ref.substring(idx);
        //@formatter:off
        sb
            .append("[").append(endpointName).append("](")
            .append(ref).append(")")
            .append(" `").append(endpointUid).append("`\n");
        //@formatter:on
        return sb.toString();
    }

    public void dumpTree(File fbase) throws Exception
    {
        AtomicInteger level = new AtomicInteger(-2);
        traverse(endpoints.root, fbase, level);

        String path = fbase.getPath() + "/root/" + "technical.md";
        PrintWriter out = new PrintWriter(path);
        out.println(techPage);
        out.close();
        log.info("\ntotalGroups: " + totalGroups.get() + "\ntotalLeafs: " + totalLeafs.get() + "\ntotalUids: " + totalUids.get());
    }

    private void traverse(Endpoint endpoint, File fbase, AtomicInteger level) throws Exception
    {
        level.incrementAndGet();
        File nfbase = null;
        if (endpoint.relative == null)
        {
            nfbase = new File(fbase.getPath() + "/" + "root");
            nfbase.mkdirs();
        } else
        {
            String path = fbase.getPath() + endpoint.relative;
            if (path.endsWith("/0/"))
                path = path.substring(0, path.length() - 3);
            path = checkCutUi(path);

            nfbase = new File(path);
            nfbase.mkdirs();
        }
        if (endpoint.relative != null)
        {
            writeFile(nfbase, endpoint, level);
            pingUrl(endpoint);
        }
        for (Endpoint child : endpoint.children)
            traverse(child, nfbase, level);
        level.decrementAndGet();
    }

    private String checkCutUi(String path)
    {
        path = path.replace('\\', '/');
        int count = 0;
        do
        {
            String remove = "ui/autopilot/";
            if (count == 1)
                remove = "ui/openwindow/";
            if (count > 1)
                break;
            ++count;
            int idx = path.indexOf(remove);
            if (idx == -1)
                continue;
            String base = path.substring(0, idx);
            String end = path.substring(idx + remove.length());
            return base + end;
        } while (count < 2);
        return path;
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
            root = new Endpoint("root", UidBase + "Api-v5+json", null, null, null, rootUrl + "/", null, Type.Leaf);
        }
    }

//    Create = PUT with a new URI
//             POST to a base URI returning a newly created URI
//           Read   = GET
//           Update = PUT with an existing URI
//           Delete = DELETE
//    CRUD for PUT, think relative uri  POST think full url.    
           
    public class Endpoint
    {
        public final String name;
        public final String cuid;   // POST's assigned here
        public final String ruid;   // GET's assigned here
        public final String uuid;   // PUT's assigned here
        public final String duid;   // DELETE's assigned here
        public final String url;
        public final String relative;
        public final Type type;
        public final List<Endpoint> children;

        //@formatter:off
        public Endpoint(
            String name, 
            String cuid, String ruid, String uuid, String duid, 
            String url, String relative, Type type)
        //@formatter:on
        {
            this.name = name;
            this.cuid = cuid;
            this.ruid = ruid;
            this.uuid = uuid;
            this.duid = duid;
            this.url = url;
            this.relative = relative;
            this.type = type;
            children = new ArrayList<>();
        }

        @Override
        public String toString()
        {
            return "name: " + name + " type: " + type.name() + " uid: " + ruid + " relative: " + relative + " url: " + url;
        }
    }

    public static void main(String[] args)
    {
        RootEndpoint rjg = null;
        try
        {
            rjg = new RootEndpoint();
            LoggerFactory.getLogger(RootEndpoint.class).info("\n" + rjg.json);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void init()
    {
        endpoints = new Endpoints();
        Endpoint root = endpoints.root;
        //@formatter:off
        addChild(                             root, "alliances",        UidBase + "AllianceCollection-v2+json",             rootUrl + "/alliances/",                                   "/alliances/");
        addChild(                             root, "bloodlines",       UidBase + "BloodlineCollection-v2+json",            rootUrl + "/bloodlines/",                                  "/bloodlines/");
        Endpoint child = addChild(            root, "characters",       UidBase + "CharacterSearch-v1+json",                rootUrl + "/characters/",                                  "/characters/", Type.Group);
        Endpoint gchild = addChild(          child, "contacts",         UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactCollection-v2+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/contacts/",                    "/contacts/", Type.Mixed);
        addChild(                           gchild, "contact",          null,
                                                                        null, 
                                                                        UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactDelete-v1+json",                  rootUrl + "/characters/1364371482/contacts/0/",                  "/0/", Type.Leaf);
        gchild = addChild(                   child, "fittings",         UidBase + "FittingCreate-v1+json",
                                                                        UidBase + "FittingCollection-v1+json",
                                                                        null, 
                                                                        null,                                               rootUrl + "/characters/1364371482/fittings/",                    "/fittings/", Type.Mixed);
        addChild(                           gchild, "fitting",          null,
                                                                        UidBase + "Fitting-v1+json",
                                                                        null,
                                                                        UidBase + "FittingDelete-v1+json",                  rootUrl + "/characters/1364371482/fittings/0/",                  "/0/", Type.Leaf);
//        addChild(               child, "characterOpportunitiesRead",    UidBase + "CharacterOpportunities",                 rootUrl + "/characters/1364371482/characterOpportunitiesRead/",  "/characterOpportunitiesRead/");
        
        addChild(                            child, "waypoints",        UidBase + "PostWaypoint-v1+json",
                                                                        null,
                                                                        null, 
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/autopilot/waypoints/",      "/ui/autopilot/waypoints/", Type.Leaf);
        addChild(                            child, "location",         UidBase + "CharacterLocation-v1+json",              rootUrl + "/characters/1364371482/location/",                    "/location/");
        
        addChild(                            child, "marketdetails",    UidBase + "ShowMarketDetails-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/openwindow/marketdetails/", "/ui/openwindow/marketdetails/", Type.Leaf);
        addChild(                             root, "constellations",   UidBase + "ConstellationCollection-v1+json",        rootUrl + "/constellations/",                              "/constellations/");
        child = addChild(                     root, "corporation",      UidBase + "Corporation"+Fixme,                      rootUrl + "/corporations/",                                "/corporations/", Type.Mixed);
        gchild = addChild(                   child, "corporation",      UidBase + "CorporationCollection",                  rootUrl + "/corporations/",                                "");
        addChild(                           gchild, "npcCorporation",   UidBase + "NPCCorporationsCollection-v1+json",      rootUrl + "/corporations/npccorps/",                       "/npccorps/");
//        child = addChild(                     root, "dogma",            UidBase + "Dogma",                                  rootUrl + "/dogma/",                                       "/dogma/", Type.Group);
        addChild(                            child, "attributes",       UidBase + "DogmaAttributeCollection-v1+json",       rootUrl + "/dogma/attributes/",                            "/attributes/");
        addChild(                            child, "effects",          UidBase + "DogmaEffectCollection-v1+json",          rootUrl + "/dogma/effects/",                               "/effects/");
//        child = addChild(                     root, "fleet",            UidBase + "Fleet",                                  rootUrl + "/fleets/",                                      "/fleets/", Type.Group);
        child = addChild(                     root, "fleets",           null,
                                                                        UidBase + "Fleet-v1+json",
                                                                        UidBase + "FleetUpdate-v1+json",
                                                                        null,                                               rootUrl + "/fleets/0/",                                    "/0/", Type.Leaf);
        gchild = addChild(                   child, "fleetWings",       UidBase + "FleetWingCreate-v1+json",
                                                                        UidBase + "FleetWings-v1+json",
                                                                        null, 
                                                                        null,                                               rootUrl + "/fleets/0/wings/",                              "/wings/", Type.Mixed);
        addChild(                           gchild, "fleetSquads",      UidBase + "FleetSquadCreate-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/wings/0/squads/",                     "/squads/", Type.Leaf);
        addChild(                           gchild, "fleetMembers",     UidBase + "FleetMemberInvite-v1+json",
                                                                        UidBase + "FleetMembers-v1+json",
                                                                        null, 
                                                                        null,                                               rootUrl + "/fleets/0/members/",                            "/members/", Type.Leaf);
        addChild(                             root, "incursions",       UidBase + "IncursionCollection-v1+json",            rootUrl + "/incursions/",                                  "/incursions/");
//        child = addChild(                     root, "industry",         UidBase + "Industry",                               rootUrl + "/industry/",                                    "/industry/", Type.Group);
        addChild(                             root, "facility",         UidBase + "IndustryFacilityCollection-v1+json",     rootUrl + "/industry/facilities/",                           "/facilities/");
        addChild(                             root, "system",           UidBase + "IndustrySystemCollection-v1+json",       rootUrl + "/industry/systems/",                             "/systems/");
//        child = addChild(                     root, "inventory",        UidBase + "Inventory" + Fixme,                      rootUrl + "/inventory/",                                   "/inventory/", Type.Group);
        addChild(                             root, "categories",       UidBase + "ItemCategoryCollection-v1+json",         rootUrl + "/inventory/categories/",                        "/categories/");
        addChild(                             root, "groups",           UidBase + "ItemGroupCollection-v1+json",            rootUrl + "/inventory/groups/",                            "/groups/");
        addChild(                             root, "types",            UidBase + "ItemTypeCollection-v1+json",             rootUrl + "/inventory/types/",                             "/types/");
//        child = addChild(                     root, "market",           UidBase + "Market" + Fixme,                         rootUrl + "/market/",                                      "/market/", Type.Group);
        addChild(                             root, "groups",           UidBase + "MarketGroupCollection-v1+json",          rootUrl + "/market/groups/",                               "/groups/");
        addChild(                             root, "prices",           UidBase + "MarketTypePriceCollection-v1+json",      rootUrl + "/market/prices/",                               "/prices/");
        addChild(                             root, "types",            UidBase + "MarketTypeCollection-v1+json",           rootUrl + "/market/types/",                                "/types/");
//        child = addChild(                     root, "opportunity",      UidBase + "Opportunity" + Fixme,                    rootUrl + "/opportunities/",                               "/opportunities/", Type.Group);
        addChild(                             root, "groups",           UidBase + "OpportunityGroupsCollection-v1+json",    rootUrl + "/opportunities/groups/",                        "/groups/");
        addChild(                             root, "tasks",            UidBase + "OpportunityTasksCollection-v1+json",     rootUrl + "/opportunities/tasks/",                         "/tasks/");
//        child = addChild(                     root, "planetsGroup",     UidBase + "PlanetsGroup",                           rootUrl + "/planets/",                                     "/planets/", Type.Mixed);
//        addChild(                            root, "planets",          UidBase + "Planets",                                rootUrl + "/planets/",                                     "");
        addChild(                            root, "planet",           UidBase + "Planet-v2+json",                         rootUrl + "/planets/0/",                                   "/0/");
//        child = addChild(                     root, "sovereignty",      UidBase + "Sovereignty" + Fixme,                    rootUrl + "/sovereignty/",                                 "/sovereignty/", Type.Group);
        addChild(                             root, "campaigns",        UidBase + "SovCampaignsCollection-v1+json",         rootUrl + "/sovereignty/campaigns/",                       "/campaigns/");
        addChild(                             root, "structures",       UidBase + "SovStructureCollection-v1+json",         rootUrl + "/sovereignty/structures/",                      "/structures/");
//        child = addChild(                     root, "stargateGroup",    UidBase + "Stargates",                              rootUrl + "/stargate/",                                    "/stargate/", Type.Mixed);
        addChild(                             root, "stargate",         UidBase + "Stargate-v1+json",                       rootUrl + "/stargates/0/",                                 "/stargates/0/");
        addChild(                             root, "races",            UidBase + "RaceCollection-v3+json",                 rootUrl + "/races/",                                       "/races/");
        addChild(                             root, "regions",          UidBase + "RegionCollection-v1+json",               rootUrl + "/regions/",                                     "/regions/");
//        child = addChild(                     root, "schema",           UidBase + "Schema" + Fixme,                         rootUrl + "/schema/",                                      "/schema/", Type.Group);
//        addChild(                            child, "endpoints",        UidBase + "SchemaEndpointCollection" + Fixme,       rootUrl + "/schema/endpoints/",                            "/endpoints/");
//        addChild(                            child, "options",          UidBase + "SchemaOptionCollection",                 rootUrl + "/schema/options/",                              "/options/");
        child = addChild(                     root, "tournaments",      UidBase + "TournamentCollection-v1+json" + Fixme,   rootUrl + "/tournaments/",                                  "/tournaments/");
        gchild = addChild(                   child, "tournaments",      UidBase + "TournamentCollection-v1+json",           rootUrl + "/tournaments/",                                 "", Type.Mixed);
        Endpoint ggchild = addChild(        gchild, "tournament",       UidBase + "Tournament-v1+json",                     rootUrl + "/tournaments/0/",                               "/0/");
        Endpoint gggchild = addChild(      ggchild, "series",           UidBase + "TournamentSeriesCollection-v1+json",     rootUrl + "/tournaments/0/series/",                        "/series/", Type.Group);
//        addChild(                         gggchild, "bans",             UidBase + "TournamentSeries",                       rootUrl + "/tournaments/0/series/0/bans/",                  "/bans/");
        Endpoint ggggchild = addChild(    gggchild, "matches",          UidBase + "TournamentMatchCollection-v1+json",      rootUrl + "/tournaments/0/series/0/matches/",              "/matches/", Type.Group);
        Endpoint gggggchild = addChild(  ggggchild, "match",            UidBase + "TournamentMatch-v1+json",                rootUrl + "/tournaments/0/series/0/matches/0/",            "/0/");
        addChild(                       gggggchild, "static",           UidBase + "TournamentStaticSceneData-v2+json",      rootUrl + "/tournaments/0/series/0/matches/0/static/",     "/static/", Type.Group);
        addChild(                       gggggchild, "pilotStats",       UidBase + "TournamentPilotStatsCollection-v1+json", rootUrl + "/tournaments/0/series/0/matches/0/pilotstats/", "/pilotstats/");
//        Endpoint ggggggchild = addChild(gggggchild, "realtime",         UidBase + "TournamentMatchRealtime" + Fixme,        rootUrl + "/tournaments/0/series/0/matches/0/realtime/",   "/realtime/", Type.Mixed);
        addChild(                      gggggchild, "pilotStats",       UidBase + "TournamentRealtimeMatchFrame-v2+json",   rootUrl + "/tournaments/0/series/0/matches/0/realtime/0/", "/0/");
//        gggchild = addChild(               ggchild, "teams",            UidBase + "TournamentTeams" + Fixme,                rootUrl + "/tournaments/0/teams/",                         "/teams/", Type.Mixed);
        addChild(                         gggchild, "team",             UidBase + "TournamentTeam-v1+json",                 rootUrl + "/tournaments/0/teams/0/",                       "/0/");
        ggchild = addChild(                 gchild, "teams",            UidBase + "TournamentTeamCollection-v1+json",       rootUrl + "/tournaments/teams/",                           "/teams/", Type.Group);
        gggchild = addChild(               ggchild, "team",             UidBase + "TournamentTeam-v1+json",                 rootUrl + "/tournaments/teams/0/",                         "/0/");
        addChild(                         gggchild, "members",          UidBase + "TournamentTeamMemberCollection-v1+json", rootUrl + "/tournaments/teams/0/members/",                 "/members/");
        addChild(                             root, "solarsystems",     UidBase + "SystemCollection-v1+json",               rootUrl + "/solarsystems/",                                "/solarsystems/");
        addChild(                             root, "time",             UidBase + "Time-v1+json",                           rootUrl + "/time/",                                        "/time/");
        addChild(                             root, "decode",           UidBase + "TokenDecode-v1+json",                    rootUrl + "/decode/",                                      "/decode/");
//        addChild(                             root, "virtualGoodStore", UidBase + "VirtualGoodStore",                       rootUrl + "/virtualGoodStore/",                            "/virtualGoodStore/");
        child = addChild(                     root, "wars",             UidBase + "WarsCollection-v1+json",                 rootUrl + "/wars/",                                        "/wars/", Type.Mixed);
        addChild(                            child, "war",              UidBase + "War-v1+json",                            rootUrl + "/wars/0/",                                      "/wars/0/");
        //@formatter:on
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative)
    {
        return addChild(ep, name, ruid, url, relative, Type.Leaf);
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative, Type type)
    {
        return addChild(ep, name, null, ruid, null, null, url, relative, Type.Leaf);
    }

    private Endpoint addChild(Endpoint ep, String name, String cuid,  String ruid, String uuid, String duid,String url, String relative, Type type)
    {
        Endpoint child = new Endpoint(name, cuid, ruid, uuid, duid, url, relative, type);
        ep.children.add(child);
        return child;
    }

    private enum Type
    {
        Group, Leaf, Mixed
    };
}
