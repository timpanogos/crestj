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
package com.ccc.crest.da;

@SuppressWarnings("javadoc")
public class PagingData
{
    public final long totalItems;
    public final long pageCount;
    public final int itemsPerPage;
    public final String uid;

    public PagingData(long totalItems, long pageCount, int itemsPerPage, String uid)
    {
        this.totalItems = totalItems;
        this.pageCount = pageCount;
        this.itemsPerPage = itemsPerPage;
        this.uid = uid;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + itemsPerPage;
        result = prime * result + (int) (pageCount ^ pageCount >>> 32);
        result = prime * result + (int) (totalItems ^ totalItems >>> 32);
        result = prime * result + (uid == null ? 0 : uid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PagingData other = (PagingData) obj;
        if (itemsPerPage != other.itemsPerPage)
            return false;
        if (pageCount != other.pageCount)
            return false;
        if (totalItems != other.totalItems)
            return false;
        if (uid == null)
        {
            if (other.uid != null)
                return false;
        } else if (!uid.equals(other.uid))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "PagingData [totalItems=" + totalItems + ", pageCount=" + pageCount + ", itemsPerPage=" + itemsPerPage + ", uid=" + uid + "]";
    }

}
