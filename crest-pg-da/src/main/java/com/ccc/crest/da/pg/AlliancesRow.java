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

import com.ccc.crest.da.PagingData;

@SuppressWarnings("javadoc")
public class AlliancesRow extends PagingData
{
    public AlliancesRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getLong(AlliancesJdbc.TotalAlliancesIdx),
            rs.getLong(AlliancesJdbc.PageCountIdx),
            rs.getInt(AlliancesJdbc.CountPerPageIdx));
        //@formatter:on
    }

    public AlliancesRow(Object[] columns)
    {
        //@formatter:off
        super(
            (long)columns[AlliancesJdbc.TotalAlliancesIdx-1],
            (long)columns[AlliancesJdbc.PageCountIdx-1],
            (int)columns[AlliancesJdbc.CountPerPageIdx-1]);
        //@formatter:on
    }

    @Override
    public String toString()
    {
        return "AlliancesRow [totalAlliances=" + total + ", pageCount=" + pageCount + ", countPerPage=" + countPerPage + "]";
    }
}
