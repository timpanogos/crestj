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
package com.ccc.crest.core.cache.api.schema;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Representations extends BaseEveData implements JsonDeserializer<Representations>
{
    private static final long serialVersionUID = 546207299369351871L;
    public final List<Representation> representations;
    
    public Representations()
    {
        representations = new ArrayList<>();
    }
    
    private static final String RepresentationsKey = "representations";
    
    @Override
    public Representations deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Entry<String, JsonElement> representationsEntry = ((JsonObject)json).entrySet().iterator().next(); 
        if (!representationsEntry.getKey().equals(RepresentationsKey))
            throw new JsonParseException("Expected topObj key: " + RepresentationsKey + " : " + representationsEntry.getKey());
        JsonElement representationsElement = representationsEntry.getValue();
        if (!representationsElement.isJsonArray())
            throw new JsonParseException("Expected representations array received json element " + representationsElement.toString());
        int size = ((JsonArray)representationsElement).size();
        for(int i=0; i < size; i++)
        {
            JsonElement repElement = ((JsonArray)representationsElement).get(i);
            Representation representation = new Representation();
            representations.add(representation);
            representation.deserialize(repElement, typeOfT, context);
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
        format.ttl("representations:");
        format.inc();
        for(Representation rep : representations)
            rep.toString(format);
        format.dec();
        return format;
    }
}
