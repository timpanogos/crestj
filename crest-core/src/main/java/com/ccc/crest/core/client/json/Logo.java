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
package com.ccc.crest.core.client.json;

import java.io.Serializable;

import com.ccc.tools.TabToLevel;
import com.google.gson.JsonObject;

@SuppressWarnings("javadoc")
public class Logo implements Serializable
{
    private static final long serialVersionUID = 4580965619925667264L;
    public Href href32x32;
    public Href href64x64;
    public Href href128x128;
    public Href href256x256;
 
    public Logo(JsonObject jsonObject)
    {
        href32x32 = new Href(jsonObject.get("32x32").getAsJsonObject());
        href64x64 = new Href(jsonObject.get("64x64").getAsJsonObject());
        href128x128 = new Href(jsonObject.get("128x128").getAsJsonObject());
        href256x256 = new Href(jsonObject.get("256x256").getAsJsonObject());
        System.out.println(jsonObject);
    }
    
    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        format.ttl("logo");
        format.level.incrementAndGet();
        format.ttl("href32x32: ", href32x32.toString());
        format.ttl("href64x64: ", href64x64.toString());
        format.ttl("href128x128: ", href128x128.toString());
        format.ttl("href256x256: ", href256x256.toString());
        format.level.decrementAndGet();
        return format.toString();
    }
}

