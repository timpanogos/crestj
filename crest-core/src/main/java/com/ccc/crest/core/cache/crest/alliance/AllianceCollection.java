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
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class AllianceCollection extends BaseEveData implements JsonDeserializer<AllianceCollection>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String PostBase = null;
    public static final String GetBase = "application/vnd.ccp.eve.AllianceCollection";
    public static final String PutBase = null;
    public static final String DeleteBase = null;
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AllianceCollection allianceCollection;
    static
    {
        allianceCollection = new AllianceCollection();
    }

    private volatile int currentPage;
    private final AtomicLong totalAlliances;
    private final AtomicInteger countPerPage;
    private volatile Alliances alliances;

    private AllianceCollection()
    {
//        alliances = new ArrayList<>();
        totalAlliances = new AtomicLong();
        countPerPage = new AtomicInteger();
    }

    public static void setAlliances(Alliances alliances)
    {
        allianceCollection.alliances = alliances;
    }

    @Override
    public void init()
    {
        synchronized (allianceCollection)
        {
            allianceCollection.alliances.alliances.size();
        }
//        totalAlliances.set(alliances.get(0).totalCount);
    }

    public Alliances getAlliances()
    {
        return alliances;
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
        if(page != 0)
            sb.append("?page=").append(page);
//        ?page=2
        return sb.toString();
    }

    public static Future<EveData> getFuture(int page, CrestResponseCallback callback) throws Exception
    {
        allianceCollection.currentPage = page;
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(AllianceCollection.class, allianceCollection);
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getUrl(page),
                        gson.create(), null, AllianceCollection.class,
                        callback,
                        ReadScope, getVersion(VersionType.Get), continueRefresh, false);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    @Override
    public AllianceCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        alliances = new Alliances();
        alliances.deserialize(json, typeOfT, context);
        return this;
    }
}
