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
package com.ccc.crest.da.pg;

import java.util.List;
import java.util.Properties;

import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.CapsuleerData;
import com.ccc.crest.da.CrestDataAccessor;
import com.ccc.crest.da.EntityData;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.NotFoundException;
import com.ccc.db.postgres.PgBaseDataAccessor;


@SuppressWarnings("javadoc")
public class PgDataAccessor extends PgBaseDataAccessor implements CrestDataAccessor
{
    @Override
    public void init(Properties properties) throws Exception
    {
        super.init(properties);
    }

    @Override
    public void close()
    {
        super.close();
    }

    @Override
    public void addCapsuleer(CapsuleerData userData) throws AlreadyExistsException, Exception
    {
        CapsuleerJdbc.addCapsuleer(getConnection(), userData, true);
    }

    @Override
    public CapsuleerData getCapsuleer(String name) throws NotFoundException, Exception
    {
        return CapsuleerJdbc.getCapsuleer(getConnection(), name, true);
    }

    @Override
    public void updateCapsuleer(String name, CapsuleerData userData) throws NotFoundException, Exception
    {
        CapsuleerJdbc.updateCapsuleer(getConnection(), userData);
    }

    @Override
    public void deleteCapsuleer(String name) throws Exception
    {
        CapsuleerJdbc.deleteCapsuleer(getConnection(), name, true);
    }

    @Override
    public List<CapsuleerData> listCapsuleers() throws Exception
    {
        return CapsuleerJdbc.listCapsuleers(getConnection());
    }

    @Override
    public boolean hasApiKeys(String name) throws Exception
    {
        CapsuleerData capData = getCapsuleer(name);
        return capData.apiKeyId != -1;
    }

    @Override
    public void addEntity(EntityData entityData) throws AlreadyExistsException, Exception
    {
        EntityJdbc.addEntity(getConnection(), entityData, true);
    }

    @Override
    public EntityData getEntity(String name) throws NotFoundException, Exception
    {
        return EntityJdbc.getEntity(getConnection(), name);
    }

    @Override
    public void updateEntity(String name, EntityData entityData) throws NotFoundException, Exception
    {
        EntityJdbc.updateEntity(getConnection(), entityData);
    }

    @Override
    public void deleteEntity(String name) throws Exception
    {
        EntityJdbc.deleteEntity(getConnection(), name, true);
    }

    @Override
    public List<EntityData> listEntities() throws Exception
    {
        return EntityJdbc.listEntities(getConnection());
    }

    @Override
    public void addGroup(String admin, EntityData groupData) throws AlreadyExistsException, Exception
    {
        AccessGroupJdbc.addGroup(getConnection(), admin, groupData);
    }

    @Override
    public AccessGroup getAccessGroup(String name) throws NotFoundException, Exception
    {
        return AccessGroupJdbc.getGroup(getConnection(), name);
    }

//    @Override
//    public void updateAccessGroup(String name, EntityData userData) throws NotFoundException, Exception
//    {
//        AccessGroupJdbc.updateGroup(getConnection(), admin, groupData);
//    }

    @Override
    public void deleteAccessGroup(String name, boolean force) throws Exception
    {
        AccessGroupJdbc.deleteGroup(getConnection(), name, force);
    }

    @Override
    public List<AccessGroup> listGroups() throws Exception
    {
        return AccessGroupJdbc.listGroups(getConnection());
    }

    @Override
    public void addMemberToGroup(String member, String group) throws NotFoundException, Exception
    {
        AccessGroupJdbc.addMemberToGroup(getConnection(), member, group);
    }

    @Override
    public void removeMemberFromGroup(String member, String group) throws Exception
    {
        AccessGroupJdbc.removeMemberFromGroup(getConnection(), member, group);
    }

    @Override
    public boolean isMember(String member, String group) throws Exception
    {
        return AccessGroupJdbc.isMember(getConnection(), member, group);
    }

    @Override
    public List<EntityData> listMembers(String group) throws Exception
    {
        return AccessGroupJdbc.listMembers(getConnection(), group);
    }
}