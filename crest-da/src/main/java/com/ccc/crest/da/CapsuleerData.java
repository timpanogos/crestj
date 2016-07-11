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
    
    public CapsuleerData(String name, long userId, long apiKeyId, String apiCode, String refreshToken)
    {
        this.capsuleer = name;
        this.capsuleerId = userId;
        this.apiKeyId = apiKeyId;
        this.apiCode = apiCode;
        this.refreshToken = refreshToken;
    }
    
    @Override
    public String toString()
    {
        return "capsuleerId: " + capsuleerId + " capsuleer: " + capsuleer + " keyId: " + apiKeyId + " apiCode: " + (apiCode == null ? "null" : apiCode) + " refreshToken: " + (refreshToken == null ? "null" : refreshToken); 
    }
}
