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
import java.io.FileNotFoundException;
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
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.EveJsonData;
import com.ccc.crest.core.cache.crest.alliance.AllianceCollection;
import com.ccc.crest.core.cache.crest.bloodline.BloodlineCollection;
import com.ccc.crest.core.cache.crest.character.CharacterSearch;
import com.ccc.crest.core.cache.crest.character.ContactCollection;
import com.ccc.crest.core.cache.crest.schema.endpoint.EndpointCollection;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.tools.StrH;
import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@SuppressWarnings("javadoc")
public class RootEndpoint
{
    public static final boolean PingOptions = true;
    public static final boolean PingGets = false;
    public static final String HomeBase = "/wsp/";
    public static final String WorkBase = "/wsc/";
    public static final String TemplateBase = WorkBase + "eveonline-third-party-documentation/docs/crest/root/";
    public static final String TechDocTemplate = TemplateBase + "technicalPage.md";
    public static final String GroupDocTemplate = TemplateBase + "groupPage.md";
    public static final String LeafDocTemplate = TemplateBase + "leafPage.md";

    public static final String DefaultLogPath = "/var/opt/ccc/crestj/log/rootJsonGen.log";
    public static final String LogFilePathKey = "ccc.tools.log-file-path";
    public static final String LogFileBaseKey = "ccc.tools.log-file-base";
    //    public static final String UidBase = "application/vnd.ccp.eve.";
    public static final String UidBase = "vnd.ccp.eve.";
    public static final String Fixme = "(TBD)";

    public Endpoints endpoints;
    public String jsonStr;

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

