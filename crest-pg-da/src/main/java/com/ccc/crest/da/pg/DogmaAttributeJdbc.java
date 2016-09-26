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

import com.ccc.crest.da.DogmaAttributeData;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc")
public class DogmaAttributeJdbc
{
    public static final String TableName;

    public static final String ColIdName = "id";
    public static final String ColTickerName = "ticker";
    public static final String ColNameName = "name";
    public static final String ColDescName = "description";
    public static final String ColUrlName = "corpurl";
    public static final String ColHqNameName = "hqname";
    public static final String ColHqUrlName = "hqurl";
    public static final String ColLoyaltyName = "loyaltyurl";
    public static final String ColPageName = "page";
    
    public static final int IdIdx = 1;
    public static final int TickerIdx = 2;
    public static final int NameIdx = 3;
    public static final int DescIdx = 4;
    public static final int UrlIdx = 5;
    public static final int HqNameIdx = 6;
    public static final int HqUrldx = 7;
    public static final int LoyaltyUrlIdx = 8;
    public static final int PageIdx = 9;

    private static final String PrepTruncate;
    private static final String PrepGetRows;
    private static final String PrepInsertRow;

    static
    {
        TableName = "corporation";
        PrepTruncate = "truncate " + TableName + ";";
        PrepGetRows = "select * from " + TableName + " where " + ColPageName + "=?;";
        PrepInsertRow = "insert into " + TableName + " values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    }

    public static void truncate(Connection connection, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepTruncate);
        try
        {
            stmt.executeUpdate();
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }

    public static List<DogmaAttributeData> getRows(Connection connection, int page, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        ResultSet rs = null;
        List<DogmaAttributeData> rows = new ArrayList<>();
        try
        {
            stmt.setInt(1, page);
            rs = stmt.executeQuery();
            while (rs.next())
                rows.add(new DogmaAttributeRow(rs));
            return rows;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void insertRow(Connection connection, DogmaAttributeData attribute, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        try
        {
            stmt.setLong(IdIdx, attribute.id);
            stmt.setString(TickerIdx, attribute.ticker);
            stmt.setString(NameIdx, attribute.name);
            stmt.setString(DescIdx, attribute.description);
            stmt.setString(UrlIdx, attribute.corpUrl);
            stmt.setString(HqNameIdx, attribute.headquartersName);
            stmt.setString(HqUrldx, attribute.headquartersUrl);
            stmt.setString(LoyaltyUrlIdx, attribute.loyaltyUrl);
            stmt.setInt(PageIdx, attribute.page);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }
}
