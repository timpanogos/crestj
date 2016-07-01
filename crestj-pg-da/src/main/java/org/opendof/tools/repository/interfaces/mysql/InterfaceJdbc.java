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

import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.DataAccessor.InterfaceIterable;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.mysql.data.InterfaceRow;
import org.opendof.tools.repository.interfaces.mysql.data.MysqlInterfaceIterable;

import com.mysql.jdbc.Statement;

@SuppressWarnings("javadoc")
public class InterfaceJdbc
{
    public static final String TableName;

    public static final String ColIfacePkName = "ifacePk";
    public static final String ColIidName = "iid";
    public static final String ColXmlName = "xml";
    public static final String ColVersionName = "version";
    public static final String ColRepoTypeName = "repotype";
    public static final String ColSubmitterFkName = "submitterFk";
    public static final String ColGroupFkName = "groupFk";
    public static final String ColCreationDateName = "creationDate";
    public static final String ColModDateName = "modifiedDate";
    public static final String ColPublishedName = "published";

    public static final int IfacePkIdx = 1;
    public static final int IidIdx = 2;
    public static final int XmlIdx = 3;
    public static final int VersionIdx = 4;
    public static final int RepoIdx = 5;
    public static final int SubmitterIdx = 6;
    public static final int GroupIdx = 7;
    public static final int CreationDateIdx = 8;
    public static final int ModDateIdx = 9;
    public static final int PubIdx = 10;

    private static final String PrepGetIfacePk;
    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;
    private static final String PrepSearch;
    private static final String PrepSearchWithPublish;

    static
    {
        TableName = "interface";

        PrepGetIfacePk = "select " + ColIfacePkName + " from " + TableName + " where " + ColIidName + "=? and " + ColVersionName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColIfacePkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColIfacePkName + "=?;";

        //@formatter:off
        PrepSearch = 
            "select i.*, s.*, g.*" +
            " from " + TableName + " as i" + 
                " inner join " + SubmitterJdbc.TableName + 
                    " as s on s." + SubmitterJdbc.ColSubmitterPkName + " = i." + ColSubmitterFkName +
                " inner join " + SubmitterJdbc.TableName + 
                    " as g on g." + SubmitterJdbc.ColSubmitterPkName + " = i." + ColGroupFkName +
            " where i." + ColRepoTypeName + "=? and s." + 
            SubmitterJdbc.ColEmailName + " like ? and g." +
            SubmitterJdbc.ColSubmitterName + " like ?;"; 

        PrepSearchWithPublish = 
            "select i.*, s.*, g.*" +
                " from " + TableName + " as i" + 
                    " inner join " + SubmitterJdbc.TableName + 
                        " as s on s." + SubmitterJdbc.ColSubmitterPkName + " = i." + ColSubmitterFkName +
                    " inner join " + SubmitterJdbc.TableName + 
                        " as g on g." + SubmitterJdbc.ColSubmitterPkName + " = i." + ColGroupFkName +
                " where i." + ColRepoTypeName + "=? and s." + 
                SubmitterJdbc.ColEmailName + " like ? and g." +
                SubmitterJdbc.ColSubmitterName + " like ? and i." + 
                ColPublishedName + "=? ;";
        
        // adjust the above for + ColPublishedName + "in (true, false, true and false)=? ;";
        
        PrepInsertRow = 
            "insert into " + TableName + " (" + 
            ColIidName + "," + 
            ColXmlName + "," + 
            ColVersionName + "," + 
            ColRepoTypeName + "," + 
            ColSubmitterFkName + "," + 
            ColGroupFkName + "," + 
            ColCreationDateName + "," + 
            ColModDateName + "," + 
            ColPublishedName + ")" + 
            " values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColIidName + "=?," + 
            ColXmlName + "=?," + 
            ColVersionName + "=?," + 
            ColRepoTypeName + "=?," + 
            ColSubmitterFkName + "=?," + 
            ColGroupFkName + "=?," + 
            ColCreationDateName + "=?," + 
            ColModDateName + "=?," + 
            ColPublishedName + "=?" + 
            " where " + ColIfacePkName + "=?;";
        //@formatter:on
    }

