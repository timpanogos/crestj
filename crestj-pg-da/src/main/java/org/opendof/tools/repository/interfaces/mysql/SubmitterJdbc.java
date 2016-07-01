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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.DataAccessor.SubmitterIterable;
import org.opendof.tools.repository.interfaces.da.GroupData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.data.GroupRow;
import org.opendof.tools.repository.interfaces.mysql.data.MysqlSubmitterIterable;
import org.opendof.tools.repository.interfaces.mysql.data.SubmitterRow;

import com.mysql.jdbc.Statement;

@SuppressWarnings("javadoc")
public class SubmitterJdbc
{
    public static final String TableName;

    public static final String ColSubmitterPkName = "submitterPk";
    public static final String ColSubmitterName = "name";
    public static final String ColEmailName = "email";
    public static final String ColDescName = "description";
    public static final String ColDateName = "joinedDate";
    public static final String ColGroupName = "isGroup";

    public static final int SubmitterPkIdx = 1;
    public static final int NameIdx = 2;
    public static final int EmailIdx = 3;
    public static final int DescIdx = 4;
    public static final int DateIdx = 5;
    public static final int IsGroupIdx = 6;

    private static final String PrepGetSubmitterPk;
    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;
    private static final String PrepGetAllSubmitters;
    private static final String PrepGetAllGroups;

    static
    {
        TableName = "submitter";
        PrepGetSubmitterPk = "select " + ColSubmitterPkName + " from " + TableName + " where " + ColEmailName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColSubmitterPkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColSubmitterPkName + "=?;";
        PrepGetAllSubmitters = "select * from " + TableName + " where " + ColGroupName + "=0;";
        PrepGetAllGroups = "select * from " + TableName + " where " + ColGroupName + "=1;";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName +
            " (" + ColSubmitterName + "," +
            ColEmailName + "," +
            ColDescName + "," +
            ColDateName + "," +
            ColGroupName + ")" +
            " values(?, ?, ?, ?, ?);";
        
        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColSubmitterName + "=?," + 
            ColEmailName + "=?," +
            ColDescName + "=?," + 
            ColDateName + "=?," + 
            ColGroupName + "=?" + 
            " where " + ColSubmitterPkName + "=?;";
        //@Formatter:on
    }

    public static Long getPid(Connection connection, String submitterEmail, boolean close) throws NotFoundException, SQLException
    {
        if(submitterEmail == null)
            return null;  // groupFk can be null
        PreparedStatement stmt = connection.prepareStatement(PrepGetSubmitterPk);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, submitterEmail);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("submitter: " + submitterEmail + " not found");
            return rs.getLong(1);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static SubmitterRow getSubmitter(Connection connection, String submitterEmail, boolean close) throws NotFoundException, SQLException
    {
        long pid = getPid(connection, submitterEmail, false);
        return getRow(connection, pid, close);
    }

    public static SubmitterRow getRow(Connection connection, long pid, boolean close) throws NotFoundException, SQLException
    {
        if(pid == 0)
            return null;
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("submitter pid: " + pid + " not found");
            return new SubmitterRow(rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addSubmitter(Connection connection, SubmitterData submitterData, boolean close) throws AlreadyExistsException, SQLException
    {
        try
        {
            getPid(connection, submitterData.email, false);
            throw new AlreadyExistsException("submitter: '" + submitterData.name + "' already exists");
        } catch (NotFoundException nfe)
        {
            insertRow(connection, submitterData, close);
        }
    }

    public static long insertRow(Connection connection, SubmitterData submitterData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        
        Date joinedDate = null;
        if(submitterData.date != null){
        	joinedDate = submitterData.date;
        } else{
        	joinedDate = new Date();
        }
        
        try
        {
            stmt.setString(NameIdx-1, submitterData.name);
            stmt.setString(EmailIdx-1, submitterData.email);
            stmt.setString(DescIdx-1, submitterData.description);
            stmt.setTimestamp(DateIdx-1, new Timestamp(joinedDate.getTime()));
            stmt.setBoolean(IsGroupIdx-1, submitterData.group);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
        finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void updateSubmitter(Connection connection, String submitterEmail, SubmitterData submitterData) throws NotFoundException, SQLException
    {
        Long pid = getPid(connection, submitterEmail, false);
        updateRow(connection, pid, submitterData, true);
    }

    public static void updateRow(Connection connection, Long pid, SubmitterData submitterData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setString(1, submitterData.name);
            stmt.setString(2, submitterData.email);
            stmt.setString(3, submitterData.description);
            stmt.setDate(4, new java.sql.Date(submitterData.date.getTime())); // I think this one is ok on utc
            stmt.setBoolean(5, submitterData.group);
            stmt.setLong(6, pid);
            
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void deleteSubmitter(Connection connection, String submitter) throws NotFoundException, SQLException
    {
        Long pid = getPid(connection, submitter, false);
        deleteRow(connection, pid);
    }

    public static void deleteRow(Connection connection, Long pid) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
        try
        {
            stmt.setLong(1, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, true);
        }
    }

    public static SubmitterIterable listSubmitters(Connection connection) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetAllSubmitters);
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery();
            return new MysqlSubmitterIterable(connection, stmt, rs);
            // hail mary, the caller sure better call close on that iterable!
            //TODO: add a timer to the iterables that auto close after some configured timeout
        }catch(Exception e)
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
    }
    
    public static List<GroupData> listGroups(Connection connection) throws Exception
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetAllGroups);
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery();
            MysqlSubmitterIterable subiter = new MysqlSubmitterIterable(connection, stmt, rs);
            Iterator<SubmitterData> siter = subiter.iterator();
            List<GroupData> groups = new ArrayList<GroupData>();
            long adminPid = -1;
            while(siter.hasNext())
            {
                SubmitterData sub = siter.next();
                long groupPid = SubmitterJdbc.getPid(connection, sub.name, false);
                List<GroupRow> rows = GroupJdbc.getRows(connection, groupPid, false);
                adminPid = rows.get(0).submitterId;
                SubmitterData admin = SubmitterJdbc.getRow(connection, adminPid, false);
                groups.add(new GroupData(sub, admin));
            }
            subiter.close();
            return groups;
        }catch(Exception e)
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
    }
}
