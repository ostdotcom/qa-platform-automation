package com.platform.steps;

import com.google.gson.JsonObject;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.DevicesDriver;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import com.platform.utils.EthAddress;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DevicesSteps {

    EthAddress ethAddress = new EthAddress();
    String deviceAddress;

    DevicesDriver devicesDriver = new DevicesDriver();
    ResultDriver resultDriver = new ResultDriver();


    private Base_API base;

    public DevicesSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make POST request to create device with defined user$")
    public void create_device() {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }


    @And("^I should get newly created device details$")
    public void get_device_address() {
        base.devicesService = base.services.devices;
        deviceAddress = devicesDriver.get_device_address(base.response);
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);
//        JsonObject base.response = null;
        try {
            base.response = base.devicesService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to create device with user as (.+)$")
    public void create_device_with_userId(String userId) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to create device with device address as (.+)$")
    public void create_device_with_device_address(String deviceAddress) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",deviceAddress );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        System.out.println();
        System.out.println(params);

        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to create device with api signer address as (.+)$")
    public void create_device_with_api_signer(String api_signer_address) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", api_signer_address);
        System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get user device details for defined user and device address$")
    public void get_device_with_user_device_address() {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^Device status should be (.+)$")
    public void get_device_status(String deviceStatus) {
        Assert.assertEquals(deviceStatus,devicesDriver.get_device_status(base.response));
    }

    @When("^I make GET request to get device details with user id as (.+)$")
    public void get_device_with_userID(String userId) {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userId);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get device with device address as (.+)$")
    public void get_device_with_device_address(String deviceAddress) {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get device lists for a defined user$")
    public void get_devices_list() {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        System.out.println(params);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get device lists with user id as (.+)$")
    public void get_device_list_with_userID(String userID) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userID);
        System.out.println(params);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }


    @When("^I make GET request to get device list with limit as (.+)$")
    public void get_device_list_with_limit(Object limit) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("limit",limit);
        System.out.println(params);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I should get the device list with (.+) members$")
    public void verify_list_with_limit(int expected_limit) {
        int actual_limit= devicesDriver.get_list_number_of_devices(base.response);
        if(expected_limit==actual_limit)
        {
            Assert.assertTrue(true);
        }
        else
        {
            if(resultDriver.pagination_identifier_present(base.response))
            {
                Assert.fail("Limit is not validate");
            }
            else
            {
                System.out.println("Limit is not matching. This is because Economy might not have these many members created");
            }
        }
    }

    @When("^I make GET request to get device list with pagination identifier as (.+)$")
    public void get_device_list_with_pagination_identifier(String pagination_identifier) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("pagination_identifier",pagination_identifier);
        try {
            base.response = base.devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get devices list with defined devices address array$")
    public void get_devices_list_with_address_array() {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        ArrayList<Object> addressesArray = new ArrayList<Object>();
        addressesArray.add(TestDataManager.economy1.device_address);
        params.put("addresses", addressesArray);

        try {
            base.response = base.devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }
}
