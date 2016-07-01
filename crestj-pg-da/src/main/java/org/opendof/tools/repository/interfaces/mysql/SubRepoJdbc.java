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
package org.opendof.tools.repository.interfaces.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.DataAccessor;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubRepoRelationship;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode.TabToLevel;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.mysql.data.SubRepoParentRow;
import org.opendof.tools.repository.interfaces.mysql.data.SubRepoRow;
import org.opendof.tools.repository.interfaces.mysql.data.SubmitterRow;

import com.mysql.jdbc.Statement;

@SuppressWarnings("javadoc")
public class SubRepoJdbc
{
    public static final String RootLabel = "Root";
    public static final String TableName;

    public static final String ColSubRepoPkName = "subrepoPk";
    public static final String ColRepoTypeName = "repotype";
    public static final String ColNameName = "name";
    public static final String ColLabelName = "label";
    public static final String ColDepthName = "depth";
    public static final String ColParentPidName = "parentPid";
    public static final String ColGroupFkName = "groupFk";

    public static final int SubRepoPkIdx = 1;
    public static final int RepoTypeIdx = 2;
    public static final int NameIdx = 3;
    public static final int LabelIdx = 4;
    public static final int DepthIdx = 5;
    public static final int ParentPidIdx = 6;
    public static final int GroupFkIdx = 7;

    private static final String PrepInsertRow;
    private static final String PrepGetRoot;
    private static final String PrepFindNode;
    private static final String PrepDeleteRow;
    
    private static final String PrepGetPid;
    private static final String PrepGetRow;
    private static final String PrepUpdateRow;
    private static final String PrepUpdateRowWithGroup;

    static
    {
        TableName = "subrepo";
        PrepGetRow = "select * from " + TableName + " where " + ColSubRepoPkName + "=?;";
        PrepGetRoot = "select * from " + TableName + " where " + ColParentPidName + "=-1;"; 

        //@formatter:off
        PrepGetPid = "select * from " + TableName + " where " + 
            ColRepoTypeName + "=? and " + 
            ColParentPidName + "=? and " + 
            ColDepthName + "=? and " + 
            ColNameName + "=? and " +
            ColLabelName + "=?;";
        
        PrepDeleteRow = "delete from " + TableName + " where " + 
            ColRepoTypeName + "=? and " + 
            ColParentPidName + "=? and " + 
            ColDepthName + "=? and " + 
            ColNameName + "=? and " +
            ColLabelName + "=?;";
            
        PrepFindNode = "select * from " + TableName + " where " + 
            ColRepoTypeName + "=? and " + 
            ColParentPidName + "=? and " + 
            ColNameName + "=? and " + 
            ColDepthName + "=?;";
        
        PrepInsertRow = 
            "insert into " + TableName +
            " (" + ColRepoTypeName + "," +
            ColNameName + "," +
            ColLabelName + "," +
            ColDepthName + "," +
            ColParentPidName + "," +
            ColGroupFkName + ")" +
            " values(?, ?, ?, ?, ?, ?);";
        
        PrepUpdateRow = 
            "update " + TableName + " set " + 
            ColRepoTypeName + "=?," + 
            ColNameName + "=?," +
            ColLabelName + "=?," + 
            ColDepthName + "=?," + 
            ColParentPidName + "=?" + 
            " where " + ColSubRepoPkName + "=?;";
        
        PrepUpdateRowWithGroup = 
            "update " + TableName + " set " + 
            ColRepoTypeName + "=?," + 
            ColNameName + "=?," +
            ColLabelName + "=?," + 
            ColDepthName + "=?," + 
            ColParentPidName + "=?," + 
            ColGroupFkName + "=?" + 
            " where " + ColSubRepoPkName + "=?;";
        //@Formatter:on
    }

