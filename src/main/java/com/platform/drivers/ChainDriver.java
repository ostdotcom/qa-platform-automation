package com.platform.drivers;

import com.google.gson.JsonObject;

public class ChainDriver {

    private JsonObject get_chain_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("chain");
    }


    public String get_current_block(JsonObject response) {

        return get_chain_object(response).get("block_height").getAsString();
    }
}
