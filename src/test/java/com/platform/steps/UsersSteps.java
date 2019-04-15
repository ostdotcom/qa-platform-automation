package com.platform.steps;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.ResultDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;

public class UsersSteps extends Base_API {


    String pagination_identifier;

    @When("^I make POST request to create user$")
    public void post_user() {

        usersService = services.users;
        HashMap<String,Object> params = new HashMap<>();
        try {
            response = usersService.create(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get users list$")
    public void get_users_list() {
        usersService = services.users;
        HashMap<String,Object> params = new HashMap<>();
        try {
            response = usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get (.+) details$")
    public void get_user_details(String userType) {
        usersService = services.users;
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
            response = usersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get user details with (.+)$")
    public void get_user_with_invalid_userID(String userID) {

        usersService = services.users;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userID);
        try {
            response = usersService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            System.out.println("SDK Error");
            System.out.println(invalidParameter.getMessage());
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^type should be (.+)$")
    public void verify_user_type(String user_type) {
        Assert.assertEquals("User type",user_type, ResultDriver.get_user_type(response));
    }

    @When("^I make GET request to get users list with limit as (.+)$")
    public void get_user_list_with_limit(Object limit) {

        usersService = services.users;
        HashMap<String,Object> params = new HashMap<>();
        params.put("limit",limit);
        try {
            response = usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get the user list with (.+) members$")
    public void verify_list_with_limit(int expected_limit) {
        int actual_limit= ResultDriver.get_list_number_of_users(response);
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
                System.out.println("Limit is not matching. This is because Economy might not have these many users created");
            }
        }
    }


    @When("^I make GET request to get users list with pagination identifier as (.+)$")
    public static void get_user_list_with_pagination_identifier(String pagination_identifier) {
        usersService = services.users;
        HashMap<String,Object> params = new HashMap<>();
        params.put("pagination_identifier",pagination_identifier);
        try {
            response = usersService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get the unique id of the user$")
    public void get_user_id() {
        Assert.assertNotNull("User ID: ", ResultDriver.get_user_id(response));
    }
}
