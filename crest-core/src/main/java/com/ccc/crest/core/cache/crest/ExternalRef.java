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
package com.ccc.crest.core.cache.crest;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class ExternalRef implements JsonDeserializer<ExternalRef>
{
    private volatile String url;
    
    private ExternalRef()
    {
    }

    public static String getUrl(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        ExternalRef externalRef = new ExternalRef();
        externalRef.deserialize(json, typeOfT, context);
        return externalRef.url;
    }
    
    private static final String HrefKey = "href";
    
    @Override
    public ExternalRef deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> classIter = ((JsonObject)json).entrySet().iterator();
        Entry<String, JsonElement> classEntry = classIter.next();
        if (!classEntry.getKey().equals(HrefKey))
            throw new JsonParseException("Expected: " + HrefKey + " rec: " + classEntry.getKey());
        url = classEntry.getValue().getAsString();
        
        while(classIter.hasNext())
        {
            classEntry = classIter.next();
            LoggerFactory.getLogger(getClass()).info(getClass().getSimpleName() + " has a field not currently being handled: \n" + classEntry.toString());
        }
        return this;
    }
}
