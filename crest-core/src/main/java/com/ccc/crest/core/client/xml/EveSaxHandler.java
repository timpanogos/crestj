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
package com.ccc.crest.core.client.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ccc.crest.core.cache.EveData;

@SuppressWarnings("javadoc")
public class EveSaxHandler
{
    public static final String DateFormat = "yyyy-MM-dd HH:mm:ss z"; 
    public static final String CachedUntilTag = "cachedUntil";
    
    public static int getCachedUntil(String body) throws Exception
    {
        Date current = new Date();
        long t0 = System.currentTimeMillis();
        String tag = CachedUntilTag + ">";
        int idx = body.indexOf(tag);
        if(idx == -1)
            throw new Exception("expected tag <"+tag + " in body: " + body);
        idx += tag.length();
        body = body.substring(idx);
        idx = body.indexOf("</");
        if(idx == -1)
            throw new Exception("expected tag <"+tag + " in body: " + body);
        body = body.substring(0, idx);
        body += " GMT";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        String now = sdf.format(current);
        long t1 = sdf.parse(body).getTime();
        t1 = t1 - t0;
        t1 = t1 / 1000;
        long round = t1 % 60;
        t1 = t1 / 60;
        if(round > 1)
            t1++;
        return (int)t1;
    }
    
    public static EveData getData(String body)
    {
     return null;   
    }
}
