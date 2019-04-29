package com.platform.drivers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SessionDriver {

    public JsonArray get_sessions_list(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonArray("sessions");
    }

    public  String get_session_address_from_list(JsonObject response, int i) {
        return get_sessions_list(response).get(i).getAsJsonObject().get("address").getAsString();
    }


    public  int get_number_of_sessions_from_list(JsonObject response) {
        return  get_sessions_list(response).size();
    }
}
