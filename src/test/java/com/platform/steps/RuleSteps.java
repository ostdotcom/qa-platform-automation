package com.platform.steps;

import com.platform.base.Base_API;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class RuleSteps extends Base_API {



    @When("^I make GET request to get list of rules$")
    public void get_rule_list() {

        rulesService = services.rules;
        HashMap <String,Object> params = new HashMap<String,Object>();
        try {
            response = rulesService.getList( params );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }
}

