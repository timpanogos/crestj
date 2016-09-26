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
package com.ccc.crest.core.cache.crest.dogma;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.DbPagingCallback;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.da.DogmaAttributeData;
import com.ccc.crest.da.PagedItem;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class DogmaAttributeCollection extends BaseEveData implements JsonDeserializer<DogmaAttributeCollection>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String PostBase = null;
    public static final String GetBase = "application/vnd.ccp.eve.DogmaAttributeCollection";
    public static final String PutBase = null;
    public static final String DeleteBase = null;
    public static final DbPagingCallback PagingCallback = new DogmaAttributeCallback(GetBase);
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;

    private volatile DogmaAttributes dogmaAttributes;
    
    public DogmaAttributeCollection()
    {
    }

    public DogmaAttributeCollection(DogmaAttributes dogmaAttributes)
    {
        this.dogmaAttributes = dogmaAttributes;
    }

    public DogmaAttributes getDogmaAttributes()
    {
        return dogmaAttributes;
    }
    
    @Override
    public Paging getPaging()
    {
        return dogmaAttributes;
    }

    public static String getVersion(VersionType type)
    {
        switch(type)
        {
            case Delete:
                return SchemaMap.schemaMap.getSchemaFromVersionBase(DeleteBase).getVersion();
            case Get:
                return SchemaMap.schemaMap.getSchemaFromVersionBase(GetBase).getVersion();
            case Post:
                return SchemaMap.schemaMap.getSchemaFromVersionBase(PostBase).getVersion();
            case Put:
                return SchemaMap.schemaMap.getSchemaFromVersionBase(PutBase).getVersion();
            default:
                return null;
        }
    }

    public static String getUrl(int page)
    {
        StringBuilder sb = new StringBuilder(SchemaMap.schemaMap.getSchemaFromVersionBase(GetBase).getUri());
        if (page != 0)
            sb.append("?page=").append(page);
        return sb.toString();
    }
    
    public static Future<EveData> getFuture(int page) throws Exception
    {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(DogmaAttributeCollection.class, new DogmaAttributeCollection());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getUrl(page),
                        gson.create(), null, DogmaAttributeCollection.class,
                        PagingCallback,
                        ReadScope, getVersion(VersionType.Get), continueRefresh, true);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public DogmaAttributeCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        dogmaAttributes = new DogmaAttributes();
        dogmaAttributes.deserialize(json, typeOfT, context);
        return this;
    }
    
    private static AtomicBoolean firstCollection = new AtomicBoolean(true);
    public static class DogmaAttributeCallback extends DbPagingCallback
    {
        public DogmaAttributeCallback(String uid)
        {
            super(uid);
        }

        @Override
        public void received(EveData data, int page, boolean validated)
        {
            try
            {
                Paging paging = ((DogmaAttributeCollection) data).getDogmaAttributes();
                List<DogmaAttributeData> list = new ArrayList<>();
                for (PagedItem item : paging.items)
                {
                    DogmaAttribute c = (DogmaAttribute) item;
                    list.add(new DogmaAttributeData(c.id, c.ticker, c.name, c.description, c.corpUrl, c.loyaltyUrl, "hqname", "hqurl", page));
                }
                if (!validated)
                    CrestController.getCrestController().getDataAccessor().truncateAlliance();
                CrestController.getCrestController().getDataAccessor().addDogmaAttribute(list, page);

                if (firstCollection.get())
                {
                    firstCollection.set(false);
                    try
                    {
                        getFuture(0);
                    } catch (Exception e)
                    {
                        LoggerFactory.getLogger(getClass()).warn("Alliance failed to fire heartbeat", e);
                    }
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("Alliance paging is broken, the database has failed to add alliances to alliance table", e);
                return;
            }
        }
    }
}
