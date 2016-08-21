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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.character.ContactList;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class Time extends BaseEveData
{
    private static final long serialVersionUID = 965041169279751564L;
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.CrestOnlyPublic; //?

    private static final String Uri1 = "/time/";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(true);

    private volatile String time;
    public volatile Date eveTime;
    public volatile Date localTime;

    @Override
    public void init()
    {
        // 2016-07-15T01:23:30
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try
        {
            eveTime = sdf.parse(time);
        } catch (ParseException e)
        {
            LoggerFactory.getLogger(getClass()).warn("Could not parse time string: " + time);
            eveTime = new Date();
        }
        localTime = new Date();
    }

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
        Gson gson = new Gson();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getCrestUrl(),
                        gson, Time.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getCrest(rdata);
    }

    public static Future<EveData> getContactsXml(CrestClientInfo clientInfo, CrestResponseCallback callback) throws Exception
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
