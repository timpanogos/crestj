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
package com.ccc.crest.need;

import org.slf4j.LoggerFactory;

import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.parser.ApiAuthorization;
import com.beimin.eveapi.parser.corporation.ContractItemsParser;
import com.beimin.eveapi.parser.corporation.ContractsParser;
import com.beimin.eveapi.parser.pilot.CharacterSheetParser;
import com.beimin.eveapi.parser.pilot.ContractBidsParser;
import com.beimin.eveapi.response.pilot.CharacterSheetResponse;
import com.beimin.eveapi.response.shared.ContractBidsResponse;
import com.beimin.eveapi.response.shared.ContractItemsResponse;
import com.beimin.eveapi.response.shared.ContractsResponse;

@SuppressWarnings("javadoc")
public class Need
{
    private static final long NehorId = 95293368;
    private static final long SalgareId = 1364371482;
    private static final int timp0KeyId = 4430356;
    private static final int timp1KeyId = 4430397;
    private static final int timp2KeyId = 4444632;
    private static final String timp0KeyVcode = "FadaSJXHfacexKesd01d29YgWOZkHSJiftIMfNymmq3HN4WFUeet3968x57tQn00";
    private static final String timp1KeyVcode = "FadaSJXHfacexKesd11d29YgWOZkHSJiftIMfNymmq3HN4WFUeet3968x57tQn01";
    private static final String timp2KeyVcode = "FadaSJXHfacexKesd21d29YgWOZkHSJiftIMfNymmq3HN4WFUeet3968x57tQn02";

    private static final String UserAgent = "application:Nee-d;contact:cadams@xmission.com";

    private final EveApi eveApi;
    private final ApiAuthorization auth;

    public Need()
    {
        //https://api.eveonline.com/eve/CharacterID.xml.aspx?names=Salgare
        auth = new ApiAuthorization(timp0KeyId, NehorId, timp0KeyVcode);
        eveApi = new EveApi(auth);
        EveApi.setConnector(new ApiConnector(ApiConnector.EVE_API_URL));
    }

    private void run()
    {
        CharacterSheetParser characterSheetParser = new CharacterSheetParser();
        ContractBidsParser contractBidsParser = new ContractBidsParser();
        ContractItemsParser contractItemsParser = new ContractItemsParser();
        ContractsParser contractsParser = new ContractsParser();

        try
        {
            CharacterSheetResponse characterSheetResponse = characterSheetParser.getResponse(auth);
            ContractsResponse contractsResponse = contractsParser.getResponse(auth);
            ContractItemsResponse contractItemsResponse = contractItemsParser.getResponse(auth);
            ContractBidsResponse contractBidsResponse = contractBidsParser.getResponse(auth);
        } catch (ApiException e)
        {
            LoggerFactory.getLogger(getClass()).warn("Need.run failed", e);
        }
    }


    public static void main(String[] args)
    {
        Need need = new Need();
        need.run();
    }
}
