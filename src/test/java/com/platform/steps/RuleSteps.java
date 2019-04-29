package com.platform.steps;

import com.platform.base.Base_API;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class RuleSteps  {


    private Base_API base;

    public RuleSteps(Base_API base) {
        this.base = base;
    }

    @When("^I make GET request to get list of rules$")
    public void get_rule_list() {

        base.rulesService = base.services.rules;
        HashMap <String,Object> params = new HashMap<String,Object>();
        try {
            base.response = base.rulesService.getList( params );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response: " + base.response.toString() );
    }
}

