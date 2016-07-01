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
package org.opendof.tools.repository.interfaces.da;

import java.util.Date;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("javadoc")
public interface DataAccessor
{
    public static final String NonServletInitKey = "opendof.tools.repository.persist.cli-init";
    
    public static final String AdminName = "OpenDOF Admin";
    public static final String AdminEmail = "admin@opendof.org";
    public static final String AdminDescription = "OpenDOF Manager, general inquiries only";
    
    public static final String AnonymousGroupName = "anonymous";
    public static final String AnonymousGroupEmail = AnonymousGroupName;
    public static final String AnonymousGroupDescription = "Anonymous users group";
    
    public static final String UserGroupName = "user";
    public static final String UserGroupEmail = UserGroupName;
    public static final String UserGroupDescription = "Authenticated users group";

    public static final String PrivateGroupName = "private";
    public static final String PrivateGroupEmail = PrivateGroupName;
    public static final String PrivateGroupDescription = "Private interface";
    
    public static final String OpenDofAllocatorGroupName = "opendof-allocator";
    public static final String OpenDofAllocatorGroupEmail = OpenDofAllocatorGroupName;
    public static final String OpenDofAllocatorGroupDescription = "DOF IID 1 and 2 byte size allocation group";
    
    public static final String CliAdminName = "cli-admin";
    public static final String CliAdminEmail = CliAdminName;
    public static final String CliAdminDescription = "Root user CLI Privileges";
    
    //@formatter:off
    public static final SubmitterData AnonymousGroup = new SubmitterData(
                    AnonymousGroupName, AnonymousGroupEmail, 
                    AnonymousGroupDescription);
    
    public static final SubmitterData UserGroup = new SubmitterData(
                    UserGroupName, UserGroupEmail, 
                    UserGroupDescription);
    
    public static final SubmitterData PrivateGroup = new SubmitterData(
                    PrivateGroupName, PrivateGroupEmail, 
                    PrivateGroupDescription);
    
    public static final SubmitterData OpenDofAllocationGroup = new SubmitterData(
                    OpenDofAllocatorGroupName, OpenDofAllocatorGroupEmail, 
                    OpenDofAllocatorGroupDescription);
    
    public static final SubmitterData OpendofAdmin = new SubmitterData(
                    AdminName, AdminEmail, 
                    AdminDescription);
    //@formatter:on
    
	public void init(Properties properties) throws Exception;
	public void close();

	// submitter
	
	/**
	 * @param submitterData the submitter information.
	 * @throws AlreadyExistsException if the given submitter already exists
	 * @throws Exception if the submitter could not be added
	 */
    public void addSubmitter(SubmitterData submitterData) throws AlreadyExistsException, Exception;
    
    /**
     * @param submitterEmail the requested submitter.
     * @return the requested submitter data.
     * @throws NotFoundException if the given submitter already exists
     * @throws Exception if the submitter could not be added
     */
	public SubmitterData getSubmitter(String submitterEmail) throws NotFoundException, Exception;
	
    /**
     * @param submitterEmail the requested submitter.
     * @param submitterData the submitter information.
     * @throws NotFoundException if the given submitter already exists
     * @throws Exception if the submitter could not be updated
     */
	public void updateSubmitter(String submitterEmail, SubmitterData submitterData) throws NotFoundException, Exception;
	
    /**
     * @param submitterEmail the requested submitter.
     * @throws Exception if the submitter could not be deleted
     */
    public void deleteSubmitter(String submitterEmail) throws Exception;
    
    /**
     * Warning, potential persistence memory leak, caller must call close on the 
     * iterable or iterator obtained from returned Iterable.
     * @return Iterable from which an iterator of submitters can be obtained.
     * @throws Exception if the list can not be obtained
     */
    public SubmitterIterable listSubmitters() throws Exception;

	// group
    public void addGroup(String managerEmail, SubmitterData group) throws AlreadyExistsException, NotFoundException, Exception;
    public SubmitterData getGroup(String group) throws NotFoundException, Exception;
    public void updateGroup(String group, String adminEmail, String description, Date date) throws NotFoundException, Exception;
    public void deleteGroup(String group, boolean force) throws NotFoundException, Exception;
	public void addSubmitterToGroup(String member, String group) throws NotFoundException, Exception;
    public void removeSubmitterFromGroup(String member, String group) throws Exception;
    public boolean isMember(String member, String group) throws Exception;
    
