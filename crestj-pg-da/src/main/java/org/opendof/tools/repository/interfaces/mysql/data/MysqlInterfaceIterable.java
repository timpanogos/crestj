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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.dbutils.ResultSetIterator;
import org.opendof.tools.repository.interfaces.da.DataAccessor.InterfaceIterable;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.InterfaceJdbc;
import org.opendof.tools.repository.interfaces.mysql.MysqlDataAccessor;
import org.opendof.tools.repository.interfaces.mysql.SubmitterJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class MysqlInterfaceIterable implements InterfaceIterable
{
    private final CreatorIterator creatorIterator;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MysqlInterfaceIterable(Connection connection, PreparedStatement stmt, ResultSet rs)
    {
        creatorIterator = new CreatorIterator(connection, stmt, rs);
    }

    @Override
    public void close() throws Exception
    {
        creatorIterator.close();
    }

    @Override
    public Iterator<InterfaceData> iterator()
    {
        return creatorIterator;
    }

    private class CreatorIterator implements Iterator<InterfaceData>
    {
        private final ResultSetIterator rsi;
        private final Connection connection;
        private final PreparedStatement stmt;
        private final ResultSet rs;
        private final AtomicBoolean closed;
        private Boolean isEmptySet;

        private CreatorIterator(Connection connection, PreparedStatement stmt, ResultSet rs)
        {
            this.connection = connection;
            this.stmt = stmt;
            this.rs = rs;
            try {
				if(!rs.isBeforeFirst()){
					isEmptySet = true;			
				}
				else isEmptySet = false;
			} catch (SQLException e) {
				isEmptySet = true;
				log.debug("Failed check for empty set. ", e);
			}
            rsi = new ResultSetIterator(rs);
            closed = new AtomicBoolean(false);
        }

        @Override
        public boolean hasNext()
        {
            if (closed.get())
                throw new IllegalStateException("This iterator has been closed");
            return !isEmptySet && rsi.hasNext(); //rsi.hasNext() returns true on empty sets.
        }

        @Override
        public InterfaceData next()
        {
            if (closed.get())
                throw new IllegalStateException("This iterator has been closed");
            Object[] columns = rsi.next();
            
            String iid = (String)columns[InterfaceJdbc.IidIdx-1];
            String xml = (String)columns[InterfaceJdbc.XmlIdx-1];
            String version = (String)columns[InterfaceJdbc.VersionIdx-1];
            String repoType =(String)columns[InterfaceJdbc.RepoIdx-1];
            Date creationDate = new Date( ((Timestamp)columns[InterfaceJdbc.CreationDateIdx-1]).getTime() );
            Date lastModified = new Date( ((Timestamp)columns[InterfaceJdbc.ModDateIdx-1]).getTime() );
            boolean published = ((Boolean)columns[InterfaceJdbc.PubIdx-1]).booleanValue();
            long pid = ((BigInteger)columns[InterfaceJdbc.IfacePkIdx-1]).longValue();
            Object[] submitterColumns = new Object[SubmitterJdbc.IsGroupIdx]; 
            System.arraycopy(columns, InterfaceJdbc.PubIdx, submitterColumns, 0, SubmitterJdbc.IsGroupIdx);
            SubmitterData submitter = new SubmitterRow(submitterColumns);
            Object[] groupColumns = new Object[SubmitterJdbc.IsGroupIdx]; 
            System.arraycopy(columns, InterfaceJdbc.PubIdx + SubmitterJdbc.IsGroupIdx, groupColumns, 0, SubmitterJdbc.IsGroupIdx);
            SubmitterData accessGroup = new SubmitterRow(groupColumns);
            //@formatter:off
            return new InterfaceRow(
                            null, iid, xml, version, submitter, accessGroup, 
                            repoType, creationDate, lastModified, published, pid);
            //@formatter:on
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
