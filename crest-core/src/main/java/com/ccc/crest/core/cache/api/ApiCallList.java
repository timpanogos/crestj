/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.core.cache.api;

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
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.client.CrestResponseCallback;

@SuppressWarnings("javadoc")
public class ApiCallList extends BaseEveData
{
    public static final String AccessGroup = CrestController.AnonymousGroupName;
    public static final ScopeToMask.Type ScopeType = ScopeToMask.Type.XmlOnlyPublic; //?

    private static final String Uri1 = "/api/CallList.xml.aspx/";
    private static final String ReadScope = null;
    private static final String WriteScope = null;

    public static final AtomicBoolean continueRefresh = new AtomicBoolean(false);

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
//        if (localName.equals(ServerOpenElement))
//        {
//            stack.push(localName);
//            return;
//        } else
//        if (localName.equals(OnlinePlayersElement))
//        {
//            stack.push(localName);
//            return;
//        }else
            throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
//        String current = stack.peek();
//        if (current.equals(ServerOpenElement))
//        {
//            synchronized (this)
//            {
//                String value = new String(ch, start, length);
//                try
//                {
//                    serverOpen = Boolean.parseBoolean(value);
//                } catch (Exception e)
//                {
//                    throw new SAXException("invalid boolean format: " + value, e);
//                }
//            }
//            return;
//        }
//        if (current.equals(OnlinePlayersElement))
//        {
//            synchronized (this)
//            {
//                String value = new String(ch, start, length);
//                try
//                {
//                    onlinePlayers = Integer.parseInt(value);
//                } catch (Exception e)
//                {
//                    throw new SAXException("invalid integer format: " + value, e);
//                }
//            }
//            return;
//        }
        String value = new String(ch, start, length);
        throw new SAXException(currentPath() + " characters unknown current stack: " + value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
//        if (localName.equals(ServerOpenElement) || localName.equals(OnlinePlayersElement))
//        {
//            stack.pop();
//            return;
//        }
        throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }
}
