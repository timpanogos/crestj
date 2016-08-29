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
    private static final String Version = "application/vnd.ccp.eve.Api-v1+json";
    
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
                        gson, null, Time.class,
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
                        null, null, ContactList.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        return null;
        //@formatter:on
        //        return XmlClient.getClient().getCrest(rdata);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (eveTime == null ? 0 : eveTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Time other = (Time) obj;
        if (eveTime == null)
        {
            if (other.eveTime != null)
                return false;
        } else if (!eveTime.equals(other.eveTime))
            return false;
        return true;
    }
}
