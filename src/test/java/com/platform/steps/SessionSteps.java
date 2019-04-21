package com.platform.steps;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.SessionDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;

public class SessionSteps extends Base_API {

    String company_session_address;


    @When("^I make GET request to get session for defined session address$")
    public void get_session_details_defined_session_address() {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("session_address", TestDataManager.economy1.session_address);
        try {
            response = sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get session details with session address as (.+)$")
    public void get_session_details_with_SessionAddress(String session_address) {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("session_address", session_address);
        try {
            response = sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get transactions list for a company$")
    public void get_sessions_list_for_company() {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        try {
            response = sessionsService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

        company_session_address = SessionDriver.get_session_address_from_list(response,1);

    }

    @When("^I make GET request to get session details for any of the company's session$")
    public void get_session_Address_with_address() {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("session_address", company_session_address);
        try {
            response = sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get Session list with limit as (.+)$")
    public void get_sessions_with_limit(Object limit) {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("limit",limit);
        try {
            response = sessionsService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get session list with (.+) sessions$")
    public void verify_session_list_count(int session_count) {
        Assert.assertEquals("Number of session should be: ", session_count,SessionDriver.get_number_of_sessions_from_list(response));
    }

    @When("^I make GET request to get session list with pagination identifier as (.+)$")
    public static void get_sessions_list_with_pagination_identifier(String pagination_identifier) {

        sessionsService = services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("pagination_identifier",pagination_identifier);
        try {
            response = sessionsService.getList( params );
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
