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
package org.opendof.tools.repository.interfaces.mysql.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.opendof.tools.repository.interfaces.mysql.GroupJdbc;

@SuppressWarnings("javadoc")
public class GroupRow
{
    public final long groupId;
    public final long submitterId;
    public final long memberId;

    public GroupRow(ResultSet rs) throws SQLException
    {
        groupId = rs.getLong(GroupJdbc.GroupIdIdx);
        submitterId = rs.getLong(GroupJdbc.SubmitterIdx);
        memberId = rs.getLong(GroupJdbc.MemberIdx);
    }

    public GroupRow(Object[] columns)
    {
        groupId = (Long)columns[GroupJdbc.GroupIdIdx-1];
        submitterId = (Long)columns[GroupJdbc.SubmitterIdx-1];
        memberId = (Long)columns[GroupJdbc.MemberIdx-1];
    }
}
