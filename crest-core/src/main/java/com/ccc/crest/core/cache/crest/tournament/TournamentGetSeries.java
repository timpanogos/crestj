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
package com.ccc.crest.core.cache.crest.tournament;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.crest.ExternalRef;
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class TournamentGetSeries extends BaseEveData implements JsonDeserializer<TournamentGetSeries>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String VersionBase = "application/vnd.ccp.eve.TournamentCollection";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    private volatile Series series;

    public TournamentGetSeries()
    {
    }

    public Series getSeries()
    {
        return series;
    }
    
    public static String getVersion()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getVersion();
    }
    
    public static String getUrl()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getUri();
    }

    public static Future<EveData> getFuture(long tournamentId, CrestResponseCallback callback) throws Exception
    {
        String url = getUrl();
        url += tournamentId + "/series/";
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(TournamentGetSeries.class, new TournamentGetSeries());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, url,
                        gson.create(), null, TournamentGetSeries.class,
                        callback,
                        ReadScope, getVersion(), continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public TournamentGetSeries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        series = new Series();
        series = series.deserialize(json, typeOfT, context);
        if(log.isDebugEnabled())
            log.debug(series.toString());
        return this;
    }
    
    
    public class Series implements JsonDeserializer<Series>
    {
        public volatile long pageCount;
        public volatile String pageCountStr;
        public volatile String totalCountStr;
        public volatile long totalCount;
        public final List<TeamInfo> teamInfos;
        
        public Series()
        {
            teamInfos = new ArrayList<>();
        }

        private static final String TotalCountStrKey = "totalCount_str";
        private static final String ItemsKey = "items";
        private static final String PageCountKey = "pageCount"; 
        private static final String PageCountStrKey = "pageCount_str"; 
        private static final String TotalCountKey = "totalCount";

        @Override
        public Series deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (TotalCountStrKey.equals(key))
                    totalCountStr = value.getAsString();
                else if (ItemsKey.equals(key))
                {
                    JsonElement objectElement = objectEntry.getValue();
                    if (!objectElement.isJsonArray())
                        throw new JsonParseException("Expected " + ItemsKey + " array received json element " + objectElement.toString());
                    int size = ((JsonArray) objectElement).size();
                    for (int i = 0; i < size; i++)
                    {
                        JsonElement childElement = ((JsonArray) objectElement).get(i);
                        TeamInfo child = new TeamInfo();
                        teamInfos.add(child);
                        child.deserialize(childElement, typeOfT, context);
                    }
                }
                else if (PageCountKey.equals(key))
                    pageCount = value.getAsLong();
                else if (PageCountStrKey.equals(key))
                    pageCountStr = value.getAsString();
                else if (TotalCountKey.equals(key))
                    totalCount = value.getAsLong();
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName());
            format.inc();
            format.ttl("pageCount: ", pageCount);
            format.ttl("pageCountStr: ", pageCountStr);
            format.ttl("totalCount: ", totalCount);
            format.ttl("totalCountStr: ", totalCountStr);
            format.ttl("teams: ");
            format.inc();
            for (TeamInfo info : teamInfos)
                info.toString(format);
            format.dec();
            format.dec();
            return format;
        }
    }
    
    public class TeamInfo implements JsonDeserializer<TeamInfo>
    {
        public volatile long length;
        public volatile String lengthStr;
        public volatile TeamWrapper redTeam;
        public volatile TeamWrapper blueTeam; 
        public volatile MatchesWon matchesWon;
        public volatile ExternalRef matches;
        public volatile ExternalRef self;
        public volatile TeamWrapper winner; 
        public volatile TeamWrapper loser; 
        public volatile Structure structure; 
        
        public TeamInfo()
        {
        }

        private static final String RedTeamKey = "redTeam";
        private static final String WonKey = "matchesWon";
        private static final String MatchesKey = "matches";
        private static final String SelfKey = "self";
        private static final String WinnerKey = "winner";
        private static final String LoserKey = "loser";
        private static final String LengthKey = "length";
        private static final String LengthStrKey = "length_str";
        private static final String BlueTeamKey = "blueTeam";
        private static final String StructureKey = "structure";

        @Override
        public TeamInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (RedTeamKey.equals(key))
                {
                    redTeam = new TeamWrapper();
                    redTeam.deserialize(value, typeOfT, context);
                }
                else if (WonKey.equals(key))
                {
                    matchesWon = new MatchesWon();
                    matchesWon.deserialize(value, typeOfT, context);
                }
                else if (MatchesKey.equals(key))
                {
                    matches = new ExternalRef();
                    matches.deserialize(value, typeOfT, context);
                }
                else if (SelfKey.equals(key))
                {
                    self = new ExternalRef();
                    self.deserialize(value, typeOfT, context);
                }
                else if (WinnerKey.equals(key))
                {
                    winner = new TeamWrapper();
                    winner.deserialize(value, typeOfT, context);
                }
                else if (LoserKey.equals(key))
                {
                    loser = new TeamWrapper();
                    loser.deserialize(value, typeOfT, context);
                }
                else if (LengthKey.equals(key))
                    length = value.getAsLong();
                else if (LengthStrKey.equals(key))
                    lengthStr = value.getAsString();
                else if (BlueTeamKey.equals(key))
                {
                    blueTeam = new TeamWrapper();
                    blueTeam.deserialize(value, typeOfT, context);
                }
                else if (StructureKey.equals(key))
                {
                    structure = new Structure();
                    structure.deserialize(value, typeOfT, context);
                }
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName());
            format.inc();
            format.ttl("length: ", length);
            format.ttl("lengthStr: ", lengthStr);
            format.ttl("redTeam: ");
            format.inc();
            redTeam.toString(format);
            format.dec();
            format.ttl("blueTeam: ");
            format.inc();
            blueTeam.toString(format);
            format.dec();
            format.ttl("matchesWon: ");
            format.inc();
            matchesWon.toString(format);
            format.dec();
            format.ttl("matches: ");
            format.inc();
            matches.toString(format);
            format.dec();
            format.ttl("self: ");
            format.inc();
            self.toString(format);
            format.dec();
            format.ttl("winner: ");
            format.inc();
            winner.toString(format);
            format.dec();
            format.ttl("loser: ");
            format.inc();
            loser.toString(format);
            format.dec();
            format.dec();
            return format;
        }
    }

    public class TeamWrapper implements JsonDeserializer<TeamWrapper>
    {
        public volatile boolean decided;
        public volatile boolean bye;
        public volatile Team team;
        
        public TeamWrapper()
        {
        }

        private static final String TeamKey = "team";
        private static final String DecidedKey = "isDecided";
        private static final String ByeKey = "isBye";
        
        @Override
        public TeamWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (TeamKey.equals(key))
                {
                    team = new Team();
                    team.deserialize(value, typeOfT, context);
                }
                else if (DecidedKey.equals(key))
                    decided = value.getAsBoolean();
                else if (ByeKey.equals(key))
                    bye = value.getAsBoolean();
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName());
            format.inc();
            format.ttl("team: ");
            format.inc();
            team.toString(format);
            format.dec();
            format.dec();
            return format;
        }
    }
    
    
    public class MatchesWon implements JsonDeserializer<MatchesWon>
    {
        public volatile String blueTeamStr;
        public volatile long blueTeam;
        public volatile String redTeamStr;
        public volatile long redTeam;
        
        public MatchesWon()
        {
        }

        private static final String BlueStringKey = "blueTeam_str";
        private static final String RedKey = "redTeam";
        private static final String RedStringKey = "redTeam_str";
        private static final String BlueKey = "blueTeam";

        @Override
        public MatchesWon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (BlueStringKey.equals(key))
                    blueTeamStr = value.getAsString();
                else if (RedKey.equals(key))
                    redTeam = value.getAsLong();
                else if (RedStringKey.equals(key))
                    redTeamStr = value.getAsString();
                else if (BlueKey.equals(key))
                    blueTeam = value.getAsLong();
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName());
            format.inc();
            format.ttl("redTeam: ", redTeam);
            format.ttl("redTeamStr: ", redTeamStr);
            format.ttl("blueTeam: ", blueTeam);
            format.ttl("blueTeamStr: ", blueTeamStr);
            format.dec();
            return format;
        }
    }
    
    public class Team implements JsonDeserializer<Team>
    {
        public volatile String teamUrl;
        public volatile String teamName;
        
        public Team()
        {
        }

        private static final String TeamsUrlKey = "href";
        private static final String NameKey = "teamName"; 

        @Override
        public Team deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (TeamsUrlKey.equals(key))
                    teamUrl = value.getAsString();
                else if (NameKey.equals(key))
                    teamName = value.getAsString();
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName());
            format.inc();
            format.ttl("teamName: ", teamName);
            format.ttl("teamUrl: ", teamUrl);
            format.dec();
            return format;
        }
    }
    
    public class Structure implements JsonDeserializer<Structure>
    {
        public volatile ExternalRef outgoingWinner;
        public volatile ExternalRef incomingRed;
        public volatile ExternalRef incomingBlue;
        
        public Structure()
        {
        }
        
        private static final String OutgoingKey = "outgoingWinner";
        private static final String IncomingRedKey = "incomingRed";
        private static final String IncomingBlueKey = "incomingBlue";
        
        @Override
        public Structure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (OutgoingKey.equals(key))
                {
                    outgoingWinner = new ExternalRef();
                    outgoingWinner.deserialize(value, typeOfT, context);
                }
                else if (IncomingRedKey.equals(key))
                {
                    incomingRed = new ExternalRef();
                    incomingRed.deserialize(value, typeOfT, context);
                }else if (IncomingBlueKey.equals(key))
                {
                    incomingBlue = new ExternalRef();
                    incomingBlue.deserialize(value, typeOfT, context);
                }
                else
                    LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            }
            return this;
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
            format.ttl("outgoingWinner:");
            format.inc();
            outgoingWinner.toString(format);
            format.dec();
            format.ttl("incomingRed:");
            format.inc();
            incomingRed.toString(format);
            format.dec();
            format.ttl("incomingBlue:");
            format.inc();
            incomingBlue.toString(format);
            format.dec();
            format.dec();
            return format;
        }
    }
}
