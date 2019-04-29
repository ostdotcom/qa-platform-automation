package com.platform.drivers;

import com.google.gson.JsonObject;

public class SessionDriver {


    public  String get_session_address_from_list(JsonObject response, int i) {
        return response.getAsJsonObject("data").getAsJsonArray("sessions").get(i).getAsJsonObject().get("address").getAsString();
    }


    public  int get_number_of_sessions_from_list(JsonObject response) {
        return  response.getAsJsonObject("data").getAsJsonArray("sessions").size();
    }
}
