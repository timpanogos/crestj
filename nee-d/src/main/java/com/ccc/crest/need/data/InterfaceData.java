package com.ccc.crest.need.data;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;

import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class InterfaceData implements Comparable<InterfaceData>, Serializable
{
	private static final long serialVersionUID = -3845483943880028396L;
	
	private String name;
	public final String iid;
    public final String xml;
    public final String version;
    public final SubmitterData submitter;
    public final SubmitterData accessGroup;
    private String repoType;
    public final Date creationDate;
    public final Date lastModifiedDate;
    public final Boolean publish;
    
    public InterfaceData(String iid, String version, String repoType)
    {
        this(null, iid, null, version, null, null, repoType, null, null, false);
    }
    
    public InterfaceData(String iid, String version, String xml, String repoType, SubmitterData submitter, SubmitterData accessGroup)
    {
        this(null, iid, xml, version, submitter, accessGroup, repoType, null, null, false);
    }
    
    //@Formatter:off
    public InterfaceData(
        String name, String iid, String xml, String version, 
        SubmitterData submitter, SubmitterData accessGroup, String repoType, 
        Date creationDate, Date lastModifiedDate, Boolean publish)
    //@Formatter:on
    {
        this.name = name;
        this.iid = iid;
        this.xml = xml;
        this.version = version;
        this.submitter = submitter;
        this.accessGroup = accessGroup;
        this.repoType = repoType;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.publish = publish;
    }
    
    public synchronized String getName()
    {
        return name;
    }

    public synchronized void setName(String name)
    {
        this.name = name;
    }

    
    public synchronized String getRepoType()
    {
        return repoType;
    }

    public synchronized void setRepoType(String repoType)
    {
        this.repoType = repoType;
    }

    public void toWriter(Writer writer) throws IOException
    {
        writer.write(iid); 
        writer.write("\t\t");
        if(publish)
            writer.write("published\t");
        else
            writer.write("working\t\t");
        writer.write("\tversion: ");
        writer.write(version);
        writer.write("\ttype: ");
        writer.write(repoType);
        writer.write("\tcreated: ");
        writer.write(creationDate.toString());
        writer.write("\tmodified: ");
        writer.write(lastModifiedDate.toString());
        writer.write("\tsubmitter: ");
        writer.write(submitter.name);
        writer.write("\n");
    }
    
    @Override
    public String toString()
    {
        return toString(new TabToLevel(), false).toString();
    }
    
	public TabToLevel toString(TabToLevel format, boolean withXml)
	{
	    format.ttl("\niid: ", iid == null ? "null" : iid);
	    format.level.incrementAndGet();
	    format.ttl("name: ", name == null ? "name not determined" : name);
	    format.ttl("version: ", version);
	    format.ttl("publish: ", publish);
        format.ttl("repoType: ",  repoType);
        format.ttl("created: ", creationDate);
        format.ttl("modified: ", lastModifiedDate);
        format.ttl("submitter:");
        format.level.incrementAndGet();
        if(submitter == null)
            format.ttl("null");
        else
            submitter.toString(format);
        format.level.decrementAndGet();
        format.ttl("accessGroup:");
        format.level.incrementAndGet();
        if(accessGroup == null)
            format.ttl("null");
        else
            accessGroup.toString(format);
        format.level.decrementAndGet();
        format.ttl("xml:");
        format.level.incrementAndGet();
        if(xml == null)
            format.ttl("the xml column is null");
        else
        if(!withXml)
            format.ttl("xml is present in the table");
        else
            format.ttl(xml);
		return format;
	}
	
	@Override
	public int compareTo(InterfaceData o) 
	{
		return iid.compareTo(o.iid);
	}
}
