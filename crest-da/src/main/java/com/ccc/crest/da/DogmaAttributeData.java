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
package com.ccc.crest.da;

@SuppressWarnings("javadoc")
public class DogmaAttributeData
{
    public final long id;
    public final String name;
    public final String description;
    public final String corpUrl;
    public final String loyaltyUrl;
    public final String headquartersName;
    public final String headquartersUrl;
    public final int page;
    
    public DogmaAttributeData(long id, String ticker, String name, String description, String corpUrl, String loyaltyUrl, String hqName, String hqUrl, int page)
    {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.corpUrl = corpUrl;
        this.loyaltyUrl = loyaltyUrl;
        this.headquartersName = hqName;
        this.headquartersUrl = hqUrl;
        this.page = page;
    }
}
