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
