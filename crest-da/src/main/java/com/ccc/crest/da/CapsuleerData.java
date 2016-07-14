/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.da;

@SuppressWarnings("javadoc")
public class CapsuleerData
{
    public final String capsuleer;
    public final long capsuleerId;
    public final long apiKeyId;
    public final String apiCode;
    public final String refreshToken;

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
        result = prime * result + ((apiCode == null) ? 0 : apiCode.hashCode());
        result = prime * result + (int) (apiKeyId ^ (apiKeyId >>> 32));
        result = prime * result + ((capsuleer == null) ? 0 : capsuleer.hashCode());
        result = prime * result + (int) (capsuleerId ^ (capsuleerId >>> 32));
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
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
        return "capsuleerId: " + capsuleerId + " capsuleer: " + capsuleer + " keyId: " + apiKeyId + " apiCode: " + (apiCode == null ? "null" : apiCode) + " refreshToken: " + (refreshToken == null ? "null" : refreshToken);
    }
}
