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
package com.ccc.crest.da.pg;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("javadoc")
public class AccessGroupRow// extends AccessGroupData
{
    public final long groupId;
    public final long adminId;
    public final long memberId;

    public AccessGroupRow(ResultSet rs) throws SQLException
    {
        groupId = rs.getLong(AccessGroupJdbc.GroupFkIdx);
        adminId = rs.getLong(AccessGroupJdbc.AdminIdx);
        memberId = rs.getLong(AccessGroupJdbc.MemberIdx);
    }

    public AccessGroupRow(Object[] columns)
    {
        groupId = (Long)columns[AccessGroupJdbc.GroupFkIdx-1];
        adminId = (Long)columns[AccessGroupJdbc.AdminIdx-1];
        memberId = (Long)columns[AccessGroupJdbc.MemberIdx-1];
    }
}
