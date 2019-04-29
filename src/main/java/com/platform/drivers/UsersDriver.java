package com.platform.drivers;

import com.google.gson.JsonObject;

public class UsersDriver {

    public  String get_user_status(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("status").getAsString();
    }

    public  boolean is_token_holder_address_null(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("token_holder_address").isJsonNull();
    }

    public  boolean is_device_manager_null(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("device_manager_address").isJsonNull();
    }

    public  boolean is_recovery_address_null(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("recovery_address").isJsonNull();
    }

    public  boolean is_recovery_owner_address_null(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("recovery_owner_address").isJsonNull();
    }
}
