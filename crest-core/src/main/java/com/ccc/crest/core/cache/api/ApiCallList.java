/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.core.cache.api;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.RightsException;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.character.ContactList;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class ApiCallList extends BaseEveData
{
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.Account; //?

    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(true);

//    public String totalCount_str;
//    public int pageCount;
//    public List<Item> items;
//    public Href next;
//    public int totalCount;
//    public String pageCount_str;

    public static String getApiUrl()
    {
        throw new RuntimeException("not implemented yet");
    }

    public static String getCrestUrl()
    {
        return CrestClient.getCrestBaseUri();
    }

    public static Future<EveData> getCallList(CrestResponseCallback callback) throws RightsException
    {
//        enforceRights(clientInfo, AccessGroup);
//        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        Gson gson = new Gson();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getCrestUrl(),
                        gson, ApiCallList.class,
                        callback, ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    public static Future<EveData> getContactsXml(CrestClientInfo clientInfo, CrestResponseCallback callback)
    {
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(),
                        null, ContactList.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        return null;
        //@formatter:on
        //        return XmlClient.getClient().getCrest(rdata);
    }
}
