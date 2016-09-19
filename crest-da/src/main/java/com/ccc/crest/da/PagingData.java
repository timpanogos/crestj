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
    public final long total;
    public final long pageCount;
    public final int countPerPage;
    public final String uid;

    public PagingData(long totalAlliances, long pageCount, int countPerPage, String uid)
    {
        this.total = totalAlliances;
        this.pageCount = pageCount;
        this.countPerPage = countPerPage;
        this.uid = uid;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + countPerPage;
        result = prime * result + (int) (pageCount ^ pageCount >>> 32);
        result = prime * result + (int) (total ^ total >>> 32);
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
        if (countPerPage != other.countPerPage)
            return false;
        if (pageCount != other.pageCount)
            return false;
        if (total != other.total)
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
        return "PagingData [total=" + total + ", pageCount=" + pageCount + ", countPerPage=" + countPerPage + ", uid=" + uid + "]";
    }
}
