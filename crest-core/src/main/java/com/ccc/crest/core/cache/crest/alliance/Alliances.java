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
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.crest.da.AllianceData;
import com.ccc.crest.da.PagingData;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Alliances extends Paging implements JsonDeserializer<Alliances>
{
    public Alliances()
    {
    }

    public Alliances(PagingData pagingData, List<AllianceData> allianceList)
    {
        super(pagingData, allianceList.get(0).page);
        for(AllianceData data : allianceList)
            items.add(new Alliance(""+data.id, data.shortName, data.id, data.name));
    }

    private static final String ItemsKey = "items";

    @Override
    public Alliances deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if(pagingDeserialize(key, value))
                continue;
            if (ItemsKey.equals(key))
            {
                JsonElement objectElement = objectEntry.getValue();
                if (!objectElement.isJsonArray())
                    throw new JsonParseException("Expected " + ItemsKey + " array received json element " + objectElement.toString());
                int size = ((JsonArray) objectElement).size();
                for (int i = 0; i < size; i++)
                {
                    JsonElement childElement = ((JsonArray) objectElement).get(i);
                    Alliance child = new Alliance();
                    items.add(child);
                    child.deserialize(childElement, typeOfT, context);
                }
            }
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
}
