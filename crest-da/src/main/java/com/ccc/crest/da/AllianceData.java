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
public class AllianceData
{
    public final long id;
    public final String shortName;
    public final String name;
    public final String allianceUrl;
    public final int page;
    
    public AllianceData(long id, String shortName, String name, String allianceUrl, int page)
    {
        this.id = id;
        this.shortName = shortName;
        this.name = name;
        this.page = page;
        this.allianceUrl = allianceUrl;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allianceUrl == null) ? 0 : allianceUrl.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + page;
        result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
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
        AllianceData other = (AllianceData) obj;
        if (allianceUrl == null)
        {
            if (other.allianceUrl != null)
                return false;
        } else if (!allianceUrl.equals(other.allianceUrl))
            return false;
        if (id != other.id)
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (page != other.page)
            return false;
        if (shortName == null)
        {
            if (other.shortName != null)
                return false;
        } else if (!shortName.equals(other.shortName))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "AllianceData [id=" + id + ", shortName=" + shortName + ", name=" + name + ", page=" + page + ", allianceUrl=" + allianceUrl + "]";
    }
}
