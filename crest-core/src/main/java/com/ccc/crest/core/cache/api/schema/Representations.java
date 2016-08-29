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
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.BaseEveData;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Representations extends BaseEveData implements JsonDeserializer<Representations>
{
    public final List<Representation> representations;
    private final Logger log;
    
    public Representations()
    {
        representations = new ArrayList<>();
        log = LoggerFactory.getLogger(getClass());
    }
    
    private static final String RepresentationsKey = "representations";
    private static final String AcceptVersionKey = "version_str";
    private static final String AcceptTypeKey = "acceptType";
    private static final String AcceptDumpKey = "jsonDumpOfStructure";
    private static final String AcceptNameKey = "name";
    
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
            Iterator<Entry<String, JsonElement>> repIter = ((JsonObject)repElement).entrySet().iterator();
            Entry<String, JsonElement> repEntry = repIter.next(); 
            if (!repEntry.getKey().equals(AcceptVersionKey))
                throw new JsonParseException("Expected: " + AcceptVersionKey + " rec: " + repEntry.getKey());
            
            String acceptTypeVersion = repEntry.getValue().getAsString();
            
            Entry<String, JsonElement> typeEntry = repIter.next(); 
            if (!typeEntry.getKey().equals(AcceptTypeKey))
                throw new JsonParseException("Expected: " + AcceptTypeKey + " rec: " + typeEntry.getKey());
            
            JsonObject acceptObj = typeEntry.getValue().getAsJsonObject();
            Iterator<Entry<String, JsonElement>> acceptIter = acceptObj.entrySet().iterator();
            Entry<String, JsonElement> acceptNameEntry = acceptIter.next();
            if (!acceptNameEntry.getKey().equals(AcceptNameKey))
                throw new JsonParseException("Expected: " + AcceptNameKey + " rec: " + acceptNameEntry.getKey());
            
            String acceptTypeName = acceptNameEntry.getValue().getAsString();
            
            Entry<String, JsonElement> acceptDumpEntry = acceptIter.next();
            if (!acceptDumpEntry.getKey().equals(AcceptDumpKey))
                throw new JsonParseException("Expected: " + AcceptDumpKey + " rec: " + acceptDumpEntry.getKey());
            String dumpValue = acceptDumpEntry.getValue().getAsString();
            
            SchemaTypeElement schemaTypeElement = new SchemaTypeElement();
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(SchemaTypeElement.class, schemaTypeElement);
            Object obj = gson.create().fromJson(dumpValue, SchemaTypeElement.class);
            
            log.info("look here");
            
            Iterator<Entry<String, JsonElement>> dumpIter = ((JsonObject)acceptDumpEntry.getValue()).entrySet().iterator();
            
//            JsonElement> representationsEntry = ((JsonObject)repElement).entrySet().iterator().next(); 
            log.info("look here");
        }
//        Iterator<Entry<String, JsonElement>> repIter = ((JsonArray)representationsElement).iterator();
//        Entry<String, JsonElement> topEntry = topIter.next();
//        String topKey = topEntry.getKey();
//        JsonElement topElement = topEntry.getValue();
//        do
//        {
//            if (!topIter.hasNext())
//                break;
//            Entry<String, JsonElement> topEntry = topIter.next();
//            String topKey = topEntry.getKey();
//            JsonElement topElement = topEntry.getValue();
//            
//            log.info("look here");
//        // TODO Auto-generated method stub
//        } while (true);
        return this;
    }
}
