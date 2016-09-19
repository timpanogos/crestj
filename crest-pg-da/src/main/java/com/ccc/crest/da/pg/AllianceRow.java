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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.crest.da.AllianceData;

@SuppressWarnings("javadoc")
public class AllianceRow extends AllianceData
{
    public AllianceRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getLong(AllianceJdbc.IdIdx),
            rs.getString(AllianceJdbc.ShortIdx),
            rs.getString(AllianceJdbc.NameIdx),
            rs.getString(AllianceJdbc.UrlIdx),
            rs.getInt(AllianceJdbc.PageIdx));
        //@formatter:on
    }

    public AllianceRow(Object[] columns)
    {
        //@formatter:off
        super(
            (long)columns[AllianceJdbc.IdIdx-1],
            (String)columns[AllianceJdbc.ShortIdx-1],
            (String)columns[AllianceJdbc.NameIdx-1],
            (String)columns[AllianceJdbc.UrlIdx-1],
            (int)columns[AllianceJdbc.PageIdx-1]);
        //@formatter:on
    }

    @Override
    public String toString()
    {
        return "AllianceRow [id=" + id + ", shortName=" + shortName + ", name=" + name + ", allianceUrl=" + allianceUrl + ", page=" + page + "]";
    }

}
