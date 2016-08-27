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
package com.ccc.crest.core.cache.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.Endpoint;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

@SuppressWarnings("javadoc")
public class CrestCallList extends BaseEveData
{
    private static final long serialVersionUID = -7096257178892270648L;

    private static final String Version = "application/vnd.ccp.eve.Api-v3+json";

    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?

    private static final String Uri1 = "";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(true);

    private volatile List<Endpoint> endpoints;

    public CrestCallList()
    {
        endpoints = new ArrayList<>();
    }

    public static String getCrestUrl()
    {
        StringBuilder url = new StringBuilder();
        url.append(CrestClient.getCrestBaseUri()).append(Uri1);
        return url.toString();
    }

    public static Future<EveData> getCallList(CrestResponseCallback callback) throws Exception
    {
        GsonBuilder gson = new GsonBuilder();
        CrestCallListJson parser = new CrestCallListJson();
        gson.registerTypeAdapter(CrestCallList.class, parser);
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getCrestUrl(),
                        gson.create(), null, CrestCallList.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    public static class CrestCallListJson implements JsonDeserializer<CrestCallListJson>
    {
        private final CrestCallList crestCallList;

        public CrestCallListJson()
        {
            crestCallList = new CrestCallList();
        }

        @Override
        public CrestCallListJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jobj = json.getAsJsonObject();
            JsonPrimitive jprim = jobj.getAsJsonPrimitive();
            Endpoint endpoint = new Endpoint("name", "value");
            return null;
        }
    }
}


