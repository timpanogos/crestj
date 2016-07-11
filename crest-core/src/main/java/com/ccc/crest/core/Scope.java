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
    public final List<ScopeToMask> scopes;
    private String createPredefinedUrl;
    
    public Scope()
    {
        scopes = new ArrayList<>();
    }
    
    public synchronized String getCreatePredefinedUrl(ScopeToMask.Type type)
    {
        if(type == ScopeToMask.Type.Character)
            return createPredefinedUrl + "?" + getCharacterMask();
        return createPredefinedUrl + "?" + getCorporateMask();
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
                mask |= bit.masks[0];
        }
        return mask;
    }
    
    public long getCorporateMask()
    {
        long mask = 0;
        for(ScopeToMask bit : scopes)
        {
            if(bit.type == ScopeToMask.Type.Corporate)
            mask |= bit.masks[0];
        }
        return mask;
    }
}
