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
package org.opendof.tools.repository.interfaces.da;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("javadoc")
public class SubRepositoryNode implements Serializable
{
	private static final long serialVersionUID = -163898939222321498L;
	
	private String repoType;
    private String name;
    private String label;
    private int depth;
    private SubmitterData group;
    private final List<SubRepositoryNode> children;

//    public SubRepositoryNode(String repoType, String name, String label, String group)
//    {
//        this(repoType, name, label, 0, group);
//    }
//    
    public SubRepositoryNode(SubRepositoryNode node)
    {
        this.repoType = node.repoType;
        this.name = node.name;
        this.label = node.label;
        this.depth = node.depth;
        this.group = node.group;
        children = new ArrayList<SubRepositoryNode>();
    }
    
    public SubRepositoryNode(String repoType, String name, String label, int depth, SubmitterData group)
    {
        children = new ArrayList<SubRepositoryNode>();
        this.repoType = repoType;
        this.name = name;
        this.label = label;
        this.depth = depth;
        this.group = group;
    }
    
    public synchronized String getName()
    {
        return name;
    }

    public synchronized void setName(String name)
    {
        this.name = name;
    }

    public synchronized String getLabel()
    {
        return label;
    }

    public synchronized void setLabel(String label)
    {
        this.label = label;
    }

    public synchronized int getDepth()
    {
        return depth;
    }

    public synchronized void setDepth(int depth)
    {
        this.depth = depth;
    }

    public synchronized String getRepoType()
    {
        return repoType;
    }

    public synchronized void setRepoType(String repoType)
    {
        this.repoType = repoType;
    }

    public synchronized SubmitterData getGroup()
    {
        return group;
    }

    public synchronized void setGroup(SubmitterData group)
    {
        this.group = group;
    }

    public void add(SubRepositoryNode node)
    {
        synchronized (children)
        {
            children.add(node);
        }
    }

    public List<SubRepositoryNode> getChildren()
    {
        synchronized (children)
        {
            return new ArrayList<SubRepositoryNode>(children);
        }
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl("SubRepositoryNode:");
        format.level.incrementAndGet();
        format.ttl("repoType: ", repoType);
        format.ttl("name: ", name);
        format.ttl("label: ", label);
        format.ttl("depth: ", depth);
        format.ttl("group: ", group.email);
        format.ttl("children: ");
        format.level.incrementAndGet();
        for(int i=0; i < children.size(); i++)
            children.get(i).toString(format);
        format.level.decrementAndGet();
        format.level.decrementAndGet();
        return format;
    }

    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        toString(format);
        return format.toString();
    }
    
    public static class TabToLevel
    {
        public volatile StringBuilder sb;
        public final AtomicInteger level;

        public TabToLevel()
        {
            this(null);
        }

        public void clear()
        {
            level.set(0);
            sb = new StringBuilder();
        }

        public TabToLevel(StringBuilder sbIn)
        {
            if (sbIn == null)
                sbIn = new StringBuilder();
            sb = sbIn;
            level = new AtomicInteger(0);
        }

        public void ttl(Object... values)
        {
            String[] array = new String[values.length];
            for (int i = 0; i < array.length; i++)
                array[i] = (values[i] == null ? "null" : values[i].toString());
            tabToLevel(true, array);
        }

        public void ttln(Object... values)
        {
            String[] array = new String[values.length];
            for (int i = 0; i < array.length; i++)
                array[i] = (values[i] == null ? "null" : values[i].toString());
            tabToLevel(false, array);
        }

        public void tabToLevel(boolean eol, String... values)
        {
            for (int i = 0; i < level.get(); i++)
                sb.append("\t");
            for (int j = 0; j < values.length; j++)
                sb.append(values[j]);
            if (eol)
                sb.append("\n");
        }

        public void indentedOk()
        {
            level.incrementAndGet();
            ttl("ok");
            level.decrementAndGet();
        }

        @Override
        public String toString()
        {
            return sb.toString();
        }
    }
}
