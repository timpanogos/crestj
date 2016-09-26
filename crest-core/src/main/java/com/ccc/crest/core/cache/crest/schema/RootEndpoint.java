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
import com.ccc.crest.core.cache.crest.alliance.AlliancesElement;
import com.ccc.crest.core.cache.crest.character.BloodlineCollection;
import com.ccc.crest.core.cache.crest.character.CharacterSearch;
import com.ccc.crest.core.cache.crest.character.ContactCollection;
import com.ccc.crest.core.cache.crest.character.ContactElement;
import com.ccc.crest.core.cache.crest.character.FittingCollection;
import com.ccc.crest.core.cache.crest.character.FittingElement;
import com.ccc.crest.core.cache.crest.character.Location;
import com.ccc.crest.core.cache.crest.character.MarketDetails;
import com.ccc.crest.core.cache.crest.character.NewMail;
import com.ccc.crest.core.cache.crest.character.OpportunitiesCollection;
import com.ccc.crest.core.cache.crest.character.PostWaypoint;
import com.ccc.crest.core.cache.crest.character.RaceCollection;
import com.ccc.crest.core.cache.crest.character.TokenDecode;
import com.ccc.crest.core.cache.crest.corporation.CorporationStructuresCollection;
import com.ccc.crest.core.cache.crest.corporation.NpcCorporationCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaAttributeCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaEffectCollection;
import com.ccc.crest.core.cache.crest.fleet.Fleet;
import com.ccc.crest.core.cache.crest.fleet.Members;
import com.ccc.crest.core.cache.crest.fleet.Squads;
import com.ccc.crest.core.cache.crest.fleet.Wings;
import com.ccc.crest.core.cache.crest.incursion.IncursionCollection;
import com.ccc.crest.core.cache.crest.industry.IndustryFacilityCollection;
import com.ccc.crest.core.cache.crest.industry.IndustrySystemCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemCategoryCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemGroupCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemTypeCollection;
import com.ccc.crest.core.cache.crest.map.ConstellationCollection;
import com.ccc.crest.core.cache.crest.map.Planet;
import com.ccc.crest.core.cache.crest.map.RegionCollection;
import com.ccc.crest.core.cache.crest.map.Stargate;
import com.ccc.crest.core.cache.crest.map.Station;
import com.ccc.crest.core.cache.crest.map.SystemCollection;
import com.ccc.crest.core.cache.crest.market.MarketGroupCollection;
import com.ccc.crest.core.cache.crest.market.MarketTypeCollection;
import com.ccc.crest.core.cache.crest.market.MarketTypePriceCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityGroupsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityTasksCollection;
import com.ccc.crest.core.cache.crest.schema.endpoint.EndpointCollection;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.cache.crest.sovereignty.SovCampaignsCollection;
import com.ccc.crest.core.cache.crest.sovereignty.SovStructureCollection;
import com.ccc.crest.core.cache.crest.time.CrestTime;
import com.ccc.crest.core.cache.crest.tournament.TournamentBanCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentMatchCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentMatchElement;
import com.ccc.crest.core.cache.crest.tournament.TournamentMatchFrame;
import com.ccc.crest.core.cache.crest.tournament.TournamentPilotStatusCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentSeriesCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentStatic;
import com.ccc.crest.core.cache.crest.tournament.TournamentTeam;
import com.ccc.crest.core.cache.crest.tournament.TournamentTeam2;
import com.ccc.crest.core.cache.crest.tournament.TournamentTeamCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentTeamMemberCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentTournament;
import com.ccc.crest.core.cache.crest.war.WarsCollection;
import com.ccc.crest.core.cache.crest.war.WarsElement;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.tools.StrH;
import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@SuppressWarnings("javadoc")
public class RootEndpoint
{
    public static final boolean PingOptions = false;
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
    public static final String UidBase = "application/vnd.ccp.eve.";
//    public static final String UidBase = "vnd.ccp.eve.";
    public static final String Fixme = "(TBD)";

    public volatile Endpoints endpoints;
    public String jsonStr;

    private final Logger log;
    private final String rootUrl;
    private volatile String groupPage;
    private volatile String leafPage;
    private volatile StringBuilder techPage;
    private final AtomicInteger totalLeafs;
    private final AtomicInteger totalGroups;
    private final AtomicInteger totalUids;

    public RootEndpoint()
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

