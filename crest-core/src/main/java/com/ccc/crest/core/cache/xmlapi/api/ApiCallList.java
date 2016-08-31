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
package com.ccc.crest.core.cache.xmlapi.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.RightsException;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.xmlapi.api.ApiCallListSax.XmlApiCallGroup;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;

@SuppressWarnings("javadoc")
public class ApiCallList extends BaseEveData
{
    private static final long serialVersionUID = 4330717255249943548L;
    private static final String Version = "2";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.XmlOnlyPublic; //?

    private static final String Uri1 = "/api/CallList.xml.aspx/";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(false);

    private final ApiCallListSax tableMap;
    private final List<XmlApiCallGroup> callGroups;
    
    public ApiCallList()
    {
        tableMap = new ApiCallListSax();
        callGroups = tableMap.getCallGroups();
    }
    
    public List<XmlApiCallGroup> getCallGroups()
    {
        synchronized(callGroups)
        {
            List<XmlApiCallGroup> list = new ArrayList<>();
            for(XmlApiCallGroup group : callGroups)
                list.add((XmlApiCallGroup)group.clone());
            return list;
        }
    }
    
    public static String getXmlUrl()
    {
        StringBuilder url = new StringBuilder();
        url.append(CrestClient.getXmlBaseUri()).append(Uri1);
        return url.toString();
    }

    public static Future<EveData> getCallList(CrestResponseCallback callback) throws RightsException
    {
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getXmlUrl(), null, new ApiCallList(),
                        ApiCallList.class, callback,
                        ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getXml(rdata);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals(ApiCallListSax.RowSetElement) || localName.equals(ApiCallListSax.RowElement))
        {
            tableMap.startElement(uri, localName, qName, attributes);
            return;
        }
        throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String value = new String(ch, start, length);
        throw new SAXException(currentPath() + " characters unknown current stack: " + value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals(ApiCallListSax.RowSetElement) || localName.equals(ApiCallListSax.RowElement))
        {
            tableMap.endElement(uri, localName, qName);
            String path = currentPath();
            int s = stack.size();
            s = stack.size();
        }
        else
            throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callGroups == null) ? 0 : callGroups.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApiCallList other = (ApiCallList) obj;
        if (callGroups == null)
        {
            if (other.callGroups != null)
                return false;
        } else if (!callGroups.equals(other.callGroups))
            return false;
        return true;
    }
}
