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

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.RightsException;
import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.crest.core.client.xml.EveApiSaxHandler;
import com.ccc.crest.da.AccessGroup;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.RequestThrottle.IntervalType;

@SuppressWarnings("javadoc")
public abstract class BaseEveData extends EveApiSaxHandler implements Serializable, EveData
{
    private static final long serialVersionUID = -7379109873737393844L;

    protected AtomicLong lastAccess = new AtomicLong(0);
    protected AtomicLong lastRefresh = new AtomicLong(0);
    protected AtomicBoolean continueRefresh = new AtomicBoolean(true);
    protected AtomicInteger cacheInSeconds = new AtomicInteger();
    protected AtomicLong nextRefresh = new AtomicLong();
    protected AtomicBoolean fromCrest = new AtomicBoolean(true);
    protected final Logger log;

    public BaseEveData()
    {
        log = LoggerFactory.getLogger(getClass());
    }

    public static boolean checkRights(CrestClientInfo clientInfo, String group)
    {
        List<AccessGroup> list = clientInfo.getGroups();
        for(AccessGroup agroup : list)
            if(agroup.equals(group))
                return true;
        return false;
    }

    public static void enforceRights(CrestClientInfo clientInfo, String group) throws RightsException
    {
        List<AccessGroup> list = clientInfo.getGroups();
        for(AccessGroup agroup : list)
            if(agroup.equals(group))
                return;
        throw new RightsException("group: " + group + " is required");
    }

    @Override
    public long getLastAccessed()
    {
        return lastAccess.get();
    }

    @Override
    public void accessed()
    {
        lastAccess.set(System.currentTimeMillis());
    }

    @Override
    public long getLastRefreshed()
    {
        return lastAccess.get();
    }

    @Override
    public void refreshed()
    {
        lastRefresh.set(System.currentTimeMillis());
    }

    @Override
    public void setContinueRefresh(boolean value)
    {
        continueRefresh.set(value);
    }

    @Override
    public boolean isContinueRefresh()
    {
        return continueRefresh.get();
    }

    @Override
    public int getCacheTimeInSeconds()
    {
        return cacheInSeconds.get();
    }

    @Override
    public void setCacheTimeInSeconds(int seconds)
    {
        cacheInSeconds.set(seconds);
    }

    @Override
    public RequestThrottle getThrottle(int seconds)
    {
        return IntervalType.getRequestThrottle(1, seconds);
    }

    @Override
    public boolean isFromCrest()
    {
        return fromCrest.get();
    }

    @Override
    public void setFromCrest(boolean value)
    {
        fromCrest.set(value);
    }

    @Override
    public void init()
    {
    }

    @Override
    public void setNextRefresh(long time)
    {
        nextRefresh.set(time);
    }

    @Override
    public Paging getPaging()
    {
        return null;
    }
    
    public enum VersionType {Post, Get, Put, Delete}
}
