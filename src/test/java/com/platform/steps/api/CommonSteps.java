package com.platform.steps.api;

import com.ost.OSTSDK;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;



import java.util.HashMap;

public class CommonSteps {

    public static Scenario scenario;
    private Base_API base;

    public CommonSteps(Base_API base) {
        this.base = base;
    }

    ResultDriver resultDriver = new ResultDriver();

    @Given("The Economy is up for actions")
    public void initialize_economy() {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint",TestDataManager.data.apiEndpoint);
        sdkConfig.put("apiKey",TestDataManager.economy1.apiKey);
        sdkConfig.put("apiSecret",TestDataManager.economy1.secretKey);

        base.ostObj = new OSTSDK(sdkConfig);
        base.services = (com.ost.services.Manifest) base.ostObj.services;
    }

    public void initialize_economy(String apiEndpoint, String apiKey, String apiSecret) {

        HashMap<String,Object> sdkConfig = new HashMap<String,Object>();
        sdkConfig.put("apiEndpoint",apiEndpoint);
        sdkConfig.put("apiKey",apiKey);
        sdkConfig.put("apiSecret",apiSecret);

        base.ostObj = new OSTSDK(sdkConfig);
        base.services = (com.ost.services.Manifest) base.ostObj.services;
    }

    @Then("I should get success status as (.+)")
    public void verify_success_status(String successStatus) {
        Assert.assertEquals("API success status: ",successStatus,resultDriver.get_success_status(base.response)+"");
    }

    @And("^Response should be expected as the defined JSON schema$")
    public void verify_json_schema() {
        resultDriver.verify_json_schema(base.response);
    }

    @And("^I should get error code as (.+)$")
    public void get_error_code(String error_code) {
        Assert.assertEquals("Error code of API base.response",error_code,resultDriver.get_error_code(base.response));
    }

    @Then("^I should get all the (.+) list till last page with pagination identifier$")
    public void  get_list_till_last_with_pagination_identifier(String list_type) {

        String pagination_identifier;

        switch (list_type)
        {
            case Constant.RESULT_TYPE.USERS:
                while (resultDriver.pagination_identifier_present(base.response))
                {
                    pagination_identifier = resultDriver.get_pagination_identifier(base.response);
                    new UsersSteps(base).get_user_list_with_pagination_identifier(pagination_identifier);
                }
                break;

            case Constant.RESULT_TYPE.DEVICES:
                while (resultDriver.pagination_identifier_present(base.response))
                {
                    pagination_identifier = resultDriver.get_pagination_identifier(base.response);
                    new DevicesSteps(base).get_device_list_with_pagination_identifier(pagination_identifier);
                }
                break;

            case Constant.RESULT_TYPE.SESSIONS:
                while (resultDriver.pagination_identifier_present(base.response))
                {
                    pagination_identifier = resultDriver.get_pagination_identifier(base.response);
                    new SessionSteps(base).get_sessions_list_with_pagination_identifier(pagination_identifier);
                }
                break;

            default:
                throw new AssertionError("Result type is not valid for pagination identifier");
        }
    }
}
