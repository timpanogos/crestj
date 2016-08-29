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
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class SchemaTypeElement implements JsonDeserializer<SchemaTypeElement>
{
    public volatile String name;
    public volatile String description;
    public volatile boolean optional;
    public volatile String extraData;
    public volatile SchemaType subContent;
    public volatile String typePrettyName;
    public volatile String type;
    
    private static final String DescriptionKey = "description";
    private static final String OptionalKey = "isOptional";
    private static final String ExtraKey = "extraData";
    private static final String SubContentKey = "subContent";
    private static final String PrettyTypeKey = "typePrettyName";
    private static final String TypeKey = "type";
    private static final String NullValue = "null";
    
    @Override
    public SchemaTypeElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> fieldIter = ((JsonObject)json).entrySet().iterator();
        Entry<String, JsonElement> fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(DescriptionKey))
            throw new JsonParseException("Expected: " + DescriptionKey + " rec: " + fieldEntry.getKey());
        JsonElement fieldValue = fieldEntry.getValue();
        description = checkNull(fieldEntry.getValue());
        
        fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(OptionalKey))
            throw new JsonParseException("Expected: " + OptionalKey + " rec: " + fieldEntry.getKey());
        optional = fieldEntry.getValue().getAsBoolean();
        
        fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(ExtraKey))
            throw new JsonParseException("Expected: " + ExtraKey + " rec: " + fieldEntry.getKey());
        extraData = checkNull(fieldEntry.getValue());
        
        fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(SubContentKey))
            throw new JsonParseException("Expected: " + SubContentKey + " rec: " + fieldEntry.getKey());
//        subContent = deserialize(fieldEntry.getValue(), typeOfT, context);
        fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(PrettyTypeKey))
            throw new JsonParseException("Expected: " + PrettyTypeKey + " rec: " + fieldEntry.getKey());
        typePrettyName = checkNull(fieldEntry.getValue());
     
        fieldEntry = fieldIter.next(); 
        if (!fieldEntry.getKey().equals(TypeKey))
            throw new JsonParseException("Expected: " + TypeKey + " rec: " + fieldEntry.getKey());
        type = checkNull(fieldEntry.getValue());
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
}
