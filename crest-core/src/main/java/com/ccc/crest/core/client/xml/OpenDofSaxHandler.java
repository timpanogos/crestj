/*
**  Copyright (c) 2010-2015, Panasonic Corporation.
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
package org.opendof.tools.repository.interfaces.opendof.saxParser;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * OpenDof SAX handler.  
 */
@SuppressWarnings("javadoc")
public class OpenDofSaxHandler extends DefaultHandler
{
    public final static Stack<String> stack = new Stack<String>();
    private final static AtomicBoolean publish = new AtomicBoolean(false);
    
    private InterfaceElement interfaceData;
    
    /**
     * Default OpenDofSaxHandler constructor 
     */
    public OpenDofSaxHandler()
    {
    }

    public static void setPublish(boolean value)
    {
        publish.set(value);
    }
    
    public static boolean isPublish()
    {
        return publish.get();
    }
    
    public InterfaceElement getInterface()
    {
        return interfaceData;
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
        interfaceData = new InterfaceElement();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        interfaceData.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        interfaceData.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException
    {
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String value = new String(ch, start, length).trim();
        if (value.length() == 0)
            return; // ignore white space
        interfaceData.characters(ch, start, length);
    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
        throw new SAXException("skippedEntity: " + name);
    }

    /* ************************************************************************
     * Export xml helpers
    **************************************************************************/
    public static boolean useSpaces = true;
    public static int spacesPerTab = 4;
    public static boolean pretty = true;

    public static String cdataFixup(String value)
    {
        value = value.trim();
        if (value.indexOf('<') != -1)
        {
            if (value.startsWith("<![CDATA["))
                return value;
            value = "<![CDATA[" + value + "]]>";
        }
        return value;
    }

    public static StringBuilder level(StringBuilder sb, int level)
    {
        if (pretty)
        {
            if (useSpaces)
            {
                for (int i = 0; i < level; i++)
                    for (int j = 0; j < spacesPerTab; j++)
                        sb.append(" ");
            } else
                for (int i = 0; i < level; i++)
                    sb.append("\t");
        }
        return sb;
    }

    public enum CType
    {
        Open, Single, Close
    }

    public static StringBuilder closer(StringBuilder sb, String tag, CType type, boolean newline)
    {
        switch (type)
        {
            case Close:
                sb.append("</").append(tag).append(">");
                break;
            case Open:
                sb.append(">");
                break;
            case Single:
                sb.append("/>");
                break;
            default:
                break;

        }
        if (newline)
        {
            if (!pretty)
                sb.append("");
            else
                sb.append("\n");
        }
        return sb;
    }

    public static StringBuilder exportNames(StringBuilder sb, String tag, MetadataElements names, boolean close, int level)
    {
        if (names == null)
            return sb;
        ++level;
        names.export(sb, level);
        --level;
        if (close && tag != null)
            element(sb, tag, CType.Close, level);
        return sb;
    }

    /*
        <element/>
        <element>
            <element attr="z"/>
            <element>cdata</element>
            <element attr="z" attr2="x">cdata</element>
        </element>
     */

    public static StringBuilder element(StringBuilder sb, String tag, CType type, int level)
    {
        // <element/> 
        // <element>
        // </element>
        level(sb, level);
        switch (type)
        {
            case Close:
                sb.append("</").append(tag).append(">");
                break;
            case Open:
                sb.append("<").append(tag).append(">");
                break;
            case Single:
                sb.append("<").append(tag).append("/>");
                break;
            default:
                break;

        }
        if (pretty)
            sb.append("\n");
        else
            sb.append("");
        return sb;
    }

    public static StringBuilder element(StringBuilder sb, String tag, String cdata, boolean close, int level)
    {
        //      <element>cdata</element>
        return element(sb, tag, (String[]) null, (String[]) null, cdata, close, level);
    }

    public static StringBuilder element(StringBuilder sb, String tag, String attr, String attrValue, String cdata, boolean close, int level)
    {
        //@formatter:off
            return 
                element(sb, tag, 
                     attr == null ? null : new String[] { attr }, 
                     attrValue == null ? null : new String[] { attrValue }, 
                     cdata, close, level);
            //@formatter:on
    }

    public static StringBuilder element(StringBuilder sb, String tag, String[] attrNames, String[] attrValues, String cdata, boolean close, int level)
    {
        //      <element attr="z" attr2="x">cdata</element>
        //      <element attr="z" attr2="x"/>

        level(sb, level);
        sb.append("<").append(tag);
        boolean hadAttrs = false;
        if (attrNames != null)
        {
            if (attrNames.length != attrValues.length)
                throw new RuntimeException("attr names length not equal to values length");
            for (int i = 0; i < attrNames.length; i++)
            {
                hadAttrs = true;
                sb.append(" ");
                sb.append(attrNames[i]).append("=").append("\"").append(attrValues[i]).append("\"");
            }
        }
        if (cdata == null)
        {
            if (!hadAttrs)
                return closer(sb, null, CType.Single, true);
            if (close)
                return closer(sb, null, CType.Single, true);
            return closer(sb, null, CType.Open, true);
        }
        closer(sb, null, CType.Open, false);
        sb.append(cdata);
        return closer(sb, tag, CType.Close, true);
    }

    public static class LegacyErrorHandler implements ErrorHandler
    {
        private final Logger log;

        public LegacyErrorHandler()
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

    /**
     * Check equals of two objects.
     *
     * @param o1 the object to check o2 against
     * @param o2 the object to check o1 against
     * @return true, if both objects are equal (both null is considered equal)
     */
    public static boolean checkEquals(Object o1, Object o2)
    {
        if (o1 == null && o2 != null)
            return false;
        if (o2 == null && o1 != null)
            return false;

        if (o1 == null)
            return true;

        return o1.equals(o2);
    }

    public interface ChildComplete
    {
        public void setChildComplete(Object listener, Object value) throws SAXException;
    }
}
