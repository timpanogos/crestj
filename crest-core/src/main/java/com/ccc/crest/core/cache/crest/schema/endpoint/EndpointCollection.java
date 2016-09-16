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
package com.ccc.crest.core.cache.crest.schema.endpoint;

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
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.tools.TabToLevel;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class EndpointCollection extends BaseEveData implements JsonDeserializer<EndpointCollection>
{
    private static final long serialVersionUID = -7096257178892270648L;

    public static final String PostBase = null;
    public static final String GetBase = "application/vnd.ccp.eve.Api";
    public static final String PutBase = null;
    public static final String DeleteBase = null;

    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?

    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(false);

    private volatile long userCount;
    private volatile String serverVersion;
    private volatile String serverName;
    private volatile String serviceStatus;
    private volatile List<EndpointGroup> callGroups;

    public EndpointCollection()
    {
        callGroups = new ArrayList<>();
    }

    public List<EndpointGroup> getEndpointGroups()
    {
        return new ArrayList<>(callGroups);
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
    
    public static String getCrestUrl()
    {
        return CrestClient.getCrestBaseUri();
    }

    public static Future<EveData> getFuture(CrestResponseCallback callback) throws Exception
    {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(EndpointCollection.class, new EndpointCollection());
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                    null, getCrestUrl(),
                    gson.create(), null, EndpointCollection.class,
                    callback,
                    ReadScope, getVersion(VersionType.Get), continueRefresh, false);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    private static final String UserCountKey = "userCount";
    private static final String UserCountStrKey = "userCount_str";
    private static final String ServerVersionKey = "serverVersion";
    private static final String ServerNameKey = "serverName";
    private static final String ServerStatusKey = "serviceStatus";

    @Override
    public EndpointCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
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
                userCount = Long.parseLong(topElement.getAsString());
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
                    endpointGroup.addEndpoint(new CrestEndpoint(endpointName, groupChildElement.getAsString()));
                    continue;
                }
                // expect an object with href in it
                if (!hrefElement.isJsonPrimitive())
                {
                    log.warn("expected a primitive after group: " + groupName + " = " + hrefElement.toString());
                    continue;
                }
                endpointGroup.addEndpoint(new CrestEndpoint(endpointName, hrefElement.getAsString()));
                break;
            } while (true);
        } while (true);
        return this;
    }


    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl(getClass().getSimpleName(),":");
        format.inc();
        format.ttl("userCount: ", userCount);
        format.ttl("serverVersion: ", serverVersion);
        format.ttl("serverName: ", serverName);
        format.ttl("serviceStatus: ", serviceStatus);
        format.inc();
        for(EndpointGroup group : callGroups)
            group.toString(format);
        format.dec();
        format.dec();
        return format;
    }
}
