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

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.oauth.events.EventListener;

@SuppressWarnings("javadoc")
public interface CommsEventListener extends EventListener
{
	public void crestUp(CrestClientInfo clientInfo);
	public void crestDown(CrestClientInfo clientInfo);
	public void xmlUp(CrestClientInfo clientInfo);
    public void xmlDown(CrestClientInfo clientInfo);
	
	public enum Type{CrestUp, CrestDown, XmlUp, XmlDown}
}
