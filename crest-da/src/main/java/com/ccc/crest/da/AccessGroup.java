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
package com.ccc.crest.da;

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class AccessGroup
{
    public final String group;
    public final String admin;
    public final String member;

    public AccessGroup(String group, String admin, String member)
    {
        this.group = group;
        this.admin = admin;
        this.member = member;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((admin == null) ? 0 : admin.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((member == null) ? 0 : member.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccessGroup other = (AccessGroup) obj;
        if (admin == null)
        {
            if (other.admin != null)
                return false;
        } else if (!admin.equals(other.admin))
            return false;
        if (group == null)
        {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (member == null)
        {
            if (other.member != null)
                return false;
        } else if (!member.equals(other.member))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }
    
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("group: ", group);
        format.ttl("admin: ", admin);
        format.ttl("member: ", member);
        return format;
    }
}