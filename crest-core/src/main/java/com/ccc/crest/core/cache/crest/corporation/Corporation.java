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
package com.ccc.crest.core.cache.crest.corporation;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.ExternalRef;
import com.ccc.crest.da.PagedItem;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Corporation implements PagedItem, JsonDeserializer<Corporation>
{
    public volatile long id;
    public volatile String ticker;
    public volatile String name;
    public volatile String description;
    public volatile String corpUrl;
    public volatile Headquarters headquarters;
    public volatile String loyaltyUrl;
    public volatile String idStr;

    public Corporation()
    {
    }

    public Corporation(long id, String ticker, String name, String description, String corpUrl, Headquarters headquarters, String loyaltyUrl)
    {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.corpUrl = corpUrl;
        this.headquarters = headquarters;
        this.loyaltyUrl = loyaltyUrl;
    }

    private static final String IdKey = "id";
    private static final String TickerKey = "ticker";
    private static final String NameKey = "name";
    private static final String DescriptionKey = "description";
    private static final String UrlKey = "href";
    private static final String HeadquartersKey = "headquarters";
    private static final String LoyaltyKey = "loyaltyStore";
    private static final String IdStrKey = "id_str";

    @Override
    public Corporation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (DescriptionKey.equals(key))
                description = value.getAsString();
            else if (HeadquartersKey.equals(key))
            {
                headquarters = new Headquarters();
                headquarters.deserialize(value, typeOfT, context);
            }
            else if (UrlKey.equals(key))
                corpUrl = value.getAsString();
            else if (IdStrKey.equals(key))
                idStr = value.getAsString();
            else if (LoyaltyKey.equals(key))
            {
                ExternalRef ref = new ExternalRef();
                ref.deserialize(value, typeOfT, context);
                loyaltyUrl = ref.url;
            }
            else if (TickerKey.equals(key))
                ticker = value.getAsString();
            else if (IdStrKey.equals(key))
                idStr = value.getAsString();
            else if (IdKey.equals(key))
                id = value.getAsLong();
            else if (NameKey.equals(key))
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
        format.ttl(getClass().getSimpleName());
        format.inc();
        return toString(format).toString();
    }

    @Override
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("id: ", id);
        format.ttl("ticker: ", ticker);
        format.ttl("name: ", name);
        format.ttl("description: ", description);
        format.ttl("corpUrl: ", corpUrl);
        format.ttl("loyaltyUrl: ", loyaltyUrl);
        format.ttl("headquarters:");
        format.inc();
        headquarters.toString(format);
        format.dec();
        return format;
    }
}