        groupPage = readFile(GroupDocTemplate);
        leafPage = readFile(LeafDocTemplate);
        techPage = new StringBuilder(readFile(TechDocTemplate));
        totalLeafs = new AtomicInteger();
        totalGroups = new AtomicInteger();
        totalUids = new AtomicInteger();
    }

    public void dumpTree(File fbase) throws Exception
    {
        AtomicInteger level = new AtomicInteger(-2);
        traverse(endpoints.root, fbase, level);

        String path = fbase.getPath() + "/root/" + "technical.md";
        PrintWriter out = new PrintWriter(path);
        out.println(techPage);
        out.close();
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        jsonStr = gson.create().toJson(endpoints);
        log.info("\n" + jsonStr + "\n");
        log.info("\ntotalGroups: " + totalGroups.get() + "\ntotalLeafs: " + totalLeafs.get() + "\ntotalUids: " + totalUids.get());
    }


    private void writeFile(File parent, Endpoint endpoint, AtomicInteger level) throws Exception
    {
        String parentPath = parent.getPath().replace('\\', '/');
        switch (endpoint.type)
        {
            case Group:
                writeGroup(parentPath, endpoint);
                getRef(techPage, parentPath, endpoint, level, false);
                break;
            case Leaf:
                writeLeaf(parentPath, endpoint);
                getRef(techPage, parentPath, endpoint, level, false);
                break;
            case Mixed:
                writeGroup(parentPath, endpoint);
//                getRef(techPage, parentPath, endpoint, level, false);
                writeLeaf(parentPath, endpoint);
                getRef(techPage, parentPath, endpoint, level, false);
                break;
            case PlaceHolder:
                techPage.append("* ");
                for (int j = 0; j < level.get(); j++)
                    techPage.append("......");
                techPage.append(endpoint.name).append("\n");
                break;
            default:
                break;
        }
    }

    private void writeGroup(String parentPath, Endpoint group) throws Exception
    {
        StringBuilder groupSb = new StringBuilder(groupPage);
        for (Endpoint child : group.children)
            getRef(groupSb, parentPath + child.relative, child, null, true);

        String groupStr = groupSb.toString();
        groupStr = doReplacements(groupStr, group);
        String groupPath = parentPath + "/" + getNameFromUid(group.ruid) + ".md";

        PrintWriter out = new PrintWriter(groupPath);
        out.println(groupStr);
        out.close();
        totalGroups.incrementAndGet();
    }

    private void writeLeaf(String parentPath, Endpoint leaf) throws FileNotFoundException
    {
        String[] paths = getLeafPaths(parentPath, leaf);
        for (int i = 0; i < paths.length; i++)
            if (paths[i] != null)
            {
                String leafStr = leafPage;
                leafStr = doReplacements(leafStr, leaf);
                PrintWriter out = new PrintWriter(paths[i]);
                out.println(leafStr);
                out.close();
                totalLeafs.incrementAndGet();
            }
    }

    private void getRef(StringBuilder sb, String parentPath, Endpoint child, AtomicInteger level, boolean fromGroup)
    {
        String[] paths = getLeafPaths(parentPath, child);
        for (int i = 0; i < paths.length; i += 2)
            if (paths[i] != null)
            {
                String ref = paths[i];
                ref = ref.replace('\\', '/');
                int idx = ref.indexOf("/root/");
                idx += 6;
                ref = ref.substring(idx);
                if(fromGroup)
                {
                    idx = ref.indexOf(child.relative);
                    if(idx != -1)
                        ref = ref.substring(++idx);
                }

                sb.append("* ");
                if(level != null)
                    for (int j = 0; j < level.get(); j++)
                        sb.append("......");

                //@formatter:off
                sb
                    .append("[").append(child.name).append("](")
                    .append(ref).append(")")
                    .append(" `").append(fixupPath(paths[i+1])).append("`\n");
                //@formatter:on
            }
    }

    private String[] getLeafPaths(String parentPath, Endpoint child)
    {
        parentPath = StrH.insureTailingSeparator(parentPath, '/');
        parentPath = checkCutUi(parentPath);
        String[] list = new String[8];
        for (int i = 0; i < 8; i += 2)
            switch (i)
            {
                case 0:
                    if (child.cuid != null)
                    {
                        list[0] = parentPath + getNameFromUid(child.cuid) + ".md";
                        list[1] = child.cuid;
                    }
                    break;
                case 2:
                    if (child.ruid != null)
                    {
                        list[2] = parentPath + getNameFromUid(child.ruid) + ".md";
                        list[3] = child.ruid;
                    }
                    break;
                case 4:
                    if (child.uuid != null)
                    {
                        list[4] = parentPath + getNameFromUid(child.uuid) + ".md";
                        list[5] = child.uuid;
                    }
                    break;
                case 6:
                    if (child.duid != null)
                    {
                        list[6] = parentPath + getNameFromUid(child.duid) + ".md";
                        list[7] = child.duid;
                    }
                    break;
                default:
                    break;
            }
        return list;
    }

    private String doReplacements(String value, Endpoint endpoint)
    {
        value = value.replace("$title", endpoint.name);
        value = value.replace("$url", endpoint.url == null ? "n/a" : endpoint.url);
        value = value.replace("$cuid", endpoint.cuid == null ? "n/a" : endpoint.cuid);
        value = value.replace("$ruid", endpoint.ruid == null ? "n/a" : endpoint.ruid);
        value = value.replace("$uuid", endpoint.uuid == null ? "n/a" : endpoint.uuid);
        value = value.replace("$duid", endpoint.duid == null ? "n/a" : endpoint.duid);
        value = value.replace("$cto", endpoint.cacheTimeout.get() == -1 ? "(TBD)" : endpoint.cacheTimeout.get() + " seconds");
        if (endpoint.cuid != null)
            totalUids.incrementAndGet();
        if (endpoint.ruid != null)
            totalUids.incrementAndGet();
        if (endpoint.uuid != null)
            totalUids.incrementAndGet();
        if (endpoint.duid != null)
            totalUids.incrementAndGet();
        return value;
    }

    private String readFile(String path) throws Exception
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
    }

    private void pingOption(Endpoint endpoint)
    {
        if (!PingOptions)
            return;
        if (endpoint.type == Type.PlaceHolder)
            return;
        try
        {
            log.info("\n" + endpoint.toString());
            DataCache dataCache = CrestController.getCrestController().dataCache;
            dataCache.getOptions(endpoint.url);
        } catch (Throwable e)
        {
            log.error("failed: ", e);
        }
    }

    private void pingGet(Endpoint endpoint)
    {
        if (!PingGets)
            return;
        if (endpoint.type == Type.PlaceHolder)
            return;
        try
        {
            log.info("\n*****\n" + endpoint.toString() + "\n");
            DataCache dataCache = CrestController.getCrestController().dataCache;
            CrestOptions copts = dataCache.getOptions(endpoint.url, true);
            endpoint.cacheTimeout.set(copts.getCacheTimeInSeconds());
        } catch (Throwable e)
        {
            // just get the low lying fruit for now.
            //            log.error("failed: ", e);
        }
    }


    private String fixupPath(String path)
    {
        path = path.replace('\\', '/');
        path = path.replace("//", "/");
        path = path.replace("///", "/");
        return path;
    }

    public static String getNameFromUid(String uid)
    {
        int idx = uid.lastIndexOf('.');
        if (idx == -1)
            return uid;
        String rvalue = uid.substring(++idx);
        idx = rvalue.indexOf("-v");
        if (idx == -1)
            return uid;
        return rvalue.substring(0, idx);

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
            {
                path = path.substring(0, path.length() - 2);
                path += "id/";
            }
            path = checkCutUi(path);

            nfbase = new File(path);
            nfbase.mkdirs();
        }
        if (endpoint.relative != null)
        {
            pingOption(endpoint);
            pingGet(endpoint);
            writeFile(nfbase, endpoint, level);
        }
        for (Endpoint child : endpoint.children)
            traverse(child, nfbase, level);
        level.decrementAndGet();
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
            root = new Endpoint("root", UidBase + "Api-v5+json", null, null, null, rootUrl + "/", null, Type.Leaf, null);
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
        public final String cuid; // POST's assigned here
        public final String ruid; // GET's assigned here
        public final String uuid; // PUT's assigned here
        public final String duid; // DELETE's assigned here
        public final String url;
        public final String relative;
        public final AtomicInteger cacheTimeout;
        public final List<Endpoint> children;
        public final transient Type type;
        public final transient Class<? extends EveJsonData> eveDataClass;

        //@formatter:off
        public Endpoint(
            String name,
            String cuid, String ruid, String uuid, String duid,
            String url, String relative, Type type, Class<? extends EveJsonData> eveDataClass)
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
            this.eveDataClass = eveDataClass;
            cacheTimeout = new AtomicInteger(-1);
            children = new ArrayList<>();
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName(), ":");
            format.inc();
            format.ttl("name: ", name);
            format.ttl("type: ", type.name());
            format.ttl("relative: ", relative);
            format.ttl("url: ", url);
            format.ttl("cuid: ", cuid);
            format.ttl("ruid: ", ruid);
            format.ttl("uuid: ", uuid);
            format.ttl("duid: ", duid);
            format.ttl("cache: ", cacheTimeout.get());
            format.ttl("children: ");
            format.inc();
            for (Endpoint child : children)
                child.toString(format);
            if (children.size() == 0)
                format.ttl("none");
            format.dec();
            format.dec();
            return format;
        }
    }

    public static void main(String[] args)
    {
        RootEndpoint rjg = null;
        try
        {
            rjg = new RootEndpoint();
            LoggerFactory.getLogger(RootEndpoint.class).info("\n" + rjg.jsonStr);
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
        addChild(                             root, "Endpoints",        UidBase + "Api-v5+json",                            rootUrl + "",                                                    "", EndpointCollection.class);
        addChild(                             root, "Options",          UidBase + "Options-v1+json",                        rootUrl + "",                                                    "", CrestOptions.class);
        addChild(                             root, "Alliances",        UidBase + "AllianceCollection-v2+json",             rootUrl + "/alliances/",                                         "/alliances/", AllianceCollection.class);
        addChild(                             root, "Bloodlines",       UidBase + "BloodlineCollection-v2+json",            rootUrl + "/bloodlines/",                                        "/bloodlines/", BloodlineCollection.class);
        Endpoint child = addChild(            root, "Characters",       UidBase + "CharacterSearch-v1+json",                rootUrl + "/characters/",                                        "/characters/", Type.Group, CharacterSearch.class);
        Endpoint gchild = addChild(          child, "Contacts",         UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactCollection-v2+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/contacts/",                    "/contacts/", Type.Leaf, ContactCollection.class);
        addChild(                           gchild, "Contact",          null,
                                                                        null,
                                                                        UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactDelete-v1+json",                  rootUrl + "/characters/1364371482/contacts/0/",                  "/0/", Type.Leaf);
        gchild = addChild(                   child, "Fittings",         UidBase + "FittingCreate-v1+json",
                                                                        UidBase + "FittingCollection-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/fittings/",                    "/fittings/", Type.Leaf);
        addChild(                           gchild, "Fitting",          null,
                                                                        UidBase + "Fitting-v1+json",
                                                                        null,
                                                                        UidBase + "FittingDelete-v1+json",                  rootUrl + "/characters/1364371482/fittings/0/",                  "/0/", Type.Leaf);
        addChild(                            child, "Opportunities",    UidBase + "CharacterOpportunitiesCollection-v1+json",rootUrl + "/characters/1364371482/opportunities/",               "/opportunities/");

        addChild(                            child, "Waypoints",        UidBase + "PostWaypoint-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/autopilot/waypoints/",      "/ui/autopilot/waypoints/", Type.Leaf);
        addChild(                            child, "Location",         UidBase + "CharacterLocation-v1+json",              rootUrl + "/characters/1364371482/location/",                    "/location/");

        addChild(                            child, "MarketDetails",    UidBase + "ShowMarketDetails-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/openwindow/marketdetails/", "/ui/openwindow/marketdetails/", Type.Leaf);
        addChild(                             root, "Constellations",   UidBase + "ConstellationCollection-v1+json",        rootUrl + "/constellations/",                                    "/constellations/");
        child = addChild(                     root, "Corporations",     null,                                               rootUrl + "/corporations/",                                      "/corporations/", Type.PlaceHolder);
        addChild(                            child, "NPCCorporations",  UidBase + "NPCCorporationsCollection-v1+json",      rootUrl + "/corporations/npccorps/",                             "/npccorps/");
        child = addChild(                     root, "Dogma",            null,                                               rootUrl + "/dogma/",                                             "/dogma/", Type.PlaceHolder);
        addChild(                            child, "Attributes",       UidBase + "DogmaAttributeCollection-v1+json",       rootUrl + "/dogma/attributes/",                                  "/attributes/");
        addChild(                            child, "Effects",          UidBase + "DogmaEffectCollection-v1+json",          rootUrl + "/dogma/effects/",                                     "/effects/");
        child = addChild(                     root, "Fleets",           null,                                               rootUrl + "/fleets/",                                            "/fleets/", Type.PlaceHolder);
        gchild = addChild(                   child, "Fleet",            null,
                                                                        UidBase + "Fleet-v1+json",
                                                                        UidBase + "FleetUpdate-v1+json",
                                                                        null,                                               rootUrl + "/fleets/0/",                                          "/0/", Type.Mixed);
        Endpoint ggchild = addChild(        gchild, "Wings",            UidBase + "FleetWingCreate-v1+json",
                                                                        UidBase + "FleetWings-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/wings/",                                    "/wings/", Type.Mixed);
        Endpoint gggchild = addChild(      ggchild, "Wing",             null,                                               rootUrl + "/fleets/0/winds/0/",                                  "/0/", Type.PlaceHolder);
        Endpoint ggggchild = addChild(    gggchild, "Squads",           UidBase + "FleetSquadCreate-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/wings/0/squads/",                           "/squads/", Type.Leaf);
        addChild(                       ggggchild, "Members",           UidBase + "FleetMemberInvite-v1+json",
                                                                        UidBase + "FleetMembers-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/members/",                                  "/members/", Type.Leaf);
        addChild(                             root, "Incursions",       UidBase + "IncursionCollection-v1+json",            rootUrl + "/incursions/",                                        "/incursions/");
        child = addChild(                     root, "Industry",         null,                                               rootUrl + "/industry/",                                          "/industry/", Type.PlaceHolder);
        addChild(                            child, "Facility",         UidBase + "IndustryFacilityCollection-v1+json",     rootUrl + "/industry/facilities/",                               "/facilities/");
        addChild(                            child, "Systems",          UidBase + "IndustrySystemCollection-v1+json",       rootUrl + "/industry/systems/",                                  "/systems/");
        child = addChild(                     root, "inventory",        null,                                               rootUrl + "/inventory/",                                         "/inventory/", Type.PlaceHolder);
        addChild(                            child, "Categories",       UidBase + "ItemCategoryCollection-v1+json",         rootUrl + "/inventory/categories/",                              "/categories/");
        addChild(                            child, "Groups",           UidBase + "ItemGroupCollection-v1+json",            rootUrl + "/inventory/groups/",                                  "/groups/");
        addChild(                            child, "Types",            UidBase + "ItemTypeCollection-v1+json",             rootUrl + "/inventory/types/",                                   "/types/");
        child = addChild(                     root, "Market",           null,                                               rootUrl + "/market/",                                            "/market/", Type.PlaceHolder);
        addChild(                            child, "Groups",           UidBase + "MarketGroupCollection-v1+json",          rootUrl + "/market/groups/",                                     "/groups/");
        addChild(                            child, "Prices",           UidBase + "MarketTypePriceCollection-v1+json",      rootUrl + "/market/prices/",                                     "/prices/");
        addChild(                            child, "Types",            UidBase + "MarketTypeCollection-v1+json",           rootUrl + "/market/types/",                                      "/types/");
        child = addChild(                     root, "Opportunity",      null,                                               rootUrl + "/opportunities/",                                     "/opportunities/", Type.PlaceHolder);
        addChild(                            child, "Groups",           UidBase + "OpportunityGroupsCollection-v1+json",    rootUrl + "/opportunities/groups/",                              "/groups/");
        addChild(                            child, "Tasks",            UidBase + "OpportunityTasksCollection-v1+json",     rootUrl + "/opportunities/tasks/",                               "/tasks/");
        child = addChild(                     root, "Planets",          null,                                               rootUrl + "/planets/",                                           "/planets/", Type.PlaceHolder);
        addChild(                            child, "Planet",           UidBase + "Planet-v2+json",                         rootUrl + "/planets/0/",                                         "/0/");
        child = addChild(                     root, "Sovereignty",      null,                                               rootUrl + "/sovereignty/",                                       "/sovereignty/", Type.PlaceHolder);
        addChild(                            child, "Campaigns",        UidBase + "SovCampaignsCollection-v1+json",         rootUrl + "/sovereignty/campaigns/",                             "/campaigns/");
        addChild(                            child, "Structures",       UidBase + "SovStructureCollection-v1+json",         rootUrl + "/sovereignty/structures/",                            "/structures/");
        child = addChild(                     root, "Stargates",        null,                                               rootUrl + "/stargates/",                                         "/stargates/", Type.PlaceHolder);
        addChild(                            child, "Stargate",         UidBase + "Stargate-v1+json",                       rootUrl + "/stargates/3875/",                                    "/stargates/0/");
        addChild(                             root, "Races",            UidBase + "RaceCollection-v3+json",                 rootUrl + "/races/",                                             "/races/");
        addChild(                             root, "Regions",          UidBase + "RegionCollection-v1+json",               rootUrl + "/regions/",                                           "/regions/");
        child = addChild(                     root, "Tournaments",      UidBase + "TournamentCollection-v1+json",           rootUrl + "/tournaments/",                                       "/tournaments/", Type.Group);
        gchild = addChild(                   child, "Tournament",       UidBase + "Tournament-v1+json",                     rootUrl + "/tournaments/7/",                                     "/7/");
        ggchild = addChild(                 gchild, "Series",           UidBase + "TournamentSeriesCollection-v1+json",     rootUrl + "/tournaments/7/series/",                              "/series/", Type.Group);
        gggchild = addChild(               ggchild, "Matches",          UidBase + "TournamentMatchCollection-v1+json",      rootUrl + "/tournaments/7/series/0/matches/",                    "/matches/", Type.Group);
        ggggchild = addChild(             gggchild, "Match",            UidBase + "TournamentMatch-v1+json",                rootUrl + "/tournaments/7/series/0/matches/0/",                  "/0/");
        addChild(                        ggggchild, "Bans",             UidBase + "TournamentBans"+Fixme,                   rootUrl + "/tournaments/7/series/0/matches/0/bans/",             "/bans/");
        addChild(                        ggggchild, "Static",           UidBase + "TournamentStaticSceneData-v2+json",      rootUrl + "/tournaments/7/series/0/matches/0/static/",           "/static/", Type.Group);
        addChild(                        ggggchild, "PilotStats",       UidBase + "TournamentPilotStatsCollection-v1+json", rootUrl + "/tournaments/7/series/0/matches/0/pilotstats/",       "/pilotstats/");
        Endpoint gggggchild = addChild(  ggggchild, "Realtime",         null,                                               rootUrl + "/tournaments/7/series/0/matches/0/realtime/",         "/realtime/", Type.PlaceHolder);
        addChild(                       gggggchild, "Frame",            UidBase + "TournamentRealtimeMatchFrame-v2+json",   rootUrl + "/tournaments/7/series/0/matches/0/realtime/0/",       "/0/");
        ggchild = addChild(                 gchild, "Teams",            null,                                               rootUrl + "/tournaments/7/teams/",                               "/teams/", Type.PlaceHolder);
        addChild(                          ggchild, "Team",             UidBase + "TournamentTeam-v1+json",                 rootUrl + "/tournaments/7/teams/0/",                             "/0/");
        ggchild = addChild(                 gchild, "Teams",            UidBase + "TournamentTeamCollection-v1+json",       rootUrl + "/tournaments/teams/",                                 "/teams/", Type.Group);
        gggchild = addChild(               ggchild, "Team",             UidBase + "TournamentTeam-v1+json",                 rootUrl + "/tournaments/teams/0/",                               "/0/");
        addChild(                         gggchild, "Members",          UidBase + "TournamentTeamMemberCollection-v1+json", rootUrl + "/tournaments/teams/0/members/",                       "/members/");
        addChild(                             root, "SolarSystems",     UidBase + "SystemCollection-v1+json",               rootUrl + "/solarsystems/",                                      "/solarsystems/");
        addChild(                             root, "Time",             UidBase + "Time-v1+json",                           rootUrl + "/time/",                                              "/time/");
        addChild(                             root, "Decode",           UidBase + "TokenDecode-v1+json",                    rootUrl + "/decode/",                                            "/decode/");
//      addChild(                             root, "virtualGoodStore", UidBase + "VirtualGoodStore"+Fixme,                 rootUrl + "/virtualGoodStore/",                                  "/virtualGoodStore/");
        child = addChild(                     root, "Wars",             UidBase + "WarsCollection-v1+json",                 rootUrl + "/wars/",                                              "/wars/", Type.Group);
        addChild(                            child, "war",              UidBase + "War-v1+json",                            rootUrl + "/wars/0/",                                            "/wars/0/");
        //@formatter:on
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative, Class<? extends EveData> eveDataClass)
    {
        return addChild(ep, name, ruid, url, relative, Type.Leaf, eveDataClass);
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative, Type type, Class<? extends EveData> eveDataClass)
    {
        if (type == null)
            log.info("look here");
        return addChild(ep, name, null, ruid, null, null, url, relative, type, eveDataClass);
    }

    private Endpoint addChild(Endpoint ep, String name, String cuid, String ruid, String uuid, String duid, String url, String relative, Type type, Class<? extends EveData> eveDataClass)
    {
        if (type == null)
            log.info("look here");
        Endpoint child = new Endpoint(name, cuid, ruid, uuid, duid, url, relative, type, eveDataClass);
        ep.children.add(child);
        return child;
    }

    private enum Type
    {
        Group, Leaf, Mixed, PlaceHolder
    }

    // don't have cachetime for
    //    https://api-sisi.testeveonline.com/characters/
    //    https://api-sisi.testeveonline.com/characters/1364371482/contacts/
    //    https://api-sisi.testeveonline.com/characters/1364371482/contacts/0/
    //    https://api-sisi.testeveonline.com/characters/1364371482/fittings/
    //    https://api-sisi.testeveonline.com/characters/1364371482/fittings/0/
    //    https://api-sisi.testeveonline.com/characters/1364371482/ui/autopilot/waypoints/
    //    https://api-sisi.testeveonline.com/characters/1364371482/location/
    //    https://api-sisi.testeveonline.com/characters/1364371482/ui/openwindow/marketdetails/
    //    https://api-sisi.testeveonline.com/fleets/0/
    //    https://api-sisi.testeveonline.com/fleets/0/wings/
    //    https://api-sisi.testeveonline.com/fleets/0/wings/0/squads/
    //    https://api-sisi.testeveonline.com/fleets/0/members/
    //    https://api-sisi.testeveonline.com/planets/0/
    //    https://api-sisi.testeveonline.com/sovereignty/structures/
    //    https://api-sisi.testeveonline.com/stargates/0/
    //    https://api-sisi.testeveonline.com/tournaments/0/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/0/matches/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/0/matches/0/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/0/matches/0/static/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/0/matches/0/pilotstats/
    //    https://api-sisi.testeveonline.com/tournaments/0/series/0/matches/0/realtime/0/
    //    https://api-sisi.testeveonline.com/tournaments/0/teams/0/
    //    https://api-sisi.testeveonline.com/tournaments/teams/0/
    //    https://api-sisi.testeveonline.com/tournaments/teams/0/members/
    //    https://api-sisi.testeveonline.com/decode/
    //    https://api-sisi.testeveonline.com/wars/0/
}
