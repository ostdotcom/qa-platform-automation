package com.platform.steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ost.OSTSDK;
import com.platform.Base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.EconomyManager;
import com.platform.managers.TestDataManager;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.model.CucumberScenario;
import net.masterthought.cucumber.generators.OverviewReport;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class CommonSteps extends Base_API {



    @Given("^The Economy is up for actions$")
    public void initialize_economy() {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint",TestDataManager.data.apiEndpoint);
        sdkConfig.put("apiKey",TestDataManager.data.economy.get(0).apiKey);
        sdkConfig.put("apiSecret",TestDataManager.data.economy.get(0).secretKey);

        ostObj = new OSTSDK(sdkConfig);
        services = (com.ost.services.Manifest) ostObj.services;
    }

    @Then("^I should get success status as (.+)$")
    public void verify_success_status(boolean successStatus) {
        Assert.assertEquals(successStatus, ResultDriver.get_success_status(response));
    }

    @And("^Response should be expected as the defined JSON schema$")
    public void verify_json_schema() {
        ResultDriver.verify_json_schema(response);
    }
}
