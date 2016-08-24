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

import java.util.List;

import org.slf4j.LoggerFactory;

import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.CapsuleerData;
import com.ccc.crest.da.CrestDataAccessor;
import com.ccc.crest.da.EntityData;

@SuppressWarnings("javadoc")
public class PgDataAccessorTest
{
    public static void testCapsuleer(CrestDataAccessor da)
    {
        try
        {
            CapsuleerData[] caps = new CapsuleerData[10];
            for (int i = 0; i < 10; i++)
            {
                String name = "name" + i;
                long id = i;
                long apikeyid = i + 100;
                String apicode = "code" + i;
                String refreshtoken = "refresh" + i;
                CapsuleerData cdata = new CapsuleerData(name, id, apikeyid, apicode, refreshtoken);
                da.addCapsuleer(cdata);
                CapsuleerData data = da.getCapsuleer(name);
                if (!cdata.equals(data))
                    throw new Exception("getCapsuleer did not match");
                cdata = new CapsuleerData(name, 111 + i, i % 2 == 0 ? -1 : 222 + i, "333" + i, "444" + i);
                da.updateCapsuleer(name, cdata);
                data = da.getCapsuleer(name);
                caps[i] = data;
                if (!cdata.equals(data))
                    throw new Exception("updateCapsuleer did not match");
            }

            EntityData eadata = new EntityData("anonnymous", true);
            EntityData eudata = new EntityData("user", true);
            da.addGroup("name0", eadata);
            da.addGroup("name1", eudata);

            AccessGroup edata = da.getAccessGroup("anonnymous");
            if (!eadata.equals(edata))
                throw new Exception("getAccessGroup anonn did not match");
            edata = da.getAccessGroup("user");
            if (!eudata.equals(edata))
                throw new Exception("getAccessGroup user did not match");

            for (int i = 0; i < 10; i++)
            {
                if (i % 2 == 0)
                {
                    if (i == 0)
                        continue;
                    da.addMemberToGroup(caps[i].capsuleer, "anonnymous");
                    continue;
                }
                if (i == 1)
                    continue;
                da.addMemberToGroup(caps[i].capsuleer, "user");
            }
            for (int i = 0; i < 10; i++)
            {
                if (i % 2 == 0)
                {
                    if (!da.isMember(caps[i].capsuleer, "anonnymous"))
                        throw new Exception("ismember evens failed");
                    if (da.isMember(caps[i].capsuleer, "user"))
                        throw new Exception("ismember evens failed");
                    continue;
                }
                if (!da.isMember(caps[i].capsuleer, "user"))
                    throw new Exception("ismember odds failed");
                if (da.isMember(caps[i].capsuleer, "anonnymous"))
                    throw new Exception("ismember odds failed");
            }
            List<EntityData> elist = da.listMembers("anonnymous");
            if (elist.size() != 5)
                throw new Exception("listMembers evens failed");
            elist = da.listMembers("user");
            if (elist.size() != 5)
                throw new Exception("listMembers odds failed");

            for (int i = 0; i < 10; i++)
            {
                if (i % 2 == 0)
                {
                    try
                    {
                        da.removeMemberFromGroup(caps[i].capsuleer, "anonnymous");
                        if(i == 0)
                            throw new Exception("removeMember removed admin");
                            
                    } catch (Exception e)
                    {
                        if(i != 0)
                            throw new Exception("removeMember evens failed");
                    }
                } else
                {
                    try
                    {
                        da.removeMemberFromGroup(caps[i].capsuleer, "user");
                        if(i == 1)
                            throw new Exception("removeMember removed admin");
                            
                    } catch (Exception e)
                    {
                        if(i != 1)
                            throw new Exception("removeMember odds failed");

                    }
                }
            }
            elist = da.listMembers("anonnymous");
            if (elist.size() != 1)
                throw new Exception("removeMember evens failed");
            elist = da.listMembers("user");
            if (elist.size() != 1)
                throw new Exception("removeMember odds failed");

            List<AccessGroup> glist = da.listGroups();
            if (glist.size() != 2)
                throw new Exception("list groups wrong size");
            da.deleteAccessGroup("anonnymous", false);
            da.deleteAccessGroup("user", false);
            glist = da.listGroups();
            if (glist.size() != 0)
                throw new Exception("delete groups wrong size");

            List<CapsuleerData> caplist = da.listCapsuleers();
            if (caplist.size() != 10)
                throw new Exception("listCapsuleer wrong size");
            for (int i = 9; i >= 0; i--)
            {
                CapsuleerData data = caplist.get(i);
                boolean hasApi = da.hasApiKeys(data.capsuleer);
                if (i % 2 == 0 && hasApi)
                    throw new Exception("hasApiKeys is wrong");
                da.deleteCapsuleer(data.capsuleer);
            }
            if (da.listCapsuleers().size() != 0)
                throw new Exception("deletes failed");
        } catch (Exception e)
        {
            LoggerFactory.getLogger(PgDataAccessorTest.class).error("capsuleer da testing failed", e);
        }
        LoggerFactory.getLogger(PgDataAccessorTest.class).info("capsuleer testing passed");
    }
}
