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
package com.ccc.crest.core.cache.crest.schema.endpoint;

import java.util.ArrayList;
import java.util.List;

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class EndpointGroup
{
    public final String name;
    private final List<Endpoint> endpoints;

    public EndpointGroup(String name)
    {
        this.name = name;
        endpoints = new ArrayList<>();
    }
    
    public void addEndpoint(Endpoint endpoint)
    {
        synchronized (endpoints)
        {
            endpoints.add(endpoint);
        }
    }
    
    public List<Endpoint> getEndpoints()
    {
        synchronized (endpoints)
        {
            return new ArrayList<>(endpoints);
        }
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
        for(Endpoint endpoint : endpoints)
            endpoint.toString(format);
        format.dec();
        return format;
    }
    
}
