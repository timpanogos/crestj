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
package com.ccc.crest.cache;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.ccc.tools.RequestThrottle;
import com.ccc.tools.RequestThrottle.IntervalType;

@SuppressWarnings("javadoc")
public class BaseEveData implements Serializable, EveData
{
    private static final long serialVersionUID = 3678434427208069264L;
    
    protected static final String Version = "application/vnd.ccp.eve.Api-v3+json";
    
    protected AtomicLong lastAccess = new AtomicLong(0);
    protected AtomicLong lastRefresh = new AtomicLong(0);
    protected AtomicBoolean continueRefresh = new AtomicBoolean(true);
    protected AtomicInteger cacheInSeconds = new AtomicInteger();

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
}
