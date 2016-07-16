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
package com.ccc.crest.da.pg;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ccc.crest.da.SharedRight;

@SuppressWarnings("javadoc")
public class SharedRightRow extends SharedRight
{
    public final long pid;

    public SharedRightRow(ResultSet rs) throws SQLException
    {
        //@formatter:off
        super(
            rs.getString(SharedRightJdbc.Cap1Idx),
            rs.getString(SharedRightJdbc.Cap2Idx),
            SharedRight.Type.getType(rs.getInt(SharedRightJdbc.TypeIdx)));
        //@formatter:on
        pid = rs.getLong(EntityJdbc.EntityPkIdx);
    }

    public SharedRightRow(Object[] columns)
    {
        //@formatter:off
        super(
            (String)columns[SharedRightJdbc.Cap1Idx-1],
            (String)columns[SharedRightJdbc.Cap2Idx-1],
            SharedRight.Type.getType((Integer)columns[SharedRightJdbc.TypeIdx-1]));
        pid = ((BigInteger)columns[CapsuleerJdbc.UserPkIdx-1]).longValue();
        //@formatter:on
    }

    @Override
    public String toString()
    {
        return "pid: " + pid + " cap1: " + capsuleer + " cap2: " + capsuleer2 + " type: " + type;
    }
}
