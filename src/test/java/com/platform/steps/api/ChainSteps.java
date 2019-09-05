package com.platform.steps.api;

import com.google.gson.GsonBuilder;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class ChainSteps {


    private Base_API base;

    public ChainSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make GET request to get chain details of auxiliary chain$")
    public void get_chain_details_aux() {

        base.chainsService = base.services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.aux_chain_id);
        try {
            base.response = base.chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");

    }

    @When("^I make GET request to get chain details of origin chain$")
    public void get_chain_details_origin() {

        base.chainsService = base.services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", TestDataManager.economy1.origin_chain_id);
        try {
            base.response = base.chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get chain details with chain as (.+)$")
    public void get_chain_details_with_chain_id(String chainId) {

        base.chainsService = base.services.chains;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("chain_id", chainId);
        try {
            base.response = base.chainsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");

    }
}
