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
package org.opendof.tools.repository.interfaces.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.GroupData;
import org.opendof.tools.repository.interfaces.da.InsufficentRightsException;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.da.SubmitterException;
import org.opendof.tools.repository.interfaces.mysql.data.GroupRow;
import org.opendof.tools.repository.interfaces.mysql.data.SubmitterRow;

@SuppressWarnings("javadoc")
public class GroupJdbc
{
    public static final String TableName;

    public static final String ColGroupIdName = "groupFk";
    public static final String ColSubmitterName = "managerFk";
    public static final String ColMemberName = "memberFk";

    public static final int GroupIdIdx = 1;
    public static final int SubmitterIdx = 2;
    public static final int MemberIdx = 3;

    private static final String PrepGetGroupId;
    private static final String PrepGetRow;
    private static final String PrepGetRows;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;


    static
    {
        TableName = "submittergroup";
        PrepGetGroupId = "select " + ColGroupIdName + " from " + TableName + " where " + ColSubmitterName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColGroupIdName + "=? and " + ColMemberName + "=?;";
        PrepGetRows = "select * from " + TableName + " where " + ColGroupIdName + "=?;";
        PrepInsertRow = "insert into " + TableName + " values(?, ?, ?);";
        //@Formatter:off
        PrepUpdateRow = "update " + TableName + " set " + ColSubmitterName + "=?, " + ColMemberName + "=? where " + ColGroupIdName + "=? and " + ColSubmitterName + "=? and " + ColMemberName + "=?;";
        //@Formatter:on
        PrepDeleteRow = "delete from " + TableName + " where " + ColGroupIdName + "=? and " + ColMemberName + "=?;";
    }