    /**
     * Warning, potential persistence memory leak, caller must call close on the 
     * iterable or iterator obtained from returned Iterable.
     * @return Iterable from which an iterator of submitters can be obtained.
     * @throws Exception if the list can not be obtained
     */
    public List<SubmitterData> listMembers(String group) throws NotFoundException, Exception;
    /**
     * @return List of all submitters in the group.
     * @throws Exception if the list can not be obtained
     */
    public List<GroupData> listGroups() throws Exception;

	// interface
    public void addInterface(InterfaceData interfaceData) throws AlreadyExistsException, Exception;
	public InterfaceData getInterface(String iid, String version) throws NotFoundException, Exception;
    public void updateInterface(InterfaceData interfaceData) throws NotFoundException, Exception;
	public void deleteInterface(String iid, String version, SubRepositoryNode node) throws Exception;
	
    /**
     * Warning, potential persistence memory leak, caller must call close on the 
     * iterable or iterator obtained from returned Iterable.
     * @param authUser the authenticated user.  Must not be null.
     * @param authUserGroup the authenticated user's group.  Maybe null.  If null use user as group.
     * @param repoType the repository type to return interfaces for.  Must not be null.
     * @param submitterName the stored submitterName to return interfaces for. If null, do not filter on submitter.
     * @param published the published state to return interfaces for.  If null, do not filter on published 
     * @return Iterable from which an iterator of interfaces can be obtained.
     * @throws Exception if the list can not be obtained
     */
    public InterfaceIterable listInterfaces(String repoType, String submitterName, String accessGroup, Boolean published) throws Exception;

    // reservation
    
    /**
     *  Allocate an IID from the named sub-repository node's pool.
     * @param iface Required interface information for insertion to the interface table. Must not be null
     * @param rdn the Relative Distinguished Name of the allocation pool node.  Must not be null
     * @param node the SubRepository allocation pool to be used.  Must not be null
     * @return the interfaceData added to the dn.
     * @throws NotFoundException if the given rdn was not found.
     * @throws Exception if the node can not be obtained.
     */
    public InterfaceData allocateInterfaceId(InterfaceData iface, String rdn) throws NotFoundException, Exception;
    
    
    /**
     * Return the requested node.
     * @param repoType the repository type. Must not be null.
     * @param rdn the '/' separated relative distinguished name from the repoType root.  Must not be null.
     * @return the node requested in the rdn.
     * @throws NotFoundException if the given rdn was not found.
     * @throws Exception if the node could not be retrieved
     */
    public SubRepositoryNode getSubRepoNode(String repoType, String rdn) throws NotFoundException, Exception;
    
    /**
     * Add a SubRepo node.
     * @param repoType the repository type. Must not be null.
     * @param parentRdn the parent to add the node to. May be null.
     * @param child the child to be added to given parent. Must not be null.
     * @throws AlreadyExistsException if the node already exists.
     * @throws NotFoundException if the parentRdn can not be found.
     * @throws Exception if the node could not be added.
     */
    public void addSubRepoNode(String parentRdn, SubRepositoryNode child) throws AlreadyExistsException, NotFoundException, Exception;
    public void deleteSubRepoNode(String parentRdn, SubRepositoryNode node) throws NotFoundException, Exception;
    public void updateSubRepoNode(String rdn, String repoType, SubRepositoryNode node) throws NotFoundException, Exception;
        
    /* ************************************************************************
     * List Iterable interface declarations and iid size enum 
    **************************************************************************/
    public interface SubmitterIterable extends Iterable<SubmitterData>
    {
        public void close() throws Exception;
    }
    
    public interface GroupIterable extends Iterable<SubmitterData>
    {
        public void close() throws Exception;
    }
    
    public interface InterfaceIterable extends Iterable<InterfaceData>
    {
        public void close() throws Exception;
    }
    
    public enum SizeCategory
    {
        OneByte, TwoByte, FourByte;
        
        public static SizeCategory getSize(int ordinal)
        {
            switch(ordinal)
            {
                case 0:
                    return OneByte;
                case 1 :
                    return TwoByte;
                case 2:
                    return FourByte;
                default:
                    throw new IllegalArgumentException("ordinal: " + ordinal + " is not a valid size category value");
            }
        }
    }
}
