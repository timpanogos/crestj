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
package com.ccc.crest.core.cache;

import org.slf4j.LoggerFactory;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.events.CacheEventListener;
import com.ccc.crest.da.PagingData;

@SuppressWarnings("javadoc")
public abstract class DbPagingCallback implements CrestResponseCallback
{
    private final String uid;

    public DbPagingCallback(String uid)
    {
        this.uid = uid;
    }

    public abstract void received(EveData data, int page, boolean validated);

    @Override
    public void received(CrestRequestData requestData, EveData data)
    {
//        Alliances alliances = ((AllianceCollection)data).getAlliances();
        Paging pdata = data.getPaging();
        PagingData pagingData = new PagingData(pdata.totalCount, pdata.pageCount, pdata.items.size(), uid);
        boolean validated = false;
        try
        {
            validated = CrestController.getCrestController().getDataAccessor().validatePages(pagingData);
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).warn("Paging is broken, the database has failed to validate the Paging table", e);
            return;
        }
        try
        {
            int page = 0;
            String segment = "?page=";
            String base = null;
            if(pdata.next != null)
            {
                int idx = pdata.next.url.indexOf(segment);
                base = pdata.next.url;
                String value = base.substring(idx + segment.length());
                page = Integer.parseInt(value);
                --page;
                base = base.substring(0,idx);
            }else if(pdata.previous != null)
            {
                int idx = pdata.previous.url.indexOf(segment);
                base = pdata.previous.url;
                String value = base.substring(idx + segment.length());
                page = Integer.parseInt(value);
                ++page;
                base = base.substring(0,idx);
            }else
                page = 1;

            received(data, page, validated);
        } catch (Exception e)
        {
            LoggerFactory.getLogger(getClass()).warn("Paging is broken, the database has failed to add page items to the item table", e);
            return;
        }
        CrestController controller = CrestController.getCrestController();
        if (!validated)
            controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Changed);
        controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Refreshed);
    }
}
