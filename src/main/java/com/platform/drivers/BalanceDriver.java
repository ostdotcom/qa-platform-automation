package com.platform.drivers;

import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class BalanceDriver {

    public JsonObject getBalanceObject(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("balance");
    }

    public String get_available_balance(JsonObject response) {
        return getBalanceObject(response).get("available_balance").getAsString();
    }


    public boolean is_unsettled_balance_zero(JsonObject response)
    {
        return getBalanceObject(response).get("unsettled_debit").getAsString().equals("0");
    }
}
