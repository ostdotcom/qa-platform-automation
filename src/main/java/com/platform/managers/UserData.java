package com.platform.managers;

import com.google.gson.JsonElement;

public class UserData {
    private static UserData ourInstance = new UserData();


    public static UserData getInstance() {

        if (null == ourInstance) {
            synchronized(UserData.class) {
                if (null == ourInstance) {
                    ourInstance = new UserData();
                }
            }
        }
        return ourInstance;
    }

    private UserData() {
    }


    public String user_id;
    public String api_signer_public;
    public String api_signer_private;
    public String device_manager_address;
    public String recovery_address;
    public String device_address_private;
    public String device_address_public;
    public String linked_address;
    public String recovery_owner_add_public;
    public String recovery_owner_add_private;
    public String session_address_public;
    public String session_address_private;
    public String user_token_holder;
}
