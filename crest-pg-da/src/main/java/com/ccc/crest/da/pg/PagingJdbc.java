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

import com.ccc.crest.da.PagingData;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc")
public class PagingJdbc
{
    public static final String TableName;

    public static final String ColIdName = "id";
    public static final String ColTotalItemsName = "totalitems";
    public static final String ColPageCountName = "pagecount";
    public static final String ColItemsPerPageName = "itemsperpage";
    public static final String ColUidName = "itemsuid";

    public static final int IdIdx = 1;
    public static final int TotalItemsIdx = 2;
    public static final int PageCountIdx = 3;
    public static final int ItemsPerPageIdx = 4;
    public static final int UidIdx = 5;

    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;

    static
    {
        TableName = "paging";
        PrepGetRow = "select * from " + TableName + " where " + ColUidName + "=?;";
        //@formatter:off
        PrepInsertRow = "insert into " + TableName + "(" +
            ColTotalItemsName + "," +
            ColPageCountName + "," +
            ColItemsPerPageName + "," +
            ColUidName + ")" +
            " values(?, ?, ?, ?);";

        PrepUpdateRow = "update " + TableName + " set " +
            ColTotalItemsName + "=?, " +
            ColPageCountName + "=?, " +
            ColItemsPerPageName + "=?, " +
            ColUidName +
            "=? where " + ColIdName + "=?;";
        //@formatter:on

    }

    public static PagingRow getRow(Connection connection, String uid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, uid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("getRow for " + uid + " not found");
            PagingRow row = new PagingRow(rs);
            return row;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void insertRow(Connection connection, PagingData pagingData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(TotalItemsIdx-1, pagingData.totalItems);
            stmt.setLong(PageCountIdx-1, pagingData.pageCount);
            stmt.setInt(ItemsPerPageIdx-1, pagingData.itemsPerPage);
            stmt.setString(UidIdx-1, pagingData.uid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void updateRow(Connection connection, PagingData pageData, long pid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setLong(TotalItemsIdx-1, pageData.totalItems);
            stmt.setLong(PageCountIdx-1, pageData.pageCount);
            stmt.setInt(ItemsPerPageIdx-1, pageData.itemsPerPage);
            stmt.setString(UidIdx-1, pageData.uid);
            stmt.setLong(UidIdx, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }
}
