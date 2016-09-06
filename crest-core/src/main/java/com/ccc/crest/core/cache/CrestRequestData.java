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
package com.ccc.crest.core.cache;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.tools.TabToLevel;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class CrestRequestData implements Comparable<CrestRequestData>
{
    public final CrestClientInfo clientInfo;
    public final String url;
    public final Gson gson;
    public final EveJsonData eveJsonData;
    public final Class<? extends EveJsonData> clazz;
    public final CrestResponseCallback callback;
    public final String scope;
    public final String version;
    public final AtomicBoolean continueRefresh;
    public final AtomicBoolean deprecated;  
    public final boolean logJson;
    private long nextRefresh;
    public Throwable throwable;

    //@formatter:off
    public CrestRequestData(
                    CrestClientInfo clientInfo, String url,
                    Gson gson, EveJsonData eveJsonData, Class<? extends EveJsonData> clazz,
                    CrestResponseCallback callback,
                    String scope, String version,
                    AtomicBoolean continueRefresh)
    {
        this(clientInfo, url, gson, eveJsonData, clazz, callback, scope, version, continueRefresh, false);
    }
    //@formatter:on
    
    //@formatter:off
    public CrestRequestData(
                    CrestClientInfo clientInfo, String url,
                    Gson gson, EveJsonData eveJsonData, Class<? extends EveJsonData> clazz,
                    CrestResponseCallback callback,
                    String scope, String version,
                    AtomicBoolean continueRefresh,
                    boolean logJson)
    //@formatter:on
    {
        this.clientInfo = clientInfo;
        this.url = url;
        this.gson = gson;
        this.eveJsonData = eveJsonData;
        this.clazz = clazz;
        this.callback = callback;
        this.scope = scope;
        this.version = version;
        this.continueRefresh = continueRefresh;
        this.logJson = logJson;
        deprecated = new AtomicBoolean(false);
    }

    public synchronized void setNextRefresh(long time)
    {
        nextRefresh = time;
    }

    public synchronized long getNextRefresh()
    {
        return nextRefresh;
    }

    @Override
    public int compareTo(CrestRequestData arg0)
    {
        if(arg0.nextRefresh == nextRefresh)
            return 0;
        if(arg0.nextRefresh <= nextRefresh)
            return +1;
        return -1;
    }
    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        format.ttl(getClass().getSimpleName());
        format.inc();
        return toString(format).toString();
    }
    
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("url: ", url);
        format.ttl("version: ", version);
        format.ttl("scope: ", scope);
        format.ttl("nextRefresh: ", nextRefresh);
        format.ttl("continueRefresh: ", continueRefresh);
        format.ttl("deprecated: ", deprecated);
        format.ttl("clazz: ", clazz);
        format.ttl("callback: ", callback);
        format.ttl("baseEveData: ", eveJsonData);
        format.ttl("clientInfo: ", clientInfo);
        return format;
    }
}
