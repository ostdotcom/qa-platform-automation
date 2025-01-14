package com.platform.steps.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.drivers.TokenDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.HashMap;

public class TokensSteps  {

    TokenDriver tokenDriver = new TokenDriver();

    private Base_API base;

    public TokensSteps(Base_API base) {
        this.base = base;
    }

    @When("^I make GET request to get token information$")
    public void get_tokens() {

        base.tokensService = base.services.tokens;
        HashMap <String,Object> params = new HashMap<String,Object>();
        try {
            base.response = base.tokensService.get( params );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @And("^Verify the origin and auxiliary chain id$")
    public void verify_origin_aux_chainId() {
        int aux_chain_id = TestDataManager.economy1.aux_chain_id;
        int origin_chain_id = TestDataManager.economy1.origin_chain_id;
        Assert.assertEquals("Auxiliary chain ID",aux_chain_id,tokenDriver.get_auxiliary_chain_id(base.response));
        Assert.assertEquals("Origin chain ID",origin_chain_id,tokenDriver.get_origin_chain_id(base.response));
    }
}
