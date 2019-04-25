package com.platform.steps;

import com.ost.OSTSDK;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class ApiCredentialsSteps extends Base_API {


    @Given("^The economy with wrong API key is setup$")
    public void setup_economy_with_wrong_API_key() {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint",TestDataManager.data.apiEndpoint);
        sdkConfig.put("apiKey","vge5y5v5y5yb5y5y5wbby");
        sdkConfig.put("apiSecret",TestDataManager.economy1.secretKey);

        ostObj = new OSTSDK(sdkConfig);
        services = (com.ost.services.Manifest) ostObj.services;
    }

    @Given("^The economy with wrong Secret key is setup$")
    public void setup_economy_with_wrong_secret_key() {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint",TestDataManager.data.apiEndpoint);
        sdkConfig.put("apiKey",TestDataManager.economy1.apiKey);
        sdkConfig.put("apiSecret","ckef09r4vj404v4m0t5t059tmvtu5my2vy06uy");

        ostObj = new OSTSDK(sdkConfig);
        services = (com.ost.services.Manifest) ostObj.services;
    }

    @Given("^The economy with wrong api endpoint is setup$")
    public void setup_economy_with_wrong_api_endpoint() {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint","https://s8-api.stagingost.com/testnet/v2/");
        sdkConfig.put("apiKey",TestDataManager.economy1.apiKey);
        sdkConfig.put("apiSecret",TestDataManager.economy1.secretKey);

        ostObj = new OSTSDK(sdkConfig);
        services = (com.ost.services.Manifest) ostObj.services;
    }
}
