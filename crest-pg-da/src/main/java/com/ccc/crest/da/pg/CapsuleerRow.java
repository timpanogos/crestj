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
