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

import java.util.Date;

import org.opendof.tools.repository.interfaces.da.AlreadyExistsException;
import org.opendof.tools.repository.interfaces.da.DataAccessor;
import org.opendof.tools.repository.interfaces.da.InterfaceData;
import org.opendof.tools.repository.interfaces.da.NotFoundException;
import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;
import org.opendof.tools.repository.interfaces.da.SubmitterData;

@SuppressWarnings("javadoc")
public class LegacyIids
{
    //@formatter:off
    public static final int[] legacy1Byte = new int[]
    {
        1, 1, //"OpenDOF Admin", "admin@opendof.org", "no", "initial Gavin note",   // [1:{01}] p
        8, 1,  // [1:{08}] p
        9, 1,  // [1:{09}] p
        10, 1, // [1:{0A}] p
        11, 1, // [1:{0B}] p
    };

    public static final int[] legacy2Byte = new int[]
    {
        512, 0,    // [1:{0200}]
        513, 0,    // [1:{0201}]
        514, 0,    // [1:{0202}]
        515, 0,    // [1:{0203}]
        516, 0,    // [1:{0204}]
        517, 0,    // [1:{0205}]
        518, 0,    // [1:{0206}]
        519, 0,    // [1:{0207}]
        520, 0,    // [1:{0208}]
        521, 0,    // [1:{0209}]
        522, 0,    // [1:{020A}]
        523, 0,    // [1:{020B}]
        524, 0,    // [1:{020C}]
        526, 0,    // [1:{020E}]
        527, 0,    // [1:{020F}]
        528, 0,    // [1:{0210}]
        529, 1,    // [1:{0211}] p
        530, 1,    // [1:{0212}] p
        531, 0,    // [1:{0213}]
        543, 0,    // [1:{021F}]
        544, 0,    // [1:{0220}]
        546, 0,    // [1:{0222}]
        549, 1,    // [1:{0225}] p
        550, 1,    // [1:{0226}] p
        551, 1,    // [1:{0227}] p
        552, 1,    // [1:{0228}] p
        562, 0,    // [1:{0232}]
        563, 0,    // [1:{0233}]
        565, 0,    // [1:{0235}]
        566, 0,    // [1:{0236}]
        567, 0,    // [1:{0237}]
        568, 0,   // [1:{0238}]
    };

    public static final int[] legacy4Byte = new int[]
    {
        16777216, 1,   // [1:{01000000}] p
        16777217, 1,   // [1:{01000001}] p
        16777218, 1,   // [1:{01000002}] p
        16777226, 1,   // [1:{0100000A}] p
        16777250, 1,   // [1:{01000022}] p
        16777255, 1,   // [1:{01000027}] p
        16777256, 1,   // [1:{01000028}] p
        16777268, 0,   // [1:{01000034}]
        16777275, 0,   // [1:{0100003B}]
        16777277, 0,   // [1:{0100003D}]
        16777278, 0,   // [1:{0100003E}]
        16777279, 0,   // [1:{0100003F}]
        16777280, 0,   // [1:{01000040}]
        16777281, 0,   // [1:{01000041}]
        16777282, 0,   // [1:{01000042}]
        16777283, 0,   // [1:{01000043}]
        16777284, 0,   // [1:{01000044}]
        16777285, 0,   // [1:{01000045}]
        16777286, 0,   // [1:{01000046}]
        16777287, 0,   // [1:{01000047}]
        16777293, 1,   // [1:{0100004D}] p
        16777295, 0,   // [1:{0100004F}]
        16777296, 0,   // [1:{01000050}]
        16777303, 0,   // [1:{01000057}]
        16777304, 0,   // [1:{01000058}]
    };
    
    private static void addGroups(DataAccessor da) throws AlreadyExistsException, Exception
    {
        SubmitterData manager = new SubmitterData(DataAccessor.AdminName, DataAccessor.AdminEmail, DataAccessor.AdminDescription, new Date());
        da.addSubmitter(manager);
        
        SubmitterData cliAdmin = new SubmitterData(DataAccessor.CliAdminName, DataAccessor.CliAdminEmail, DataAccessor.CliAdminDescription, new Date());
        da.addSubmitter(cliAdmin);
        
        SubmitterData anonymous = new SubmitterData(DataAccessor.AnonymousGroupName, DataAccessor.AnonymousGroupEmail, DataAccessor.AnonymousGroupDescription, new Date(), true);
        da.addGroup(manager.email, anonymous);
        
        SubmitterData user = new SubmitterData(DataAccessor.UserGroupName, DataAccessor.UserGroupEmail, DataAccessor.UserGroupDescription, new Date(), true);
        da.addGroup(manager.email, user);
        
        SubmitterData priv = new SubmitterData(DataAccessor.PrivateGroupName, DataAccessor.PrivateGroupEmail, DataAccessor. PrivateGroupDescription, new Date(), true);
        da.addGroup(manager.email, priv);
        
        SubmitterData opendof = new SubmitterData(DataAccessor.OpenDofAllocatorGroupName, DataAccessor.OpenDofAllocatorGroupEmail, DataAccessor.OpenDofAllocatorGroupDescription, new Date(), true);
        da.addGroup(manager.email, opendof);
    }
    
