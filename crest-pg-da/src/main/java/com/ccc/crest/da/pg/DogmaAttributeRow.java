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

import com.ccc.crest.da.DogmaAttributeData;

@SuppressWarnings("javadoc")
public class DogmaAttributeRow extends DogmaAttributeData
{
    public DogmaAttributeRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getLong(CorporationJdbc.IdIdx),
            rs.getString(CorporationJdbc.TickerIdx),
            rs.getString(CorporationJdbc.NameIdx),
            rs.getString(CorporationJdbc.DescIdx),
            rs.getString(CorporationJdbc.UrlIdx),
            rs.getString(CorporationJdbc.HqNameIdx),
            rs.getString(CorporationJdbc.HqUrldx),
            rs.getString(CorporationJdbc.LoyaltyUrlIdx),
            rs.getInt(CorporationJdbc.PageIdx));
        //@formatter:on
    }

    @Override
    public String toString()
    {
        return "CorporationRow [id=" + id + ", ticker=" + ticker + ", name=" + name + ", description=" + description + ", corpUrl=" + corpUrl + ", loyaltyUrl=" + loyaltyUrl + ", headquartersName=" + headquartersName + ", headquartersUrl=" + headquartersUrl + ", page=" + page + "]";
    }
}
