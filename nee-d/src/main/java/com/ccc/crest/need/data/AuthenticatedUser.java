/*
**  Copyright (c) 2010-2015, Panasonic Corporation.
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
package com.ccc.crest.need.data;

import com.ccc.tools.TabToLevel;

@SuppressWarnings({"javadoc"})
public class AuthenticatedUser extends SubmitterData
{
    private static final long serialVersionUID = 5076762517411970846L;
    
    public final UserRights rights;

    public AuthenticatedUser(String user, String email, String description, UserRights rights) 
    {
        super(user, email, description, null);
        this.rights = rights;
    }
    
    @Override
    public String toString()
    {
        return toString(new TabToLevel()).toString();
    }
    
    @Override
    public TabToLevel toString(TabToLevel format)
    {
        super.toString(format);
        format.ttl("rights:");
        format.level.incrementAndGet();
        if(rights == null)
            format.ttl("null");
        else
            rights.toString(format);
        return format;
    }
    
    public boolean match(AuthenticatedUser user)
    {
        if(user.email.equals(email))
            return true;
        return false;
    }
}
