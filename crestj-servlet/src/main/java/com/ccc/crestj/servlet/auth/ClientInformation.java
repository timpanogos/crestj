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
package com.ccc.crestj.servlet.auth;

import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.cycle.RequestCycle;

@SuppressWarnings("javadoc")
public class ClientInformation extends WebClientInfo 
{
    private static final long serialVersionUID = 1L;
    private boolean authenticated;
    private String name;
    private String email;
    private String description;
    
    public ClientInformation(RequestCycle requestCycle)
    {
        super(requestCycle);
    }

    public synchronized void setAuthenticated(boolean value)
    {
        authenticated = value;
    }
    
    public synchronized boolean isAuthenticated()
    {
        return authenticated;
    }
    
    public synchronized String getName()
    {
        return name;
    }

    public synchronized void setName(String name)
    {
        this.name = name;
    }

    public synchronized String getEmail()
    {
        return email;
    }

    public synchronized void setEmail(String email)
    {
        this.email = email;
    }

    public synchronized String getDescription()
    {
        return description;
    }

    public synchronized void setDescription(String description)
    {
        this.description = description;
    }
}
