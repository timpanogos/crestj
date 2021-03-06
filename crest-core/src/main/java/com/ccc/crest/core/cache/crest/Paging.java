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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.crest.alliance.AllianceCollection;
import com.ccc.crest.da.PagedItem;
import com.ccc.crest.da.PagingData;
import com.ccc.tools.TabToLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public abstract class Paging
{
    public volatile long totalCount;
    public volatile long pageCount;
    public volatile ExternalRef next;
    public volatile ExternalRef previous;
    public final List<PagedItem> items;

    public Paging()
    {
        items = new ArrayList<>();
    }

    public Paging(PagingData data, int page)
    {
        items = new ArrayList<>();
        this.totalCount = data.totalItems;
        this.pageCount = data.pageCount;
//        int page = list.get(0).page;
        if(page == 1)
        {
            previous = null;
            next = new ExternalRef(AllianceCollection.getUrl(page + 1), null);
        }
        else
        {
            next = new ExternalRef(AllianceCollection.getUrl(page + 1), null);
            previous = new ExternalRef(AllianceCollection.getUrl(page - 1), null);
            if(page * data.itemsPerPage >= data.totalItems)
                next = null;
        }
    }

    private static final String TotalCountStringKey = "totalCount_str";
    private static final String PageCountKey = "pageCount";
    private static final String NextKey = "next";           // optional
    private static final String PreviousKey = "previous";   // optional
    private static final String TotalCountKey = "totalCount";
    private static final String PageCountStringKey = "pageCount_str";
    private static final String ItemsKey = "items";

    private boolean pagingDeserialize(String key, JsonElement value) throws JsonParseException
    {
        if (TotalCountStringKey.equals(key))
            return true;
        if (PageCountKey.equals(key))
        {
            pageCount = value.getAsLong();
            return true;
        }
        if (NextKey.equals(key))
        {
            next = new ExternalRef();
            next.deserialize(value, null, null);
            return true;
        }
        else if (PreviousKey.equals(key))
        {
            previous = new ExternalRef();
            previous.deserialize(value, null, null);
            return true;
        }
        if (TotalCountKey.equals(key))
        {
            totalCount = value.getAsLong();
            return true;
        }
        else if (PageCountStringKey.equals(key))
            return true;
        return false;
    }

    protected abstract PagedItem getPagedItem(JsonElement json, Type typeOfT, JsonDeserializationContext context);
    
    protected void pagingDeserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
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
                    items.add(getPagedItem(childElement, typeOfT, context));
                }
            }
            else
                LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
        }
    }
    
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl(getClass().getSimpleName());
        format.inc();
        format.ttl("totalCount: ", totalCount);
        format.ttl("pageCount: ", pageCount);
        format.ttl("next: ");
        format.inc();
        if(next == null)
            format.ttl("null");
        else
            next.toString(format);
        format.dec();
        format.ttl("previous: ");
        format.inc();
        if(previous == null)
            format.ttl("null");
        else
            previous.toString(format);
        format.dec();
        format.ttl("items: ");
        format.inc();
        for(PagedItem item : items)
            item.toString(format);
        format.dec();
        format.dec();
        return format;
    }
}
