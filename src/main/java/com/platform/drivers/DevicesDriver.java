package com.platform.drivers;

import com.google.gson.JsonObject;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class DevicesDriver {

    OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver(TestDataManager.data.apiEndpoint);


    public JsonObject get_device_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("device");
    }

    public String get_device_address(JsonObject response) {
       return get_device_object(response).get("address").getAsString();
    }

    public String get_device_status(JsonObject response) {
        return get_device_object(response).get("status").getAsString();
    }

    public int get_list_number_of_devices(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("devices").size();
    }


    public JsonObject postAuthorizeDevice(Map<String, Object> requestMap, String userId, String secretKey) throws IOException {

        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/devices/authorize", userId);
        return ostHttpRequestDriver.post(resource, requestMap, secretKey);
    }


    public String get_linked_address(JsonObject response) {
        return get_device_object(response).get("linked_address").getAsString();
    }


    public JsonObject postRevokeDevice(Map<String, Object> requestMap,String userId, String secretKey) throws IOException {
        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/devices/revoke", userId);
        return ostHttpRequestDriver.post(resource, requestMap, secretKey);
    }

    public boolean is_status(String status, JsonObject response) {
        return get_device_object(response).get("status").getAsString().equals(status);
    }

    public JsonObject postInitiateRecovery(Map<String, Object> requestMap,String userId, String secretKey) throws IOException {
        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/devices/initiate-recovery", userId);
        return ostHttpRequestDriver.post(resource,requestMap,secretKey);
    }


    public JsonObject postAbortRecovery(Map<String, Object> requestMap,String userId, String secretKey) throws IOException {
        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/devices/abort-recovery", userId);
        return ostHttpRequestDriver.post(resource,requestMap,secretKey);
    }
}
