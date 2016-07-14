/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.core.cache;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class CrestRequestData
{
    public final CrestClientInfo clientInfo;
    public final String url;
    public final Gson gson;
    public final Class<? extends EveData> clazz;
    public final CrestResponseCallback callback;
    public final String scope;
    public final String version;
    public volatile int cacheSeconds;

    //@formatter:off
    public CrestRequestData(
                    CrestClientInfo clientInfo, String url, 
                    Gson gson, Class<? extends EveData> clazz, 
                    CrestResponseCallback callback,
                    int throttle, String scope, String version)
    //@formatter:on
    {
        this.clientInfo = clientInfo;
        this.url = url;
        this.gson = gson;
        this.clazz = clazz;
        this.callback = callback;
        this.cacheSeconds = throttle;
        this.scope = scope;
        this.version = version;
    }
    
    public void setCacheSeconds(int seconds)
    {
        cacheSeconds = seconds;
    }
}