    public static int getPid(Connection connection, String iid, String version, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetIfacePk);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, iid);
            stmt.setString(2, version);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("iid: " + iid + " version: " + version + " not found");
            return rs.getInt(1);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static InterfaceRow getInterface(Connection connection, String iid, String version) throws NotFoundException, SQLException
    {
        return getInterface(connection, iid, version, true);
    }

    public static InterfaceRow getInterface(Connection connection, String iid, String version, boolean close) throws NotFoundException, SQLException
    {
        int pid;
        try
        {
            pid = getPid(connection, iid, version, false);
        } catch (Exception e)
        {
            MysqlDataAccessor.close(connection, null, null, true);
            throw e;
        }
        return getRow(connection, pid, close);
    }

    public static InterfaceRow getRow(Connection connection, int pid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setInt(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("iid pid: " + pid + " not found");
            return new InterfaceRow(rs, connection);
        } catch (Exception e)
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
            throw e;
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addInterface(Connection connection, InterfaceData iidData, boolean close) throws AlreadyExistsException, SQLException, NotFoundException
    {
        try
        {
            getPid(connection, iidData.iid, iidData.version, false);
            throw new AlreadyExistsException("iid: '" + iidData.iid + "' already exists");
        } catch (NotFoundException nfe)
        {
            insertRow(connection, iidData, close);
        }
    }

    public static long insertRow(Connection connection, InterfaceData iidData, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            long submitterPid = SubmitterJdbc.getPid(connection, iidData.submitter.email, false);
            Long groupPid = SubmitterJdbc.getPid(connection, iidData.accessGroup == null ? null : iidData.accessGroup.email, false);
            stmt.setString(IidIdx - 1, iidData.iid);
            stmt.setString(XmlIdx - 1, iidData.xml);
            stmt.setString(VersionIdx - 1, iidData.version);
            stmt.setLong(SubmitterIdx - 1, submitterPid);
            if (groupPid == null)
                stmt.setNull(GroupIdx - 1, java.sql.Types.BIGINT);
            else
                stmt.setLong(GroupIdx - 1, groupPid);
            stmt.setString(RepoIdx - 1, iidData.getRepoType());
            stmt.setTimestamp(CreationDateIdx - 1, new Timestamp(iidData.creationDate.getTime()));
            stmt.setTimestamp(ModDateIdx - 1, new Timestamp(iidData.lastModifiedDate.getTime()));
            stmt.setByte(PubIdx - 1, iidData.publish ? (byte) 1 : (byte) 0);
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

    public static void updateInterface(Connection connection, InterfaceData iidData) throws NotFoundException, SQLException
    {
        int pid;
        try
        {
            pid = getPid(connection, iidData.iid, iidData.version, false);
        } catch (Exception e)
        {
            MysqlDataAccessor.close(connection, null, null, true);
            throw e;
        }
        updateRow(connection, pid, iidData);
    }

    public static void updateRow(Connection connection, int pid, InterfaceData iidData) throws SQLException, NotFoundException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            long submitterPid = SubmitterJdbc.getPid(connection, iidData.submitter.email, false);
            long groupPid = SubmitterJdbc.getPid(connection, iidData.accessGroup.email, false);
            stmt.setString(IidIdx - 1, iidData.iid);
            stmt.setString(XmlIdx - 1, iidData.xml);
            stmt.setString(VersionIdx - 1, iidData.version);
            stmt.setString(RepoIdx - 1, iidData.getRepoType());
            stmt.setLong(SubmitterIdx - 1, submitterPid);
            stmt.setLong(GroupIdx - 1, groupPid);
            stmt.setTimestamp(CreationDateIdx - 1, new Timestamp(iidData.creationDate.getTime()));
            stmt.setTimestamp(ModDateIdx - 1, new Timestamp(iidData.lastModifiedDate.getTime()));
            stmt.setByte(PubIdx - 1, iidData.publish ? (byte) 1 : (byte) 0);
            stmt.setLong(PubIdx, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, true);
        }
    }

    public static void deleteInterface(Connection connection, String iid, String version, boolean close) throws NotFoundException, SQLException
    {
        int pid;
        try
        {
            pid = getPid(connection, iid, version, false);
        } catch (Exception e)
        {
            MysqlDataAccessor.close(connection, null, null, close);
            throw e;
        }
        deleteRow(connection, pid, close);
    }

    public static void deleteRow(Connection connection, int pid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
        try
        {
            stmt.setInt(1, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    //@formatter:off
    @SuppressWarnings("null")
    public static InterfaceIterable listInterfaces(
                    Connection connection, String repoType, 
                    String submitter, String accessGroup, Boolean published) throws Exception
    //@formatter:on
    {
        boolean isSubmitterSet = submitter != null && !submitter.isEmpty();
        boolean isGroupSet = accessGroup != null && !accessGroup.isEmpty();
        boolean isPublishedSet = published != null;

        PreparedStatement stmt;

        if (!isPublishedSet)
            stmt = connection.prepareStatement(PrepSearch);
        else
            stmt = connection.prepareStatement(PrepSearchWithPublish);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, repoType);
            stmt.setString(2, isSubmitterSet ? submitter : "%");
            stmt.setString(3, isGroupSet ? accessGroup : "%");
            if (isPublishedSet)
                stmt.setBoolean(4, published);
            rs = stmt.executeQuery();
            return new MysqlInterfaceIterable(connection, stmt, rs);
            // hail mary, the caller sure better call close on that iterable!
            //TODO: add a timer to the iterables that auto close after some configured timeout
        } catch (Exception e)
        {
            MysqlDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
    }
}
