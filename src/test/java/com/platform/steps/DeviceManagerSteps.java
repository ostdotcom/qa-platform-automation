package com.platform.steps;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class DeviceManagerSteps extends Base_API {


    @When("^I make GET request to get device manager details for defined user$")
    public void get_device_manager_defined_user() {

        deviceManagersService = services.deviceManagers;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        try {
            response = deviceManagersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I make GET request to get device manager for the newly created user$")
    public void get_device_manager_with_new_user() {

        String userId = ResultDriver.get_user_id(response)+"";

        deviceManagersService = services.deviceManagers;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userId);
        try {
            response = deviceManagersService.get( params );
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
