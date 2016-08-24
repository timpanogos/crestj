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
            case XmlUp:
                if(xmlUp.get())
                    return false;
                xmlUp.set(true);
                return true;
            case XmlDown:
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
