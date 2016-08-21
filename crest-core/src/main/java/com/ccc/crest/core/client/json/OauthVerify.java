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
package com.ccc.crest.core.client.json;

import java.io.Serializable;

import com.ccc.tools.TabToLevel;
import com.google.gson.Gson;

@SuppressWarnings("javadoc")
public class OauthVerify implements Serializable
{
    private static final long serialVersionUID = 2882934574060991858L;
    
    public String CharacterID;
    public String CharacterName;
    public String ExpiresOn;
    public String Scopes;
    public String TokenType;
    public String CharacterOwnerHash;
    
    public static OauthVerify getOauthVerifyData(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, OauthVerify.class); 
    }
    
    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }
    
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("CharacterID: ", CharacterID);
        format.ttl("CharacterName: ", CharacterName);
        format.ttl("ExpiresOn: ", ExpiresOn);
        format.ttl("TokenType: ", TokenType);
        format.ttl("Scopes: ", Scopes);
        format.ttl("CharacterOwnerHash: ", CharacterOwnerHash);
        return format;
    }
}

