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
public class SharedRight implements Right
{
    public final String capsuleer;
    public final String capsuleer2;
    public final Type type;

    public SharedRight(String capsuleer, String capsuleer2, Type type)
    {
        this.capsuleer = capsuleer;
        this.capsuleer2 = capsuleer2;
        this.type = type;
    }

    public enum Type
    {
        Read, Write, ReadWrite;

        public static Type getType(int type)
        {
            switch(type)
            {
                case 0:
                    return Read;
                case 1:
                    return Write;
                case 2:
                    return Type.ReadWrite;
                default:
                    throw new RuntimeException("Invalid SharedRight.Type: " + type);
            }
        }
    }
}
