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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.InterfaceJdbc;
import org.opendof.tools.repository.interfaces.mysql.SubmitterJdbc;

@SuppressWarnings("javadoc")
public class InterfaceRow extends InterfaceData
{
    private static final long serialVersionUID = -9112028724822198158L;
    
    public final long pid;

    public InterfaceRow(ResultSet rs, Connection connection) throws SQLException, NotFoundException
    {
        //@formatter:off
        super(
            null,
            rs.getString(InterfaceJdbc.IidIdx),
            rs.getString(InterfaceJdbc.XmlIdx),
            rs.getString(InterfaceJdbc.VersionIdx),
            SubmitterJdbc.getRow(connection, rs.getInt(InterfaceJdbc.SubmitterIdx), false),
            rs.getInt(InterfaceJdbc.GroupIdx) == 0 ? null : SubmitterJdbc.getRow(connection, rs.getInt(InterfaceJdbc.GroupIdx), false),
            rs.getString(InterfaceJdbc.RepoIdx),
            new Date(rs.getTimestamp(InterfaceJdbc.CreationDateIdx).getTime()),
            new Date(rs.getTimestamp(InterfaceJdbc.ModDateIdx).getTime()),
            rs.getBoolean(InterfaceJdbc.PubIdx)
            );
        //@formatter:on
        pid = rs.getInt(InterfaceJdbc.IfacePkIdx);
    }

    //@Formatter:off
    public InterfaceRow(
        String name, String iid, String xml, String version, 
        SubmitterData submitter, SubmitterData accessGroup, String repoType, 
        Date creationDate, Date lastModifiedDate, boolean publish, long pid)
    //@Formatter:on
    {
        super(name, iid, xml, version, submitter, accessGroup, repoType, creationDate, lastModifiedDate, publish);
        this.pid = pid;
    }
    
//    public InterfaceRow(Object[] columns)
//    {
//        //@formatter:off
//        super(
//            (String)columns[InterfaceJdbc.IidIdx-1],
//            (String)columns[InterfaceJdbc.XmlIdx-1],
//            (String)columns[InterfaceJdbc.SubmitterIdx-1],
//            (String)columns[InterfaceJdbc.RepoIdx-1],
//            new Date( ((Timestamp)columns[InterfaceJdbc.CreationDateIdx-1]).getTime() ),
//            new Date( ((Timestamp)columns[InterfaceJdbc.ModDateIdx-1]).getTime() ),
//            ((Byte)columns[InterfaceJdbc.PubIdx-1])== 0 ? false : true
//            );
//        
//        pid = (Long)columns[InterfaceJdbc.IfacePkIdx-1];
//        //@formatter:on
//    }
}
