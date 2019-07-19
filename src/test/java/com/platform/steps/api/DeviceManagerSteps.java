package com.platform.steps.api;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.drivers.UsersDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class DeviceManagerSteps {

    UsersDriver usersDriver = new UsersDriver();

    private Base_API base;

    public DeviceManagerSteps(Base_API base) {
        this.base = base;
    }

    @When("^I make GET request to get device manager details for defined user$")
    public void get_device_manager_defined_user() {

        base.deviceManagersService = base.services.deviceManagers;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        try {
            base.response = base.deviceManagersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I make GET request to get device manager for the newly created user$")
    public void get_device_manager_with_new_user() {

        String userId = usersDriver.get_user_id(base.response)+"";

        base.deviceManagersService = base.services.deviceManagers;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userId);
        try {
            base.response = base.deviceManagersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    public void get_device_manager_with_userId(String user_id) {

        base.deviceManagersService = base.services.deviceManagers;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", user_id);
        try {
            base.response = base.deviceManagersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }
}
