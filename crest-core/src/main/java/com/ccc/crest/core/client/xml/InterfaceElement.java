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

import java.util.Hashtable;

import org.opendof.core.oal.DOFInterfaceID;
import org.opendof.tools.repository.interfaces.opendof.saxParser.event.EventsElement;
import org.opendof.tools.repository.interfaces.opendof.saxParser.exception.ExceptionsElement;
import org.opendof.tools.repository.interfaces.opendof.saxParser.method.MethodsElement;
import org.opendof.tools.repository.interfaces.opendof.saxParser.property.PropertiesElement;
import org.opendof.tools.repository.interfaces.opendof.saxParser.typedef.TypedefsElement;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * The top level Interface Element handler
 */
@SuppressWarnings("javadoc")
public class InterfaceElement extends OpenDofSaxHandler
{
    public static final String CatalogIrSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/interface-repository.xsd";
    public static final String CatalogIrMetaSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/interface-repository-meta.xsd";
    public static final String CatalogNamespaceSchemaLocation = "/ws2/interface-repository-servlet/WebContent/catalog/opendof/xml2009.xsd";

    public static final String InterfaceNamespace = "http://opendof.org/schema/interface-repository";
    public static final String InterfaceMetaNamespace = "http://opendof.org/schema/interface-repository-meta";
    public static final String InterfaceSchemaLocation = "http://opendof.org/schema/interface-repository.xsd";
    public static final String W3SchemaNamespace = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String W3Namespace = "http://www.w3.org/XML/1998/namespace";
    public static final String W3NamespaceLocation = "http://www.w3.org/2001/xml.xsd";

    public static final String InterfaceElement = "interface";
    //    public static final String VersionAttr = "version";
    public static final String Version = "1.0.0";
    public static final String IidAttr = "iid";
    public static String Header1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    //@formatter:off
    public static String Header2 = 
                    "<"+
                    InterfaceElement +
                    " xmlns=\"" + InterfaceNamespace + "\"" +
                    " xmlns:md=\"" + InterfaceMetaNamespace + "\"" +
                    " xmlns:xsi=\"" + W3SchemaNamespace + "\"" +
                    " xsi:schemaLocation=\"" + 
                    InterfaceNamespace + " " + InterfaceSchemaLocation + " " +
                    "\"" +
//                    " " + VersionAttr + "=\"" + Version + "\"" +
                    " " + IidAttr + "=\"";
    //@formatter:on

    public static final String ItemIdAttr = "item-id";
    public static final String UrlAttr = "url";

    private static final String TypedefsPath = InterfaceElement + "." + TypedefsElement.TypeDefsElement;
    private static final String PropertiesPath = InterfaceElement + "." + PropertiesElement.PropertiesElement;
    private static final String MethodsPath = InterfaceElement + "." + MethodsElement.MethodsElement;
    private static final String EventsPath = InterfaceElement + "." + EventsElement.MyTag;
    private static final String ExceptionsPath = InterfaceElement + "." + ExceptionsElement.MyTag;

    private DOFInterfaceID iid;
    private MetadataElements metadata;
    private TypedefsElement typedefs;
    private PropertiesElement properties;
    private MethodsElement methods;
    private EventsElement events;
    private ExceptionsElement exceptions;

    /**
     * Default Interface element constructor 
     */
    public InterfaceElement()
    {
        this.iid = null;
        this.metadata = null;
        this.typedefs = null;
        this.properties = null;
        this.methods = null;
        this.events = null;
        this.exceptions = null;
    }

    //@formatter:off
    public InterfaceElement(
                    DOFInterfaceID iid,
                    MetadataElements metadata,
                    TypedefsElement typedefs,
                    PropertiesElement properties,
                    MethodsElement methods,
                    EventsElement events,
                    ExceptionsElement exceptions)
                    //@formatter:on
    {
        this.iid = iid;
        this.metadata = metadata;
        this.typedefs = typedefs;
        this.properties = properties;
        this.methods = methods;
        this.events = events;
        this.exceptions = exceptions;
    }

    public synchronized DOFInterfaceID getIid()
    {
        return iid;
    }

    public synchronized void setIid(DOFInterfaceID iid)
    {
        this.iid = iid;
    }

    public synchronized MetadataElements getMetadata()
    {
        return metadata;
    }

    public synchronized void setMetadata(MetadataElements metadata)
    {
        this.metadata = metadata;
    }

    public synchronized TypedefsElement getTypedefs()
    {
        return typedefs;
    }

    public synchronized void setTypedefs(TypedefsElement typedefs)
    {
        this.typedefs = typedefs;
    }

    public synchronized PropertiesElement getProperties()
    {
        return properties;
    }

    public synchronized void setProperties(PropertiesElement properties)
    {
        this.properties = properties;
    }

    public synchronized MethodsElement getMethods()
    {
        return methods;
    }

    public synchronized void setMethods(MethodsElement methods)
    {
        this.methods = methods;
    }

