package com.platform.drivers;

import com.google.gson.JsonObject;

public class DevicesDriver {


    public String get_device_address(JsonObject response) {
       return response.getAsJsonObject("data").getAsJsonObject("device").get("address").getAsString();
    }

    public String get_device_status(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("device").get("status").getAsString();
    }

    public int get_list_number_of_devices(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("devices").size();
    }
}
