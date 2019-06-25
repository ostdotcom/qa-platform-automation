package com.platform.drivers;

import com.google.gson.JsonObject;


public class PricePointDriver {


    public String get_ost_price(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("price_point").getAsJsonObject("OST").get("USD").getAsString();
    }

    public String get_price(JsonObject response, String base_token, String fiat_currency) {
        return response.getAsJsonObject("data").getAsJsonObject("price_point").getAsJsonObject(base_token).get(fiat_currency).getAsString();
    }

    public int get_fiat_decimals(JsonObject response, String base_token) {
        return response.getAsJsonObject("data").getAsJsonObject("price_point").getAsJsonObject(base_token).get("decimals").getAsInt();
    }
}
