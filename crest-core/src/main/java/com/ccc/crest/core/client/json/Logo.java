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

