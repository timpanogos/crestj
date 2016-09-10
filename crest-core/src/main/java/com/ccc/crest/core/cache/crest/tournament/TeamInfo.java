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
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.ExternalRef;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
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
            } else if (WonKey.equals(key))
            {
                matchesWon = new MatchesWon();
                matchesWon.deserialize(value, typeOfT, context);
            } else if (MatchesKey.equals(key))
            {
                matches = new ExternalRef();
                matches.deserialize(value, typeOfT, context);
            } else if (SelfKey.equals(key))
            {
                self = new ExternalRef();
                self.deserialize(value, typeOfT, context);
            } else if (WinnerKey.equals(key))
            {
                winner = new TeamWrapper();
                winner.deserialize(value, typeOfT, context);
            } else if (LoserKey.equals(key))
            {
                loser = new TeamWrapper();
                loser.deserialize(value, typeOfT, context);
            } else if (LengthKey.equals(key))
                length = value.getAsLong();
            else if (LengthStrKey.equals(key))
                lengthStr = value.getAsString();
            else if (BlueTeamKey.equals(key))
            {
                blueTeam = new TeamWrapper();
                blueTeam.deserialize(value, typeOfT, context);
            } else if (StructureKey.equals(key))
            {
                structure = new Structure();
                structure.deserialize(value, typeOfT, context);
            } else
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
