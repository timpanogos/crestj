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
package org.opendof.tools.repository.interfaces.da;

import org.opendof.tools.repository.interfaces.da.SubRepositoryNode.TabToLevel;

@SuppressWarnings("javadoc")
public class GroupData extends SubmitterData  
{
    private static final long serialVersionUID = 1776199196650386001L;
    
    public final SubmitterData groupAdmin;
    
    public GroupData(SubmitterData subdata, SubmitterData groupAdmin)   
    {
        super(subdata.name, subdata.email, subdata.description, subdata.date, subdata.group);
        this.groupAdmin = groupAdmin;
    }
    
	@Override
	public String toString()
	{
	    return toString(new TabToLevel()).toString();
	}
	
    @Override
    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("name: ", name);
        format.ttl("email: ", email);
        format.ttl("description: ", description);
        format.ttl("group: " + group);
        format.ttl("date: ", date);
        format.ttl("admin:");
        format.level.incrementAndGet();
        groupAdmin.toString(format);
        format.level.decrementAndGet();
        return format;
	}
}
