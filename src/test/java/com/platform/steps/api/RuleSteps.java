package com.platform.steps.api;

import com.google.gson.GsonBuilder;
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
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }
}

