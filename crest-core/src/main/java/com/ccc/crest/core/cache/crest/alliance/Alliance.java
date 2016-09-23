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
package com.ccc.crest.core.cache.crest.alliance;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.ExternalRef;
import com.ccc.crest.da.PagedItem;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Alliance implements PagedItem, JsonDeserializer<Alliance>
{
    public volatile String idStr;
    public volatile String shortName;
    public volatile ExternalRef allianceUrl;
    public volatile long id;
    public volatile String name;


    public Alliance()
    {
    }

    public Alliance(String idStr, String shortName, long id, String name)
    {
        this.idStr = idStr;
        this.shortName = shortName;
        this.id = id;
        this.name = name;
    }

    private static final String IdKey = "id";
    private static final String ShortNameKey = "shortName";
    private static final String NameKey = "name";
    private static final String HrefKey = "href";
    private static final String IdStrKey = "id_str";

    @Override
    public Alliance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (IdStrKey.equals(key))
                idStr = value.getAsString();
            else if (ShortNameKey.equals(key))
                shortName = value.getAsString();
            else if (HrefKey.equals(key))
            {
                allianceUrl = new ExternalRef();
                allianceUrl.deserialize(value, typeOfT, context);
            }
            else if (IdKey.equals(key))
                id = value.getAsLong();
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
        return toString(format).toString();
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl(getClass().getSimpleName());
        format.inc();
        format.ttl("id: ", id);
        format.ttl("shortName: ", shortName);
        format.ttl("name: ", name);
        format.ttl("allianceUrl: ", allianceUrl);
        format.ttl("idStr: ", idStr);
        format.dec();
        return format;
    }
}
