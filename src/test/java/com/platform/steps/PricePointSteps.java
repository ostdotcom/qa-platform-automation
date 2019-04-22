package com.platform.steps;


import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;


public class PricePointSteps extends Base_API {



    @When("^I make GET request to get price point details with auxiliary chain id$")
    public static void get_price_point_with_aux_chain_id() {

        pricePointsService = services.pricePoints;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.aux_chain_id);
        try {
            response = pricePointsService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }


    @When("^I make GET request to get price point detail with chain id as (.+)$")
    public void get_price_point_with_chain_id(String chainId) {
        pricePointsService = services.pricePoints;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", chainId);
        try {
            response = pricePointsService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }
}