        try
        {
            groupPage = readFile(GroupDocTemplate);
            leafPage = readFile(LeafDocTemplate);
            techPage = new StringBuilder(readFile(TechDocTemplate));
        } catch (Exception e)
        {
            log.warn("Failed to read in documentation templates");
        }
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
        for (int i = 0; i < paths.length; i += 2)
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
        if (endpoint.type == DocType.PlaceHolder)
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
        if (endpoint.type == DocType.PlaceHolder)
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
            serverStatus = "online";
            if(rootUrl.contains("crest-tq"))
            {
                userCount = 14686;
                serverVersion = "EVE-TRANQUILITY 14.08.1072737.1072715";
                serverName = "TRANQUILITY";
            }else
            {
                userCount = 63;
                serverVersion = "EVE-2016-HERMINE 14.08.1071672.1071672";
                serverName = "SINGULARITY";
            }
            root = new Endpoint("root", UidBase + "Api-v5+json", UidBase + "Api-v5+json", UidBase + "Api-v5+json", UidBase + "Api-v5+json", rootUrl + "/", null, DocType.Leaf, null);
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
        public final transient DocType type;
        public final transient Class<? extends EveJsonData> eveDataClass;
        public final transient String cuidVersion;
        public final transient String ruidVersion;
        public final transient String uuidVersion;
        public final transient String duidVersion;

