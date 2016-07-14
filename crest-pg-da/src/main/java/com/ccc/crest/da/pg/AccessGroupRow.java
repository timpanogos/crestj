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
