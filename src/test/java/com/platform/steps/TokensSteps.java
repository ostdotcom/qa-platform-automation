package com.platform.steps;

import com.google.gson.JsonObject;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.HashMap;

public class TokensSteps extends Base_API {


    @When("^I make GET request to get token information$")
    public void get_tokens() {

        tokensService = services.tokens;
        HashMap <String,Object> params = new HashMap<String,Object>();
        try {
            response = tokensService.get( params );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^Verify the origin and auxiliary chain id$")
    public void verify_origin_aux_chainId() {
        int aux_chain_id = TestDataManager.economy1.aux_chain_id;
        int origin_chain_id = TestDataManager.economy1.origin_chain_id;
        Assert.assertEquals("Auxiliary chain ID",aux_chain_id,ResultDriver.get_auxiliary_chain_id(response));
        Assert.assertEquals("Origin chain ID",origin_chain_id,ResultDriver.get_origin_chain_id(response));
    }
}
