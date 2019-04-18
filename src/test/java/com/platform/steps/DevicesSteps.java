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

public class DevicesSteps extends Base_API {

    EthAddress ethAddress = new EthAddress();
    String deviceAddress;


    @When("^I make POST request to create device with defined user$")
    public void create_device() {

        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        try {
            response = devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }


    @And("^I should get newly created device details$")
    public void get_device_address() {
        devicesService = services.devices;
        deviceAddress = DevicesDriver.get_device_address(response);
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);
        JsonObject response = null;
        try {
            response = devicesService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to create device with user as (.+)$")
    public void create_device_with_userId(String userId) {
        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        System.out.println(params);
        try {
            response = devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to create device with device address as (.+)$")
    public void create_device_with_device_address(String deviceAddress) {
        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",deviceAddress );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        System.out.println();
        System.out.println(params);

        try {
            response = devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to create device with api signer address as (.+)$")
    public void create_device_with_api_signer(String api_signer_address) {
        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", api_signer_address);
        System.out.println(params);
        try {
            response = devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get user device details for defined user and device address$")
    public void get_device_with_user_device_address() {
        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            response = devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^Device status should be (.+)$")
    public void get_device_status(String deviceStatus) {
        Assert.assertEquals(deviceStatus,DevicesDriver.get_device_status(response));
    }

    @When("^I make GET request to get device details with user id as (.+)$")
    public void get_device_with_userID(String userId) {

        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userId);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            response = devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get device with device address as (.+)$")
    public void get_device_with_device_address(String deviceAddress) {

        devicesService = services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);

        System.out.println(params);
        try {
            response = devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get device lists for a defined user$")
    public void get_devices_list() {
        devicesService = services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        System.out.println(params);
        try {
            response = devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get device lists with user id as (.+)$")
    public void get_device_list_with_userID(String userID) {
        devicesService = services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userID);
        System.out.println(params);
        try {
            response = devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }


    @When("^I make GET request to get device list with limit as (.+)$")
    public void get_device_list_with_limit(Object limit) {
        devicesService = services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("limit",limit);
        System.out.println(params);
        try {
            response = devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get the device list with (.+) members$")
    public void verify_list_with_limit(int expected_limit) {
        int actual_limit= DevicesDriver.get_list_number_of_devices(response);
        if(expected_limit==actual_limit)
        {
            Assert.assertTrue(true);
        }
        else
        {
            if(ResultDriver.pagination_identifier_present(response))
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
    public static void get_device_list_with_pagination_identifier(String pagination_identifier) {
        devicesService = services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("pagination_identifier",pagination_identifier);
        try {
            response = devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get devices list with defined devices address array$")
    public void get_devices_list_with_address_array() {
        devicesService = services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        ArrayList<Object> addressesArray = new ArrayList<Object>();
        addressesArray.add("0x5906ae461eb6283cf15b0257d3206e74d83a6bd4");
        addressesArray.add("0xab248ef66ee49f80e75266595aa160c8c1abdd5a");
        params.put("addresses", addressesArray);

        try {
            response = devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }
}
