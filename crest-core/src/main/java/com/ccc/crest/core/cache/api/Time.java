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

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.character.ContactList;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.client.json.Logo;
import com.ccc.crest.core.client.json.LogoDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("javadoc")
public class Time extends BaseEveData
{
    private static final long serialVersionUID = 965041169279751564L;

    private static final String Uri1 = "/time/";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public String time;

    public static String getApiUrl()
    {
        throw new RuntimeException("not implemented yet");
    }

    public static String getCrestUrl()
    {
        StringBuilder url = new StringBuilder();
        url.append(CrestClient.getCrestBaseUri()).append(Uri1);
        return url.toString();
    }

    public static Future<EveData> getTime(CrestResponseCallback callback) throws Exception
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getCrestUrl(),
                        gson, ContactList.class,
                        callback, 0,
                        ReadScope, Version);
        //@formatter:on
        return CrestClient.getClient().getCrest(rdata);
    }

    public static Future<EveData> getContactsXml(CrestClientInfo clientInfo, CrestResponseCallback callback) throws Exception
    {
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(),
                        null, ContactList.class,
                        callback, 0,
                        ReadScope, Version);
        return null;
        //@formatter:on
        //        return XmlClient.getClient().getCrest(rdata);
    }
}