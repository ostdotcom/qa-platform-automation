package com.platform.drivers;

import com.google.gson.JsonObject;

public class DeviceManagerDriver {


    public String get_nonce(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("device_manager").get("nonce").getAsString();
    }
}
