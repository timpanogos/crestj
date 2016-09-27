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

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class CapsuleerData
{
    public final String capsuleer;
    public final long capsuleerId;
    public final long apiKeyId;
    public final String apiCode;
    public final String refreshToken;
    public final

    public CapsuleerData(String name, long capsuleerId, long apiKeyId, String apiCode, String refreshToken)
    {
        this.capsuleer = name;
        this.capsuleerId = capsuleerId;
        this.apiKeyId = apiKeyId;
        this.apiCode = apiCode;
        this.refreshToken = refreshToken;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (apiCode == null ? 0 : apiCode.hashCode());
        result = prime * result + (int) (apiKeyId ^ apiKeyId >>> 32);
        result = prime * result + (capsuleer == null ? 0 : capsuleer.hashCode());
        result = prime * result + (int) (capsuleerId ^ capsuleerId >>> 32);
        result = prime * result + (refreshToken == null ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        CapsuleerData other = (CapsuleerData) obj;
        if (apiCode == null)
        {
            if (other.apiCode != null)
                return false;
        } else if (!apiCode.equals(other.apiCode))
            return false;
        if (apiKeyId != other.apiKeyId)
            return false;
        if (capsuleer == null)
        {
            if (other.capsuleer != null)
                return false;
        } else if (!capsuleer.equals(other.capsuleer))
            return false;
        if (capsuleerId != other.capsuleerId)
            return false;
        if (refreshToken == null)
        {
            if (other.refreshToken != null)
                return false;
        } else if (!refreshToken.equals(other.refreshToken))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("capsuleer: ", capsuleer);
        format.ttl("capsuleerId: ", capsuleerId);
        format.ttl("apiKeyId: ", apiKeyId);
        format.ttl("apiCode: ", apiCode);
        format.ttl("refreshToken: ", refreshToken);
        return format;
    }
}
