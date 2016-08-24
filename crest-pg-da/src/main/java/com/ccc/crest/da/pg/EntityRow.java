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
