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
import com.ccc.crest.da.CorporationData;
import com.ccc.crest.da.PagedItem;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class NpcCorporationCollection extends BaseEveData implements JsonDeserializer<NpcCorporationCollection>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String PostBase = null;
    public static final String GetBase = "application/vnd.ccp.eve.NPCCorporationsCollection";
    public static final String PutBase = null;
    public static final String DeleteBase = null;
    public static final DbPagingCallback PagingCallback = new CorporationsCallback(GetBase);
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnly; //?
    private static final String ReadScope = null;

    private volatile Corporations corporations;

    public NpcCorporationCollection()
    {
    }

    public NpcCorporationCollection(Corporations corporations)
    {
        this.corporations = corporations;
    }

    public Corporations getCorporations()
    {
        return corporations;
    }

    @Override
    public Paging getPaging()
    {
        return corporations;
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
        gson.registerTypeAdapter(NpcCorporationCollection.class, new NpcCorporationCollection());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getUrl(page),
                        gson.create(), null, NpcCorporationCollection.class,
                        PagingCallback,
                        ReadScope, getVersion(VersionType.Get), continueRefresh, false);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public NpcCorporationCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        corporations = new Corporations();
        corporations.deserialize(json, typeOfT, context);
        return this;
    }
    
    private static AtomicBoolean firstCollection = new AtomicBoolean(true);
    public static class CorporationsCallback extends DbPagingCallback
    {
        public CorporationsCallback(String uid)
        {
            super(uid);
        }

        @Override
        public void received(EveData data, int page, boolean validated)
        {
            try
            {
                Paging paging = ((NpcCorporationCollection) data).getCorporations();
                List<CorporationData> list = new ArrayList<>();
                for (PagedItem item : paging.items)
                {
                    Corporation c = (Corporation) item;
                    list.add(new CorporationData(c.id, c.ticker, c.name, c.description, c.corpUrl, c.loyaltyUrl, c.headquarters.name, c.headquarters.stationUrl, page));
                }
                if (!validated)
                    CrestController.getCrestController().getDataAccessor().truncateCorporation();
                CrestController.getCrestController().getDataAccessor().addCorporation(list, page);

                if (firstCollection.get())
                {
                    firstCollection.set(false);
                    try
                    {
                        getFuture(0);
                    } catch (Exception e)
                    {
                        LoggerFactory.getLogger(getClass()).warn("NpcCorporation failed to fire heartbeat", e);
                    }
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("NpcCorporation paging is broken, the database has failed to add corporations to alliance table", e);
                return;
            }
        }
    }
}
