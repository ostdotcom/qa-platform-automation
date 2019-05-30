package com.platform.drivers;

import com.google.gson.JsonObject;
import com.platform.constants.Constant;
import com.platform.managers.TestDataManager;
import sun.security.krb5.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UsersDriver {

    OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver(TestDataManager.data.apiEndpoint);

    private static final String SESSION_ADDRESSES = "session_addresses";
    private static final String EXPIRATION_HEIGHT = "expiration_height";
    private static final String SPENDING_LIMIT = "spending_limit";
    private static final String RECOVERY_OWNER_ADDRESS = "recovery_owner_address";
    private static final String DEVICE_ADDRESS = "device_address";
    private static final String TOKEN_ID = "token_id";
    private static final String USER_ID = "user_id";


    public JsonObject get_user_object(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user");
    }

    public String get_user_status(JsonObject response) {
        return get_user_object(response).get("status").getAsString();
    }

    public boolean is_token_holder_address_null(JsonObject response) {
        return get_user_object(response).get("token_holder_address").isJsonNull();
    }

    public boolean is_device_manager_null(JsonObject response) {
        return get_user_object(response).get("device_manager_address").isJsonNull();
    }

    public boolean is_recovery_address_null(JsonObject response) {
        return get_user_object(response).get("recovery_address").isJsonNull();
    }

    public boolean is_recovery_owner_address_null(JsonObject response) {
        return get_user_object(response).get("recovery_owner_address").isJsonNull();
    }

    public String get_user_type(JsonObject response) {
        return get_user_object(response).get("type").getAsString();
    }

    public int get_list_number_of_users(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("users").size();
    }

    public String get_user_id(JsonObject response) {
        return get_user_object(response).get("id").getAsString();
    }

    public String get_recovery_owner_address(JsonObject response) {
        return get_user_object(response).get("recovery_owner_address").getAsString();
    }

    public String get_recovery_address(JsonObject response) {
        return get_user_object(response).get("recovery_address").getAsString();
    }

    public JsonObject postActivateUser(Object sessionAddress,
                                       Object expirationHeight,
                                       String spendingLimit,
                                       String recoveryOwnerAddress,
                                       String deviceAddress,
                                       String userID,
                                       String api_signer_add,
                                       String api_signer_private
    ) throws IOException {

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put(SESSION_ADDRESSES, sessionAddress);
        requestMap.put(EXPIRATION_HEIGHT, expirationHeight);
        requestMap.put(SPENDING_LIMIT, spendingLimit);
        requestMap.put(RECOVERY_OWNER_ADDRESS, recoveryOwnerAddress);
        requestMap.put(DEVICE_ADDRESS, deviceAddress);

        requestMap.putAll(ostHttpRequestDriver.getPrequisite(userID,deviceAddress,api_signer_add));
        return postUserActivate(requestMap, userID, api_signer_private);
    }

    private JsonObject postUserActivate(Map<String, Object> requestMap, String userId, String secretKey) throws IOException {

        String resource = String.format("/users/%s/activate-user/", userId);
        return ostHttpRequestDriver.post(resource, requestMap, secretKey);
    }

    public boolean is_token_holder_present(JsonObject response) {
        return get_user_object(response).get("token_holder_address").isJsonNull();
    }

    public boolean is_status(String status, JsonObject response) {
        return get_user_object(response).get("status").getAsString().equals(status);
    }

    public String get_device_manager_address(JsonObject response) {
        return get_user_object(response).get("device_manager_address").getAsString();
    }

    public String get_token_holder(JsonObject response) {
        return get_user_object(response).get("token_holder_address").getAsString();
    }
}
