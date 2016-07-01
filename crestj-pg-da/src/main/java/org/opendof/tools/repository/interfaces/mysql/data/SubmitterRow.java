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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.SubmitterJdbc;

@SuppressWarnings("javadoc")
public class SubmitterRow extends SubmitterData
{
    private static final long serialVersionUID = 3157069110565575726L;
    
    public final long pid;

    public SubmitterRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getString(SubmitterJdbc.NameIdx),
            rs.getString(SubmitterJdbc.EmailIdx),
            rs.getString(SubmitterJdbc.DescIdx),
            new Date(rs.getTimestamp(SubmitterJdbc.DateIdx).getTime()),
            rs.getByte(SubmitterJdbc.IsGroupIdx) == 1 ? true : false);
        //@formatter:on
        pid = rs.getLong(SubmitterJdbc.SubmitterPkIdx);
    }

    public SubmitterRow(Object[] columns)
    {
        //@formatter:off
        super(
            (String)columns[SubmitterJdbc.NameIdx-1],
            (String)columns[SubmitterJdbc.EmailIdx-1],
            (String)columns[SubmitterJdbc.DescIdx-1],
            new Date( ((Timestamp)columns[SubmitterJdbc.DateIdx-1]).getTime() ),
            (Boolean)columns[SubmitterJdbc.IsGroupIdx-1]);
        pid = ((BigInteger)columns[SubmitterJdbc.SubmitterPkIdx-1]).longValue();
        //@formatter:on
    }
}
