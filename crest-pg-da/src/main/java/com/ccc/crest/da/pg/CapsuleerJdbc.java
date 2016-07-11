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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.crest.da.CapsuleerData;
import com.ccc.tools.da.AlreadyExistsException;
import com.ccc.tools.da.NotFoundException;
import com.ccc.tools.da.PgBaseDataAccessor;


@SuppressWarnings("javadoc") 
public class CapsuleerJdbc
{
    public static final String TableName;

    public static final String ColUserPkName = "capsuleerpk";
    public static final String ColNameName = "capsuleer";
    public static final String ColUserIdName = "capsuleerid";
    public static final String ColApiKeyIdName = "apiKeyid";
    public static final String ColApiCodeName = "apicode";
    public static final String ColRefreshTokenName = "refreshtoken";

    public static final int UserPkIdx = 1;
    public static final int NameIdx = 2;
    public static final int UserIdIdx = 3;
    public static final int ApiKeyIdIdx = 4;
    public static final int ApiCodeIdx = 5;
    public static final int RefreshTokenIdx = 6;

    private static final String PrepGetUserPk;
    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;
    private static final String PrepListAll;

    static
    {
        TableName = "capsuleer";

        PrepGetUserPk = "select " + ColUserPkName + " from " + TableName + " where " + ColNameName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColUserPkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColUserPkName + "=?;";
        PrepListAll = "select * from " + TableName +";";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName + " (" + 
            ColNameName + "," + 
            ColUserIdName + "," + 
            ColApiKeyIdName + "," + 
            ColApiCodeName + "," + 
            ColRefreshTokenName + ")" + 
            " values(?, ?, ?, ?, ?);";

        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColNameName + "=?," + 
            ColUserIdName + "=?," + 
            ColApiKeyIdName + "=?," + 
            ColApiCodeName + "=?" + 
            ColRefreshTokenName + "=?" + 
            " where " + ColUserPkName + "=?;";
        //@formatter:on
    }

    public static long getPid(Connection connection, String name, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetUserPk);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("name: " + name + " not found");
            return rs.getLong(1);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static UserRow getUser(Connection connection, String name) throws NotFoundException, SQLException
    {
        return getCapsuleer(connection, name, true);
    }

    public static UserRow getCapsuleer(Connection connection, String name, boolean close) throws NotFoundException, SQLException
    {
        long pid;
        try
        {
            pid = getPid(connection, name, false);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
        return getRow(connection, pid, close);
    }

    public static UserRow getRow(Connection connection, long pid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("user pid: " + pid + " not found");
            return new UserRow(rs);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addCapsuleer(Connection connection, CapsuleerData userData, boolean close) throws AlreadyExistsException, SQLException
    {
        try
        {
            getPid(connection, userData.capsuleer, false);
            throw new AlreadyExistsException("name: '" + userData.capsuleer + "' already exists");
        } catch (NotFoundException nfe)
        {
            insertRow(connection, userData, close);
        }
    }

    public static long insertRow(Connection connection, CapsuleerData userData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            stmt.setString(NameIdx - 1, userData.capsuleer);
            stmt.setLong(UserIdIdx - 1, userData.capsuleerId);
            stmt.setLong(ApiKeyIdIdx - 1, userData.apiKeyId);
            stmt.setString(ApiCodeIdx - 1, userData.apiCode);
            stmt.setString(RefreshTokenIdx - 1, userData.refreshToken);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void updateCapsuleer(Connection connection, CapsuleerData userData) throws NotFoundException, SQLException
    {
        long pid;
        try
        {
            pid = getPid(connection, userData.capsuleer, false);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
        updateRow(connection, pid, userData);
    }

    public static void updateRow(Connection connection, long pid, CapsuleerData userData) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setString(NameIdx - 1, userData.capsuleer);
            stmt.setLong(UserIdIdx - 1, userData.capsuleerId);
            stmt.setLong(ApiKeyIdIdx - 1, userData.apiKeyId);
            stmt.setString(ApiCodeIdx - 1, userData.apiCode);
            stmt.setString(RefreshTokenIdx - 1, userData.refreshToken);
            stmt.setLong(4, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, true);
        }
    }

    public static void deleteCapsuleer(Connection connection, String name, boolean close) throws NotFoundException, SQLException
    {
        long pid;
        try
        {
            pid = getPid(connection, name, false);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, close);
            throw e;
        }
        deleteRow(connection, pid, close);
    }

    public static void deleteRow(Connection connection, long pid, boolean close) throws SQLException
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
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    //@formatter:off
    public static List<CapsuleerData> listCapsuleers(Connection connection) throws Exception
    //@formatter:on
    {
        PreparedStatement stmt = connection.prepareStatement(PrepListAll);
        ResultSet rs = null;
        List<CapsuleerData> list = new ArrayList<>();
        try
        {
            rs = stmt.executeQuery();
            while(rs.next())
                list.add(new UserRow(rs));
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
        return list;
    }
}
