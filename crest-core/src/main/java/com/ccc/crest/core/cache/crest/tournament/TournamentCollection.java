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
public class TournamentCollection extends BaseEveData implements JsonDeserializer<TournamentCollection>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(false);
    public static final String VersionBase = "application/vnd.ccp.eve.TournamentCollection";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    private volatile Tournaments tournaments;

    public TournamentCollection()
    {
    }

    public Tournaments getTournaments()
    {
        return tournaments;
    }

    public static String getVersion()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getVersion();
    }

    public static String getUrl()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getUri();
    }

    public static Future<EveData> getFuture(CrestResponseCallback callback) throws Exception
    {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(TournamentCollection.class, new TournamentCollection());

        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getUrl(),
                        gson.create(), null, TournamentCollection.class,
                        callback,
                        ReadScope, getVersion(), continueRefresh, false);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public TournamentCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Gson gson = new Gson();
        tournaments = new Tournaments();
        tournaments = tournaments.deserialize(json, typeOfT, context);
        if(log.isDebugEnabled())
            log.debug(tournaments.toString());
        return this;
    }

    public class Tournaments implements JsonDeserializer<Tournaments>
    {
        public volatile String totalCountStr;
        public final List<Tournament> tournaments;
        public volatile long pageCount;
        public volatile String pageCountStr;
        public volatile long totalCount;

        public Tournaments()
        {
            tournaments = new ArrayList<>();
        }

        private static final String TotalCountStringKey = "totalCount_str";
        private static final String ItemsKey = "items";
        private static final String PageCountKey = "pageCount";
        private static final String PageCountStringKey = "pageCount_str";
        private static final String TotalCountKey = "totalCount";

        @Override
        public Tournaments deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (TotalCountStringKey.equals(key))
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
                        Tournament child = new Tournament();
                        tournaments.add(child);
                        child.deserialize(childElement, typeOfT, context);
                    }
                }
                else if (PageCountKey.equals(key))
                    pageCount = value.getAsLong();
                else if (PageCountStringKey.equals(key))
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
            format.ttl(getClass().getSimpleName());
            format.inc();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl("pageCount: ", pageCount);
            format.ttl("pageCountStr: ", pageCountStr);
            format.ttl("totalCount: ", totalCount);
            format.ttl("totalCountStr: ", totalCountStr);
            format.ttl("tournaments: ");
            format.inc();
            for (Tournament tournament : tournaments)
                tournament.toString(format);
            format.dec();
            return format;
        }
    }

    public class Tournament implements JsonDeserializer<Tournament>
    {
        public volatile TournamentHrefWrapper hrefWrapper;

        public Tournament()
        {
        }

        private static final String HrefKey = "href";

        @Override
        public Tournament deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (HrefKey.equals(key))
                {
                    hrefWrapper = new TournamentHrefWrapper();
                    hrefWrapper.deserialize(value, typeOfT, context);
                } else
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
            format.ttl("hrefWrapper: ");
            format.inc();
            hrefWrapper.toString(format);
            format.dec();
            return format;
        }
    }

    public class TournamentHrefWrapper implements JsonDeserializer<TournamentHrefWrapper>
    {
        public volatile String tournamentUrl;
        public volatile String name;

        public TournamentHrefWrapper()
        {
        }

        private static final String HrefKey = "href";
        private static final String NameKey = "name";

        @Override
        public TournamentHrefWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
            while (objectIter.hasNext())
            {
                Entry<String, JsonElement> objectEntry = objectIter.next();
                String key = objectEntry.getKey();
                JsonElement value = objectEntry.getValue();
                if (HrefKey.equals(key))
                    tournamentUrl = value.getAsString();
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
            format.ttl("tournamentUrl: ", tournamentUrl);
            return format;
        }
    }
}