    public static SubRepoRow getRoot(Connection connection) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRoot);
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("root not found");
            return new SubRepoRow(connection, rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, false);
        }
    }
    
    public static SubRepoRow getRow(Connection connection, int pid, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepGetRow);
        ResultSet rs = null;
        try
        {
            stmt.setInt(1, pid);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("pid: " + pid + " not found");
            return new SubRepoRow(connection, rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    private static void traverse(Connection connection, SubRepoRow parentRow, SubRepositoryNode parentNode) throws NotFoundException, SQLException
    {
        List<SubRepoParentRow> prows = SubRepoParentJdbc.getChildren(connection, parentRow.getPid(), false);
        for(SubRepoParentRow row : prows)
        {
            if(row.depth == 0)
                continue;   // skip the root itself
            SubRepoRow childRow = getRow(connection, row.childPid, false);
            //@formatter:off
            SubRepositoryNode child = new SubRepoRow(
                            parentRow.getPid(),
                            childRow.getPid(),
                            parentNode.getRepoType(), 
                            childRow.getName(), 
                            childRow.getLabel(), 
                            childRow.getDepth(), 
                            childRow.getGroup());
            //@formatter:on
            parentNode.add(child);
            traverse(connection, childRow, child); 
        }
    }
    
    public static SubRepoRow getNode(Connection connection, String repoType, String parentRdn) throws NotFoundException, SQLException
    {
        SubRepoRow root = null;
        try
        {
            connection.setAutoCommit(false);
            SubRepoRow rootRow = getRoot(connection);
            ParentChild pc = getFirstParentChild(parentRdn, 1);
            pc.parentPid = rootRow.getPid();
            ParentChild parentChild = pc;
            if(!pc.isRoot())
                parentChild = traverse(connection, repoType, pc);
            else
                parentChild.row = rootRow;
            //@formatter:off
            root = new SubRepoRow( 
                            parentChild.row.getPid(), parentChild.parentPid, 
                            repoType, 
                            parentChild.row.getName(), 
                            parentChild.row.getLabel(), 
                            parentChild.row.getDepth(), 
                            parentChild.row.getGroup());
            //@formatter:on
//            root = new SubRepositoryNode(repoType, parentChild.row.getName(), parentChild.row.getLabel(), parentChild.row.getDepth(), parentChild.row.getGroup());
            traverse(connection, parentChild.row, root);
            connection.commit();
            return root;
        }catch (Exception e)
        {
            connection.rollback();
            throw e;
        }
        finally
        {
            MysqlDataAccessor.close(connection, null, null, true);
        }
    }
    
    public static SubRepoRow find(Connection connection, String repoType, Integer parentPid, String name, int depth, boolean close) throws NotFoundException, SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepFindNode);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, repoType);
