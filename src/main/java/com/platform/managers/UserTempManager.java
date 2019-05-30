package com.platform.managers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.platform.constants.Constant;

import java.io.*;
import java.util.List;

public class UserTempManager {
    public static final UserTempManager data=loadData();
    Gson gson = new Gson();
    //public static final UserTempManager wirteData=WriteData();


    public UserTempManager()
    {
    }

    public UserTempManager setData()
    {
        this.user_id = user_id;
        this.api_signer_private = api_signer_private;
        this.api_signer_public = api_signer_public;
        this.api_signer_public = api_signer_public;
        this.device_address = device_address;
        this.recovery_owner_add = recovery_owner_add;
        this.token_holder = token_holder;
        this.session_address = session_address;

        return new UserTempManager();
    }


    /**
     * This function will return the test_data.json file as Java object.
     * @return
     */
    private static UserTempManager loadData()  {

        try {
            return new Gson().fromJson(new FileReader(Constant.TestDataFilePath.USERTEMPDATAFILE),UserTempManager.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new UserTempManager();
        }
    }


    public void setUser_id(String user_id)  {
        this.user_id = user_id;
        try {
            gson.toJson(setData(), new FileWriter(Constant.TestDataFilePath.USERTEMPDATAFILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setToken_holder(String token_holder) {
        this.token_holder = token_holder;
    }

    public void setApi_signer_public(String api_signer_public) {
        this.api_signer_public = api_signer_public;
    }

    public void setApi_signer_private(String api_signer_private) {
        this.api_signer_private = api_signer_private;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    public void setSession_address(String session_address) {
        this.session_address = session_address;
    }

    public void setRecovery_owner_add(String recovery_owner_add) {
        this.recovery_owner_add = recovery_owner_add;
    }

    public String user_id;
    public String token_holder;
    public String api_signer_public;
    public String api_signer_private;
    public String device_address;
    public String session_address;
    public String recovery_owner_add;
}
