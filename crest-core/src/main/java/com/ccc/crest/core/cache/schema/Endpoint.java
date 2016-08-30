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
package com.ccc.crest.core.cache.schema;

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class Endpoint
{
    public final String name;
    public final String uri;

    public Endpoint(String name, String url)
    {
        this.name = name;
        this.uri = url;
    }
    
    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }
    
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("name: ", name);
        format.inc();
        format.ttl("uri: ", uri);
        format.dec();
        return format;
    }
}
