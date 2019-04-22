package com.platform.drivers;

import com.google.gson.JsonObject;

public class PricePointDriver {


    public static String get_ost_price(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("price_point").getAsJsonObject("OST").get("USD").getAsString();
    }
}
