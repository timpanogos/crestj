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
package com.ccc.crest.core.cache.xmlapi.server;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.ScopeToMask;
import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.CrestRequestData;
import com.ccc.crest.core.cache.EveData;
import com.ccc.crest.core.cache.crest.time.CrestTime;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;

@SuppressWarnings("javadoc")
public class ServerStatus extends BaseEveData
{
    private static final long serialVersionUID = -5863306344484912590L;
    private static final String Version = "2";
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.XmlOnlyPublic; //?

    public static final String ServerOpenElement = "serverOpen";
    public static final String OnlinePlayersElement = "onlinePlayers";

    private static final String Uri1 = "/Server/ServerStatus.xml.aspx/";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(true);

    private volatile boolean serverOpen;
    public volatile int onlinePlayers;

    public ServerStatus()
    {
    }

    @Override
    public void init()
    {
    }

    public static String getXmlUrl()
    {
        StringBuilder url = new StringBuilder();
        url.append(CrestClient.getXmlBaseUri()).append(Uri1);
        return url.toString();
    }

    public static Future<EveData> getServerStatus(CrestResponseCallback callback) throws Exception
    {
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        null, getXmlUrl(),
                        null, new ServerStatus(), CrestTime.class,
                        callback,
                        ReadScope, Version, continueRefresh);
        //@formatter:on
        return CrestController.getCrestController().crestClient.getXml(rdata);
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals(ServerOpenElement))
        {
            stack.push(localName);
            return;
        }else
        if (localName.equals(OnlinePlayersElement))
        {
            stack.push(localName);
            return;
        }else
            throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String current = stack.peek();
        if (current.equals(ServerOpenElement))
        {
            synchronized (this)
            {
                String value = new String(ch, start, length);
                try
                {
                    serverOpen = Boolean.parseBoolean(value);
                } catch (Exception e)
                {
                    throw new SAXException("invalid boolean format: " + value, e);
                }
            }
            return;
        }
        if (current.equals(OnlinePlayersElement))
        {
            synchronized (this)
            {
                String value = new String(ch, start, length);
                try
                {
                    onlinePlayers = Integer.parseInt(value);
                } catch (Exception e)
                {
                    throw new SAXException("invalid integer format: " + value, e);
                }
            }
            return;
        }
        String value = new String(ch, start, length);
        throw new SAXException(currentPath() + " characters unknown current stack: " + value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals(ServerOpenElement) || localName.equals(OnlinePlayersElement))
        {
            stack.pop();
            return;
        }
        throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + onlinePlayers;
        result = prime * result + (serverOpen ? 1231 : 1237);
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
        ServerStatus other = (ServerStatus) obj;
        if (onlinePlayers != other.onlinePlayers)
            return false;
        if (serverOpen != other.serverOpen)
            return false;
        return true;
    }
}

