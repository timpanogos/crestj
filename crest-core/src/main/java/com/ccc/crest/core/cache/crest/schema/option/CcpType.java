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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.tools.TabToLevel;
import com.google.gson.JsonArray;
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
    public final List<String> extraData;
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
        extraData = new ArrayList<>();
    }

    @Override
    public CcpType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (DescriptionKey.equals(key))
                description = checkNull(value);
            else if (OptionalKey.equals(key))
                optional = value.getAsBoolean();
            else if (ExtraKey.equals(key))
            {
                JsonElement objectElement = objectEntry.getValue();
                if (objectElement.isJsonArray())
                {
                    int size = ((JsonArray) objectElement).size();
                    for (int i = 0; i < size; i++)
                    {
                        JsonElement childElement = ((JsonArray) objectElement).get(i);
                        extraData.add(childElement.getAsString());
                    }
                }else
                    extraData.add(checkNull(value));
                    
            } else if (SubContentKey.equals(key))
            {
                if (!value.isJsonNull())
                {
                    Iterator<Entry<String, JsonElement>> varsIter = ((JsonObject) value).entrySet().iterator();
                    do
                    {
                        if (!varsIter.hasNext())
                            break;
                        Entry<String, JsonElement> varsEntry = varsIter.next();
                        CcpType subContent = new CcpType(varsEntry.getKey());
                        subContent = subContent.deserialize(varsEntry.getValue(), typeOfT, context);
                        children.add(subContent);
                    } while (true);
                }
            } else if (PrettyTypeKey.equals(key))
                typePrettyName = checkNull(value);
            else if (TypeKey.equals(key))
                type = checkNull(value);
            else
                LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
        }
        return this;
    }

    private String checkNull(JsonElement value)
    {
        try
        {
            if (value.isJsonNull())
                return null;
            if (NullValue.equals(value.getAsString()))
                return null;
            return value.getAsString();
        } catch (Exception e)
        {
            System.out.println("look here");
            return null;
        }
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
        if (children.size() == 0)
            format.ttl("none");
        for (int i = 0; i < children.size(); i++)
            children.get(i).toString(format);
        format.dec();
        return format;
    }
}
