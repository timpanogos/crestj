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
package com.ccc.crest.client.json.character;

import java.io.Serializable;
import java.util.List;

import com.ccc.crest.client.CrestClient;
import com.ccc.crest.client.CrestResponseCallback;
import com.ccc.crest.client.json.CrestData;
import com.ccc.crest.client.json.Href;
import com.ccc.crest.client.json.Logo;
import com.ccc.crest.client.json.LogoDeserializer;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("javadoc")
public class Contacts implements Serializable, CrestData
{
    private static final long serialVersionUID = 191050364876160103L;
    private static final String Version = "application/vnd.ccp.eve.Api-v3+json";
    
    private static final int CacheTime = 5 * 60;
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
    
    public static void getContacts(CrestClientInfo clientInfo, CrestResponseCallback callback) throws Exception
    {
        StringBuilder url = new StringBuilder();
        //@formatter:off
        url.append(CrestClient.getCrestBaseUri())
        .append(Uri1)
        .append(clientInfo.getVerifyData().CharacterID)
        .append(Uri2);
        //@formatter:on
        
        Gson gson = new GsonBuilder().registerTypeAdapter(Logo.class, new LogoDeserializer()).create();
        //@formatter:off
        CrestClient.getClient().getCrest(
                        clientInfo, url.toString(), 
                        gson, Contacts.class, 
                        callback,
                        CacheTime, ReadScope, Version);
        //@formatter:on
//String json = "{\"totalCount_str\": \"4\", \"items\": [{\"standing\": 10.0, \"character\": {\"name\": \"Nehor\", \"corporation\": {\"name\": \"New Evolution Express\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/98399546/\", \"id_str\": \"98399546\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98399546_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98399546_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98399546_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98399546_256.png\"}}, \"id\": 98399546}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/95293368/\", \"id_str\": \"95293368\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/95293368_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/95293368_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/95293368_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/95293368_256.jpg\"}}, \"id\": 95293368}, \"contact\": {\"id_str\": \"95293368\", \"href\": \"https://crest-tq.eveonline.com/characters/95293368/\", \"name\": \"Nehor\", \"id\": 95293368}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/95293368/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}, {\"standing\": 10.0, \"character\": {\"name\": \"osgi\", \"corporation\": {\"name\": \"Satan's Gut\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/98465079/\", \"id_str\": \"98465079\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_256.png\"}}, \"id\": 98465079}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/93982333/\", \"id_str\": \"93982333\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_256.jpg\"}}, \"id\": 93982333}, \"contact\": {\"id_str\": \"93982333\", \"href\": \"https://crest-tq.eveonline.com/characters/93982333/\", \"name\": \"osgi\", \"id\": 93982333}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/93982333/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}, {\"standing\": 10.0, \"character\": {\"name\": \"r Cubed\", \"corporation\": {\"name\": \"The Night Crew\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/490514892/\", \"id_str\": \"490514892\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_256.png\"}}, \"id\": 490514892}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/758871918/\", \"id_str\": \"758871918\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_256.jpg\"}}, \"id\": 758871918}, \"contact\": {\"id_str\": \"758871918\", \"href\": \"https://crest-tq.eveonline.com/characters/758871918/\", \"name\": \"r Cubed\", \"id\": 758871918}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/758871918/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}, {\"standing\": 10.0, \"character\": {\"name\": \"Shevai Asan\", \"corporation\": {\"name\": \"The Night Crew\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/490514892/\", \"id_str\": \"490514892\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_256.png\"}}, \"id\": 490514892}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/1664456144/\", \"id_str\": \"1664456144\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_256.jpg\"}}, \"id\": 1664456144}, \"contact\": {\"id_str\": \"1664456144\", \"href\": \"https://crest-tq.eveonline.com/characters/1664456144/\", \"name\": \"Shevai Asan\", \"id\": 1664456144}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/1664456144/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}], \"pageCount\": 1, \"pageCount_str\": \"1\", \"totalCount\": 4}";
//        Contacts c = gsonb.create().fromJson(json, Contacts.class);
//        return c;
    }
}

