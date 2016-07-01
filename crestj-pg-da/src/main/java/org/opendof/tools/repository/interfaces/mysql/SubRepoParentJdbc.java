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
import org.opendof.tools.repository.interfaces.da.SubRepoRelationship;
import org.opendof.tools.repository.interfaces.mysql.data.SubRepoParentRow;

@SuppressWarnings("javadoc")
public class SubRepoParentJdbc
{
    public static final String TableName;

    public static final String ColParentFkName = "parentFk";
    public static final String ColChildFkName = "childFk";
    public static final String ColDepthName = "depth";

    public static final int ParentFkIdx = 1;
    public static final int ChildFkIdx = 2;
    public static final int DepthIdx = 3;

    private static final String PrepGetParentFk;
    private static final String PrepGetChildFk;
    private static final String PrepInsertRow;
    private static final String PrepDeleteRow;
    private static final String PrepGetChildren;

    static
    {
        TableName = "subrepoparent";
        PrepGetParentFk = "select * from " + TableName + " where " + ColParentFkName + "=?;";
        PrepGetChildFk = "select * from " + TableName + " where " + ColParentFkName + "=? and " + ColChildFkName + "=?;";
        PrepGetChildren = "select * from " + TableName + " where " + ColParentFkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColParentFkName + "=? and " + ColChildFkName + "=? and " + ColDepthName + "=?;";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName +
            " (" + ColParentFkName + "," +
            ColChildFkName + "," +
            ColDepthName + ")" +
            " values(?, ?, ?);";
        //@Formatter:on
    }

    public static List<SubRepoParentRow> getChildren(Connection connection, int parentPid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetChildren);
        ResultSet rs = null;
        List<SubRepoParentRow> children = new ArrayList<SubRepoParentRow>();
        try
        {
            stmt.setInt(1, parentPid);
            rs = stmt.executeQuery();
            if (!rs.next())
                return children;
            children.add(new SubRepoParentRow(rs));
            while(rs.next())
                children.add(new SubRepoParentRow(rs));
            return children;
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static SubRepoParentRow find(Connection connection, String parent, String child, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetChildFk);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, parent);
            stmt.setString(2, child);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("parent: " + parent + " child: " + child + " not found");
            return new SubRepoParentRow(rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void insertRow(Connection connection, SubRepoRelationship relationship, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow);
        ResultSet rs = null;
        try
        {
            if(relationship.parent == null)
                stmt.setNull(ParentFkIdx, java.sql.Types.INTEGER);
            else
                stmt.setInt(ParentFkIdx, relationship.parent);
            stmt.setInt(ChildFkIdx, relationship.child);
            stmt.setInt(DepthIdx, relationship.depth);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
        }
        finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }
    
    public static void deleteRow(Connection connection, long parentPid, long childPid, int depth, boolean close) throws SQLException
    {
       PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
       try
       {
           stmt.setLong(1, parentPid);
           stmt.setLong(2, childPid);
           stmt.setInt(3, depth);
           int rows = stmt.executeUpdate();
           if (rows != 1)
               throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
       } finally
       {
           MysqlDataAccessor.close(connection, stmt, null, close);
       }
   }
}
