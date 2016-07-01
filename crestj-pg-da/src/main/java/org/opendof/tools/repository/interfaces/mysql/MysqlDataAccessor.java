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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.opendof.core.oal.DOFInterfaceID;
import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.DataAccessor;
import org.opendof.tools.repository.interfaces.da.GroupData;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;
import org.opendof.tools.repository.interfaces.da.SubmitterData;
import org.opendof.tools.repository.interfaces.da.SubmitterException;
import org.opendof.tools.repository.interfaces.mysql.LegacyIids.LegacyData;
import org.opendof.tools.repository.interfaces.mysql.data.HolesRow;
import org.opendof.tools.repository.interfaces.mysql.data.SubRepoRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SuppressWarnings("javadoc")
public class MysqlDataAccessor implements DataAccessor
{
    public static final String UserKey = "opendof.tools.interface.repository.persist.mysql.user";
    public static final String PasswordKey = "opendof.tools.interface.repository.persist.mysql.password";
    public static final String ServerNameKey = "opendof.tools.interface.repository.persist.mysql.server-name";
    public static final String PortKey = "opendof.tools.interface.repository.persist.mysql.port";
    public static final String DbNameKey = "opendof.tools.interface.repository.persist.mysql.db-name";
    public static final String MaxConnsKey = "opendof.tools.interface.repository.persist.mysql.max-conns";
    public static final String DataSourceJndiNameKey = "opendof.tools.interface.repository.persist.datasource-jndi-name";

    public static final String SourceNameDefault = "interface-repository";
    public static final String PortDefault = "3306";
    public static final String MaxConnsDefault = "20";
    public static final String DataSourceJndiNameDefault = "jdbc/interfacerepository";
    public static final String TomcatJndiDsNamePrefix = "java:comp/env/";

    private final static Logger log = LoggerFactory.getLogger(MysqlDataAccessor.class);

    private final AtomicBoolean dsIsMine;
    private static volatile DataSource dsource;

    public MysqlDataAccessor()
    {
        dsIsMine = new AtomicBoolean(false);
    }

    public void handleLegacy(LegacyData fixData) throws AlreadyExistsException, Exception
    {
        LegacyIids.handleLegacy(this, fixData);
    }
    
    /* ****************************************************************************
     * DataAccessor implementation    
    ******************************************************************************/
    @Override
    public void init(Properties properties) throws Exception
    {
        //TODO: figure out how to set the mysql utf-8mb4 attributes set here.
        Class.forName("com.mysql.jdbc.Driver");
        String doInit = properties.getProperty(NonServletInitKey);
        StringBuilder sb = new StringBuilder("\nMySql connection information:");
        if (doInit != null)
        {
            dsIsMine.set(true);
            String user = getParameter(properties, UserKey, true, sb);
            String pass = getParameter(properties, PasswordKey, true, sb);
            String dbName = getParameter(properties, DbNameKey, true, sb);
            String srvName = getParameter(properties, ServerNameKey, true, sb);
            String port = properties.getProperty(PortKey, PortDefault).trim();
            sb.append("\n\t" + PortKey + "=" + port);

            try
            {
                HikariConfig config = new HikariConfig();
                String url = "jdbc:mysql://" + srvName + ":" + port +"/" + dbName + "?characterEncoding=UTF-8";
                config.setJdbcUrl(url);
                config.setUsername(user); 
                config.setPassword(pass);
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                config.setIdleTimeout(0);
                dsource = new HikariDataSource(config);
            } catch (Exception e)
            {
                log.error(sb.toString(), e);
            }
            log.debug(sb.toString());
            return;
        }

        InitialContext ctx = null;
        ctx = new InitialContext();
        String datasourceName = properties.getProperty(DataSourceJndiNameKey, DataSourceJndiNameDefault).trim();
        sb.append("\n\t" + DataSourceJndiNameKey + "=" + datasourceName);
        datasourceName = TomcatJndiDsNamePrefix + datasourceName;
        sb.append("\n\tfull context name: " + datasourceName);

        dsource = (DataSource) ctx.lookup(datasourceName);
        if (dsource == null)
        {
            String msg = "failed to obtain the DataSource: java:/comp/env/jdbc/interfacerepository";
            log.error(msg);
            throw new Exception(msg);
        }
        sb.append("\n\tDataSource class: " + dsource.getClass().getName()+"\n");
        log.info(sb.toString());
        // cause db connection to be established, this takes noticeable time on aws for the first connection
        dsource.getConnection().close();  
    }
    
    private String getParameter(Properties properties, String key, boolean required, StringBuilder sb) throws Exception
    {
        String value = properties.getProperty(key);
        if(value != null)
            value = value.trim();
        sb.append("\n\t" + key + "=" + value);
        if(required && value == null)
            throw new Exception(key + " not specified in properties file");
        return value;
    }

