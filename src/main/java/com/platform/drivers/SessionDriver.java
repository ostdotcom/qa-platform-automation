package com.platform.drivers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class SessionDriver {

    OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver(TestDataManager.data.apiEndpoint);

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


    public JsonObject postAddSession(Map<String, Object> requestMap, String userId, String secretKey) throws IOException {
        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/sessions/authorize", userId);
        return ostHttpRequestDriver.post(resource,requestMap,secretKey);
    }


    public String get_nonce(JsonObject response) {

        return response.getAsJsonObject("data").getAsJsonObject("session").get("nonce").getAsString();
    }
}
