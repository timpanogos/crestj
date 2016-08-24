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