//            stmt.setObject(2, parentPid);
            if(parentPid == null)
            {
//                stmt.setNull(2, java.sql.Types.INTEGER);
                stmt.setInt(2, -1);
            }
            else
                stmt.setInt(2, parentPid);
            stmt.setString(3, name);
            stmt.setInt(4, depth);
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("repoType: " + repoType + " parentPid: " + (parentPid == null ? "null" : parentPid) + " name: " + name + " depth: " + depth + " not found");
            return new SubRepoRow(connection, rs);
        }finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    
    private static ParentChild traverse(Connection connection, String repoType, ParentChild parentChild) throws NotFoundException, SQLException
    {
        if(parentChild.parent == null || parentChild.parent.length() == 0)
            return parentChild;
        SubRepoRow row = find(connection, repoType, parentChild.parentPid, parentChild.parent, parentChild.parentDepth, false);
        parentChild = getFirstParentChild(parentChild.remainder, parentChild.parentDepth + 1);
        parentChild.parentPid = row.getPid();
        parentChild.row = row;
//        if(parentChild.remainder == null)
//        {
//            parentChild.row = row;
//            return parentChild;
//        }
        return traverse(connection, repoType, parentChild);
    }
    
    private static ParentChild getFirstParentChild(String rdn, int depth)
    {
//        null
//        /
//        ""        
//        r1
//        /r1
//        /r1/
//        r1/4b
//        r1/4b/
        String separator = "/"; 
        String parent = null;
        String child = null;
        if(rdn == null)
            return new ParentChild(rdn, depth, null, null);
        if(rdn.equals(separator))
            return new ParentChild(rdn, depth, null, null);
        if(rdn.length() == 0)
            return new ParentChild(rdn, depth, null, null);
        int idx = rdn.indexOf(separator);
        if(idx == -1)
            return new ParentChild(rdn, depth, null, null);
        if(idx == 0)
        {
            rdn = rdn.substring(1);
            idx = rdn.indexOf(separator);
            if(idx == -1)
                return new ParentChild(rdn, depth, null, null);
        }
        parent = rdn.substring(0, idx);
        child = rdn.substring(idx);
        if(child.length() == 1)
            return new ParentChild(parent, depth, null, null);
        child = child.substring(1);
        int eidx = child.indexOf('/');
        if(eidx == -1)
            return new ParentChild(parent, depth, child, child);
        child = child.substring(0, eidx);
        String remainder = child.substring(eidx);
        if(remainder.length() == 1)
            return new ParentChild(parent, depth, child, null);
        remainder = remainder.substring(1);
        return new ParentChild(parent, depth, child, remainder);
    }
    
    public static void addNode(Connection connection, String parentRdn, SubRepositoryNode child) throws AlreadyExistsException, NotFoundException, SQLException
    {
        SubRepoRow node = new SubRepoRow(child);
        try
        {
            connection.setAutoCommit(false);
//            ParentChild parentChild = null;
//            Integer parentPid = null;
            if(parentRdn != null)
            {
                SubRepoRow root = getRoot(connection);
                ParentChild pc = getFirstParentChild(parentRdn, 1);
                pc.parentPid = root.getPid();
                ParentChild parentChild = traverse(connection, node.getRepoType(), pc);
//                parentPid = parentChild.row.parentPid;
                node.setDepth(parentChild.parentDepth);
                node.setParentPid(parentChild.row.getPid());
            }else
            {
                // see if the root (repoType) has been created yet
                try
                {
                    SubRepoRow root = find(connection, node.getRepoType(), -1, node.getRepoType(), 0, false);
                    // yes, grab parentPid and adjust node depth
                    node.setDepth(1);
                    node.setParentPid(root.getPid()); //TODO: same null search problem as below moved to -1 to deal with it for now
                } catch (NotFoundException nfe)
                {
                    // nope, create it
                    SubRepoRow root = new SubRepoRow(node);
                    root.setName(node.getRepoType());
                    root.setLabel(RootLabel);
                    root.setGroup(DataAccessor.AnonymousGroup);
                    root.setParentPid(-1); // TODO: can not get the null search to work for anything
                    root.setDepth(0);
                    int rootPid = insertRow(connection, root, false);
                    node.setDepth(1);
                    node.setParentPid(rootPid);
                    SubRepoRelationship relationship = new SubRepoRelationship(rootPid, rootPid, 0);
                    SubRepoParentJdbc.insertRow(connection, relationship, false);
                    root.setPid(rootPid);
                    root.setParentPid(rootPid);
//                    updateRow(connection, root, false);
                }
            }
            // see if the new node already exists
            try
            {
                find(connection, node.getRepoType(), node.getParentPid(), node.getName(), node.getDepth(), false);
                throw new AlreadyExistsException("repoType: " + node.getRepoType() + " parent: " + node.getPid() + " name: " + node.getName() + " depth: 1 already exists");
            } catch (NotFoundException nfe)
            {
                int childPid = insertRow(connection, node, false);
                SubRepoRelationship relationship = new SubRepoRelationship(node.getParentPid(), childPid, node.getDepth());
                SubRepoParentJdbc.insertRow(connection, relationship, false);
            }
            connection.commit();
        }catch (Exception e)
        {
            connection.rollback();
            throw e;
        }
        finally
        {
            MysqlDataAccessor.close(connection, null, null, true);
         }
    }

    public static int insertRow(Connection connection, SubRepoRow node, boolean close) throws SQLException, NotFoundException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepInsertRow, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = null;
        try
        {
            if(node.getGroup() != null)
            {
                SubmitterRow submitterRow = GroupJdbc.getGroup(connection, node.getGroup().email, false);
                stmt.setLong(GroupFkIdx-1, submitterRow.pid);
            }else
                stmt.setNull(GroupFkIdx-1, java.sql.Types.BIGINT);
                
            stmt.setString(RepoTypeIdx-1, node.getRepoType());
            stmt.setString(NameIdx-1, node.getName());
            stmt.setString(LabelIdx-1, node.getLabel());
            stmt.setInt(DepthIdx-1, node.getDepth());
            if(node.getParentPid() == null)
                stmt.setNull(ParentPidIdx-1, java.sql.Types.INTEGER);
            else
                stmt.setInt(ParentPidIdx-1, node.getParentPid());
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("insertRow affected an unexpected number of rows: " + rows);
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
        finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }
    
    public static void updateRow(Connection connection, SubRepoRow row,  boolean close) throws SQLException, NotFoundException
    {
        PreparedStatement stmt = connection.prepareStatement(PrepUpdateRow);
        if(row.getGroup() != null)
            stmt = connection.prepareStatement(PrepUpdateRowWithGroup);
        try
        {
            stmt.setString(1, row.getRepoType());
            stmt.setString(2, row.getName());
            stmt.setString(3, row.getLabel());
            stmt.setInt(4, row.getDepth());
            stmt.setInt(5, row.getParentPid());
            if(row.getGroup() != null)
            {
                Long groupPid = SubmitterJdbc.getSubmitter(connection, row.getGroup().name, false).pid;
                stmt.setLong(6, groupPid); 
                stmt.setInt(7, row.getPid());
            }else
                stmt.setInt(6, row.getPid());
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("updateRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

    
    public static void deleteNode(Connection connection, String parentRdn, SubRepositoryNode node) throws Exception
    {
        SubRepoRow row = new SubRepoRow(node);
        try
        {
            connection.setAutoCommit(false);
            SubRepoRow root = null;
            if(parentRdn != null)
            {
                root = getRoot(connection);
                ParentChild pc = getFirstParentChild(parentRdn, 1);
                pc.parentPid = root.getPid();
                row.setDepth(pc.parentDepth + 1);
                ParentChild parentChild = traverse(connection, node.getRepoType(), pc);
                row.setParentPid(parentChild.row.getPid());
            }else
            {
                root = find(connection, node.getRepoType(), -1, node.getRepoType(), 0, false);
                row.setParentPid(root.getPid()); //TODO: same null search problem as below moved to -1 to deal with it for now
                row.setDepth(1);
                // see if the root (repoType) has been created yet
            }
            Integer childPid = getPid(connection, row.getParentPid(), row, false);
            SubRepoParentJdbc.deleteRow(connection, row.getParentPid(), childPid, row.getDepth(), false);
            deleteRow(connection, row.getParentPid(), row, false);
            connection.commit();
            MysqlDataAccessor.close(connection, null, null, true);
        }catch (Exception e)
        {
            connection.rollback();
            throw e;
        }
        finally
        {
            MysqlDataAccessor.close(connection, null, null, true);
         }
    }
        
     public static void deleteRow(Connection connection, long parentPid, SubRepoRow row, boolean close) throws SQLException
     {
        PreparedStatement stmt = connection.prepareStatement(PrepDeleteRow);
        try
        {
            stmt.setString(1, row.getRepoType());
            stmt.setLong(2, parentPid);
            stmt.setInt(3, row.getDepth());
            stmt.setString(4, row.getName());
            stmt.setString(5, row.getLabel());
            int rows = stmt.executeUpdate();
            if (rows != 1)
                throw new SQLException("deleteRow affected an unexpected number of rows: " + rows);
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, null, close);
        }
    }

     public static void updateNode(Connection connection, String rdn, SubRepositoryNode node) throws Exception
     {
         SubRepoRow row = new SubRepoRow(node);
         try
         {
             connection.setAutoCommit(false);
             SubRepoRow root = null;
             if(rdn == null)
                 throw new Exception("update of the root node is not allowed");
             root = getRoot(connection);
             ParentChild pc = getFirstParentChild(rdn, 1);
             pc.parentPid = root.getPid();
             row.setDepth(pc.parentDepth + 1);
             ParentChild parentChild = traverse(connection, row.getRepoType(), pc);
             row.setParentPid(parentChild.row.getPid());
             
             SubRepoRow currentRow = getRow(connection, parentChild.row.getPid(), false);
             
             SubmitterData newGroup = currentRow.getGroup();
             if(node.getGroup() != null)
                 newGroup = SubmitterJdbc.getSubmitter(connection, node.getGroup().name, false);
             String nlabel = currentRow.getLabel();
             if(node.getLabel() != null)
                 nlabel = node.getLabel();
             SubRepoRow newRow = new SubRepoRow(currentRow.getPid(), currentRow.getParentPid(), node.getRepoType(), node.getName(), nlabel, currentRow.getDepth(), newGroup);
             updateRow(connection, newRow, false);
             connection.commit();
             MysqlDataAccessor.close(connection, null, null, true);
         }catch (Exception e)
         {
             connection.rollback();
             throw e;
         }
         finally
         {
             MysqlDataAccessor.close(connection, null, null, true);
          }
     }
     
     public static Integer getPid(Connection connection, Integer parentPid, SubRepoRow row, boolean close) throws SQLException, NotFoundException
     {
        PreparedStatement stmt = connection.prepareStatement(PrepGetPid);
        ResultSet rs = null;
        try
        {
            stmt.setString(1, row.getRepoType());
            stmt.setInt(2, parentPid);
            stmt.setInt(3, row.getDepth());
            stmt.setString(4, row.getName());
            stmt.setString(5, row.getLabel());
            rs = stmt.executeQuery();
            if (!rs.next())
                throw new NotFoundException("repoType: " + row.getRepoType() + " parentPid: " + (parentPid == null ? "null" : parentPid) + " name: " + row.getName() + " depth: " + row.getDepth() + " not found");
            return new SubRepoRow(connection, rs).getPid();
        } finally
        {
            MysqlDataAccessor.close(connection, stmt, rs, close);
        }
    }

    
    private static class ParentChild
    {
        public final String remainder;
        public final String child;
        public final String parent;
        public volatile int parentDepth;
        public volatile int childPid;
        public volatile Integer parentPid;
        public volatile SubRepoRow row;
        
        public ParentChild(String parent, int parentDepth, String child, String remainder)
        {
            this.parent = parent;
            this.parentDepth = parentDepth;
            this.child = child;
            this.remainder = remainder;
        }
        
        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            format.ttl("\nParentChild:");
            format.level.incrementAndGet();
            format.ttl("parent: ", parent);
            format.ttl("parentDepth: ", parentDepth);
            format.ttl("child: ", child);
            format.ttl("remainder: ", remainder);
            format.ttl("childPid: ", childPid);
            format.ttl("parentPid: ", parentPid);
            return format.toString();
        }
        
        public boolean isRoot()
        {
            if(parent == null)
                return true;
            if(parent.equals("/"))
                return true;
            if(parent.length() == 0)
                return true;
            return false;
        }
    }
}
