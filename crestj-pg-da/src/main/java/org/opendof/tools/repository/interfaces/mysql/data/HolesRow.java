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

import org.opendof.tools.repository.interfaces.mysql.HolesJdbc;

@SuppressWarnings("javadoc")
public class HolesRow
{
    public volatile int pid;
    public final int subRepoFk;
    public final long min;
    public final long max;
    
    public HolesRow(int subRepoFk, long min, long max)
    {
        this(-1, subRepoFk, min, max);
    }
    
    public HolesRow(int pid, int subRepoFk, long min, long max)
    {
        this.pid = pid;
        this.subRepoFk = subRepoFk;
        this.min = min;
        this.max = max;
    }
    
    public HolesRow(ResultSet rs) throws SQLException
    {
        pid = rs.getInt(HolesJdbc.holesPkIdx);
        subRepoFk = rs.getInt(HolesJdbc.SubRepoFkIdx);
        min = rs.getLong(HolesJdbc.MinIdx);
        max = rs.getLong(HolesJdbc.MaxIdx);
    }

    public HolesRow(Object[] columns)
    {
        pid = (int)columns[HolesJdbc.holesPkIdx];
        subRepoFk = (int)columns[HolesJdbc.SubRepoFkIdx];
        min = (long)columns[HolesJdbc.MinIdx];
        max = (long)columns[HolesJdbc.MaxIdx];
    }
    
    @Override
    public String toString()
    {
        return "pid: " + pid + " subRepoFk: " + subRepoFk + " min: " + min + " max: " + max;
    }
}
