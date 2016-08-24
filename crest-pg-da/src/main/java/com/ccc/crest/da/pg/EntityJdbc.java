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

import com.ccc.crest.da.EntityData;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc") 
public class EntityJdbc
{
    public static final String TableName;

    public static final String ColEntityPkName = "entitypk";
    public static final String ColNameName = "name";
    public static final String ColGroupName = "isgroup";

    public static final int EntityPkIdx = 1;
    public static final int NameIdx = 2;
    public static final int GroupIdx = 3;

    private static final String PrepGetEntityPk;
    private static final String PrepGetRow;
    private static final String PrepInsertRow;
    private static final String PrepUpdateRow;
    private static final String PrepDeleteRow;
    private static final String PrepListAll;
    private static final String PrepListAllGroups;

    static
    {
        TableName = "entity";

        PrepGetEntityPk = "select " + ColEntityPkName + " from " + TableName + " where " + ColNameName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColEntityPkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColEntityPkName + "=?;";
        PrepListAll = "select * from " + TableName +";";
        PrepListAllGroups = "select * from " + TableName + " where " + ColGroupName + "=true;";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName + " (" + 
            ColNameName + "," + 
            ColGroupName + ")" + 
            " values(?, ?);";

        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColNameName + "=?," + 
            ColGroupName + "=?" + 
            " where " + ColEntityPkName + "=?;";
        //@formatter:on
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

    public static EntityRow getEntity(Connection connection, String name) throws NotFoundException, SQLException
    {
        return getEntity(connection, name, true);
    }

    public static EntityRow getEntity(Connection connection, String name, boolean close) throws NotFoundException, SQLException
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
        return getRow(connection, pid, close);
    }

    public static EntityRow getRow(Connection connection, long pid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("user pid: " + pid + " not found");
            return new EntityRow(rs);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addEntity(Connection connection, EntityData entityData, boolean close) throws AlreadyExistsException, SQLException
    {
        try
        {
            getPid(connection, entityData.name, false);
            throw new AlreadyExistsException("name: '" + entityData.name + "' already exists");
        } catch (NotFoundException nfe)
        {
            insertRow(connection, entityData, close);
        }
    }

    public static long insertRow(Connection connection, EntityData entityData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            stmt.setString(NameIdx - 1, entityData.name);
            stmt.setBoolean(GroupIdx - 1, entityData.group);
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

    public static void updateEntity(Connection connection, EntityData entityData) throws NotFoundException, SQLException
    {
        long pid;
        try
        {
            pid = getPid(connection, entityData.name, false);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
        updateRow(connection, pid, entityData);
    }

    public static void updateRow(Connection connection, long pid, EntityData entityData) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setString(NameIdx - 1, entityData.name);
            stmt.setBoolean(GroupIdx - 1, entityData.group);
            stmt.setLong(3, pid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, true);
        }
    }

    public static void deleteEntity(Connection connection, String name, boolean close) throws NotFoundException, SQLException
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

    public static List<EntityData> listEntities(Connection connection) throws Exception
    {
        PreparedStatement stmt = connection.prepareStatement(PrepListAll);
        ResultSet rs = null;
        List<EntityData> list = new ArrayList<>();
        try
        {
            rs = stmt.executeQuery();
            while(rs.next())
                list.add(new EntityRow(rs));
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
        return list;
    }
    
    public static List<EntityRow> listGroups(Connection connection, boolean close) throws Exception
    {
        PreparedStatement stmt = connection.prepareStatement(PrepListAllGroups);
        ResultSet rs = null;
        List<EntityRow> list = new ArrayList<>();
        try
        {
            rs = stmt.executeQuery();
            while(rs.next())
                list.add(new EntityRow(rs));
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
            throw e;
        }
        return list;
    }
}
