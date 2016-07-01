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

import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.SubRepoJdbc;
import org.opendof.tools.repository.interfaces.mysql.SubmitterJdbc;


@SuppressWarnings("javadoc")
public class SubRepoRow extends SubRepositoryNode
{
    private static final long serialVersionUID = 3252844778112129523L;
    
    private int pid;
    private Integer parentPid;

    public SubRepoRow(int pid, int parentPid, String repoType, String name, String label, int depth, SubmitterData group)
    {
        super(repoType, name, label, depth, group);
        this.pid = pid;
        this.parentPid = parentPid;
    }
    
    public SubRepoRow(SubRepositoryNode node)
    {
        super(node);
        int mpid = -1;
        Integer ppid = -1;
        
        if(node instanceof SubRepoRow)
        {
            mpid = ((SubRepoRow)node).pid;
            ppid = ((SubRepoRow)node).parentPid;
        }
        pid = mpid;
        parentPid = ppid;
    }
    
    public SubRepoRow(Connection connection, ResultSet rs) throws SQLException, NotFoundException
    {
        //@formatter:off
        super(
            rs.getString(SubRepoJdbc.RepoTypeIdx),
            rs.getString(SubRepoJdbc.NameIdx),
            rs.getString(SubRepoJdbc.LabelIdx),
            rs.getInt(SubRepoJdbc.DepthIdx),
            SubmitterJdbc.getRow(connection, rs.getLong(SubRepoJdbc.GroupFkIdx), false));
        //@formatter:on
        pid = rs.getInt(SubRepoJdbc.SubRepoPkIdx);
        Integer ppid = rs.getInt(SubRepoJdbc.ParentPidIdx);
        if(rs.wasNull())
            parentPid = null;
        else
            parentPid = ppid;
    }

//    public SubRepoRow(Object[] columns)
//    {
//        //@formatter:off
//        super(
//            (String)columns[SubRepoJdbc.RepoTypeIdx-1],
//            (String)columns[SubRepoJdbc.NameIdx-1],
//            (String)columns[SubRepoJdbc.LabelIdx-1],
//            (int)columns[SubRepoJdbc.DepthIdx-1],
//            SubmitterJdbc.getRow((long)columns[SubRepoJdbc.GroupFkIdx-1]);
//        pid = (Integer)columns[SubRepoJdbc.SubRepoPkIdx-1];
//        parentPid = (Integer)columns[SubRepoJdbc.ParentPidIdx-1];
//        //@formatter:on
//    }

    public synchronized int getPid()
    {
        return pid;
    }

    public synchronized void setPid(int pid)
    {
        this.pid = pid;
    }

    public synchronized Integer getParentPid()
    {
        return parentPid;
    }

    public synchronized void setParentPid(Integer parentPid)
    {
        this.parentPid = parentPid;
    }
    
}
