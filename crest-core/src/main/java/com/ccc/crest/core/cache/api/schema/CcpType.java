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

import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class CcpType implements JsonDeserializer<CcpType>
{
    public volatile String name;
    public volatile String description;
    public volatile boolean optional;
    public volatile String extraData;
    public volatile String typePrettyName;
    public volatile String type;
    public final List<CcpType> children;
    
    private static final String DescriptionKey = "description";
    private static final String OptionalKey = "isOptional";
    private static final String ExtraKey = "extraData";
    private static final String SubContentKey = "subContent";
    private static final String PrettyTypeKey = "typePrettyName";
    private static final String TypeKey = "type";
    private static final String NullValue = "null";
    
    public CcpType(String name)
    {
        this.name = name;
        children = new ArrayList<>();
    }
    
    @Override
    public CcpType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> fieldIter = ((JsonObject)json).entrySet().iterator();
        Entry<String, JsonElement> entry = fieldIter.next(); 
        if (!entry.getKey().equals(DescriptionKey))
            throw new JsonParseException("Expected: " + DescriptionKey + " rec: " + entry.getKey());
        description = checkNull(entry.getValue());
        
        entry = fieldIter.next(); 
        if (!entry.getKey().equals(OptionalKey))
            throw new JsonParseException("Expected: " + OptionalKey + " rec: " + entry.getKey());
        optional = entry.getValue().getAsBoolean();
        
        entry = fieldIter.next(); 
        if (!entry.getKey().equals(ExtraKey))
            throw new JsonParseException("Expected: " + ExtraKey + " rec: " + entry.getKey());
        extraData = checkNull(entry.getValue());
        
        entry = fieldIter.next(); 
        if (!entry.getKey().equals(SubContentKey))
            throw new JsonParseException("Expected: " + SubContentKey + " rec: " + entry.getKey());
        JsonElement subContentElement = entry.getValue();
        if(!subContentElement.isJsonNull())
        {
            Iterator<Entry<String, JsonElement>> varsIter = ((JsonObject)entry.getValue()).entrySet().iterator();
            do
            {
                if(!varsIter.hasNext())
                    break;
                Entry<String, JsonElement> varsEntry = varsIter.next(); 
                CcpType subContent = new CcpType(varsEntry.getKey());
                subContent = subContent.deserialize(varsEntry.getValue(), typeOfT, context);
                children.add(subContent);
            }while(true);
        }
        
        entry = fieldIter.next(); 
        if (!entry.getKey().equals(PrettyTypeKey))
            throw new JsonParseException("Expected: " + PrettyTypeKey + " rec: " + entry.getKey());
        typePrettyName = checkNull(entry.getValue());
     
        entry = fieldIter.next(); 
        if (!entry.getKey().equals(TypeKey))
            throw new JsonParseException("Expected: " + TypeKey + " rec: " + entry.getKey());
        type = checkNull(entry.getValue());
        return this;
    }
    
    private String checkNull(JsonElement value)
    {
        if(value.isJsonNull())
            return null;
        if(NullValue.equals(value.getAsString()))
            return null;
        return value.getAsString();
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
        format.ttl("name: ", name);
        format.ttl("description: ", description);
        format.ttl("optional: ", optional);
        format.ttl("extraData: ", extraData);
        format.ttl("typePrettyName: ", typePrettyName);
        format.ttl("type: ", type);
        format.ttl("children: ");
        format.inc();
        if(children.size() == 0)
            format.ttl("none");
        for(int i=0; i < children.size(); i++)
            children.get(i).toString(format);
        format.dec();
        return format;
    }
}
