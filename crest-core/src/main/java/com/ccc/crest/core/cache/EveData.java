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
package com.ccc.crest.core.cache;

import com.ccc.crest.core.cache.crest.Paging;
import com.ccc.tools.RequestThrottle;

@SuppressWarnings("javadoc")
public interface EveData extends EveJsonData
{
    public void init();
    public void setCacheTimeInSeconds(int seconds);
    public void setNextRefresh(long time);
    public int getCacheTimeInSeconds();
    public RequestThrottle getThrottle(int seconds);
    public long getLastAccessed();
    public void accessed();
    public long getLastRefreshed();
    public void refreshed();
    public boolean isContinueRefresh();
    public void setContinueRefresh(boolean value);
    public boolean isFromCrest();
    public void setFromCrest(boolean value);
    public Paging getPaging();
}
