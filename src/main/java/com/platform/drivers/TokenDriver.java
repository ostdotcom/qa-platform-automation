package com.platform.drivers;

import com.google.gson.JsonObject;

public class TokenDriver {

    public JsonObject get_token_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("token");
    }

    public  String get_conversion_factor(JsonObject response) {
        return get_token_object(response).get("conversion_factor").getAsString();
    }

    public int get_auxiliary_chain_id(JsonObject response)
    {
        return get_token_object(response)
                .getAsJsonArray("auxiliary_chains").get(0)
                .getAsJsonObject().get("chain_id").getAsInt();
    }

    public int get_origin_chain_id(JsonObject response) {
        return get_token_object(response)
                .getAsJsonObject( "origin_chain").get("chain_id").getAsInt();
    }

    public String get_base_token(JsonObject response) {
        return get_token_object(response)
                .get("base_token").getAsString();
    }

    public int get_token_decimal(JsonObject response) {
        return get_token_object(response)
                .get("decimals").getAsInt();
    }
}
