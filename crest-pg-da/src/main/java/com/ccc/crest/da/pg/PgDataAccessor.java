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
package com.ccc.crest.da.pg;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.AllianceData;
import com.ccc.crest.da.CapsuleerData;
import com.ccc.crest.da.CrestDataAccessor;
import com.ccc.crest.da.EntityData;
import com.ccc.crest.da.PagingData;
import com.ccc.crest.da.SharedRight;
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

    @Override
    public void addSharedRight(SharedRight right) throws AlreadyExistsException, Exception
    {
        SharedRightJdbc.addSharedRight(getConnection(), right);
    }

    @Override
    public void deleteSharedRight(String capsuleer) throws Exception
    {
        SharedRightJdbc.deleteSharedRight(getConnection(), capsuleer);
    }

    @Override
    public List<SharedRight> listSharedRights(String capsuleer) throws Exception
    {
        return SharedRightJdbc.listSharedRights(getConnection(), capsuleer);
    }

    @Override
    public boolean validatePages(PagingData pageData) throws Exception
    {
        Connection connection = getConnection();
        try
        {
            PagingRow row = PagingJdbc.getRow(connection, pageData.uid, false);
            if(row.totalItems != pageData.totalItems)
            {
                PagingJdbc.updateRow(connection, row, row.pid, false);
                //FIXME:
//                PagingJdbc.truncate(connection, true);
                return false;
            }
            PgBaseDataAccessor.close(connection, null, null, true);
            return true;
        } catch (NotFoundException e)
        {
            PagingJdbc.insertRow(connection, pageData, true);
            return true;
        }
    }

    @Override
    public PagingData getAlliances(String uid) throws Exception
    {
        return PagingJdbc.getRow(getConnection(), uid, true);
    }

    @Override
    public List<AllianceData> getAlliances(int page) throws Exception
    {
        return AllianceJdbc.getRows(getConnection(), page, true);
    }

    @Override
    public void addAlliances(List<AllianceData> alliances) throws Exception
    {
        Connection connection = getConnection();
        List<AllianceData> list = AllianceJdbc.getRows(getConnection(), alliances.get(0).page, false);
        if(list.size() == 0)
            for(AllianceData data : alliances)
                AllianceJdbc.insertRow(connection, data, false);
        PgBaseDataAccessor.close(connection, null, null, true);
    }
}
