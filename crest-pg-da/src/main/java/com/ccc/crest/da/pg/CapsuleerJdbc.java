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
import com.ccc.crest.da.EntityData;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;

@SuppressWarnings("javadoc") 
public class CapsuleerJdbc
{
    public static final String TableName;

    public static final String ColCapPkName = "capsuleerpk";
    public static final String ColCapName = "capsuleer";
    public static final String ColCapIdName = "capsuleerid";
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

        PrepGetUserPk = "select " + ColCapPkName + " from " + TableName + " where " + ColCapName + "=?;";
        PrepGetRow = "select * from " + TableName + " where " + ColCapPkName + "=?;";
        PrepDeleteRow = "delete from " + TableName + " where " + ColCapPkName + "=?;";
        PrepListAll = "select * from " + TableName +";";

        //@formatter:off
        PrepInsertRow = 
            "insert into " + TableName + " (" + 
            ColCapName + "," + 
            ColCapIdName + "," + 
            ColApiKeyIdName + "," + 
            ColApiCodeName + "," + 
            ColRefreshTokenName + ")" + 
            " values(?, ?, ?, ?, ?);";

        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColCapName + "=?," + 
            ColCapIdName + "=?," + 
            ColApiKeyIdName + "=?," + 
            ColApiCodeName + "=?," + 
            ColRefreshTokenName + "=?" + 
            " where " + ColCapPkName + "=?;";
        //@formatter:on
    }

    public static long getPid(Connection connection, long capId, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetUserPk);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, capId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("capId: " + capId + " not found");
            return rs.getLong(1);
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static CapsuleerRow getCapsuleer(Connection connection, String name) throws NotFoundException, SQLException
    {
        return getCapsuleer(connection, name, true);
    }

    public static CapsuleerRow getCapsuleer(Connection connection, String name, boolean close) throws NotFoundException, SQLException
    {
        try
        {
            EntityRow erow = EntityJdbc.getEntity(connection, name, false);
            long pid = getPid(connection, erow.pid, false);
            return getRow(connection, pid, erow, close);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
    }

    public static CapsuleerRow getRow(Connection connection, long capId, EntityRow erow, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setLong(1, capId);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("capsuleer: " + erow.name + " not found");
            CapsuleerRow crow = new CapsuleerRow(rs);
            return new CapsuleerRow(crow, erow.name);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, rs, close);
        }
    }

    public static void addCapsuleer(Connection connection, CapsuleerData capData, boolean close) throws AlreadyExistsException, SQLException
    {
        try
        {
            EntityJdbc.getEntity(connection, capData.capsuleer, false);
            throw new AlreadyExistsException("capsuleer: '" + capData.capsuleer + "' already exists");
        } catch (NotFoundException nfe)
        {
            try
            {
                // begin a transaction block to rollback new entity insert if our insert fails
                connection.setAutoCommit(false);
                // add the new capsuleer to the entity table.
                EntityData edata = new EntityData(capData.capsuleer, false);
                long pid = EntityJdbc.insertRow(connection, edata, false); // groupId is the pid of the groups admin row
                insertRow(connection, pid, capData, false);
                connection.commit();
            } catch (Exception e)
            {
                connection.rollback();
                throw e;
            }
        } finally
        {
            PgBaseDataAccessor.close(connection, null, null, close);
        }
        
    }

    public static long insertRow(Connection connection, long entityPid, CapsuleerData capData, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            stmt.setLong(NameIdx - 1, entityPid);
            stmt.setLong(UserIdIdx - 1, capData.capsuleerId);
            stmt.setLong(ApiKeyIdIdx - 1, capData.apiKeyId);
            stmt.setString(ApiCodeIdx - 1, capData.apiCode);
            stmt.setString(RefreshTokenIdx - 1, capData.refreshToken);
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

    public static void updateCapsuleer(Connection connection, CapsuleerData capData) throws NotFoundException, SQLException
    {
        try
        {
            EntityRow erow = EntityJdbc.getEntity(connection, capData.capsuleer, false);
            long pid = getPid(connection, erow.pid, false);
            updateRow(connection, pid, erow, capData);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, true);
            throw e;
        }
    }

    public static void updateRow(Connection connection, long capPid, EntityRow erow, CapsuleerData capData) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        try
        {
            stmt.setLong(NameIdx - 1, erow.pid);
            stmt.setLong(UserIdIdx - 1, capData.capsuleerId);
            stmt.setLong(ApiKeyIdIdx - 1, capData.apiKeyId);
            stmt.setString(ApiCodeIdx - 1, capData.apiCode);
            stmt.setString(RefreshTokenIdx - 1, capData.refreshToken);
            stmt.setLong(6, capPid);
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
        try
        {
            EntityRow erow = EntityJdbc.getEntity(connection, name, false);
            long pid = getPid(connection, erow.pid, false);
            deleteRow(connection, pid, erow.pid, close);
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, null, null, close);
            throw e;
        }
    }

    public static void deleteRow(Connection connection, long capPid, long epid, boolean close) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
        try
        {
            connection.setAutoCommit(false);
            stmt.setLong(1, capPid);
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
            EntityJdbc.deleteRow(connection, epid, false);
            connection.commit();
        } catch (Exception e)
        {
            connection.rollback();
            throw e;
        } finally
        {
            PgBaseDataAccessor.close(connection, stmt, null, close);
        }
    }
    
    public static List<CapsuleerData> listCapsuleers(Connection connection) throws Exception
    {
        PreparedStatement stmt = connection.prepareStatement(PrepListAll);
        ResultSet rs = null;
        List<CapsuleerData> list = new ArrayList<>();
        try
        {
            rs = stmt.executeQuery();
            while(rs.next())
            {
                CapsuleerRow crow = new CapsuleerRow(rs);
                EntityRow erow = EntityJdbc.getRow(connection, Long.parseLong(crow.capsuleer), false);
                list.add(new CapsuleerRow(crow, erow.name));
            }
        } catch (Exception e)
        {
            PgBaseDataAccessor.close(connection, stmt, rs, true);
            throw e;
        }
        return list;
    }
}
