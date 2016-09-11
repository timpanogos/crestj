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
package com.ccc.crest.core.cache.crest.schema.option;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.ccc.tools.TabToLevel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@SuppressWarnings("javadoc")
public class Representation implements JsonDeserializer<Representation>
{
    public volatile String versionStr;
    public volatile AcceptType acceptType;
    public volatile String verb;
    public volatile long version;
    public volatile boolean thirdParty;

    private static final String AcceptVersionStrKey = "version_str";
    private static final String AcceptTypeKey = "acceptType";
    private static final String VerbKey = "verb";
    private static final String VersionKey = "version";
    private static final String ThirdPartyKey = "thirdParty";

    @Override
    public Representation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Iterator<Entry<String, JsonElement>> objectIter = ((JsonObject) json).entrySet().iterator();
        while (objectIter.hasNext())
        {
            Entry<String, JsonElement> objectEntry = objectIter.next();
            String key = objectEntry.getKey();
            JsonElement value = objectEntry.getValue();
            if (AcceptVersionStrKey.equals(key))
                versionStr = value.getAsString();
            else if (AcceptTypeKey.equals(key))
            {
                acceptType = new AcceptType();
                acceptType.deserialize(value, typeOfT, context);

            } else if (VerbKey.equals(key))
                verb = value.getAsString();
            else if (VersionKey.equals(key))
                version = value.getAsLong();
            else if (ThirdPartyKey.equals(key))
                thirdParty = value.getAsBoolean();
            else
                LoggerFactory.getLogger(getClass()).warn(key + " has a field not currently being handled: \n" + objectEntry.toString());
        }
        return this;
    }

    @Override
    public String toString()
    {
        TabToLevel format = new TabToLevel();
        return toString(format).toString();
    }

    public TabToLevel toString(TabToLevel format)
    {
        format.ttl(getClass().getSimpleName());
        format.inc();
        format.ttl("versionStr: ", versionStr);
        format.ttl("verb: ", verb);
        format.ttl("version: ", version);
        format.ttl("thirdParty: ", thirdParty);
        if (acceptType == null)
            format.ttl("acceptType: null");
        else
        {
            format.ttl("acceptType: ");
            format.inc();
            acceptType.toString(format);
            format.dec();
        }
        format.dec();
        return format;
    }
}
