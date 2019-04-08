package com.platform.steps;

import com.google.gson.JsonObject;
import com.platform.Base.Base_API;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class UsersSteps extends Base_API {


    @When("^I make post request to create user$")
    public void iMakePostRequestToCreateUser() {

        usersService = services.users;
        HashMap<String,Object> params = new HashMap<>();
        try {
            response = usersService.create(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }


}
