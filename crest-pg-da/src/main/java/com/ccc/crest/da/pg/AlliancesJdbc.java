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
public class AlliancesJdbc
{
    public static final String TableName;

    public static final String ColTotalAlliancesName = "totalAlliances";
    public static final String ColPageCountName = "pagecount";
    public static final String ColCountPerPageName = "countperpage";

    public static final int TotalAlliancesIdx = 1;
    public static final int PageCountIdx = 2;
    public static final int CountPerPageIdx = 3;

    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;

    static
    {
        TableName = "alliances";
        PrepGetRow = "select * from " + TableName + ";";
        PrepInsertRow = "insert into " + TableName + " values(?, ?, ?);";
        PrepUpdateRow = "update " + TableName + " set " + ColTotalAlliancesName + "=?, " + ColPageCountName + "=?, " + ColCountPerPageName + "=? where " + ColTotalAlliancesName + "=?;";
        
    }

    public static AlliancesRow getRow(Connection connection, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("totalAlliances: not found");
            AlliancesRow row = new AlliancesRow(rs); 
            if(rs.next())
                throw new SQLException("More than one row in alliances table");
            return row;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void insertRow(Connection connection, PagingData alliances, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(TotalAlliancesIdx, alliances.total);
            stmt.setLong(PageCountIdx, alliances.pageCount);
            stmt.setInt(CountPerPageIdx, alliances.countPerPage);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static void updateRow(Connection connection, long totalAlliances, PagingData alliances, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setLong(TotalAlliancesIdx, alliances.total);
            stmt.setLong(PageCountIdx, alliances.pageCount);
            stmt.setInt(CountPerPageIdx, alliances.countPerPage);
            stmt.setLong(CountPerPageIdx + 1, totalAlliances);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }
}
