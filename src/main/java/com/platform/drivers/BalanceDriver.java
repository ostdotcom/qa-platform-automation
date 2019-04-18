package com.platform.drivers;

import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class BalanceDriver {


    public static String get_available_balance(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("balance").get("available_balance").getAsString();
    }


    public static boolean is_unsettled_balance_zero(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("balance").get("unsettled_debit").getAsString().equals("0");
    }
}
