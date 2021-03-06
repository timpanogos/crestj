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
package com.ccc.crest.core.cache.crest.schema.option;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class AcceptType implements JsonDeserializer<AcceptType>
{
    public volatile String name;
    public volatile CcpType ccpType;

    private static final String AcceptDumpKey = "jsonDumpOfStructure";
    private static final String AcceptNameKey = "name";
    
    @Override
    public AcceptType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (AcceptDumpKey.equals(key))
            {
                CcpType schemaTypeElement = new CcpType(key);
                GsonBuilder gson = new GsonBuilder();
                gson.registerTypeAdapter(CcpType.class, schemaTypeElement);
                ccpType = gson.create().fromJson(value.getAsString(), CcpType.class);
            }
            else if (AcceptNameKey.equals(key))
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
        format.ttl("name: ", name);
        if(ccpType == null)
            format.ttl("ccpType: null");
        else
        {
            format.ttl("ccpType: ");
            format.inc();
            ccpType.toString(format);
            format.dec();
        }
        format.dec();
        return format;
    }
}
