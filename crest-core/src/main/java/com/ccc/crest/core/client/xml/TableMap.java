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

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * The top level Interface Element handler
 */
@SuppressWarnings("javadoc")
public class TableMap extends EveApiSaxHandler
{
    public static final String RowSetElement = "rowset";
    public static final String NameAttr = "name";
    public static final String KeyAttr = "key";
    public static final String ColumnsAttr = "columns";
    public static final String RowElement = "row";

    private final Map<String, RowSet> tables;

    public TableMap()
    {
        tables = new HashMap<>();
    }

    private class RowSet
    {
        final String name;
        final String[] key;
        final String[] columns;
        final Map<String, Row> rowmap;

        private RowSet(String name, String key, String columns)
        {
            this.name = name;
            this.key = key.split(",");
            this.columns = columns.split(",");
            rowmap = new HashMap<>();
        }
    }

    private class Row
    {
        final String[] columnNames;

        private Row(String[] columnNames)
        {
            this.columnNames = columnNames;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals(RowSetElement))
        {
            stack.push(localName);
            int size = attributes.getLength();
            if (size < 3)
                throw new SAXException(RowSetElement + " expects 3 attributes: " + NameAttr + "," + KeyAttr + "," + ColumnsAttr);
            int index1 = attributes.getIndex(NameAttr);
            if (index1 < 0)
                throw new SAXException(RowSetElement + " did not find the attribute " + NameAttr);
            int index2 = attributes.getIndex(KeyAttr);
            if (index2 < 0)
                throw new SAXException(RowSetElement + " did not find the attribute " + KeyAttr);
            int index3 = attributes.getIndex(ColumnsAttr);
            if (index3 < 0)
                throw new SAXException(RowSetElement + " did not find the attribute " + ColumnsAttr);
            synchronized (this)
            {
                RowSet rowset = new RowSet(attributes.getValue(index1), attributes.getValue(index2), attributes.getValue(index3));
                tables.put(rowset.name, rowset);
            }
            return;
        }
        if (localName.equals(RowElement))
        {
            stack.push(localName);
            return;
        }else
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
