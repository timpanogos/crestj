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

import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Alliance implements JsonDeserializer<Alliance>
{
    public volatile String idStr;
    public volatile String shortName;
    public volatile String allianceUrl;
    public volatile long id;
    public volatile String name;
    
    public Alliance()
    {
    }

    private static final String IdStrKey = "id_str";
    private static final String ShortNameKey = "shortName";
    private static final String HrefKey = "href";
    private static final String IdKey = "id";
    private static final String NameKey = "name";
    
    @Override
    public Alliance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> classIter = ((JsonObject)json).entrySet().iterator();
        
        Entry<String, JsonElement> classEntry = classIter.next();
        if (!classEntry.getKey().equals(IdStrKey))
            throw new JsonParseException("Expected: " + IdStrKey + " rec: " + classEntry.getKey());
        idStr = classEntry.getValue().getAsString();
        
        classEntry = classIter.next();
        if (!classEntry.getKey().equals(ShortNameKey))
            throw new JsonParseException("Expected: " + ShortNameKey + " rec: " + classEntry.getKey());
        shortName = classEntry.getValue().getAsString();
        
        classEntry = classIter.next();
        if (!classEntry.getKey().equals(HrefKey))
            throw new JsonParseException("Expected: " + HrefKey + " rec: " + classEntry.getKey());
        allianceUrl = classEntry.getValue().getAsString();
        
        classEntry = classIter.next();
        if (!classEntry.getKey().equals(IdKey))
            throw new JsonParseException("Expected: " + IdKey + " rec: " + classEntry.getKey());
        id = classEntry.getValue().getAsLong();
        
        classEntry = classIter.next();
        if (!classEntry.getKey().equals(NameKey))
            throw new JsonParseException("Expected: " + NameKey + " rec: " + classEntry.getKey());
        name = classEntry.getValue().getAsString();
        
        while(classIter.hasNext())
        {
            classEntry = classIter.next();
            LoggerFactory.getLogger(getClass()).warn(getClass().getSimpleName() + " has a field not currently being handled: \n" + classEntry.toString());
        }
        if(!idStr.equals(Long.toString(id)))
            LoggerFactory.getLogger(getClass()).warn("idStr is not equal to id " + idStr + ", " +id);
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
        format.ttl("idStr: ", idStr);
        format.ttl("shortName: ", shortName);
        format.ttl("allianceUrl: ", allianceUrl);
        format.ttl("id: ", id);
        format.ttl("name: ", name);
        return format;
    }
}
