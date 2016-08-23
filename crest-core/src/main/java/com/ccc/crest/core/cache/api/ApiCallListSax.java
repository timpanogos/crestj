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
package com.ccc.crest.core.cache.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ccc.crest.core.client.xml.EveApiSaxHandler;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class ApiCallListSax extends EveApiSaxHandler
{
    public static final String RowSetElement = "rowset";
    public static final String RowElement = "row";
    public static final String NameAttr = "name";
    public static final String GroupIdAttr = "groupID";
    public static final String DescriptionAttr = "description";
    public static final String AccessMaskAttr = "accessMask";
    public static final String TypeAttr = "type";

    private final AtomicBoolean onCallGroups;
    private final List<XmlApiCallGroup> callGroups;

    public ApiCallListSax()
    {
        onCallGroups = new AtomicBoolean(true);
        callGroups = new ArrayList<>();
    }

    public List<XmlApiCallGroup> getCallGroups()
    {
        return callGroups;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals(RowSetElement))
        {
            stack.push(localName);
            int size = attributes.getLength();
            int expSize = 3;
            if (size < expSize)
                throw new SAXException(RowSetElement + " expects " + expSize + " attributes");
            return;
        }
        if (localName.equals(RowElement))
        {
            stack.push(localName);
            int[] indices = null;
            String[] expAttrs = null;
            if (onCallGroups.get())
            {
                indices = new int[3];
                expAttrs = new String[3];
                expAttrs[0] = GroupIdAttr;
                expAttrs[1] = NameAttr;
                expAttrs[2] = DescriptionAttr;
            } else
            {
                indices = new int[5];
                expAttrs = new String[5];
                expAttrs[0] = AccessMaskAttr;
                expAttrs[1] = TypeAttr;
                expAttrs[2] = NameAttr;
                expAttrs[3] = GroupIdAttr;
                expAttrs[4] = DescriptionAttr;
            }
            int size = attributes.getLength();
            if (size < expAttrs.length)
                throw new SAXException(RowElement + " expects " + expAttrs.length + " attributes: " + expAttrs);
            for (int i = 0; i < expAttrs.length; i++)
            {
                String attrName = expAttrs[i];
                int idx = attributes.getIndex(attrName);
                indices[i] = idx;
                if (indices[i] < 0)
                    throw new SAXException(RowElement + " did not find the attribute " + attrName);
            }
            synchronized (this)
            {
                String[] values = new String[expAttrs.length];
                for (int i = 0; i < values.length; i++)
                {
                    String value = attributes.getValue(indices[i]);
                    values[i] = value;
                }
                if (onCallGroups.get())
                {
                    XmlApiCallGroup group = new XmlApiCallGroup(Long.parseLong(values[0]), values[1], values[2]);
                    callGroups.add(group);
                } else
                {
                    XmlApiCall call = new XmlApiCall(Long.parseLong(values[0]), values[1], values[2], Long.parseLong(values[3]), values[4]);
                    boolean found = false;
                    for (XmlApiCallGroup group : callGroups)
                    {
                        if (Long.parseLong(values[3]) == group.groupId)
                        {
                            group.calls.add(call);
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        throw new SAXException("unknown call group ID: " + values[3]);
                }
            }
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
        if (localName.equals(RowSetElement) || localName.equals(RowElement))
        {
            if (localName.equals(RowSetElement))
                onCallGroups.set(false);
            stack.pop();
            return;
        }
        throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }

    public class XmlApiCallGroup implements Cloneable
    {
        final public long groupId;
        final public String name;
        final public String description;
        final private List<XmlApiCall> calls;

        public XmlApiCallGroup(long groupId, String name, String description)
        {
            this.groupId = groupId;
            this.name = name;
            this.description = description;
            calls = new ArrayList<>();
        }

        @Override
        public Object clone()
        {
            synchronized (calls)
            {
                XmlApiCallGroup rvalue = new XmlApiCallGroup(groupId, name, description);
                for (XmlApiCall call : calls)
                    rvalue.calls.add((XmlApiCall) call.clone());
                return rvalue;
            }
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((calls == null) ? 0 : calls.hashCode());
            result = prime * result + ((description == null) ? 0 : description.hashCode());
            result = prime * result + (int) (groupId ^ (groupId >>> 32));
            result = prime * result + ((name == null) ? 0 : name.hashCode());
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
            XmlApiCallGroup other = (XmlApiCallGroup) obj;
            if (calls == null)
            {
                if (other.calls != null)
                    return false;
            } else if (!calls.equals(other.calls))
                return false;
            if (description == null)
            {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (groupId != other.groupId)
                return false;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            format.ttl(getClass().getSimpleName(), ":");
            format.inc();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl("groupId: ", groupId);
            format.ttl("name: ", name);
            format.ttl("description: ", description);
            format.ttl("calls:");
            format.inc();
            for (XmlApiCall call : calls)
                call.toString(format);
            format.dec();
            return format;
        }
    }

    public class XmlApiCall implements Cloneable
    {
        final public long accessMask;
        final public String type;
        final public String name;
        final public long groupId;
        final public String description;

        public XmlApiCall(long accessMask, String type, String name, long groupId, String description)
        {
            this.accessMask = accessMask;
            this.type = type;
            this.name = name;
            this.groupId = groupId;
            this.description = description;
        }

        @Override
        public Object clone()
        {
            synchronized (this)
            {
                return new XmlApiCall(accessMask, type, name, groupId, description);
            }
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (accessMask ^ (accessMask >>> 32));
            result = prime * result + ((description == null) ? 0 : description.hashCode());
            result = prime * result + (int) (groupId ^ (groupId >>> 32));
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
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
            XmlApiCall other = (XmlApiCall) obj;
            if (accessMask != other.accessMask)
                return false;
            if (description == null)
            {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (groupId != other.groupId)
                return false;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (type == null)
            {
                if (other.type != null)
                    return false;
            } else if (!type.equals(other.type))
                return false;
            return true;
        }

        @Override
        public String toString()
        {
            TabToLevel format = new TabToLevel();
            return toString(format).toString();
        }

        public TabToLevel toString(TabToLevel format)
        {
            format.ttl(getClass().getSimpleName(), ":");
            format.inc();
            format.ttl("accessMask: ", accessMask, "(0x", Long.toHexString(accessMask), ")");
            format.ttl("type: ", type);
            format.ttl("name: ", name);
            format.ttl("groupId: ", groupId);
            format.ttl("description: ", description);
            format.dec();
            return format;
        }
    }
}
