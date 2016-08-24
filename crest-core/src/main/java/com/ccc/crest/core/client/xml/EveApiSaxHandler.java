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
package com.ccc.crest.core.client.xml;

import java.io.ByteArrayInputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.ccc.crest.core.cache.BaseEveData;
import com.ccc.crest.core.cache.EveData;

/**
 * EveApi SAX handler.
 */
@SuppressWarnings("javadoc")
public class EveApiSaxHandler extends DefaultHandler
{
    public final static Stack<String> stack = new Stack<String>();
    private BaseEveData baseEveData;
    private EveApi eveApi;

    public EveApiSaxHandler()
    {
    }

    public EveData getData(String body, BaseEveData baseEveData) throws Exception
    {
        this.baseEveData = baseEveData;
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setErrorHandler(new EveApiErrorHandler());
        EveApiSaxHandler handler = this;
        xmlReader.setContentHandler(handler);
        ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        InputSource is = new InputSource(bais);
        xmlReader.parse(is);
        return baseEveData;
    }

    public boolean isMyTag(String tag)
    {
        return stack.peek().equals(tag);
    }

    public boolean amIParent(String parent)
    {
        int size = stack.size();
        if (size < 2)
            return false;
        if (stack.get(size - 2).equals(parent))
            return true;
        return false;
    }

    public boolean isMyPath(String path)
    {
        String[] elements = path.split(",");
        int size = stack.size();
        if (size < elements.length)
            return false;
        for (int i = 0; i < elements.length; i++)
        {
            if (!stack.get(i).equals(elements[i]))
                return false;
        }
        return true;
    }

    public String currentPath()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stack.size(); i++)
        {
            if (i != 0)
                sb.append(".");
            sb.append(stack.get(i));
        }
        return sb.toString();
    }

    @Override
    public void startDocument() throws SAXException
    {
        eveApi = new EveApi(baseEveData);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        eveApi.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        eveApi.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException
    {
        if(eveApi != null)
            eveApi.endDocument();
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String value = new String(ch, start, length).trim();
        if (value.length() == 0)
            return; // ignore white space
        eveApi.characters(ch, start, length);
    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
        throw new SAXException("skippedEntity: " + name);
    }

    public static class EveApiErrorHandler implements ErrorHandler
    {
        private final Logger log;

        public EveApiErrorHandler()
        {
            log = LoggerFactory.getLogger(getClass());
        }

        @Override
        public void warning(SAXParseException e) throws SAXException
        {
            Exception ce = e.getException();
            if (ce != null)
                log.error(getClass().getSimpleName() + " warning", ce);
            log.warn(getClass().getSimpleName() + " warning SAXParseException", e);
            throw e;
        }

        @Override
        public void error(SAXParseException e) throws SAXException
        {
            Exception ce = e.getException();
            if (ce != null)
                log.error(getClass().getSimpleName() + " error", ce);
            log.warn(getClass().getSimpleName() + " error SAXParseException", e);
            throw e;
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException
        {
            Exception ce = e.getException();
            if (ce != null)
                log.error(getClass().getSimpleName() + " fatal", ce);
            log.warn(getClass().getSimpleName() + " fatal SAXParseException", e);
            throw e;
        }
    }

    public interface ChildComplete
    {
        public void setChildComplete(Object listener, Object value) throws SAXException;
    }
}
