package com.platform.steps;

import com.platform.base.Base_API;
import cucumber.api.java.en.When;

import java.util.HashMap;

public class UsersSteps extends Base_API {


    @When("^I make post request to create user$")
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
}
