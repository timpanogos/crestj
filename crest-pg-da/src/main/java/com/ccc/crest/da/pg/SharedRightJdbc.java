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
package com.ccc.crest.da.pg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.crest.da.SharedRight;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc")
public class SharedRightJdbc
{
    public static final String TableName;

    public static final String ColRightPkName = "rightpk";
    public static final String ColCap1Name = "capsuleer1";
    public static final String ColCap2Name = "capsuleer2";
    public static final String ColTypeName = "type";

    public static final int RightPkIdx = 1;
    public static final int Cap1Idx = 2;
    public static final int Cap2Idx = 3;
    public static final int TypeIdx = 4;

    private static final String PrepGetEntityPk;
    private static final String PrepInsertRow;
    private static final String PrepDeleteRow;
    private static final String PrepList;

    static
    {
        TableName = "sharedrights";

        PrepGetEntityPk = "select " + ColRightPkName + " from " + TableName + " where " + ColCap1Name + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColRightPkName + "=?;";
        PrepList = "select * from " + TableName + " where " + ColCap1Name + "=?;";

        //@formatter:off
        PrepInsertRow =
            "insert into " + TableName + " (" +
                            ColCap1Name + "," +
                            ColCap2Name + "," +
                            ColTypeName + ")" +
                            " values(?, ?, ?);";
        //@formatter:on
    }

    public static void addSharedRight(Connection connection, SharedRight right) throws AlreadyExistsException, Exception
    {
        try
        {
            getPid(connection, right.capsuleer, false);
            throw new AlreadyExistsException("capsuleer: '" + right.capsuleer + "' already exists");
        } catch (NotFoundException nfe)
        {
            insertRow(connection, right, true);
        }
    }

    public static void deleteSharedRight(Connection connection, String capsuleer) throws Exception
    {
        long pid;
        try
        {
            pid = getPid(connection, capsuleer, false);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
        deleteRow(connection, pid, true);
    }

    public static List<SharedRight> listSharedRights(Connection connection, String capsuleer) throws Exception
    {
        PreparedStatement stmt = connection.prepareStatement(PrepList);
        ResultSet rs = null;
        List<SharedRight> list = new ArrayList<>();
        try
        {
            stmt.setString(Cap1Idx - 1, capsuleer);
            rs = stmt.executeQuery();
            while (rs.next())
                list.add(new SharedRightRow(rs));
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
        return list;
    }

    public static long insertRow(Connection connection, SharedRight sharedRight, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            stmt.setString(Cap1Idx - 1, sharedRight.capsuleer);
            stmt.setString(Cap2Idx - 1, sharedRight.capsuleer2);
            stmt.setInt(TypeIdx - 1, sharedRight.type.ordinal());
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

    public static long getPid(Connection connection, String name, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetEntityPk);
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

}
