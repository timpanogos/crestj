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
package com.ccc.crest.core;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "javadoc" })
public class Scope
{
    public static final String AccessMask = "?accessMask=";
    public static final String OwnerId = "&ownerID=";
    public static final String OwnerType = "&ownerType=";
    public static final String OwnerTypeDefault = "Character";
    
    public final List<ScopeToMask> scopes;
    
    private String createPredefinedUrl;
    
    public Scope()
    {
        scopes = new ArrayList<>();
    }
    
    public synchronized String getCreatePredefinedUrl(CrestClientInfo clientInfo, ScopeToMask.Type type)
    {
        String ownerId = clientInfo.getVerifyData().CharacterID;
        if(type == ScopeToMask.Type.Character)
            return createPredefinedUrl + AccessMask + getCharacterMask() + OwnerId + ownerId;
        //TODO: need to get corporation ownerID not character
        return createPredefinedUrl + AccessMask + getCorporateMask() + OwnerId + ownerId + OwnerType + OwnerTypeDefault; // note this one did not seem to work with manual entry of url in browser
    }

    public synchronized void setCreatePredefinedUrl(String createPredefinedUrl)
    {
        this.createPredefinedUrl = createPredefinedUrl;
    }

    public void addScope(String scope) throws Exception
    {
        ScopeToMask stm = ScopeToMask.characterScopes.get(scope);
        if(stm == null)
            throw new Exception("Configured scope: " + scope + " is invalid");
        scopes.add(stm);
    }
    
    public long getCharacterMask()
    {
        long mask = 0;
        for(ScopeToMask bit : scopes)
        {
            if(bit.type == ScopeToMask.Type.Character)
            {
                for(int i=0; i < bit.masks.length; i++)
                    mask |= bit.masks[i];
            }
        }
        return mask;
    }
    
    public long getCorporateMask()
    {
        long mask = 0;
        for(ScopeToMask bit : scopes)
        {
            if(bit.type == ScopeToMask.Type.Corporate)
            {
                for(int i=0; i < bit.masks.length; i++)
                    mask |= bit.masks[i];
            }
        }
        return mask;
    }
}
