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
package com.ccc.crest.core.cache.crest.sovereignty;

import java.lang.reflect.Type;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

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
public class SovCampaignsCollection extends BaseEveData implements JsonDeserializer<SovCampaignsCollection>
{
    private static final long serialVersionUID = -2711682230241156568L;
    private static final AtomicBoolean continueRefresh = new AtomicBoolean(true);
    public static final String VersionBase = "application/vnd.ccp.eve.SovCampaignsCollection";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public SovCampaignsCollection()
    {
    }

    public static String getVersion()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getVersion();
    }
    
    public static String getCrestUrl()
    {
        return SchemaMap.schemaMap.getSchemaFromVersionBase(VersionBase).getUri();
    }

    public static Future<EveData> getFuture(String url, CrestResponseCallback callback) throws Exception
    {
        if(url == null)
            url = getCrestUrl();
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(SovCampaignsCollection.class, new SovCampaignsCollection());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, url,
                        gson.create(), null, SovCampaignsCollection.class,
                        callback,
                        ReadScope, getVersion(), continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getOptions(rdata);
    }

    @Override
    public SovCampaignsCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return null;
    }
}