    private static void subRepo(DataAccessor da) throws AlreadyExistsException, NotFoundException, Exception
    {
        //TODO: 
        // 1. modify the final saved sql and add drop table reserved and registry
        // 2. drop index from submitter.name column ... email is the unique key
        // 3. allow null xml in the interface table.
        // 4. add groupFk to interface table
        SubRepositoryNode child = new SubRepositoryNode("opendof", "1", "Registry", 1, DataAccessor.UserGroup);
        da.addSubRepoNode(null, child);
        child = new SubRepositoryNode("opendof", "2", "Registry", 1, DataAccessor.UserGroup);
        da.addSubRepoNode(null, child);
        
        child = new SubRepositoryNode("opendof", "4", "Number of Bytes", 2, DataAccessor.UserGroup);
        da.addSubRepoNode("1", child);
        child = new SubRepositoryNode("opendof", "2", "Number of Bytes", 2, DataAccessor.OpenDofAllocationGroup);
        da.addSubRepoNode("1", child);
        child = new SubRepositoryNode("opendof", "1", "Number of Bytes", 2, DataAccessor.OpenDofAllocationGroup);
        da.addSubRepoNode("1", child);

        child = new SubRepositoryNode("opendof", "4", "Number of Bytes", 2, DataAccessor.UserGroup);
        da.addSubRepoNode("2", child);
        child = new SubRepositoryNode("opendof", "2", "Number of Bytes", 2, DataAccessor.OpenDofAllocationGroup);
        da.addSubRepoNode("2", child);
        child = new SubRepositoryNode("opendof", "1", "Number of Bytes", 2, DataAccessor.OpenDofAllocationGroup);
        da.addSubRepoNode("2", child);
    }
    
    private static void preAllocate(MysqlDataAccessor da, LegacyData legacyData) throws AlreadyExistsException, Exception
    {
        SubmitterData admin = new SubmitterData(DataAccessor.AdminName, DataAccessor.AdminEmail, null, null);
        SubmitterData anonGroup = new SubmitterData(DataAccessor.AnonymousGroupName, DataAccessor.AnonymousGroupEmail, null, null);
//        for(int i=0; i < legacy1Byte.length; i +=2)
//        {
//            da.allocateInterfaceId("1/1", legacy1Byte[i], 1, admin, anonGroup, legacy1Byte[i+1] == 1);
//            
//        }
//        for(int i=0; i < legacy2Byte.length; i += 2)
//        {
//            da.allocateInterfaceId("1/2", legacy2Byte[i], 2, admin, anonGroup, legacy2Byte[i+1] == 1);
//        }
//        for(int i=0; i < legacy4Byte.length; i += 2)
//        {
//            da.allocateInterfaceId("1/4", legacy4Byte[i], 4, admin, anonGroup, legacy4Byte[i+1] == 1);
//        }
    }
    
    public static void handleLegacy(MysqlDataAccessor da, LegacyData legacyData) throws AlreadyExistsException, Exception
    {
        System.out.println("look here");
        switch(legacyData.command)
        {
            case PostEmail:
                break;
            case PreAllocate:
                preAllocate(da, legacyData);
                break;
            case SubRepo:
                addGroups(da);
                subRepo(da);
                break;
            default:
                break;
        }
    }
    
    public static class LegacyData
    {
        public final LegacyCommand command;
        public final SubmitterData submitter;
        public final InterfaceData iface;
        
        public LegacyData(LegacyCommand command, SubmitterData submitter, InterfaceData iface)
        {
            this.command = command;
            this.submitter = submitter;
            this.iface = iface;
       }
    }
    
    public enum LegacyCommand{SubRepo, PreAllocate, PostEmail}
}