    public synchronized EventsElement getEvents()
    {
        return events;
    }

    public synchronized void setEvents(EventsElement events)
    {
        this.events = events;
    }

    public synchronized ExceptionsElement getExceptions()
    {
        return exceptions;
    }

    public synchronized void setExceptions(ExceptionsElement exceptions)
    {
        this.exceptions = exceptions;
    }

    /**
     * Validate that this Interface element meets OpenDOF requirements
     * @throws SAXException if the validation fails.
     */
    public void validate() throws SAXException
    {
        if (stack.size() != 0)
            throw new SAXException("Interface complete but stack is not zero");
        if (metadata == null)
        {
            if (OpenDofSaxHandler.isPublish())
                throw new SAXException("interface.metadata is null");
            LoggerFactory.getLogger(getClass()).warn("interface.metadata is null");
        } else
            metadata.validate(true, true, true, "interface");

        if (typedefs == null)
            throw new SAXException("interface.typedefs is null");
        typedefs.validate();
        Hashtable<Integer, Integer> uniqueMap = new Hashtable<Integer, Integer>();
        if (properties != null)
        {
            properties.validate(typedefs);
            properties.uniqueItemId(uniqueMap);
        }
        if (methods != null)
        {
            methods.validate(typedefs);
            methods.uniqueItemId(uniqueMap);
        }
        if (events != null)
        {
            events.validate(typedefs);
            events.uniqueItemId(uniqueMap);
        }
        if (exceptions != null)
        {
            exceptions.validate(typedefs);
            exceptions.uniqueItemId(uniqueMap);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (stack.size() == 0)
        {
            stack.push(localName);
            if (!isMyTag(InterfaceElement))
                throw new SAXException("did not find element: " + InterfaceElement);
            int size = attributes.getLength();
            if (size < 1)
                throw new SAXException(InterfaceElement + " expects 1 attributes: " + IidAttr);
            int index = attributes.getIndex(IidAttr);
            if (index < 0)
                throw new SAXException(InterfaceElement + " did not find the attribute " + IidAttr);
            synchronized (this)
            {
                iid = DOFInterfaceID.create(attributes.getValue(index));
            }
            return;
        }
        if (stack.peek().equals(InterfaceElement))
        {
            // the Interface level can contain:
            // Metadata, TypeDefs, Properties, Methods, Events, Exceptions, Externals, Parent, Siblings
            //The stack here is: "interface"
            if (localName.equals(TypedefsElement.TypeDefsElement))
            {
                if (typedefs == null)
                {
                    synchronized (this)
                    {
                        typedefs = new TypedefsElement();
                    }
                    typedefs.startElement(uri, localName, qName, attributes);
                    return;
                }
                throw new SAXException(currentPath() + " startElement two typedefs elements ");
            }
            if (localName.equals(PropertiesElement.PropertiesElement))
            {
                if (properties == null)
                {
                    synchronized (this)
                    {
                        properties = new PropertiesElement();
                    }
                    properties.startElement(uri, localName, qName, attributes);
                    return;
                }
                throw new SAXException(currentPath() + " startElement two properties elements ");
            }
            if (localName.equals(MethodsElement.MethodsElement))
            {
                if (methods == null)
                {
                    synchronized (this)
                    {
                        methods = new MethodsElement();
                    }
                    methods.startElement(uri, localName, qName, attributes);
                    return;
                }
                throw new SAXException(currentPath() + " startElement two Methods elements ");
            }
            if (localName.equals(EventsElement.EventsElement))
            {
                if (events == null)
                {
                    synchronized (this)
                    {
                        events = new EventsElement();
                    }
                    events.startElement(uri, localName, qName, attributes);
                    return;
                }
                throw new SAXException(currentPath() + " startElement two Events elements ");
            }
            if (localName.equals(ExceptionsElement.ExceptionsElement))
            {
                if (exceptions == null)
                {
                    synchronized (this)
                    {
                        exceptions = new ExceptionsElement();
                    }
                    exceptions.startElement(uri, localName, qName, attributes);
                    return;
                }
                throw new SAXException(currentPath() + " startElement two Exception elements ");
            }
            if (MetadataElements.oneOfYours(localName))
            {
                if (metadata == null)
                {
                    synchronized (this)
                    {
                        metadata = new MetadataElements();
                    }
                }
                metadata.startElement(uri, localName, qName, attributes);
                return;
            }
            throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
        }

        /* *************
         * third level calls
         */
        //The stack here is: "interface,<second level>"
        if (currentPath().startsWith(TypedefsPath))
        {
            //The stack here is: "interface,typedefs,..."
            typedefs.startElement(uri, localName, qName, attributes);
            return;
        }
        if (currentPath().startsWith(PropertiesPath))
        {
            //The stack here is: "interface,properties,..."
            properties.startElement(uri, localName, qName, attributes);
            return;
        }
        if (currentPath().startsWith(MethodsPath))
        {
            //The stack here is: "interface,methods,..."
            methods.startElement(uri, localName, qName, attributes);
            return;
        }
        if (currentPath().startsWith(EventsPath))
        {
            //The stack here is: "interface,events,..."
            events.startElement(uri, localName, qName, attributes);
            return;
        }
        if (currentPath().startsWith(ExceptionsPath))
        {
            //The stack here is: "interface,exceptions,..."
            exceptions.startElement(uri, localName, qName, attributes);
            return;
        }
        throw new SAXException(currentPath() + " startElement unknown localName for this level: " + localName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException
    {
        if (isMyTag(InterfaceElement))
        {
            String value = new String(ch, start, length);
            throw new SAXException(currentPath() + " characters unexpected: " + value);
        }
        String current = stack.peek();
        if (currentPath().startsWith(TypedefsPath))
            typedefs.characters(ch, start, length);
        else if (currentPath().startsWith(PropertiesPath))
            properties.characters(ch, start, length);
        else if (currentPath().startsWith(MethodsPath))
            methods.characters(ch, start, length);
        else if (currentPath().startsWith(EventsPath))
            events.characters(ch, start, length);
        else if (currentPath().startsWith(ExceptionsPath))
            exceptions.characters(ch, start, length);
        else if (currentPath().startsWith(InterfaceElement) && MetadataElements.oneOfYours(current))
            metadata.characters(ch, start, length);
        else
        {
            String value = new String(ch, start, length);
            throw new SAXException(currentPath() + " characters unknown current stack: " + value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals(InterfaceElement))
        {
            stack.pop();
            validate();
            return;
        }
        String current = stack.peek();
        if (currentPath().startsWith(TypedefsPath))
            typedefs.endElement(uri, localName, qName);
        else if (currentPath().startsWith(PropertiesPath))
            properties.endElement(uri, localName, qName);
        else if (currentPath().startsWith(MethodsPath))
            methods.endElement(uri, localName, qName);
        else if (currentPath().startsWith(EventsPath))
            events.endElement(uri, localName, qName);
        else if (currentPath().startsWith(ExceptionsPath))
            exceptions.endElement(uri, localName, qName);
        else if (currentPath().startsWith(InterfaceElement) && MetadataElements.oneOfYours(current))
            metadata.endElement(uri, localName, qName);
        else
            throw new SAXException(currentPath() + " endElement unknown stack path for localName: " + localName);
    }

    public class Sibling
    {
        public final String url;
        public String name;

        private Sibling(String url)
        {
            this.url = url;
        }

        public void validate() throws SAXException
        {
            if (url == null || url.length() == 0)
                throw new SAXException("Sibling.url is null or empty");
            if (name == null || name.length() == 0)
                throw new SAXException("Sibling.name is null or empty");
        }

        public boolean validPublish(Sibling other)
        {
            if (!checkEquals(url, other.url))
                return false;
            if (!checkEquals(name, other.name))
                return false;
            return true;
        }
    }

    /**
     * Export the whole Interface as XML
     * @param pretty <cr/lf> and indents added if true.
     * @return the XML string.
     */
    public String export(boolean pretty)
    {
        synchronized (this)
        {

            OpenDofSaxHandler.pretty = pretty;
            int level = 0;
            StringBuilder sb = new StringBuilder(Header1);
            if (pretty)
                sb.append("\n");
            sb.append(Header2);
            sb.append(iid.toStandardString()).append("\"");
            closer(sb, null, CType.Open, true);
            ++level;
            if (metadata != null)
                metadata.export(sb, level);
            if (typedefs != null && typedefs.getTypeDefs().size() > 0)
                typedefs.export(sb, level);
            if (properties != null && properties.getProperties().size() > 0)
                properties.export(sb, level);
            if (methods != null && methods.getMethods().size() > 0)
                methods.export(sb, level);
            if (events != null && events.getEvents().size() > 0)
                events.export(sb, level);
            if (exceptions != null && exceptions.getExceptions().size() > 0)
                exceptions.export(sb, level);
            --level;
            element(sb, InterfaceElement, CType.Close, level);
            return sb.toString();
        }
    }

    public boolean validPublish(InterfaceElement ifaceE)
    {
        synchronized (this)
        {
            if (!iid.equals(ifaceE.iid))
                return false;
            if ((typedefs == null && ifaceE.typedefs != null) || (typedefs != null && !typedefs.validPublish(ifaceE.typedefs)))
                return false;
            if ((properties == null && ifaceE.properties != null) || (properties != null && !properties.validPublish(ifaceE.properties)))
                return false;
            if ((methods == null && ifaceE.methods != null) || (methods != null && !methods.validPublish(ifaceE.methods)))
                return false;
            if ((events == null && ifaceE.events != null) || (events != null && !events.validPublish(ifaceE.events)))
                return false;
            if ((exceptions == null && ifaceE.exceptions != null) || (exceptions != null && !exceptions.validPublish(ifaceE.exceptions)))
                return false;
            return true;
        }
    }
}
