package com.platform.drivers;

import com.google.gson.JsonObject;

public class UsersDriver {

    public JsonObject get_user_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("user");
    }

    public  String get_user_status(JsonObject response) {
        return get_user_object(response).get("status").getAsString();
    }

    public  boolean is_token_holder_address_null(JsonObject response) {
        return get_user_object(response).get("token_holder_address").isJsonNull();
    }

    public  boolean is_device_manager_null(JsonObject response) {
        return get_user_object(response).get("device_manager_address").isJsonNull();
    }

    public  boolean is_recovery_address_null(JsonObject response) {
        return get_user_object(response).get("recovery_address").isJsonNull();
    }

    public  boolean is_recovery_owner_address_null(JsonObject response) {
        return get_user_object(response).get("recovery_owner_address").isJsonNull();
    }

    public  String get_user_type(JsonObject response) {
        return get_user_object(response).get("type").getAsString();
    }

    public  int get_list_number_of_users(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("users").size();
    }

    public  Object get_user_id(JsonObject response) {
        return get_user_object(response).get("id").getAsString();
    }

    public  String get_recovery_owner_address(JsonObject response) {
        return get_user_object(response).get("recovery_owner_address").getAsString();
    }
}
