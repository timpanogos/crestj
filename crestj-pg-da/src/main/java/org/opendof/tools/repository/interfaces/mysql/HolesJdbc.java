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
import java.util.List;

import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.mysql.data.HolesRow;

import com.mysql.jdbc.Statement;

@SuppressWarnings("javadoc")
public class HolesJdbc
{
    public static final String TableName;

    public static final String ColHolesPkName = "holesPk";
    public static final String ColSubRepoFkName = "subrepoFk";
    public static final String ColMinName = "min";
    public static final String ColMaxName = "max";

    public static final int holesPkIdx = 1;
    public static final int SubRepoFkIdx = 2;
    public static final int MinIdx = 3;
    public static final int MaxIdx = 4;

    private static final String PrepGetRow;
    private static final String PrepGetRows;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;

    static
    {
        TableName = "holes";
        
        PrepGetRow = "select * from " + TableName + " where " + ColHolesPkName + "=?;";
        PrepGetRows = "select * from " + TableName + " where " + ColSubRepoFkName + "=? order by " + ColMinName +";";
        PrepDeleteRow = "delete from " + TableName + " where " + ColHolesPkName + "=?;";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName +
            " (" + ColSubRepoFkName + "," +
            ColMinName + "," +
            ColMaxName +  ")" +
            " values(?, ?, ?);";
        
        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColMinName + "=?," +
            ColMaxName + "=?" + 
            " where " + ColHolesPkName + "=?;";
        //@Formatter:on
    }

    public static HolesRow getRow(Connection connection, long pid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("holes pid: " + pid + " not found");
            return new HolesRow(rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static List<HolesRow> getRows(Connection connection, long pid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRows);
        List<HolesRow> list = new ArrayList<HolesRow>();
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, pid);
            rs = stmt.executeQuery();
            while (rs.next())
                list.add(new HolesRow(rs));
            return list;
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static int insertRow(Connection connection, HolesRow row, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            stmt.setInt(SubRepoFkIdx-1, row.subRepoFk);
            stmt.setLong(MinIdx-1, row.min);
            stmt.setLong(MaxIdx-1, row.max);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
        finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void updateRow(Connection connection, HolesRow row, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setLong(1, row.min);
            stmt.setLong(2, row.max);
            stmt.setInt(3, row.pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
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
}
