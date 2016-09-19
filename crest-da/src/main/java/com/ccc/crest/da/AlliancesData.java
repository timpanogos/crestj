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
public class AlliancesData
{
    public final long totalAlliances;
    public final long pageCount;
    public final int countPerPage;
    
    public AlliancesData(long totalAlliances, long pageCount, int countPerPage)
    {
        this.totalAlliances = totalAlliances;
        this.pageCount = pageCount;
        this.countPerPage = countPerPage;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + countPerPage;
        result = prime * result + (int) (pageCount ^ (pageCount >>> 32));
        result = prime * result + (int) (totalAlliances ^ (totalAlliances >>> 32));
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
        AlliancesData other = (AlliancesData) obj;
        if (countPerPage != other.countPerPage)
            return false;
        if (pageCount != other.pageCount)
            return false;
        if (totalAlliances != other.totalAlliances)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "AlliancesData [totalAlliances=" + totalAlliances + ", pageCount=" + pageCount + ", countPerPage=" + countPerPage + "]";
    }
    
}
