package com.platform.drivers;

import com.google.gson.JsonObject;

public class TokenDriver {


    public static String get_conversion_factor(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("token").get("conversion_factor").getAsString();
    }
}
