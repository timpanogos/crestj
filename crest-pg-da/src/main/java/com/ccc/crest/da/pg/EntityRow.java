package com.ccc.crest.da.pg;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.crest.da.EntityData;

@SuppressWarnings("javadoc")
public class EntityRow extends EntityData
{
    public final long pid;

    public EntityRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getString(EntityJdbc.NameIdx),
            rs.getBoolean(EntityJdbc.GroupIdx));
        //@formatter:on
        pid = rs.getLong(EntityJdbc.EntityPkIdx);
    }

    public EntityRow(Object[] columns)
    {
        //@formatter:off
        super(
            (String)columns[EntityJdbc.NameIdx-1],
            (Boolean)columns[EntityJdbc.GroupIdx-1]);
        pid = ((BigInteger)columns[CapsuleerJdbc.UserPkIdx-1]).longValue();
        //@formatter:on
    }
    
    @Override
    public String toString()
    {
        return "pid: " + pid + " name: " + name + " group: " + group; 
    }
}
