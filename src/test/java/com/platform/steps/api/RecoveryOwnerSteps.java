package com.platform.steps.api;

import com.google.gson.GsonBuilder;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.drivers.UsersDriver;
import com.platform.managers.TestDataManager;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.HashMap;

public class RecoveryOwnerSteps {

    UsersDriver usersDriver = new UsersDriver();
    private Base_API base;

    public RecoveryOwnerSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make GET request to get recovery owner details for defined user$")
    public void get_recovery_owner_defined_user() {

        base.recoveryOwnersService = base.services.recoveryOwners;

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(TestDataManager.economy1.user_Id);
        String recovery_owner_address = usersDriver.get_recovery_owner_address(base.response);

        params.put("recovery_owner_address",recovery_owner_address);
        try {
            base.response = base.recoveryOwnersService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get recovery owner details for user with recovery owner address as (.+)$")
    public void get_recovery_owner_details_with_recovery_owner_address(String recovery_owner_address) {

        base.recoveryOwnersService = base.services.recoveryOwners;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("recovery_owner_address",recovery_owner_address);
        try {
            base.response = base.recoveryOwnersService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }
}
