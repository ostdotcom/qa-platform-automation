package com.platform.steps.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.ChainDriver;
import com.platform.drivers.DevicesDriver;
import com.platform.drivers.ResultDriver;
import com.platform.drivers.UsersDriver;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import com.platform.utils.AssertionUtils;
import com.platform.utils.EthAddress;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;



import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class UsersSteps {


    String pagination_identifier;
    EthAddress ethAddress = new EthAddress();
    String userId;
    String spendingLimit = "10000000000000000000";
    Long expiryBlock = Long.valueOf(1000);
    UsersDriver usersDriver = new UsersDriver();
    ResultDriver resultDriver = new ResultDriver();
    DevicesDriver devicesDriver = new DevicesDriver();



    private Base_API base;

    public UsersSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make POST request to create user$")
    public void create_user()  {

        base.usersService = base.services.users;
        HashMap<String,Object> params = new HashMap<>();

        base.scenario.write(params.toString()+"\n");
        try {
            base.response = base.usersService.create(params);
            base.scenario.write(params.toString()+"\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
    }

    @When("^I make GET request to get users list$")
    public void get_users_list() {
        base.usersService = base.services.users;
        HashMap<String,Object> params = new HashMap<>();
        try {
            base.response = base.usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get (.+) details$")
    public void get_user_details(String userType) {
        base.usersService = base.services.users;
        HashMap <String,Object> params = new HashMap<String,Object>();
        if(userType.equalsIgnoreCase("user")) {
            params.put("user_id", TestDataManager.economy1.user_Id);
        }
        else if(userType.equalsIgnoreCase("company user"))
        {
            params.put("user_id", TestDataManager.economy1.company_Id);
        }
        else
        {
            throw new AssertionError("Given user type is invalid. User only type 'user' or 'company'");
        }


        try {
            base.response = base.usersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get user details with (.+)$")
    public void get_user_with_userID(String userID) {

        base.usersService = base.services.users;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userID);
        try {
            base.response = base.usersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            System.out.println("SDK Error");
            base.response = new JsonObject();
            System.out.println(invalidParameter.getMessage());
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^type should be (.+)$")
    public void verify_user_type(String user_type) {
        Assert.assertEquals("User type",user_type, usersDriver.get_user_type(base.response));
    }

    @When("^I make GET request to get users list with limit as (.+)$")
    public void get_user_list_with_limit(Object limit) {

        base.usersService = base.services.users;
        HashMap<String,Object> params = new HashMap<>();
        params.put("limit",limit);
        try {
            base.response = base.usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I should get the user list with (.+) members$")
    public void verify_list_with_limit(int expected_limit) {
        int actual_limit= usersDriver.get_list_number_of_users(base.response);
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
                System.out.println("Limit is not matching. This is because Economy might not have these many users created");
            }
        }
    }


    @When("^I make GET request to get users list with pagination identifier as (.+)$")
    public void get_user_list_with_pagination_identifier(String pagination_identifier) {
        base.usersService = base.services.users;
        HashMap<String,Object> params = new HashMap<>();
        params.put("pagination_identifier",pagination_identifier);
        try {
            base.response = base.usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + base.response.toString() );
    }

    @And("^I should get the unique id of the user$")
    public void get_user_id() {
        Assert.assertNotNull("User ID: ", usersDriver.get_user_id(base.response));
    }

    @And("^User status is (.+)$")
    public void verify_user_status(String status) {
        Assert.assertEquals("Status of New user created: ",status, usersDriver.get_user_status(base.response));
    }

    @And("^Token holder address should be null$")
    public void verify_token_holder_is_null() {
        Assert.assertTrue("Token holder should be",usersDriver.is_token_holder_address_null(base.response));
    }

    @And("^Device manager address should be null$")
    public void verify_device_manager_is_null() {
        Assert.assertTrue("Device Manager should be",usersDriver.is_device_manager_null(base.response));
    }

    @And("^Recovery address should be null$")
    public void verify_recovery_is_null() {
        Assert.assertTrue("Device Manager should be :",usersDriver.is_recovery_address_null(base.response));
    }

    @And("^Recovery Owner address should be null$")
    public void verify_recovery_owner_is_null() {
        Assert.assertTrue("Device Manager should be :",usersDriver.is_recovery_owner_address_null(base.response));
    }

    @And("^User is in registered state$")
    public void user_in_registered_state()  {

        //If user is not created then create a new user
        if(UserData.getInstance().user_id == null)
        {
            create_user();
            UserData.getInstance().user_id = usersDriver.get_user_id(base.response);
        }
        else
        {
            get_user_with_userID(UserData.getInstance().user_id);
            get_user_id();
            if(usersDriver.is_status(Constant.STATUS.CREATED,base.response))
            {

            }
            else
            {
                create_user();
                UserData.getInstance().user_id = usersDriver.get_user_id(base.response);
            }
        }


    }

    @When("^I make POST request to activate user with newly created user$")
    public void post_activate_user() throws IOException {


        System.out.println(UserData.getInstance().user_id);

        //If device is not created then create a new one and assign api signer and private to the UserData object
        if(UserData.getInstance().device_address_public == null)
        {
            DevicesSteps devicesSteps = new DevicesSteps(base);
            devicesSteps.create_device_with_userId(UserData.getInstance().user_id);

            UserData.getInstance().device_address_private = base.deviceAddress.get(Constant.ETH.PRIVATEKEY).getAsString();
            UserData.getInstance().device_address_public = base.deviceAddress.get(Constant.ETH.ADDRESS).getAsString();
            UserData.getInstance().api_signer_private = base.api_signer_object.get(Constant.ETH.PRIVATEKEY).getAsString();
            UserData.getInstance().api_signer_public = base.api_signer_object.get(Constant.ETH.ADDRESS).getAsString();
        }

        if(UserData.getInstance().recovery_owner_add_public==null)
        {
            JsonObject recovery_owner_object =ethAddress.getNewEthKeys();
            UserData.getInstance().recovery_owner_add_public = recovery_owner_object.get(Constant.ETH.ADDRESS).getAsString();
            UserData.getInstance().recovery_owner_add_private = recovery_owner_object.get(Constant.ETH.PRIVATEKEY).getAsString();
        }

        if(UserData.getInstance().session_address_public==null)
        {
            JsonObject recovery_owner_object =ethAddress.getNewEthKeys();
            UserData.getInstance().session_address_public = recovery_owner_object.get(Constant.ETH.ADDRESS).getAsString();
            UserData.getInstance().session_address_private = recovery_owner_object.get(Constant.ETH.PRIVATEKEY).getAsString();
        }



        // Get Chain details to get Current block
        ChainSteps chainSteps = new ChainSteps(base);
        chainSteps.get_chain_details_aux();

        // Get Current block from the above response
        ChainDriver chainDriver = new ChainDriver();
        base.current_block = chainDriver.get_current_block(base.response);

      base.response = usersDriver.postActivateUser(
              Arrays.asList(UserData.getInstance().session_address_public),
              (Long.valueOf(base.current_block)+expiryBlock),
              spendingLimit,
              UserData.getInstance().recovery_owner_add_public,
              UserData.getInstance().device_address_public,
              UserData.getInstance().user_id,
              UserData.getInstance().api_signer_public,
              UserData.getInstance().api_signer_private
              );
      System.out.println(base.response);
    }

    @And("^Token holder should be created$")
    public void get_token_holder() {
        Assert.assertTrue("Token Holder should be not null :", usersDriver.is_token_holder_present(base.response) );
    }

    @When("^I make POST request to activate user with user id as (.+)$")
    public void post_activate_user_with_userId(String userId) throws IOException {

        // Get Chain details to get Current block
        ChainSteps chainSteps = new ChainSteps(base);
        chainSteps.get_chain_details_aux();

        // Get Current block from the above response
        ChainDriver chainDriver = new ChainDriver();
        String current_block = chainDriver.get_current_block(base.response);

        base.response = usersDriver.postActivateUser(
                Arrays.asList(ethAddress.getNewEthAddress()),
                (Long.valueOf(current_block)+expiryBlock),
                spendingLimit,
                ethAddress.getNewEthAddress(),
                base.deviceAddress.get(Constant.ETH.ADDRESS).getAsString(),
                userId,
                base.api_signer_object.get(Constant.ETH.ADDRESS).getAsString(),
                base.api_signer_object.get(Constant.ETH.PRIVATEKEY).getAsString()
        );
        System.out.println(base.response);
    }

    @And("^User is in activated state$")
    public void user_in_activated_state() throws IOException {

        if(UserData.getInstance().user_id == null)
        {
            create_user();
            UserData.getInstance().user_id = usersDriver.get_user_id(base.response);
            post_activate_user();
        }
        else
        {
            get_user_with_userID(UserData.getInstance().user_id);
            if(usersDriver.is_status(Constant.STATUS.ACTIVATED,base.response))
            {

            }
            else
            {
                create_user();
                UserData.getInstance().user_id = usersDriver.get_user_id(base.response);
                post_activate_user();
            }
        }

        wait_till_user_status("ACTIVATED");
    }


    @And("^User status changed to (.+)$")
    public void wait_till_user_status(String status) {

        AssertionUtils.repeatWhenFailedForSeconds(250, ()->
        {
            get_user_with_userID(UserData.getInstance().user_id);
            Assert.assertEquals(true,usersDriver.is_status(Constant.STATUS.ACTIVATED,base.response));
        });
    }
}
