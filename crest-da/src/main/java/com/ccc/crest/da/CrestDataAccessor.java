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
