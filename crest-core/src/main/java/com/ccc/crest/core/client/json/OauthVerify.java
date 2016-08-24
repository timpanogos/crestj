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

