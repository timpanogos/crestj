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
package com.ccc.crest.core.cache.character;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.RightsException;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.client.json.Href;
import com.ccc.crest.core.client.json.Logo;
import com.ccc.crest.core.client.json.LogoDeserializer;
import com.ccc.crest.core.client.json.character.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("javadoc")
public class ContactList extends BaseEveData
{
    private static final long serialVersionUID = 965041169279751564L;
    private static final String Version = "application/vnd.ccp.eve.Api-v3+json";
    public static final String AccessGroup = CrestController.UserGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.Character; //?

    private static final String Uri1 = "/characters/";
    private static final String Uri2 = "/contacts/";
    private static final String ReadScope = "characterContactsRead";
    private static final String WriteScope = "characterContactsWrite";

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(true);

    public String totalCount_str;
    public int pageCount;
    public List<Item> items;
    public Href next;
    public int totalCount;
    public String pageCount_str;

    public static String getApiUrl(CrestClientInfo clientInfo)
    {
        throw new RuntimeException("not implemented yet");
    }

    public static String getCrestUrl(CrestClientInfo clientInfo)
    {
        StringBuilder url = new StringBuilder();
        //@formatter:off
        url.append(CrestClient.getCrestBaseUri())
        .append(Uri1)
        .append(clientInfo.getVerifyData().CharacterID)
        .append(Uri2);
        //@formatter:on
        return url.toString();
    }

    public static Future<EveData> getContacts(CrestClientInfo clientInfo, CrestResponseCallback callback) throws RightsException
    {
        enforceRights(clientInfo, AccessGroup);
        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(clientInfo),
                        gson, null, ContactList.class,
                        callback, ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    public static Future<EveData> getContacts(CrestClientInfo clientInfo, CrestClientInfo clientInfo2, CrestResponseCallback callback) throws RightsException
    {
        enforceRights(clientInfo, AccessGroup);
        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(clientInfo),
                        gson, null, ContactList.class,
                        callback, ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    public static Future<EveData> getContactsXml(CrestClientInfo clientInfo, CrestResponseCallback callback) throws RightsException
    {
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(clientInfo),
                        null, null, ContactList.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        return null;
        //@formatter:on
        //        return XmlClient.getClient().getCrest(rdata);
    }
}

