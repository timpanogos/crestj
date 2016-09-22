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
public class PagingRow extends PagingData
{
    public final long pid;

    public PagingRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getLong(PagingJdbc.TotalItemsIdx),
            rs.getLong(PagingJdbc.PageCountIdx),
            rs.getInt(PagingJdbc.ItemsPerPageIdx),
            rs.getString(PagingJdbc.UidIdx));
        //@formatter:on
        pid = rs.getLong(PagingJdbc.IdIdx);
    }

    @Override
    public String toString()
    {
        return "PagingRow [pid=" + pid + ", totalItems=" + totalItems + ", pageCount=" + pageCount + ", itemsPerPage=" + itemsPerPage + ", uid=" + uid + "]";
    }

}
