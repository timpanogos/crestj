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
import java.util.ArrayList;
import java.util.List;

import com.ccc.crest.da.AllianceData;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc")
public class AllianceJdbc
{
    public static final String TableName;

    public static final String ColIdName = "id";
    public static final String ColShortName = "shortname";
    public static final String ColNameName = "name";
    public static final String ColPageName = "page";
    public static final String ColUrlName = "allianceurl";

    public static final int IdIdx = 1;
    public static final int ShortIdx = 2;
    public static final int NameIdx = 3;
    public static final int PageIdx = 4;
    public static final int UrlIdx = 5;

    private static final String PrepTruncate;
    private static final String PrepGetRows;
    private static final String PrepInsertRow;

    static
    {
        TableName = "alliance";
        PrepTruncate = "truncate " + TableName + ";";
        PrepGetRows = "select * from " + TableName + " where " + ColPageName + "=?;";
        PrepInsertRow = "insert into " + TableName + " values(?, ?, ?, ?, ?);";
    }

    public static void truncate(Connection connection, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepTruncate);
        try
        {
            stmt.executeQuery();
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static List<AllianceData> getRows(Connection connection, int page, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        ResultSet rs = null;
        List<AllianceData> rows = new ArrayList<>();
        try
        {
            stmt.setInt(1, page);
            rs = stmt.executeQuery();
            while (rs.next())
                rows.add(new AllianceRow(rs));
            return rows;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void insertRow(Connection connection, AllianceData alliance, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(IdIdx, alliance.id);
            stmt.setString(ShortIdx, alliance.shortName);
            stmt.setString(NameIdx, alliance.name);
            stmt.setInt(PageIdx, alliance.page);
            stmt.setString(UrlIdx, alliance.allianceUrl);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }
}
