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