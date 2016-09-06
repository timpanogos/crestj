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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class TournamentGetTournament extends BaseEveData implements JsonDeserializer<TournamentGetTournament>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String VersionBase = "application/vnd.ccp.eve.TournamentCollection";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    private volatile Series series;

    public TournamentGetTournament()
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
        url += tournamentId + "/";
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(TournamentGetTournament.class, new TournamentGetTournament());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, url,
                        gson.create(), null, TournamentGetTournament.class,
                        callback,
                        ReadScope, getVersion(), continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public TournamentGetTournament deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Gson gson = new Gson();
        series = new Series();
        series = series.deserialize(json, typeOfT, context);
        log.info(series.toString());
        return this;
    }
    
    public class Series implements JsonDeserializer<Series>
    {
        public volatile String name;
        public volatile String type;
        public volatile ExternalRef seriesUrl;
        public final List<TeamStats> teamStats;
        
        public Series()
        {
            teamStats = new ArrayList<>();
        }

        private static final String NameKey = "name";
        private static final String TypeKey = "type";
        private static final String SeriesUrlKey = "series"; 
        private static final String TeamStatsKey = "entries"; // optional

        @Override
        public Series deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (SeriesUrlKey.equals(key))
                {
                    seriesUrl = new ExternalRef();
                    seriesUrl.deserialize(value, typeOfT, context);
                }
                else if (TypeKey.equals(key))
                    type = value.getAsString();
                else if (NameKey.equals(key))
                    name = value.getAsString();
                else if (TeamStatsKey.equals(key))
                {
                    JsonElement objectElement = objectEntry.getValue();
                    if (!objectElement.isJsonArray())
                        throw new JsonParseException("Expected " + TeamStatsKey + " array received json element " + objectElement.toString());
                    int size = ((JsonArray) objectElement).size();
                    for (int i = 0; i < size; i++)
                    {
                        JsonElement childElement = ((JsonArray) objectElement).get(i);
                        TeamStats child = new TeamStats();
                        teamStats.add(child);
                        child.deserialize(childElement, typeOfT, context);
                    }
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
            format.ttl("name: ", name);
            format.ttl("type: ", type);
            format.ttl("SeriesUrl: ");
            format.inc();
            seriesUrl.toString(format);
            format.dec();
            format.ttl("teamStats: ");
            format.inc();
            for (TeamStats stats : teamStats)
                stats.toString(format);
            return format;
        }
    }
    
    public class TeamStats implements JsonDeserializer<TeamStats>
    {
        public volatile ExternalRef matches;
        public volatile String teamsUrl;
        public volatile String name;
        
        public TeamStats()
        {
        }

        private static final String MatchesKey = "teamStats";
        private static final String TeamsUrlKey = "href";
        private static final String NameKey = "name"; 

        @Override
        public TeamStats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (MatchesKey.equals(key))
                {
                    matches = new ExternalRef();
                    matches.deserialize(value, typeOfT, context);
                }
                else if (TeamsUrlKey.equals(key))
                    teamsUrl = value.getAsString();
                else if (NameKey.equals(key))
                    name = value.getAsString();
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
            format.ttl("name: ", name);
            format.ttl("teamsUrl: ", teamsUrl);
            format.ttl("MatchesUrl: ");
            format.inc();
            matches.toString(format);
            format.dec();
            return format;
        }
    }
}
