package com.platform.drivers;

import com.google.gson.JsonObject;

public class DevicesDriver {


    public JsonObject get_device_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("device");
    }

    public String get_device_address(JsonObject response) {
       return get_device_object(response).get("address").getAsString();
    }

    public String get_device_status(JsonObject response) {
        return get_device_object(response).get("status").getAsString();
    }

    public int get_list_number_of_devices(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("devices").size();
    }
}
