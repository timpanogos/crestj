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
package com.ccc.crestj.servlet.data;

import java.io.Serializable;
import java.util.Date;

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class SubmitterData implements Serializable  
{
    private static final long serialVersionUID = 5773906748536040934L;
    
    public final String name;
    public final String email;
    public final String description;
    public final Date date;
    public final boolean group;
    
    public SubmitterData(String submitter, String email, String description)   
    {
        this(submitter, email, description, null);
    }
    
    public SubmitterData(String submitter, String email, String description, Date date)   
    {
        this(submitter, email, description, date, false);
    }
    
    public SubmitterData(String name, String email, String description, Date date, boolean group)   
    {
        this.name = name;
        this.email = email;
        this.description = description;
        this.date = date;
        this.group = group;
    }

	@Override
	public String toString()
	{
	    return toString(new TabToLevel()).toString();
	}
	
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("name: ", name);
        format.ttl("email: ", email);
        format.ttl("description: ", description);
        format.ttl("group: " + group);
        format.ttl("date ", date);
        return format;
	}
}
