/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as 
**  published by the Free Software Foundation, either version 3 of the 
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.da;

import java.util.List;

import com.ccc.db.AlreadyExistsException;
import com.ccc.db.DataAccessor;
import com.ccc.db.NotFoundException;

@SuppressWarnings("javadoc")
public interface CrestDataAccessor extends DataAccessor
{
    public void addCapsuleer(CapsuleerData capData) throws AlreadyExistsException, Exception;
    public CapsuleerData getCapsuleer(String capsuleer) throws NotFoundException, Exception;
    public void updateCapsuleer(String capsuleer, CapsuleerData capData) throws NotFoundException, Exception;
    public void deleteCapsuleer(String capsuleer) throws Exception;
    public List<CapsuleerData> listCapsuleers() throws Exception;
    public boolean hasApiKeys(String capsuleer) throws Exception;

    public void addEntity(EntityData entityData) throws AlreadyExistsException, Exception;
    public EntityData getEntity(String name) throws NotFoundException, Exception;
    public void updateEntity(String name, EntityData userData) throws NotFoundException, Exception;
    public void deleteEntity(String name) throws Exception;
    public List<EntityData> listEntities() throws Exception;

    public void addGroup(String admin, EntityData groupData) throws AlreadyExistsException, Exception;
    public AccessGroup getAccessGroup(String name) throws NotFoundException, Exception;
    //    public void updateAccessGroup(String name, EntityData userData) throws NotFoundException, Exception;
    public void deleteAccessGroup(String name, boolean force) throws Exception;
    public List<AccessGroup> listGroups() throws Exception;
    public void addMemberToGroup(String member, String group) throws NotFoundException, Exception;
    public void removeMemberFromGroup(String member, String group) throws Exception;
    public boolean isMember(String member, String group) throws Exception;
    public List<EntityData> listMembers(String group) throws Exception;

    public void addSharedRight(SharedRight right) throws AlreadyExistsException, Exception;
    public void deleteSharedRight(String capsuleer) throws Exception;
    public List<SharedRight> listSharedRights(String capsuleer) throws Exception;
}