    @Override
    public void close()
    {
        if (dsIsMine.get())
        {
            if(dsource != null)
                ((HikariDataSource) dsource).close();
        }
    }

    /* ****************************************************************************
     * Submitter DataAccessor implementations    
    ******************************************************************************/

    @Override
    public void addSubmitter(SubmitterData submitter) throws AlreadyExistsException, Exception
    {
        SubmitterJdbc.addSubmitter(getConnection(), submitter, true);
    }

    @Override
    public SubmitterData getSubmitter(String submitter) throws NotFoundException, Exception
    {
        return SubmitterJdbc.getSubmitter(getConnection(), submitter, true);
    }

    @Override
    public void updateSubmitter(String submitter, SubmitterData submitterData) throws NotFoundException, Exception
    {
        SubmitterJdbc.updateSubmitter(getConnection(), submitter, submitterData);
    }

    @Override
    public void deleteSubmitter(String submitter) throws NotFoundException, SubmitterException, Exception
    {
        SubmitterJdbc.deleteSubmitter(getConnection(), submitter);
    }

    @Override
    public SubmitterIterable listSubmitters() throws Exception
    {
        return SubmitterJdbc.listSubmitters(getConnection());
    }

    /* ****************************************************************************
     * Group DataAccessor implementations    
    ******************************************************************************/

    @Override
    public void addGroup(String managerEmail, SubmitterData group) throws AlreadyExistsException, NotFoundException, Exception
    {
        GroupJdbc.addGroup(getConnection(), managerEmail, group);
    }

    @Override
    public SubmitterData getGroup(String group) throws NotFoundException, Exception
    {
        return GroupJdbc.getGroup(getConnection(), group, true);
    }

    @Override
    public void updateGroup(String group, String adminEmail, String description, Date date) throws NotFoundException, Exception
    {
        GroupJdbc.updateGroup(getConnection(), group, adminEmail, description, date);
    }

    @Override
    public void deleteGroup(String group, boolean force) throws NotFoundException, SubmitterException, Exception
    {
        GroupJdbc.deleteGroup(getConnection(), group, force);
    }

    @Override
    public void addSubmitterToGroup(String member, String group) throws NotFoundException, Exception
    {
        GroupJdbc.addSubmitterToGroup(getConnection(), member, group);
    }

    @Override
    public void removeSubmitterFromGroup(String member, String group) throws NotFoundException, Exception
    {
        GroupJdbc.removeSubmitterFromGroup(getConnection(), member, group);
    }

    @Override
    public boolean isMember(String group, String member) throws Exception
    {
        return GroupJdbc.isMember(getConnection(), member, group);
    }

    @Override
    public List<SubmitterData> listMembers(String group) throws NotFoundException, Exception
    {
        //TODO: move to SubmitterIterable return instead of List at some point
        return GroupJdbc.getMembers(getConnection(), group);
    }

    @Override
    public List<GroupData> listGroups() throws Exception
    {
        return GroupJdbc.listGroups(getConnection());
    }

    /* ****************************************************************************
     * Interface DataAccessor implementations    
    ******************************************************************************/
    @Override
    public void addInterface(InterfaceData interfaceData) throws AlreadyExistsException, Exception
    {
        Connection conn = getConnection();
        InterfaceJdbc.addInterface(conn, interfaceData, true);
    }

    @Override
    public InterfaceData getInterface(String iid, String version) throws NotFoundException, Exception
    {
        return InterfaceJdbc.getInterface(getConnection(), iid, version);
    }

