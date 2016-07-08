/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.cache.character;

import java.util.List;
import java.util.concurrent.Future;

import com.ccc.crest.cache.BaseEveData;
import com.ccc.crest.cache.CrestRequestData;
import com.ccc.crest.cache.EveData;
import com.ccc.crest.client.CrestClient;
import com.ccc.crest.client.CrestResponseCallback;
import com.ccc.crest.client.json.Href;
import com.ccc.crest.client.json.Logo;
import com.ccc.crest.client.json.LogoDeserializer;
import com.ccc.crest.client.json.character.Item;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("javadoc")
public class ContactList extends BaseEveData
{
    private static final long serialVersionUID = 965041169279751564L;
    
    private static final String Uri1 = "/characters/"; 
    private static final String Uri2 = "/contacts/";
    private static final String ReadScope = "characterContactsRead";
    private static final String WriteScope = "characterContactsWrite";

    public String totalCount_str;
    public int pageCount;
    public List<Item> items;
    public Href next;
    public int totalCount;
    public String pageCount_str;
    
    public static String getApiUrl(CrestClientInfo clientInfo)
    {
        throw new RuntimeException("not implemented yet");
    }
    
    public static String getCrestUrl(CrestClientInfo clientInfo)
    {
        StringBuilder url = new StringBuilder();
        //@formatter:off
        url.append(CrestClient.getCrestBaseUri())
            .append(Uri1)
            .append(clientInfo.getVerifyData().CharacterID)
            .append(Uri2);
        //@formatter:on
        return url.toString();
    }
    
    public static Future<EveData> getContacts(CrestClientInfo clientInfo, CrestResponseCallback callback) throws Exception
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        //@formatter:off
        CrestRequestData rdata = new CrestRequestData(
                        clientInfo, getCrestUrl(clientInfo), 
                        gson, ContactList.class, 
                        callback, 0, 
                        ReadScope, Version);
        //@formatter:on
        return CrestClient.getClient().getCrest(rdata);
    }
}

