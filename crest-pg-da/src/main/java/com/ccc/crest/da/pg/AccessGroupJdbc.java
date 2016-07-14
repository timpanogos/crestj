/*
**  Copyright (c) 2010-2015, Panasonic Corporation.
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
package com.ccc.crest.da.pg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.EntityData;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc")
public class AccessGroupJdbc
{
    public static final String TableName;

    public static final String ColGroupFkName = "groupfk";
    public static final String ColAdminFkName = "adminfk";
    public static final String ColMemberName = "memberfk";

    public static final int GroupFkIdx = 1;
    public static final int AdminIdx = 2;
    public static final int MemberIdx = 3;

    private static final String PrepGetGroupFk;
    private static final String PrepGetRow;
    private static final String PrepGetRows;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;
    private static final String PrepGetMembers;

    static
    {
        TableName = "accessgroup";
        PrepGetGroupFk = "select " + ColGroupFkName + " from " + TableName + " where " + ColAdminFkName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColGroupFkName + "=? and " + ColMemberName + "=?;";
        PrepGetRows = "select * from " + TableName + " where " + ColGroupFkName + "=?;";
        PrepInsertRow = "insert into " + TableName + " values(?, ?, ?);";
        PrepUpdateRow = "update " + TableName + " set " + ColAdminFkName + "=?, " + ColMemberName + "=? where " + ColGroupFkName + "=? and " + ColAdminFkName + "=? and " + ColMemberName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColGroupFkName + "=? and " + ColMemberName + "=?;";
        PrepGetMembers = "select * from " + TableName + " where " + ColGroupFkName + "=?;";
        
    }

    public static int getGroupId(Connection connection, String admin) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetGroupFk);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, admin);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("submitter: " + admin + " not found");
            return rs.getInt(1);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
        }
    }

    public static AccessGroup getGroup(Connection connection, String group) throws Exception
    {
        AccessGroup accessGroup = null;
        try
        {
            List<EntityRow> entities = EntityJdbc.listGroups(connection, false);
            for (EntityRow groupRow : entities)
            {
                if(!groupRow.name.equals(group))
                    continue;
                EntityRow groupEntityRow = getGroupEntity(connection, groupRow.name, false);
                List<AccessGroupRow> groupEntityRows = getRows(connection, groupEntityRow.pid, false);
                long adminId = -1;
                for(AccessGroupRow arow : groupEntityRows)
                {
                    if(arow.adminId != -1)
                    {
                        adminId = arow.adminId;
                        break;
                    }
                }
                EntityRow arow = EntityJdbc.getRow(connection, adminId, false);
                accessGroup = new AccessGroup(groupRow.name, arow.name, arow.name);
            }
            if(accessGroup == null)
                throw new NotFoundException("AccessGroup " + group + " not found");
        } finally
        {
            PgBaseDataAccessor.close(connection, null, null, true);
        }
        return accessGroup;
    }
    
    public static EntityRow getGroupEntity(Connection connection, String group, boolean close) throws NotFoundException, SQLException
    {
        Long pid = EntityJdbc.getPid(connection, group, false);
        EntityRow row = EntityJdbc.getRow(connection, pid, close);
        if (!row.group)
            throw new NotFoundException(group + " is not a group name");
        return row;
    }

    public static AccessGroupRow getRow(Connection connection, long groupId, long memberId) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, groupId);
            stmt.setLong(2, memberId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("groupId: " + groupId + " memberId: " + memberId + " not found");
            return new AccessGroupRow(rs);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
        }
    }

    public static List<EntityData> getMembers(Connection connection, String group) throws NotFoundException, SQLException
    {
        long groupPid = EntityJdbc.getPid(connection, group, false);
        List<AccessGroupRow> rows = getRows(connection, groupPid, false);
        List<EntityData> list = new ArrayList<EntityData>();
        for (AccessGroupRow row : rows)
            list.add(EntityJdbc.getRow(connection, row.memberId, false));
        PgBaseDataAccessor.close(connection, null, null, true);
        return list;
    }

    public static List<AccessGroupRow> getRows(Connection connection, long groupId, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        ResultSet rs = null;
        List<AccessGroupRow> rows = new ArrayList<AccessGroupRow>();
        try
        {
            stmt.setLong(1, groupId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("groupId: " + groupId + " not found");
            rows.add(new AccessGroupRow(rs));
            while (rs.next())
                rows.add(new AccessGroupRow(rs));
            return rows;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static long getAdmin(Connection connection, long groupId, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, groupId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("groupId: " + groupId + " not found");
            return new AccessGroupRow(rs).adminId;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addGroup(Connection connection, String admin, EntityData group) throws Exception
    {
        try
        {
            if (!group.group)
                throw new Exception("The entity for group: " + group + " is not flagged as a group");
            // check already exists
            EntityJdbc.getEntity(connection, group.name, false);
            throw new AlreadyExistsException("group: '" + group.name + "' already exists");
        } catch (NotFoundException nfe)
        {
            // get the manager of the groups pid
            long adminPid = EntityJdbc.getPid(connection, admin, false);
            try
            {
                // begin a transaction block to rollback new group insert into the submitters table if our insert fails
                connection.setAutoCommit(false);
                // add the new group to the entity table.
                long groupId = EntityJdbc.insertRow(connection, group, false); // groupId is the pid of the groups admin row
                // we are assuming the admin wants to be a member
                insertRow(connection, groupId, adminPid, adminPid, false);
                connection.commit();
            } catch (Exception e)
            {
                connection.rollback();
                throw e;
            }
        } finally
        {
            PgBaseDataAccessor.close(connection, null, null, true);
        }
    }

    public static void insertRow(Connection connection, Long groupId, long adminId, long memberId, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(GroupFkIdx, groupId);
            if (adminId == -1)
                stmt.setNull(AdminIdx, java.sql.Types.BIGINT);
            else
                stmt.setLong(AdminIdx, adminId);
            stmt.setLong(MemberIdx, memberId);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void updateGroup(Connection connection, String groupName, String admin, String description, Date date) throws NotFoundException, SQLException
    {
        Long groupPid = EntityJdbc.getPid(connection, groupName, false);
        EntityData egroupRow = EntityJdbc.getRow(connection, groupPid, false);
        if (description != null || date != null)
        {
            EntityData newGroup = new EntityData(groupName, true);
            EntityJdbc.updateRow(connection, groupPid, newGroup);
        }
        if (admin != null)
        {
            long oldAdminPid = getAdmin(connection, groupPid, false);
            long adminPid = EntityJdbc.getPid(connection, admin, false);
            if (oldAdminPid != adminPid)
                AccessGroupJdbc.updateRow(connection, groupPid, oldAdminPid, adminPid, false);
        }
        PgBaseDataAccessor.close(connection, null, null, true);
    }

    public static void updateRow(Connection connection, long groupPid, long oldAdminPid, long adminPid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setLong(1, adminPid);
            stmt.setLong(2, adminPid);
            stmt.setLong(3, groupPid);
            stmt.setLong(4, oldAdminPid);
            stmt.setLong(5, oldAdminPid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void deleteGroup(Connection connection, String group, boolean force) throws Exception
    {
        Long groupPid = EntityJdbc.getPid(connection, group, false);
        List<AccessGroupRow> rows = getRows(connection, groupPid, false);
        if (rows.size() > 1 && !force)
            throw new Exception("force flag required to delete a group with members");
        for (AccessGroupRow row : rows)
            AccessGroupJdbc.deleteRow(connection, groupPid, row.memberId, false);
        Long pid = EntityJdbc.getPid(connection, group, false);
        EntityJdbc.deleteRow(connection, pid, true);
    }

    public static void deleteRow(Connection connection, long groupId, long memberId, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
        try
        {
            stmt.setLong(1, groupId);
            stmt.setLong(2, memberId);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static List<AccessGroup> listGroups(Connection connection) throws Exception
    {
        List<EntityRow> entities = EntityJdbc.listGroups(connection, false);
        ArrayList<AccessGroup> accessGroups = new ArrayList<>();
        for (EntityRow groupRow : entities)
        {
            EntityRow groupEntityRow = getGroupEntity(connection, groupRow.name, false);
            List<AccessGroupRow> groupEntityRows = getRows(connection, groupEntityRow.pid, false);
            long adminId = -1;
            for(AccessGroupRow arow : groupEntityRows)
            {
                if(arow.adminId != -1)
                {
                    adminId = arow.adminId;
                    break;
                }
            }
            EntityRow arow = EntityJdbc.getRow(connection, adminId, false);
            accessGroups.add(new AccessGroup(groupRow.name, arow.name, arow.name));
        }
        return accessGroups;
    }

    public static void addMemberToGroup(Connection connection, String member, String group) throws NotFoundException, SQLException
    {
        long memberPid = EntityJdbc.getPid(connection, member, false);
        AccessGroupJdbc.getGroupEntity(connection, group, false);
        long groupPid = EntityJdbc.getPid(connection, group, false);
        insertRow(connection, groupPid, -1, memberPid, true);
    }

    public static void removeMemberFromGroup(Connection connection, String member, String group) throws Exception
    {
        long memberPid = EntityJdbc.getPid(connection, member, false);
        long groupPid = EntityJdbc.getPid(connection, group, false);
        long adminPid = getAdmin(connection, groupPid, false);
        if(memberPid == adminPid)
            throw new Exception("You can not remove the admin from the group, replace group admin first");
        deleteRow(connection, groupPid, memberPid, true);
    }

    public static boolean isMember(Connection connection, String member, String group) throws SQLException
    {
        try
        {
            long memberPid = EntityJdbc.getPid(connection, member, false);
            long groupPid = EntityJdbc.getPid(connection, group, false);
            getRow(connection, groupPid, memberPid);
            return true;
        } catch (NotFoundException nfe)
        {
            return false;
        }
    }

    public static List<EntityData> listMembers(Connection connection, String group) throws Exception
    {
        List<EntityRow> entities = EntityJdbc.listGroups(connection, false);
        ArrayList<EntityData> list = new ArrayList<>();
        for (EntityRow row : entities)
        {
            if(!row.name.equals(group))
                continue;
            PreparedStatement stmt = connection.prepareStatement(PrepGetMembers);
            ResultSet rs = null;
            try
            {
                stmt.setLong(1, row.pid);
                rs = stmt.executeQuery();
                while(rs.next())
                {
                    AccessGroupRow arow = new AccessGroupRow(rs);
                    list.add(EntityJdbc.getRow(connection, arow.memberId, false));
                }
            } finally
            {
                PgBaseDataAccessor.close(connection, stmt, rs, true);
            }
        }
        return list;
    }
}