/*
{
    \"totalCount_str\": \"4\", 
    \"items\": [
    {
        \"standing\": 10.0, 
        \"character\": 
        {
            \"name\": \"Nehor\", 
            \"corporation\": 
            {
                \"name\": \"New Evolution Express\", 
                \"isNPC\": false, 
                \"href\": \"https://crest-tq.eveonline.com/corporations/98399546/\", 
                \"id_str\": \"98399546\", 
                \"logo\": 
                {
                    \"32x32\": 
                    {
                        \"href\": \"http://imageserver.eveonline.com/Corporation/98399546_32.png\"
                    }, 
                    \"64x64\": 
                    {
                        \"href\": \"http://imageserver.eveonline.com/Corporation/98399546_64.png\"
                    }, 
                    \"128x128\": 
                    {
                        \"href\": \"http://imageserver.eveonline.com/Corporation/98399546_128.png\"
                    }, 
                    \"256x256\": 
                    {
                        \"href\": \"http://imageserver.eveonline.com/Corporation/98399546_256.png\"
                    }
                }, \"id\": 98399546
            }, 
            \"isNPC\": false, 
            \"href\": \"https://crest-tq.eveonline.com/characters/95293368/\", 
            \"id_str\": \"95293368\", 
            \"portrait\": 
            {
                \"32x32\": 
                {
                    \"href\": \"http://imageserver.eveonline.com/Character/95293368_32.jpg\"
                }, 
                \"64x64\": 
                {
                    \"href\": \"http://imageserver.eveonline.com/Character/95293368_64.jpg\"
                }, 
                \"128x128\": 
                {
                    \"href\": \"http://imageserver.eveonline.com/Character/95293368_128.jpg\"
                }, 
                \"256x256\": 
                {
                    \"href\": \"http://imageserver.eveonline.com/Character/95293368_256.jpg\"
                }
            }, 
            \"id\": 95293368
        }, 
        \"contact\": 
        {
            \"id_str\": \"95293368\", 
            \"href\": \"https://crest-tq.eveonline.com/characters/95293368/\", 
            \"name\": \"Nehor\", \"id\": 95293368
        }, 
        \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/95293368/\", 
        \"contactType\": \"Character\", 
        \"watched\": false, 
        \"blocked\": false
    }, 
    {
        \"standing\": 10.0, \"character\": {\"name\": \"osgi\", \"corporation\": {\"name\": \"Satan's Gut\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/98465079/\", \"id_str\": \"98465079\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/98465079_256.png\"}}, \"id\": 98465079}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/93982333/\", \"id_str\": \"93982333\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/93982333_256.jpg\"}}, \"id\": 93982333}, \"contact\": {\"id_str\": \"93982333\", \"href\": \"https://crest-tq.eveonline.com/characters/93982333/\", \"name\": \"osgi\", \"id\": 93982333}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/93982333/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}, {\"standing\": 10.0, \"character\": {\"name\": \"r Cubed\", \"corporation\": {\"name\": \"The Night Crew\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/490514892/\", \"id_str\": \"490514892\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_256.png\"}}, \"id\": 490514892}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/758871918/\", \"id_str\": \"758871918\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/758871918_256.jpg\"}}, \"id\": 758871918}, \"contact\": {\"id_str\": \"758871918\", \"href\": \"https://crest-tq.eveonline.com/characters/758871918/\", \"name\": \"r Cubed\", \"id\": 758871918}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/758871918/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}, {\"standing\": 10.0, \"character\": {\"name\": \"Shevai Asan\", \"corporation\": {\"name\": \"The Night Crew\", \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/corporations/490514892/\", \"id_str\": \"490514892\", \"logo\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_32.png\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_64.png\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_128.png\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Corporation/490514892_256.png\"}}, \"id\": 490514892}, \"isNPC\": false, \"href\": \"https://crest-tq.eveonline.com/characters/1664456144/\", \"id_str\": \"1664456144\", \"portrait\": {\"32x32\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_32.jpg\"}, \"64x64\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_64.jpg\"}, \"128x128\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_128.jpg\"}, \"256x256\": {\"href\": \"http://imageserver.eveonline.com/Character/1664456144_256.jpg\"}}, \"id\": 1664456144}, \"contact\": {\"id_str\": \"1664456144\", \"href\": \"https://crest-tq.eveonline.com/characters/1664456144/\", \"name\": \"Shevai Asan\", \"id\": 1664456144}, \"href\": \"https://crest-tq.eveonline.com/characters/95217276/contacts/1664456144/\", \"contactType\": \"Character\", \"watched\": false, \"blocked\": false}], \"pageCount\": 1, \"pageCount_str\": \"1\", \"totalCount\": 4}\";
*/
