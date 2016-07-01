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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.dbutils.ResultSetIterator;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.da.DataAccessor.SubmitterIterable;
import org.opendof.tools.repository.interfaces.mysql.MysqlDataAccessor;

@SuppressWarnings("javadoc")
public class MysqlSubmitterIterable implements SubmitterIterable
{
    private final SubmitterIterator creatorIterator;

    public MysqlSubmitterIterable(Connection connection, PreparedStatement stmt, ResultSet rs)
    {
        creatorIterator = new SubmitterIterator(connection, stmt, rs);
    }

    @Override
    public void close() throws Exception
    {
        creatorIterator.close();
    }

    @Override
    public Iterator<SubmitterData> iterator()
    {
        return creatorIterator;
    }

    private class SubmitterIterator implements Iterator<SubmitterData>
    {
        private final ResultSetIterator rsi;
        private final Connection connection;
        private final PreparedStatement stmt;
        private final ResultSet rs;
        private final AtomicBoolean closed;

       
        private SubmitterIterator(Connection connection, PreparedStatement stmt, ResultSet rs)
        {
            this.connection = connection;
            this.stmt = stmt;
            this.rs = rs;
            rsi = new ResultSetIterator(rs);
            closed = new AtomicBoolean(false);
        }

        @Override
        public boolean hasNext()
        {
            if (closed.get())
                throw new IllegalStateException("This iterator has been closed");
            return rsi.hasNext();
        }

        @Override
        public SubmitterData next()
        {
            if (closed.get())
                throw new IllegalStateException("This iterator has been closed");
            Object[] columns = rsi.next();
            return new SubmitterRow(columns);
        }

        public void close() throws Exception
        {
            if (closed.get())
                return;
            closed.set(true);
            MysqlDataAccessor.close(connection, stmt, rs, true);
        }
    }
}
