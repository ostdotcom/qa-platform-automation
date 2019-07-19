package com.platform.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Keys;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public CommonUtils() {
    }

    public JSONArray listToJSONArray(List<String> list) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonArray.put(list.get(i));
        }
        return jsonArray;
    }

    public List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
        }
        return list;
    }

    public List<String> toCheckSumAddresses(List<String> addressList) {
        for (int i = 0; i < addressList.size(); i++) {
            String address = Keys.toChecksumAddress(addressList.get(i));
            addressList.set(i, address);
        }
        return addressList;
    }

    private static final byte[] nonSecret = ("LETS_CLEAR_BYTES" + String.valueOf((int) (System.currentTimeMillis()))  ).getBytes();

    public static void clearBytes(byte[] secret) {
        if ( null == secret ) { return; }
        for (int i = 0; i < secret.length; i++) {
            secret[i] = nonSecret[i % nonSecret.length];
        }
    }

    public String parseStringResponseForKey(JSONObject jsonObject, String key) {
        try {
            JSONObject resultType = (JSONObject) parseResponseForResultType(jsonObject);
            String stringValue = resultType.getString(key);
            return stringValue;
        } catch (JSONException e) {

        }
        return null;
    }


    public Object parseResponseForResultType(JSONObject jsonObject) throws JSONException {

        JSONObject jsonData = jsonObject.getJSONObject("data");
        Object resultTypeObject = jsonData.get(jsonData.getString("result_type"));
        return resultTypeObject;
    }

    public JSONObject parseObjectResponseForKey(JSONObject jsonObject, String key) {
        try {
            JSONObject resultType = (JSONObject) parseResponseForResultType(jsonObject);
            JSONObject keyObject = resultType.getJSONObject(key);
            return keyObject;
        } catch (JSONException e) {
        }
        return null;
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}