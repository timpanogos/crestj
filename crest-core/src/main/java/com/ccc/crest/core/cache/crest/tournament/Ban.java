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
import java.util.List;
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
public class Ban implements JsonDeserializer<Ban>
{
    public volatile List<String> typeBans;
    public volatile ExternalRef bannedBy;

    public Ban()
    {
    }

    private static final String TypeBansKey = "typeBanshref";
    private static final String BannedByKey = "bannedBy";

    @Override
    public Ban deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (TypeBansKey.equals(key))
                LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
            else if (BannedByKey.equals(key))
            {
                bannedBy = new ExternalRef();
                bannedBy.deserialize(value, typeOfT, context);
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
        format.ttl("bannedBy: ");
        format.inc();
        bannedBy.toString(format);
        format.dec();
        format.ttl("typeBans: ");
        format.inc();
        for(String type : typeBans)
            format.ttl(type);
        if(typeBans.size() == 0)
            format.ttl("none");
        format.dec();
        format.dec();
        return format;
    }
}
