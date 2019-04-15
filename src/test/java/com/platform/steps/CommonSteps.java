package com.platform.steps;

import com.ost.OSTSDK;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

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
        Assert.assertEquals("API success status",successStatus,ResultDriver.get_success_status(response));
    }

    @And("^Response should be expected as the defined JSON schema$")
    public void verify_json_schema() {
        ResultDriver.verify_json_schema(response);
    }

    @And("^I should get error code as (.+)$")
    public void get_error_code(String error_code) {
        Assert.assertEquals("Error code of API response",error_code,ResultDriver.get_error_code(response));
    }

    @Then("^I should get all the (.+) list till last page with pagination identifier$")
    public void get_list_till_last_with_pagination_identifier(String list_type) {

        String pagination_identifier;

        switch (list_type)
        {
            case Constant.RESULT_TYPE.USERS:
                while (ResultDriver.pagination_identifier_present(response))
                {
                    pagination_identifier = ResultDriver.get_pagination_identifier(response);
                    UsersSteps.get_user_list_with_pagination_identifier(pagination_identifier);
                }
                break;

            case Constant.RESULT_TYPE.DEVICES:
                while (ResultDriver.pagination_identifier_present(response))
                {
                    pagination_identifier = ResultDriver.get_pagination_identifier(response);
                    DevicesSteps.get_device_list_with_pagination_identifier(pagination_identifier);
                }
                break;
        }

    }

    public void continueTillLast(String pagination_identifier)
    {

    }
}
