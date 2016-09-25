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
import java.util.List;

import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.crest.da.AllianceData;
import com.ccc.crest.da.PagedItem;
import com.ccc.crest.da.PagingData;
import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
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
            items.add(new Alliance(data.id, data.shortName, data.name, data.url));
    }

    @Override
    public Alliances deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        super.pagingDeserialize(json, typeOfT, context);
        return this;
    }
    
    @Override
    protected PagedItem getPagedItem(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        StupidHref child = new StupidHref();
        child.deserialize(json, typeOfT, context);
        return child;
    }
    
    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }
    
    public String toJson()
    {
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        return gson.create().toJson(this);
    }
}