    public static int getGroupId(Connection connection, String submitter) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetGroupId);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, submitter);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("submitter: " + submitter + " not found");
            return rs.getInt(1);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
        }
    }

    public static SubmitterRow getGroup(Connection connection, String group, boolean close) throws NotFoundException, SQLException
    {
        Long pid = SubmitterJdbc.getPid(connection, group, false);
        SubmitterRow row = SubmitterJdbc.getRow(connection, pid, close);
        if (!row.group)
            throw new NotFoundException(group + " is not a group name");
        return row;
    }

    public static GroupRow getRow(Connection connection, long groupId, long memberId) throws NotFoundException, SQLException
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
            return new GroupRow(rs);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
        }
    }

    public static List<SubmitterData> getMembers(Connection connection, String group) throws NotFoundException, SQLException
    {
        long groupPid = SubmitterJdbc.getPid(connection, group, false);
        List<GroupRow> rows = getRows(connection, groupPid, false);
        List<SubmitterData> list = new ArrayList<SubmitterData>();
        for(GroupRow row : rows)
            list.add(SubmitterJdbc.getRow(connection, row.memberId, false));
        MysqlDataAccessor.close(connection, null, null, true);
        return list;
    }
    
    public static List<GroupRow> getRows(Connection connection, long groupId, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        ResultSet rs = null;
        List<GroupRow> rows = new ArrayList<GroupRow>();
        try
        {
            stmt.setLong(1, groupId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("groupId: " + groupId + " not found");
            rows.add(new GroupRow(rs));
            while(rs.next())
                rows.add(new GroupRow(rs));
            return rows;
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
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
            return new GroupRow(rs).submitterId;
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addGroup(Connection connection, String managerEmail, SubmitterData group) throws AlreadyExistsException, NotFoundException, SubmitterException, SQLException
    {
        try
        {
            if (!group.group)
                throw new SubmitterException("The " + SubmitterData.class.getSimpleName() + " is not flagged as a group");
            // check already exists
            SubmitterJdbc.getSubmitter(connection, group.email, false);
            throw new AlreadyExistsException("group: '" + group.email + "' already exists");
        } catch (NotFoundException nfe)
        {
            // get the manager of the groups pid
            long managerPid = SubmitterJdbc.getPid(connection, managerEmail, false);
            try
            {
                // begin a transaction block to rollback new group insert into the submitters table if our insert fails
                connection.setAutoCommit(false);
                // add the new group to the submitter table.
                long groupId = SubmitterJdbc.insertRow(connection, group, false); // groupId is the pid of the groups submitter row
                // we are assuming the submitter wants to be a member
                insertRow(connection, groupId, managerPid, managerPid, false);
                connection.commit();
            } catch (Exception e)
            {
                connection.rollback();
                throw e;
            }
        } finally
        {
            MysqlDataAccessor.close(connection, null, null, true);
        }
    }

    public static void insertRow(Connection connection, Long groupId, long managerId, long memberId, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(GroupIdIdx, groupId);
            if (managerId == -1)
                stmt.setNull(SubmitterIdx, java.sql.Types.BIGINT);
            else
                stmt.setLong(SubmitterIdx, managerId);
            stmt.setLong(MemberIdx, memberId);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void updateGroup(Connection connection, String groupName, String adminEmail, String description, Date date) throws NotFoundException, SQLException
    {
        Long groupPid = SubmitterJdbc.getPid(connection, groupName, false);
        SubmitterData sgroupRow = SubmitterJdbc.getRow(connection, groupPid, false);
        if(description != null || date != null)
        {
            SubmitterData newGroup = new SubmitterData(groupName, groupName, description, date == null ? sgroupRow.date : date, sgroupRow.group);
            SubmitterJdbc.updateRow(connection, groupPid, newGroup, false);
        }
        if(adminEmail != null)
        {
            long oldAdminPid = getAdmin(connection, groupPid, false);
            long adminPid = SubmitterJdbc.getPid(connection, adminEmail, false);
            if(oldAdminPid != adminPid)
                GroupJdbc.updateRow(connection, groupPid, oldAdminPid, adminPid, false);
        }
        MysqlDataAccessor.close(connection, null, null, true);
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
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void deleteGroup(Connection connection, String group, boolean force) throws NotFoundException, SubmitterException, SQLException, InsufficentRightsException
    {
        Long groupPid = SubmitterJdbc.getPid(connection, group, false);
        List<GroupRow> rows = getRows(connection, groupPid, false);
        if(rows.size() > 1 && !force)
            throw new InsufficentRightsException("force flag required to delete a group with memembers"); 
        for(GroupRow row : rows)
            GroupJdbc.deleteRow(connection, groupPid, row.memberId, false);
        Long pid = SubmitterJdbc.getPid(connection, group, false);
        SubmitterJdbc.deleteRow(connection, pid);
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
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static List<GroupData> listGroups(Connection connection) throws Exception
    {
        return SubmitterJdbc.listGroups(connection);
    }

    public static void addSubmitterToGroup(Connection connection, String member, String group) throws NotFoundException, SQLException
    {
        long memberPid = SubmitterJdbc.getPid(connection, member, false);
        GroupJdbc.getGroup(connection, group, false);
        long groupPid = SubmitterJdbc.getPid(connection, group, false);
        insertRow(connection, groupPid, -1, memberPid, true);
    }

    public static void removeSubmitterFromGroup(Connection connection, String member, String group) throws NotFoundException, SQLException
    {
        long memberPid = SubmitterJdbc.getPid(connection, member, false);
        long groupPid = SubmitterJdbc.getPid(connection, group, false);
        deleteRow(connection, groupPid, memberPid, true);
    }

    public static boolean isMember(Connection connection, String member, String group) throws SQLException
    {
        try
        {
            long memberPid = SubmitterJdbc.getPid(connection, member, false);
            long groupPid = SubmitterJdbc.getPid(connection, group, false);
            getRow(connection, groupPid, memberPid);
            return true;
        } catch (NotFoundException nfe)
        {
            return false;
        }
    }
}