    @Override
    public void updateInterface(InterfaceData interfaceData) throws NotFoundException, Exception
    {
        InterfaceJdbc.updateInterface(getConnection(), interfaceData);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteInterface(String iid, String version, SubRepositoryNode node) throws NotFoundException, Exception
    {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        long id = DOFInterfaceID.create(iid).getIdentifier();
        try
        {
            List<HolesRow> list = HolesJdbc.getRows(connection, ((SubRepoRow)node).getPid(), false);
            HolesRow previousHole = null;
            HolesRow nextHole = null;
            int i = 0;
            for(; i < list.size(); i++)
            {
                nextHole = list.get(i);
                if(nextHole.min > id )
                    break;
                previousHole = nextHole;
            }
            if(id + 1 == nextHole.min)
            {
                // bump next down one
                nextHole = new HolesRow(nextHole.pid, nextHole.subRepoFk, id, nextHole.max);
                HolesJdbc.updateRow(connection, nextHole, false);
            }else if(previousHole != null && (id - 1 == previousHole.max))
            {
                // bump previous up one
                previousHole = new HolesRow(previousHole.pid, previousHole.subRepoFk, previousHole.min, id);
                HolesJdbc.updateRow(connection, previousHole, false);
            }else
            {
                // its a new hole
                HolesRow row = new HolesRow(nextHole.subRepoFk, id, id);
                HolesJdbc.insertRow(connection, row, false);
            }
            if(previousHole != null)
            {
                if(previousHole.max + 1 == nextHole.min)
                {
                    previousHole = new HolesRow(previousHole.pid, previousHole.subRepoFk, previousHole.min, nextHole.max);
                    HolesJdbc.updateRow(connection, previousHole, false);
                    HolesJdbc.deleteRow(connection, nextHole.pid, false);
                }
            }
            InterfaceJdbc.deleteInterface(connection, iid, version, false);
            connection.commit();
        }catch(Exception e)
        {
            connection.rollback();
            throw e;
        }
        finally
        {
            close(connection, null, null, true);
        }
    }

	@Override
	public InterfaceIterable listInterfaces(String repoType, String submitter, String accessGroup, Boolean published) throws Exception 
	{
		return InterfaceJdbc.listInterfaces(getConnection(), repoType, submitter, accessGroup, published);
	}

    //    public static Date timestampToJavaDate(java.sql.Timestamp timestamp)
    //    {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    //        timestamp.getTime();
    //        
    //        return null;//"'"+sdf.format(date)+"'";
    //    }
    //    
    //    
    
    
	private static AtomicInteger totalConnections = new AtomicInteger();
    static Hashtable<Connection, AtomicInteger> connections = new Hashtable<Connection, AtomicInteger>();
    public static Connection getConnection() throws SQLException
    {
        Connection conn = dsource.getConnection();
        AtomicInteger counter = connections.get(conn);
        if(counter == null)
        {
            counter = new AtomicInteger(0);
            connections.put(conn, counter);
        }
        counter.incrementAndGet();
        totalConnections.incrementAndGet();
        return conn;
    }
    
    public static void close(Connection connection, Statement stmt, ResultSet rs, boolean closeConn) throws SQLException
    {
        try
        {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        } finally
        {
            // close the connection if either of the above two fail
            if (connection != null && closeConn)
            {
                connection.close();
                AtomicInteger counter = connections.get(connection);
                counter.decrementAndGet();
                totalConnections.decrementAndGet();
            }
        }
    }

    @Override
    public InterfaceData allocateInterfaceId(InterfaceData iface, String rdn) throws Exception
    {
        if(iface.iid != null)
            return allocateGivenInterfaceId(iface, rdn);
            
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        try
        {
            SubRepositoryNode node = getSubRepoNode(iface.getRepoType(), rdn);
            List<HolesRow> rows = HolesJdbc.getRows(connection, ((SubRepoRow)node).getPid(), false);
            HolesRow row = rows.get(0);
            if(row.min == row.max)
                HolesJdbc.deleteRow(connection, row.pid, false);  // collapse this hole
            else
                HolesJdbc.updateRow(connection, new HolesRow(row.pid, row.subRepoFk, row.min + 1, row.max), false);
            
            // TODO: hardcoded to Opendof.org for now.
            int idx = rdn.indexOf('/');
            if(idx == 0)
            {
                rdn = rdn.substring(1);
                idx = rdn.indexOf('/');
            }
            rdn = rdn.substring(0, idx);
            String iid = "["+rdn+":{"+Long.toHexString(row.min)+"}]";
            DOFInterfaceID.create(iid);
            iid = DOFInterfaceID.create(iid).toStandardString();
// check to see if the submitter is known
            try
            {
                SubmitterJdbc.addSubmitter(connection, iface.submitter, false);
            }catch(Exception e)
            {
                // ok if already exists, inserted if not
            }
// insert the interface
            iface = new InterfaceData(null, iid, null, iface.version, iface.submitter, iface.accessGroup, iface.getRepoType(), iface.creationDate, iface.lastModifiedDate, iface.publish);
            InterfaceJdbc.addInterface(connection, iface, false);
            connection.commit();
            return iface;
        }catch(Exception e)
        {
        	log.debug("Failed to insert allocated interface: " + e, e);
            connection.rollback();
        }finally
        {
            close(connection, null, null, true);
        }
        return null;
    }

    @Override
    public SubRepositoryNode getSubRepoNode(String repoType, String parentRdn) throws NotFoundException, Exception
    {
        return SubRepoJdbc.getNode(getConnection(), repoType, parentRdn);
    }

    @Override
    public void addSubRepoNode(String parentRdn, SubRepositoryNode child) throws NotFoundException, Exception
    {
        SubRepoJdbc.addNode(getConnection(), parentRdn, child);
    }
    
    @Override
    public void deleteSubRepoNode(String parentRdn, SubRepositoryNode node) throws NotFoundException, Exception
    {
        
        SubRepoJdbc.deleteNode(getConnection(), parentRdn, node);
    }
    
    @Override
    public void updateSubRepoNode(String repoType, String rdn, SubRepositoryNode node) throws NotFoundException, Exception
    {
        
        SubRepoJdbc.updateNode(getConnection(), rdn, node);
    }
    

    public InterfaceData allocateGivenInterfaceId(InterfaceData iface, String rdn) throws Exception
    {
        SubRepoRow subRepo = SubRepoJdbc.getNode(getConnection(), "opendof", rdn);
        int subRepoPid = subRepo.getPid();
        DOFInterfaceID iid = DOFInterfaceID.create(iface.iid); 
        String standard = iid.toStandardString();
        boolean exists = false;
        try
        {
            getInterface(standard, "1");
//            iface = new InterfaceData(
//                            iface.getName(), iface.iid, iface.xml, iface.version, DataAccessor.OpendofAdmin, 
//                            DataAccessor.AnonymousGroup, iface.getRepoType(), iface.creationDate, 
//                            iface.lastModifiedDate, iface.publish);
//            updateInterface(iface);
            // if exists only fix up holes
            log.info(iface.iid + " is already in the IR, fixing up the allocation tables");
            exists = true;
        }catch(Exception e)
        {
            log.info(standard + " is not in the IR, Adjusting allocation tables and adding to interface table");
            // if exists only add interface as pre-allocated
            exists = false;
        }

        Connection connection = getConnection();
        connection.setAutoCommit(false);
        try
        {
            int idx = iface.iid.indexOf('{');
            String iids = iface.iid.substring(++idx);
            iids = iids.substring(0, iids.length()-2);
            int numberOfBytes = 0;
            switch(iids.length())
            {
                case 2:
                    numberOfBytes = 1;
                    break;
                case 4:
                    numberOfBytes = 2;
                    break;
                case 8:
                    numberOfBytes = 4;
                    break;
                default:
                    throw new Exception("unknown iid number of bytes: " + iid.toStandardString());
            }
            
            List<HolesRow> holes = HolesJdbc.getRows(connection, subRepoPid, false);
            long min = 0;
            long max = 0;
            HolesRow currentHole = null;
            if(holes.size() == 0)
            {
                switch(numberOfBytes)
                {
                    case 1:
                        min = 0;
                        max = 255;
                        break;
                    case 2:
                        min = 256;
                        max = 65535;
                        break;
                    case 4:
                        min = 65536;
                        max = Long.MAX_VALUE;
                        break;
                    default:
                        throw new Exception("invalid size: " + iid.getByteLength());
                }
                currentHole = new HolesRow(subRepoPid, min, max);
                int pid = HolesJdbc.insertRow(connection, currentHole, false);
                currentHole.pid = pid;
                holes.add(currentHole);
            }
            long identifier = iid.getIdentifier();
            for(HolesRow row : holes)
            {
                if(row.min <= identifier && row.max >= identifier)
                {
                    currentHole = row;
                    break;
                }
            }
            if(currentHole == null)
                throw new Exception("Could not find a hole, does the given interface ID already exist? iid: " + iface.iid);
            HolesRow row = null;
            if(currentHole.max - currentHole.min == 0)
                // just filled the last slot in this hole, remove it
                HolesJdbc.deleteRow(connection, currentHole.pid, false);
            else if(currentHole.max == identifier)
            {
                if(currentHole.max - currentHole.min == 0)
                    // just filled the last slot in this hole, remove it
                    HolesJdbc.deleteRow(connection, currentHole.pid, false);
                else
                {
                    row = new HolesRow(currentHole.subRepoFk, currentHole.min, identifier - 1);
                    row.pid = currentHole.pid;
                    HolesJdbc.updateRow(connection, row, false);
                }
            }else if(currentHole.min == identifier)
            {
                row = new HolesRow(currentHole.subRepoFk, currentHole.min + 1, currentHole.max);
                row.pid = currentHole.pid;
                HolesJdbc.updateRow(connection, row, false);
            }else
            {
                // need to create a new hole
                row = new HolesRow(currentHole.subRepoFk, currentHole.min, identifier - 1);
                row.pid = currentHole.pid;
                HolesJdbc.updateRow(connection, row, false);
                row = new HolesRow(currentHole.subRepoFk, identifier + 1, currentHole.max);
                HolesJdbc.insertRow(connection, row, false);
            }
            connection.commit();
            if(exists)
                return iface;
            Date date = new Date();
            iface = new InterfaceData(null, standard, iface.xml, iface.version, iface.submitter, iface.accessGroup, iface.getRepoType(), date, date, iface.publish);
            addInterface(iface);
            return iface;
        }catch(Exception e)
        {
            connection.rollback();
            throw e;
        }finally
        {
            close(connection, null, null, true);
        }
    }
}
