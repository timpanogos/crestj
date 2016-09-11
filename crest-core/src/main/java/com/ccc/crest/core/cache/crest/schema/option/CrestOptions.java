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
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class CrestOptions extends BaseEveData implements JsonDeserializer<CrestOptions>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(false);
    public static final String VersionBase = "application/vnd.ccp.eve.Options";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public volatile Representations representations;
    private final boolean doGet;

    public CrestOptions(boolean doGet)
    {
        representations = new Representations();
        this.doGet = doGet;
    }

    public Representations getRepresentations()
    {
        return representations;
    }

    public static String getVersion()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getVersion();
    }

    public static String getCrestUrl()
    {
        return CrestClient.getCrestBaseUri();
    }

    public static Future<EveData> getFuture(String url, boolean doGet, CrestResponseCallback callback) throws Exception
    {
        if(url == null)
            url = getCrestUrl();
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(CrestOptions.class, new CrestOptions(doGet));
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, url,
                        gson.create(), null, CrestOptions.class,
                        callback,
                        ReadScope, getVersion(), continueRefresh, false);
        //@formatter:on
        if(doGet)
            return CrestController.getCrestController().crestClient.getCrest(rdata);
        return CrestController.getCrestController().crestClient.getOptions(rdata);
    }

    @Override
    public CrestOptions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if(doGet)
            return this;
        representations = new Representations();
        representations.deserialize(json, typeOfT, context);
        return this;
    }
}
