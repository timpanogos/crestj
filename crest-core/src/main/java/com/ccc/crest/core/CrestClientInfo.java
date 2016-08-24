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
package com.ccc.crest.core;

import java.util.ArrayList;
import java.util.List;

import com.ccc.crest.core.client.json.OauthVerify;
import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.Right;
import com.ccc.oauth.clientInfo.Base20ClientInfo;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class CrestClientInfo extends Base20ClientInfo
{
    private static final long serialVersionUID = 7825545875283686681L;

    private OauthVerify verifyData;
    private final List<AccessGroup> groups;
    private final List<Right> rights;

    public CrestClientInfo()
    {
        groups = new ArrayList<>();
        rights = new ArrayList<>();
    }

    public List<Right> getRights()
    {
        return rights;
    }

    public void addGroup(Right right)
    {
        synchronized(rights)
        {
            rights.add(right);
        }
    }

    public List<AccessGroup> getGroups()
    {
        synchronized(groups)
        {
            return new ArrayList<>(groups);
        }
    }

    public void addGroup(AccessGroup group)
    {
        synchronized(groups)
        {
            groups.add(group);
        }
    }

    public OauthVerify getVerifyData()
    {
        return verifyData;
    }

    public synchronized void setVerifyData(OauthVerify verifyData)
    {
        this.verifyData = verifyData;
    }
    
    @Override
    public TabToLevel toString(TabToLevel format)
    {
        super.toString(format);
        format.ttl("CrestClient:");
        format.inc();
        format.ttl("verifyData:");
        format.inc();
        if(verifyData == null)
            format.ttl("null");
        else
            verifyData.toString(format);
        format.dec();
        format.ttl("groups:");
        format.inc();
        if(groups.size() == 0)
            format.ttl("none");
        else
        {
            for(AccessGroup ag : groups)
                ag.toString(format);
        }
        format.dec();
        format.ttl("rights:");
        format.inc();
        if(rights.size() == 0)
            format.ttl("none");
        else
        {
            for(Right right : rights)
                right.toString();
        }
        return format;
    }
}
