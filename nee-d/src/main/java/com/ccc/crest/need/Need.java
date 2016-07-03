package com.ccc.crest.need;

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
import com.ccc.tools.RequestThrottle;

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
    
    private EveApi eveApi;
    private final ApiAuthorization auth;
    private final RequestThrottle requestThrottle;
    
    public Need()
    {
        //https://api.eveonline.com/eve/CharacterID.xml.aspx?names=Salgare
        auth = new ApiAuthorization(timp0KeyId, NehorId, timp0KeyVcode);
        eveApi = new EveApi(auth);
        EveApi.setConnector(new ApiConnector(ApiConnector.EVE_API_URL));
        requestThrottle = new RequestThrottle(30);
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
            System.out.println("look here");
        } catch (ApiException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    public static void main(String[] args)
    {
        Need need = new Need();
        need.run();
    }
}
