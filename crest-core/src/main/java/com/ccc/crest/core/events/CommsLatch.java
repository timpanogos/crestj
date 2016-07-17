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
package com.ccc.crest.core.events;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class CommsLatch
{
    private final AtomicBoolean crestUp;
    private final AtomicBoolean xmlUp;
    public CommsLatch()
    {
        crestUp = new AtomicBoolean(false);
        xmlUp = new AtomicBoolean(false);
    }
    
    public boolean shouldFire(CommsEventListener.Type type)
    {
        switch(type)
        {
            case CrestUp:
                if(crestUp.get())
                    return false;
                crestUp.set(true);
                return true;
            case CrestDown:
                if(!crestUp.get())
                    return false;
                crestUp.set(false);
                return true;
            case XmlDown:
                if(xmlUp.get())
                    return false;
                xmlUp.set(true);
                return true;
            case XmlUp:
                if(!xmlUp.get())
                    return false;
                xmlUp.set(false);
                return true;
            default:
                LoggerFactory.getLogger(getClass()).warn("Unknown CommsEventListener.Type: " + type);
                return true;
        }
    }
    
    public boolean isCrestUp()
    {
        return crestUp.get();
    }
    
    public boolean isXmlUp()
    {
        return xmlUp.get();
    }
}
