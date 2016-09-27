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
        scopes.add(ScopeToMask.getScopeToMask(scope));
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