        //@formatter:off
        public Endpoint(
            String name,
            String cuid, String ruid, String uuid, String duid,
            String url, String relative, DocType type, Class<? extends EveJsonData> eveDataClass)
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
            cuidVersion = getVersion(cuid);
            ruidVersion = getVersion(ruid);
            uuidVersion = getVersion(uuid);
            duidVersion = getVersion(duid);
        }

        private String getVersion(String uid)
        {
            if(uid == null)
                return null;
            int idx = uid.indexOf("-v");
            if(idx == -1)
                return uid;
            return uid.substring(idx);
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
        Endpoint child = addChild(            root, "Alliances",        UidBase + "AllianceCollection-v2+json",             rootUrl + "/alliances/",                                         "/alliances/", AllianceCollection.class);
        addChild(                            child, "Alliance",         UidBase + "Alliance-v1+json",                       rootUrl + "/alliances/99000006/",                                "/0/", AlliancesElement.class);
        addChild(                             root, "Bloodlines",       UidBase + "BloodlineCollection-v2+json",            rootUrl + "/bloodlines/",                                        "/bloodlines/", BloodlineCollection.class);
        child = addChild(                     root, "Characters",       UidBase + "CharacterSearch-v1+json",                rootUrl + "/characters/",                                        "/characters/", DocType.Group, CharacterSearch.class);
        Endpoint gchild = addChild(          child, "Contacts",         UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactCollection-v2+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/contacts/",                    "/contacts/", DocType.Leaf, ContactCollection.class);
        addChild(                           gchild, "Contact",          null,
                                                                        null,
                                                                        UidBase + "ContactCreate-v1+json",
                                                                        UidBase + "ContactDelete-v1+json",                  rootUrl + "/characters/1364371482/contacts/0/",                  "/0/", DocType.Leaf, ContactElement.class);
        gchild = addChild(                   child, "Fittings",         UidBase + "FittingCreate-v1+json",
                                                                        UidBase + "FittingCollection-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/fittings/",                    "/fittings/", DocType.Leaf, FittingCollection.class);
        addChild(                           gchild, "Fitting",          null,
                                                                        UidBase + "Fitting-v1+json",
                                                                        null,
                                                                        UidBase + "FittingDelete-v1+json",                  rootUrl + "/characters/1364371482/fittings/0/",                  "/0/", DocType.Leaf, FittingElement.class);
        addChild(                            child, "Opportunities",    UidBase + "CharacterOpportunitiesCollection-v1+json",rootUrl + "/characters/1364371482/opportunities/",              "/opportunities/", OpportunitiesCollection.class);
        addChild(                            child, "Location",         UidBase + "CharacterLocation-v1+json",              rootUrl + "/characters/1364371482/location/",                    "/location/", Location.class);
        gchild = addChild(                   child, "ui",               null,                                               rootUrl + "/characters/1364371482/ui/",                          "/ui/", DocType.PlaceHolder, null);
        Endpoint ggchild = addChild(        gchild, "autopilot",        null,                                               rootUrl + "/characters/1364371482/ui/autopilot/",                "/autopilot/", DocType.PlaceHolder, null);
        Endpoint gggchild = addChild(      ggchild, "Waypoints",        UidBase + "PostWaypoint-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/autopilot/waypoints/",      "/waypoints/", DocType.Leaf, PostWaypoint.class);
        ggchild = addChild(                 gchild, "openwindow",       null,                                               rootUrl + "/characters/1364371482/ui/openwindow/",               "/openwindow/", DocType.PlaceHolder, null);
        addChild(                          ggchild, "Mail",             UidBase + "NewMail-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/openwindow/newmail/",       "/newmail/", DocType.Leaf, NewMail.class);
        addChild(                            child, "MarketDetails",    UidBase + "ShowMarketDetails-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/characters/1364371482/ui/openwindow/marketdetails/", "/ui/openwindow/marketdetails/", DocType.Leaf, MarketDetails.class);
        addChild(                             root, "Constellations",   UidBase + "ConstellationCollection-v1+json",        rootUrl + "/constellations/",                                    "/constellations/", ConstellationCollection.class);
        child = addChild(                     root, "Corporations",     null,                                               rootUrl + "/corporations/",                                      "/corporations/", DocType.PlaceHolder, null);
        gchild = addChild(                   child, "NPCCorporation",   UidBase + "Corporation-v1+json",                    rootUrl + "/corporations/1000004/",                              "/0/", NpcCorporationCollection.class);
        ggchild = addChild(                 gchild, "Structures",       UidBase + "CorporationStructuresCollection-v1+json",rootUrl + "/corporations/665335352/structures/",                 "/structures/", CorporationStructuresCollection.class);

        addChild(                           gchild, "LoyaltyStore",     UidBase + "LoyaltyStoreOffersCollection-v1+json",   rootUrl + "/corporations/1000004/loyaltystore/",                 "/loyaltystore/", NpcCorporationCollection.class);
        addChild(                            child, "NPCCorporations",  UidBase + "NPCCorporationsCollection-v1+json",      rootUrl + "/corporations/npccorps/",                             "/npccorps/", NpcCorporationCollection.class);

        child = addChild(                     root, "Dogma",            null,                                               rootUrl + "/dogma/",                                             "/dogma/", DocType.PlaceHolder, null);
        addChild(                            child, "Attributes",       UidBase + "DogmaAttributeCollection-v1+json",       rootUrl + "/dogma/attributes/",                                  "/attributes/", DogmaAttributeCollection.class);
        addChild(                            child, "Effects",          UidBase + "DogmaEffectCollection-v1+json",          rootUrl + "/dogma/effects/",                                     "/effects/", DogmaEffectCollection.class);
        child = addChild(                     root, "Fleets",           null,                                               rootUrl + "/fleets/",                                            "/fleets/", DocType.PlaceHolder, null);
        gchild = addChild(                   child, "Fleet",            null,
                                                                        UidBase + "Fleet-v1+json",
                                                                        UidBase + "FleetUpdate-v1+json",
                                                                        null,                                               rootUrl + "/fleets/0/",                                          "/0/", DocType.Mixed, Fleet.class);
        ggchild = addChild(                 gchild, "Wings",            UidBase + "FleetWingCreate-v1+json",
                                                                        UidBase + "FleetWings-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/wings/",                                    "/wings/", DocType.Mixed, Wings.class);
        gggchild = addChild(               ggchild, "Wing",             null,                                               rootUrl + "/fleets/0/winds/0/",                                  "/0/", DocType.PlaceHolder, null);
        Endpoint ggggchild = addChild(    gggchild, "Squads",           UidBase + "FleetSquadCreate-v1+json",
                                                                        null,
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/wings/0/squads/",                           "/squads/", DocType.Leaf, Squads.class);
        addChild(                       ggggchild, "Members",           UidBase + "FleetMemberInvite-v1+json",
                                                                        UidBase + "FleetMembers-v1+json",
                                                                        null,
                                                                        null,                                               rootUrl + "/fleets/0/members/",                                  "/members/", DocType.Leaf, Members.class);
        addChild(                             root, "Incursions",       UidBase + "IncursionCollection-v1+json",            rootUrl + "/incursions/",                                        "/incursions/", IncursionCollection.class);
        child = addChild(                     root, "Industry",         null,                                               rootUrl + "/industry/",                                          "/industry/", DocType.PlaceHolder, null);
        addChild(                            child, "Facility",         UidBase + "IndustryFacilityCollection-v1+json",     rootUrl + "/industry/facilities/",                               "/facilities/", IndustryFacilityCollection.class);
        addChild(                            child, "Systems",          UidBase + "IndustrySystemCollection-v1+json",       rootUrl + "/industry/systems/",                                  "/systems/", IndustrySystemCollection.class);
        child = addChild(                     root, "inventory",        null,                                               rootUrl + "/inventory/",                                         "/inventory/", DocType.PlaceHolder, null);
        addChild(                            child, "Categories",       UidBase + "ItemCategoryCollection-v1+json",         rootUrl + "/inventory/categories/",                              "/categories/", ItemCategoryCollection.class);
        addChild(                            child, "Groups",           UidBase + "ItemGroupCollection-v1+json",            rootUrl + "/inventory/groups/",                                  "/groups/", ItemGroupCollection.class);
        addChild(                            child, "Types",            UidBase + "ItemTypeCollection-v1+json",             rootUrl + "/inventory/types/",                                   "/types/", ItemTypeCollection.class);
        child = addChild(                     root, "Market",           null,                                               rootUrl + "/market/",                                            "/market/", DocType.PlaceHolder, null);
        addChild(                            child, "Groups",           UidBase + "MarketGroupCollection-v1+json",          rootUrl + "/market/groups/",                                     "/groups/", MarketGroupCollection.class);
        addChild(                            child, "Prices",           UidBase + "MarketTypePriceCollection-v1+json",      rootUrl + "/market/prices/",                                     "/prices/", MarketTypePriceCollection.class);
        addChild(                            child, "Types",            UidBase + "MarketTypeCollection-v1+json",           rootUrl + "/market/types/",                                      "/types/", MarketTypeCollection.class);
        child = addChild(                     root, "Opportunity",      null,                                               rootUrl + "/opportunities/",                                     "/opportunities/", DocType.PlaceHolder, null);
        addChild(                            child, "Groups",           UidBase + "OpportunityGroupsCollection-v1+json",    rootUrl + "/opportunities/groups/",                              "/groups/", OpportunityGroupsCollection.class);
        addChild(                            child, "Tasks",            UidBase + "OpportunityTasksCollection-v1+json",     rootUrl + "/opportunities/tasks/",                               "/tasks/", OpportunityTasksCollection.class);
        child = addChild(                     root, "Planets",          null,                                               rootUrl + "/planets/",                                           "/planets/", DocType.PlaceHolder, null);
        addChild(                            child, "Planet",           UidBase + "Planet-v2+json",                         rootUrl + "/planets/0/",                                         "/0/", Planet.class);
        child = addChild(                     root, "Sovereignty",      null,                                               rootUrl + "/sovereignty/",                                       "/sovereignty/", DocType.PlaceHolder, null);
        addChild(                            child, "Campaigns",        UidBase + "SovCampaignsCollection-v1+json",         rootUrl + "/sovereignty/campaigns/",                             "/campaigns/", SovCampaignsCollection.class);
        addChild(                            child, "Structures",       UidBase + "SovStructureCollection-v1+json",         rootUrl + "/sovereignty/structures/",                            "/structures/", SovStructureCollection.class);
        child = addChild(                     root, "Stargates",        null,                                               rootUrl + "/stargates/",                                         "/stargates/", DocType.PlaceHolder, null);
        addChild(                            child, "Stargate",         UidBase + "Stargate-v1+json",                       rootUrl + "/stargates/3875/",                                    "/0/", Stargate.class);
        child = addChild(                     root, "Stations",         null,                                               rootUrl + "/stations/",                                          "/stations/", DocType.PlaceHolder, null);
        addChild(                            child, "Station",          UidBase + "Station-v1+json",                        rootUrl + "/stations/60000004/",                                 "/0/", Station.class);
        addChild(                             root, "Races",            UidBase + "RaceCollection-v3+json",                 rootUrl + "/races/",                                             "/races/", RaceCollection.class);
        addChild(                             root, "Regions",          UidBase + "RegionCollection-v1+json",               rootUrl + "/regions/",                                           "/regions/", RegionCollection.class);
        child = addChild(                     root, "Tournaments",      UidBase + "TournamentCollection-v1+json",           rootUrl + "/tournaments/",                                       "/tournaments/", DocType.Group, TournamentCollection.class);
        gchild = addChild(                   child, "Tournament",       UidBase + "Tournament-v1+json",                     rootUrl + "/tournaments/7/",                                     "/0/", TournamentTournament.class);
        ggchild = addChild(                 gchild, "Series",           UidBase + "TournamentSeriesCollection-v1+json",     rootUrl + "/tournaments/7/series/",                              "/series/", DocType.Group, TournamentSeriesCollection.class);
        gggchild = addChild(               ggchild, "Matches",          UidBase + "TournamentMatchCollection-v1+json",      rootUrl + "/tournaments/7/series/0/matches/",                    "/matches/", DocType.Group, TournamentMatchCollection.class);
        ggggchild = addChild(             gggchild, "Match",            UidBase + "TournamentMatch-v1+json",                rootUrl + "/tournaments/7/series/0/matches/0/",                  "/0/", TournamentMatchElement.class);
        addChild(                        ggggchild, "Bans",             UidBase + "TournamentTypeBanCollection-v1+json",    rootUrl + "/tournaments/7/series/0/matches/0/bans/",             "/bans/", TournamentBanCollection.class);
        addChild(                        ggggchild, "Static",           UidBase + "TournamentStaticSceneData-v2+json",      rootUrl + "/tournaments/7/series/0/matches/0/static/",           "/static/", DocType.Group, TournamentStatic.class);
        addChild(                        ggggchild, "PilotStats",       UidBase + "TournamentPilotStatsCollection-v1+json", rootUrl + "/tournaments/7/series/0/matches/0/pilotstats/",       "/pilotstats/", TournamentPilotStatusCollection.class);
        Endpoint gggggchild = addChild(  ggggchild, "Realtime",         null,                                               rootUrl + "/tournaments/7/series/0/matches/0/realtime/",         "/realtime/", DocType.PlaceHolder, null);
        addChild(                       gggggchild, "Frame",            UidBase + "TournamentRealtimeMatchFrame-v2+json",   rootUrl + "/tournaments/7/series/0/matches/0/realtime/0/",       "/0/", TournamentMatchFrame.class);
        ggchild = addChild(                 gchild, "Teams",            null,                                               rootUrl + "/tournaments/7/teams/",                               "/teams/", DocType.PlaceHolder, null);
        addChild(                          ggchild, "Team",             UidBase + "TournamentTeam-v1+json",                 rootUrl + "/tournaments/7/teams/0/",                             "/0/", TournamentTeam.class);
        ggchild = addChild(                 gchild, "Teams",            UidBase + "TournamentTeamCollection-v1+json",       rootUrl + "/tournaments/teams/",                                 "/teams/", DocType.Group, TournamentTeamCollection.class);
        gggchild = addChild(               ggchild, "Team",             UidBase + "TournamentTeam2-v1+json",                 rootUrl + "/tournaments/teams/0/",                              "/0/", TournamentTeam2.class);  // ccp duplicate bug, fudged uid
        addChild(                         gggchild, "Members",          UidBase + "TournamentTeamMemberCollection-v1+json", rootUrl + "/tournaments/teams/0/members/",                       "/members/", TournamentTeamMemberCollection.class);
        addChild(                             root, "SolarSystems",     UidBase + "SystemCollection-v1+json",               rootUrl + "/solarsystems/",                                      "/solarsystems/", SystemCollection.class);
        addChild(                             root, "Time",             UidBase + "Time-v1+json",                           rootUrl + "/time/",                                              "/time/", CrestTime.class);
        addChild(                             root, "Decode",           UidBase + "TokenDecode-v1+json",                    rootUrl + "/decode/",                                            "/decode/", TokenDecode.class);
//      addChild(                             root, "virtualGoodStore", UidBase + "VirtualGoodStore"+Fixme,                 rootUrl + "/virtualGoodStore/",                                  "/virtualGoodStore/");
        child = addChild(                     root, "Wars",             UidBase + "WarsCollection-v1+json",                 rootUrl + "/wars/",                                              "/wars/", DocType.Group, WarsCollection.class);
        addChild(                            child, "war",              UidBase + "War-v1+json",                            rootUrl + "/wars/0/",                                            "/0/", WarsElement.class);
        //@formatter:on
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative, Class<? extends EveData> eveDataClass)
    {
        return addChild(ep, name, ruid, url, relative, DocType.Leaf, eveDataClass);
    }

    private Endpoint addChild(Endpoint ep, String name, String ruid, String url, String relative, DocType type, Class<? extends EveData> eveDataClass)
    {
        if (type == null)
            log.info("look here");
        return addChild(ep, name, null, ruid, null, null, url, relative, type, eveDataClass);
    }

    private Endpoint addChild(Endpoint ep, String name, String cuid, String ruid, String uuid, String duid, String url, String relative, DocType type, Class<? extends EveData> eveDataClass)
    {
        if (type == null)
            log.info("look here");
        Endpoint child = new Endpoint(name, cuid, ruid, uuid, duid, url, relative, type, eveDataClass);
        ep.children.add(child);
        return child;
    }

    private enum DocType
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
