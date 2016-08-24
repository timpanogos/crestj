package com.ccc.crest.da.pg;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.crest.da.CapsuleerData;

@SuppressWarnings("javadoc")
public class CapsuleerRow extends CapsuleerData
{
    public final long pid;

    public CapsuleerRow(CapsuleerRow data, String capsuleer)
    {
        super(capsuleer, data.capsuleerId, data.apiKeyId, data.apiCode, data.refreshToken);
        this.pid = data.pid;
    }
    
    public CapsuleerRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getString(CapsuleerJdbc.NameIdx),
            rs.getLong(CapsuleerJdbc.UserIdIdx),
            rs.getLong(CapsuleerJdbc.ApiKeyIdIdx),
            rs.getString(CapsuleerJdbc.ApiCodeIdx),
            rs.getString(CapsuleerJdbc.RefreshTokenIdx));
        //@formatter:on
        pid = rs.getLong(CapsuleerJdbc.UserPkIdx);
    }

    public CapsuleerRow(Object[] columns)
    {
        //@formatter:off
        super(
            (String)columns[CapsuleerJdbc.NameIdx-1],
            ((BigInteger)columns[CapsuleerJdbc.ApiKeyIdIdx-1]).longValue(),
            ((BigInteger)columns[CapsuleerJdbc.ApiKeyIdIdx-1]).longValue(),
            (String)columns[CapsuleerJdbc.ApiCodeIdx-1],
            (String)columns[CapsuleerJdbc.RefreshTokenIdx-1]);
        pid = ((BigInteger)columns[CapsuleerJdbc.UserPkIdx-1]).longValue();
        //@formatter:on
    }
}
