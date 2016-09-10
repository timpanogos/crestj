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

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.ExternalRef;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Bans implements JsonDeserializer<Bans>
{
    public volatile ExternalRef self;
    public final List<Ban> redTeam;
    public final List<Ban> blueTeam;

    public Bans()
    {
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();
    }

    private static final String SelfKey = "self";
    private static final String RedKey = "redTeam";
    private static final String BlueKey = "blueTeam";

    @Override
    public Bans deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (SelfKey.equals(key))
            {
                self = new ExternalRef();
                self.deserialize(value, typeOfT, context);
            }
            else if (RedKey.equals(key))
            {
                JsonElement objectElement = objectEntry.getValue();
                if (!objectElement.isJsonArray())
                    throw new JsonParseException("Expected " + RedKey + " array received json element " + objectElement.toString());
                int size = ((JsonArray) objectElement).size();
                for (int i = 0; i < size; i++)
                {
                    JsonElement childElement = ((JsonArray) objectElement).get(i);
                    Ban child = new Ban();
                    redTeam.add(child);
                    child.deserialize(childElement, typeOfT, context);
                }
            }
            else if (BlueKey.equals(key))
            {
                JsonElement objectElement = objectEntry.getValue();
                if (!objectElement.isJsonArray())
                    throw new JsonParseException("Expected " + BlueKey + " array received json element " + objectElement.toString());
                int size = ((JsonArray) objectElement).size();
                for (int i = 0; i < size; i++)
                {
                    JsonElement childElement = ((JsonArray) objectElement).get(i);
                    Ban child = new Ban();
                    blueTeam.add(child);
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
        return toString(format).toString();
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl(getClass().getSimpleName());
        format.inc();

        format.ttl("self: ");
        format.inc();
        self.toString(format);
        format.dec();

        format.ttl("redTeam: ");
        format.inc();
        for(Ban ban : redTeam)
            ban.toString(format);
        format.dec();

        format.ttl("blueTeam: ");
        format.inc();
        for(Ban ban : blueTeam)
            ban.toString(format);
        format.dec();
        format.dec();
        return format;
    }
}
