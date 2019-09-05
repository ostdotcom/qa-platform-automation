package com.platform.steps.api;


import com.google.gson.GsonBuilder;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;


public class PricePointSteps {


    private Base_API base;

    public PricePointSteps(Base_API base) {
        this.base = base;
    }

    @When("^I make GET request to get price point details with auxiliary chain id$")
    public void get_price_point_with_aux_chain_id() {

        base.pricePointsService = base.services.pricePoints;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.aux_chain_id);
        try {
            base.response = base.pricePointsService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    @When("^I make GET request to get price point detail with chain id as (.+)$")
    public void get_price_point_with_chain_id(String chainId) {
        base.pricePointsService = base.services.pricePoints;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", chainId);
        try {
            base.response = base.pricePointsService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }
}
