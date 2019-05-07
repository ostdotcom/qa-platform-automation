package com.platform.temp;

import com.google.gson.JsonObject;
import com.platform.drivers.OstHttpRequestDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ActiveTH {


    String userId = "b3d0c65b-cba0-443f-bd0f-9549a150f4fd";
    String device_add = "0xeb2e252253aa164bc0f7a12bb3b0707426e482e6";
    String device_private = "aee6b816a2efcaddf6e1fc72639ed2e2728aaf311f98acee880953c969cda8d9";
    String api_signer_add = "0x128cd970e25f8ec5c52e8c783ffffa137581dc2c";
    String api_signer_private = "0x4be3bc23eba026bc7eabca768aa4d90786269a6d648d33e200708c6621da5e05";
    String tokenId =  "1287";
    String secret = "0x4be3bc23eba026bc7eabca768aa4d90786269a6d648d33e200708c6621da5e05";

    private static final String SESSION_ADDRESSES = "session_addresses";
    private static final String EXPIRATION_HEIGHT = "expiration_height";
    private static final String SPENDING_LIMIT = "spending_limit";
    private static final String RECOVERY_OWNER_ADDRESS = "recovery_owner_address";
    private static final String DEVICE_ADDRESS = "device_address";


    @Test
    public void testCall() throws IOException {

        Map<String, Object> requestMap = getPrerequisiteMap();
        requestMap.put(SESSION_ADDRESSES, Arrays.asList("0xbe840b2789227e0df2e40ef85618718f05094698"));
        requestMap.put(EXPIRATION_HEIGHT, "1000000");
        requestMap.put(SPENDING_LIMIT, "10000007888");
        requestMap.put(RECOVERY_OWNER_ADDRESS, "0xbe840b2789227e0df2e40ef85618718f05094698");
        requestMap.put(DEVICE_ADDRESS, device_add);

//        EthAddress ethAddress = new EthAddress();
//        JsonObject ethKey = ethAddress.getNewEthKeys();
//        System.out.println(ethKey);

        activateUser(String.format("/users/%s/activate-user",userId), requestMap);
//        getSalt(String.format("/users/%s/salt",userId), requestMap);
    }

    public void getSalt(String path, Map<String, Object> requestMap) throws IOException {

        OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver("https://s6-api.stagingost.com/testnet/v2");
//        String response = ostHttpRequestDriver.get(path,requestMap).toString();
//        System.out.println(response);
    }

    private void activateUser(String path, Map<String, Object> requestMap) throws IOException {

        OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver("https://s6-api.stagingost.com/testnet/v2");
        JsonObject response = ostHttpRequestDriver.post(path,requestMap,secret);
        System.out.println(response.toString());
    }

    private Map<String, Object> getPrerequisiteMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("api_key", String.format("%s.%s.%s.%s",tokenId, userId, device_add, api_signer_add));
        map.put("api_request_timestamp", String.valueOf((int) (System.currentTimeMillis() / 1000)));
        map.put("api_signature_kind", "OST1-PS");
        return map;
    }
}
