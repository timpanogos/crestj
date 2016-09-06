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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Alliances implements JsonDeserializer<Alliances>
{
    public volatile String totalCountStr;
    public volatile long pageCount;
    public final List<Alliance> alliances;
    public volatile long totalCount;
    public volatile String next;
    public volatile String previous;
    public volatile String pageCountStr;
    
    public Alliances()
    {
        alliances = new ArrayList<>();
    }

    private static final String TotalCountStringKey = "totalCount_str";
    private static final String PageCountKey = "pageCount";
    private static final String NextKey = "next";           // optional 
    private static final String PreviousKey = "previous";   // optional
    private static final String ItemsKey = "items";
    private static final String TotalCountKey = "totalCount";
    private static final String PageCountStringKey = "pageCount_str";
    
    @Override
    public Alliances deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        String classname = getClass().getSimpleName();
        String childname = Alliance.class.getSimpleName();
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject)json).entrySet().iterator();
// schema does not show this here        
        Entry<String, JsonElement> objectEntry = objectIter.next(); 
        if (!objectEntry.getKey().equals(TotalCountStringKey))
            throw new JsonParseException("Expected topObj key: " + TotalCountStringKey + " : " + objectEntry.getKey());
        totalCountStr = objectEntry.getValue().getAsString();
        
        objectEntry = objectIter.next(); 
        if (!objectEntry.getKey().equals(PageCountKey))
            throw new JsonParseException("Expected topObj key: " + PageCountKey + " : " + objectEntry.getKey());
        pageCount = objectEntry.getValue().getAsLong();
        
        objectEntry = objectIter.next(); 
        if (!objectEntry.getKey().equals(ItemsKey))
            throw new JsonParseException("Expected topObj key: " + ItemsKey + " : " + objectEntry.getKey());
        
        JsonElement objectElement = objectEntry.getValue();
        if (!objectElement.isJsonArray())
            throw new JsonParseException("Expected " + childname + " array received json element " + objectElement.toString());
        
        int size = ((JsonArray)objectElement).size();
        for(int i=0; i < size; i++)
        {
            JsonElement childElement = ((JsonArray)objectElement).get(i);
            Alliance child = new Alliance();
            alliances.add(child);
            child.deserialize(childElement, typeOfT, context);
        }

// schema mess up        
//        objectEntry = objectIter.next(); 
//        if (!objectEntry.getKey().equals(TotalCountKey))
//            throw new JsonParseException("Expected topObj key: " + TotalCountKey + " : " + objectEntry.getKey());
//        totalCount = objectEntry.getValue().getAsLong();
        
        objectEntry = objectIter.next();
//        if (objectEntry.getKey().equals(NextKey))
//            next = ExternalRef.getUrl(objectEntry.getValue(), typeOfT, context);
//        else if (objectEntry.getKey().equals(PreviousKey))
//            previous = ExternalRef.getUrl(objectEntry.getValue(), typeOfT, context);
//        else if (!objectEntry.getKey().equals(TotalCountKey))
//            throw new JsonParseException("Expected " + TotalCountKey + " array received json element " + objectEntry.toString());
//        else totalCount = objectEntry.getValue().getAsLong();
        
        if(objectIter.hasNext())
        {
            objectEntry = objectIter.next();
//            if (objectEntry.getKey().equals(PreviousKey))
//                previous = ExternalRef.getUrl(objectEntry.getValue(), typeOfT, context);
//            else if (!objectEntry.getKey().equals(TotalCountKey))
//                throw new JsonParseException("Expected " + TotalCountKey + " array received json element " + objectEntry.toString());
//            else totalCount = objectEntry.getValue().getAsLong();
        }
        if(objectIter.hasNext())
        {
            objectEntry = objectIter.next();
            if (objectEntry.getKey().equals(TotalCountKey))
                totalCount = objectEntry.getValue().getAsLong();
            else if (!objectEntry.getKey().equals(PageCountStringKey))
                throw new JsonParseException("Expected " + PageCountStringKey + " array received json element " + objectEntry.toString());
            else
                pageCountStr = objectEntry.getValue().getAsString();
        }        
        
        while(objectIter.hasNext())
        {
            Entry<String, JsonElement> entry = objectIter.next();
            LoggerFactory.getLogger(getClass()).warn(classname + " has a field not currently being handled: \n" + entry.toString());
        }
        return this;
    }
}
