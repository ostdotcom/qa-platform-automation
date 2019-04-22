package com.platform.steps;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class ChainSteps extends Base_API {



    @When("^I make GET request to get chain details of auxiliary chain$")
    public void get_chain_details_aux() {

        chainsService = services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.aux_chain_id);
        try {
            response = chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

    }

    @When("^I make GET request to get chain details of origin chain$")
    public void get_chain_details_origin() {

        chainsService = services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.origin_chain_id);
        try {
            response = chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get chain details with chain as (.+)$")
    public void get_chain_details_with_chain_id(String chainId) {

        chainsService = services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", chainId);
        try {
            response = chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

    }
}
