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
package com.ccc.crest.core.client.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.EveData;

/**
 * The top level Interface Element handler
 */
@SuppressWarnings("javadoc")
public class EveApi extends EveApiSaxHandler
{
    //    public static final String CatalogIrSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/interface-repository.xsd";
    //    public static final String CatalogIrMetaSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/interface-repository-meta.xsd";
    //    public static final String CatalogNamespaceSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/xml2009.xsd";
    //
    //    public static final String InterfaceNamespace = "http://opendof.org/schema/interface-repository";
    //    public static final String InterfaceMetaNamespace = "http://opendof.org/schema/interface-repository-meta";
    //    public static final String InterfaceSchemaLocation = "http://opendof.org/schema/interface-repository.xsd";
    //    public static final String W3SchemaNamespace = "http://www.w3.org/2001/XMLSchema-instance";
    //    public static final String W3Namespace = "http://www.w3.org/XML/1998/namespace";
    //    public static final String W3NamespaceLocation = "http://www.w3.org/2001/xml.xsd";

    public static final String DateFormat = "yyyy-MM-dd HH:mm:ss z";
    public static final String EveApiElement = "eveapi";
    public static final String CachedUntilElement = "cachedUntil";
    public static final String CurrentTimeElement = "currentTime";
    public static final String ResultElement = "result";
    public static final String VersionAttr = "version";
    public static final String FfpdmAttr = "ffpdm";

    private static final String ResultPath = EveApiElement + "." + ResultElement;

    private String version;
    private Date currentTime;
    private Date cachedUntil;
    private String ffpdm;
    private BaseEveData result;

    public EveApi(BaseEveData eveData)
    {
        super();
        result = eveData;
    }

    public synchronized String getVersion()
    {
        return version;
    }

    public synchronized EveData getResult()
    {
        return result;
    }

    public synchronized Date getCachedUntil()
    {
        return cachedUntil;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (stack.size() == 0)
        {
            stack.push(localName);
            if (!isMyTag(EveApiElement))
                throw new SAXException("did not find element: " + EveApiElement);
            int size = attributes.getLength();
            if (size < 1)
                throw new SAXException(EveApiElement + " expects 1 attributes: " + VersionAttr);
            int index1 = attributes.getIndex(VersionAttr);
            if (index1 < 0)
                throw new SAXException(EveApiElement + " did not find the attribute " + VersionAttr);
            //            int index2 = attributes.getIndex(FfpdmAttr);
            //            if (index2 < 0)
            //                throw new SAXException(EveApiElement + " did not find the attribute " + FfpdmAttr);
            synchronized (this)
            {
                version = attributes.getValue(index1);
                //                ffpdm = attributes.getValue(index2);
            }
            return;
        }
        if (stack.peek().equals(EveApiElement))
        {
            // the Interface level can contain:
            // result, cachedUntil
            //The stack here is: "interface"
            if (localName.equals(CurrentTimeElement) || localName.equals(ResultElement) || localName.equals(CachedUntilElement))
            {
                stack.push(localName);
                return;
            }
        }else
        if (currentPath().startsWith(ResultPath))
        {
            result.startElement(uri, localName, qName, attributes);
            return;
        }
        throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String current = stack.peek();
        if (current.equals(CurrentTimeElement) || current.equals(CachedUntilElement))
        {
            String timestr = new String(ch, start, length) + " GMT";
            SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
            synchronized (this)
            {
                try
                {
                    if(current.equals(CurrentTimeElement))
                        currentTime = sdf.parse(timestr);
                    else
                        cachedUntil = sdf.parse(timestr);
                } catch (ParseException e)
                {
                    throw new SAXException("invalid date format: " + timestr, e);
                }
            }
            return;
        }
        else if (currentPath().startsWith(ResultPath))
            result.characters(ch, start, length);
        //        else if (currentPath().startsWith(MethodsPath))
        //            methods.characters(ch, start, length);
        //        else if (currentPath().startsWith(EventsPath))
        //            events.characters(ch, start, length);
        //        else if (currentPath().startsWith(ExceptionsPath))
        //            exceptions.characters(ch, start, length);
        //        else if (currentPath().startsWith(EveApiElement) && MetadataElements.oneOfYours(current))
        //            metadata.characters(ch, start, length);
        else
        {
            String value = new String(ch, start, length);
            throw new SAXException(currentPath() + " characters unknown current stack: " + value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals(EveApiElement))
        {
            stack.pop();
            //            validate();
            return;
        }
        String current = stack.peek();
        if (current.equals(CurrentTimeElement) || current.equals(ResultElement) || current.equals(CachedUntilElement))
            stack.pop();
        else if (currentPath().startsWith(ResultPath))
            result.endElement(uri, localName, qName);
        //        else if (currentPath().startsWith(MethodsPath))
        //            methods.endElement(uri, localName, qName);
        //        else if (currentPath().startsWith(EventsPath))
        //            events.endElement(uri, localName, qName);
        //        else if (currentPath().startsWith(ExceptionsPath))
        //            exceptions.endElement(uri, localName, qName);
        //        else if (currentPath().startsWith(EveApiElement) && MetadataElements.oneOfYours(current))
        //            metadata.endElement(uri, localName, qName);
        else
            throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }
    
    @Override
    public void endDocument() throws SAXException
    {
        result.endDocument();
    }

    public static int getCachedUntil(String body) throws Exception
    {
        Date current = new Date();
        long t0 = System.currentTimeMillis();
        String tag = CachedUntilElement + ">";
        int idx = body.indexOf(tag);
        if (idx == -1)
            throw new Exception("expected tag <" + tag + " in body: " + body);
        idx += tag.length();
        body = body.substring(idx);
        idx = body.indexOf("</");
        if (idx == -1)
            throw new Exception("expected tag <" + tag + " in body: " + body);
        body = body.substring(0, idx);
        body += " GMT";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        String now = sdf.format(current);
        long t1 = sdf.parse(body).getTime();
        t1 = t1 - t0;
        long rmd = t1 % 1000; 
        t1 = t1 / 1000;
        if(rmd >= 500)
        ++t1;
        return (int) t1;
    }
}
