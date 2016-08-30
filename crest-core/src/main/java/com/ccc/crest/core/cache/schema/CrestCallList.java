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
package com.ccc.crest.core.cache.schema;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class CrestCallList extends BaseEveData implements JsonDeserializer<CrestCallList>
{
    private static final long serialVersionUID = -7096257178892270648L;

    private static final String Version = "application/vnd.ccp.eve.Api-v3+json";

    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?

    private static final String Uri1 = "";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(false);

    private volatile int userCount;
    private volatile String serverVersion;
    private volatile String serverName;
    private volatile String serviceStatus;
    private volatile List<EndpointGroup> callGroups;
    private volatile Representations representations;

    public CrestCallList()
    {
        callGroups = new ArrayList<>();
        representations = new Representations();
    }

    public List<EndpointGroup> getCallGroups()
    {
        return new ArrayList<>(callGroups);
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
        gson.registerTypeAdapter(CrestCallList.class, new CrestCallList());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                    null, getCrestUrl(),
                    gson.create(), null, CrestCallList.class,
                    callback,
                    ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    private static final String UserCountKey = "userCount";
    private static final String UserCountStrKey = "userCount_str";
    private static final String ServerVersionKey = "serverVersion";
    private static final String ServerNameKey = "serverName";
    private static final String ServerStatusKey = "serviceStatus";

    @Override
    public CrestCallList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject topObj = (JsonObject) json;
        Set<Entry<String, JsonElement>> topSet = topObj.entrySet();
        Iterator<Entry<String, JsonElement>> topIter = topSet.iterator();
        do
        {
            if (!topIter.hasNext())
                break;
            Entry<String, JsonElement> topEntry = topIter.next();
            String topKey = topEntry.getKey();
            JsonElement topElement = topEntry.getValue();
            if (topKey.equals(UserCountKey))
            {
                userCount = Integer.parseInt(topElement.getAsString());
                continue;
            }
            if (topKey.equals(UserCountStrKey))
                continue;
            if (topKey.equals(ServerVersionKey))
            {
                serverVersion = topElement.getAsString();
                continue;
            }
            if (topKey.equals(ServerNameKey))
            {
                serverName = topElement.getAsString();
                continue;
            }
            if (topKey.equals(ServerStatusKey))
            {
                serviceStatus = topElement.getAsString();
                continue;
            }
            // if its not a top object level known variable from above list, it must be a group object
            if (topElement.isJsonPrimitive())
            {
                log.warn("unexpected key: " + topKey + " = " + topObj.toString());
                continue;
            }
            if (!topElement.isJsonObject())
            {
                log.warn("expected an object: " + topKey + " = " + topObj.toString());
                continue;
            }
            // first pass you should have a group in the topElement
            String groupName = topKey;
            EndpointGroup endpointGroup = new EndpointGroup(groupName);
            callGroups.add(endpointGroup);
            Set<Entry<String, JsonElement>> groupSet = topElement.getAsJsonObject().entrySet();
            Iterator<Entry<String, JsonElement>> groupIter = groupSet.iterator();
            do
            {
                if (!groupIter.hasNext())
                    break;
                Entry<String, JsonElement> groupEntry = groupIter.next();
                // expecting a primitive href here
                String endpointName = groupEntry.getKey();
                JsonElement hrefElement = groupEntry.getValue();
                if (hrefElement.isJsonObject())
                {
                    JsonObject groupChildObj = (JsonObject) hrefElement;
                    Set<Entry<String, JsonElement>> groupChildSet = groupChildObj.entrySet();
                    Iterator<Entry<String, JsonElement>> groupChildIter = groupChildSet.iterator();
                    if (!groupChildIter.hasNext())
                        break;
                    Entry<String, JsonElement> groupChildEntry = groupChildIter.next();
                    String groupChildKey = groupChildEntry.getKey();
                    JsonElement groupChildElement = groupChildEntry.getValue();
                    endpointGroup.addEndpoint(new Endpoint(endpointName, groupChildElement.getAsString()));
                    continue;
                }
                // expect an object with href in it
                if (!hrefElement.isJsonPrimitive())
                {
                    log.warn("expected a primitive after group: " + groupName + " = " + hrefElement.toString());
                    continue;
                }
                endpointGroup.addEndpoint(new Endpoint(endpointName, hrefElement.getAsString()));
                break;
            } while (true);
        } while (true);
        return this;
    }
}
