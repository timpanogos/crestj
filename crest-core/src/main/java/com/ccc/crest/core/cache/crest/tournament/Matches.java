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
public class Matches implements JsonDeserializer<Matches>
{
    public volatile ExternalRef winner;
    public volatile Stats stats;
    public volatile Team redTeam;
    public volatile Bans bans;
    public volatile boolean finalized;
    public volatile ExternalRef series;
    public volatile ExternalRef tournament;
    public volatile ExternalRef staticSceneData;
    public volatile ExternalRef firstReplayFrame;
    public volatile ExternalRef lastReplayFrame;
    public volatile Team blueTeam;
    public volatile boolean inProgress;
    public volatile Score score;


    public Matches()
    {
    }

    private static final String WinnerKey = "winner";
    private static final String StatsKey = "stats";
    private static final String RedTeamKey = "redTeam";
    private static final String BansKey = "bans";
    private static final String FinalizedKey = "finalized";
    private static final String SeriesKey = "series";
    private static final String TournamentKey = "tournament";
    private static final String StaticSceneKey = "staticSceneData";
    private static final String FirstReplayKey = "firstReplayFrame";
    private static final String LastReplayKey = "lastReplayFrame";
    private static final String BlueTeamKey = "blueTeam";
    private static final String InProgressKey = "inProgress";
    private static final String ScoreKey = "score";

    @Override
    public Matches deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (WinnerKey.equals(key))
            {
                winner = new ExternalRef();
                winner.deserialize(value, typeOfT, context);
            } else if (StatsKey.equals(key))
            {
                stats = new Stats();
                stats.deserialize(json, typeOfT, context);
            }else if (RedTeamKey.equals(key))
            {
                redTeam = new Team();
                redTeam.deserialize(value, typeOfT, context);
            }
            else if (BansKey.equals(key))
            {
                bans = new Bans();
                bans.deserialize(value, typeOfT, context);
            }
            else if (FinalizedKey.equals(key))
                finalized = value.getAsBoolean();
            else if (SeriesKey.equals(key))
            {
                series = new ExternalRef();
                series.deserialize(value, typeOfT, context);
            }
            else if (TournamentKey.equals(key))
            {
                tournament = new ExternalRef();
                tournament.deserialize(value, typeOfT, context);
            }
            else if (StaticSceneKey.equals(key))
            {
                staticSceneData = new ExternalRef();
                staticSceneData.deserialize(value, typeOfT, context);
            }
            else if (FirstReplayKey.equals(key))
            {
                firstReplayFrame = new ExternalRef();
                firstReplayFrame.deserialize(value, typeOfT, context);
            }
            else if (LastReplayKey.equals(key))
            {
                lastReplayFrame = new ExternalRef();
                lastReplayFrame.deserialize(value, typeOfT, context);
            }
            else if (BlueTeamKey.equals(key))
            {
                blueTeam = new Team();
                blueTeam.deserialize(value, typeOfT, context);
            }
            else if (InProgressKey.equals(key))
                inProgress = value.getAsBoolean();
            else if (ScoreKey.equals(key))
            {
                score = new Score();
                score.deserialize(value, typeOfT, context);
            }else
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
        format.ttl("finalized: ", finalized);
        format.ttl("inProgress: ", inProgress);

        format.ttl("redTeam: ");
        format.inc();
        redTeam.toString(format);
        format.dec();

        format.ttl("blueTeam: ");
        format.inc();
        blueTeam.toString(format);
        format.dec();

        format.ttl("winner: ");
        format.inc();
        winner.toString(format);
        format.dec();

        format.ttl("stats: ");
        format.inc();
        stats.toString(format);
        format.dec();

        format.ttl("bans: ");
        format.inc();
        bans.toString(format);
        format.dec();

        format.ttl("series: ");
        format.inc();
        series.toString(format);
        format.dec();

        format.ttl("tournament: ");
        format.inc();
        tournament.toString(format);
        format.dec();

        format.ttl("staticSceneData: ");
        format.inc();
        staticSceneData.toString(format);
        format.dec();

        format.ttl("firstReplayFrame: ");
        format.inc();
        firstReplayFrame.toString(format);
        format.dec();

        format.ttl("lastReplayFrame: ");
        format.inc();
        lastReplayFrame.toString(format);
        format.dec();
        format.dec();
        return format;
    }
}
