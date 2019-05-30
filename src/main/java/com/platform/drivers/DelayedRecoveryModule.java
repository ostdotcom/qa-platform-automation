package com.platform.drivers;

import org.json.JSONException;
import org.json.JSONObject;

public class DelayedRecoveryModule {

    private static final String TAG = "OstDelayedRecovery";

    public DelayedRecoveryModule() {
    }

    public JSONObject resetRecoveryOwnerData(String oldRecoveryOwnerAddress, String newRecoveryOwnerAddress, String recoveryAddress) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{\n" +
                    "      types: {\n" +
                    "        EIP712Domain: [{ name: 'verifyingContract', type: 'address' }],\n" +
                    "        ResetRecoveryOwnerStruct: [\n" +
                    "          { name: 'oldRecoveryOwner', type: 'address' },\n" +
                    "          { name: 'newRecoveryOwner', type: 'address' }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      primaryType: 'ResetRecoveryOwnerStruct',\n" +
                    "      domain: {\n" +
                    "        verifyingContract: " + recoveryAddress + " \n" +
                    "      },\n" +
                    "      message: {\n" +
                    "        oldRecoveryOwner: " + oldRecoveryOwnerAddress + ",\n" +
                    "        newRecoveryOwner: " + newRecoveryOwnerAddress + "\n" +
                    "      }\n" +
                    "    }");
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }

    public JSONObject initiateRecoveryOwnerData(String prevOwnerAddress, String oldOwnerAddress,
                                                String newOwnerAddress, String recoveryAddress) {
        return getRecoveryOperationTypedData(prevOwnerAddress, oldOwnerAddress, newOwnerAddress, recoveryAddress,
                "InitiateRecoveryStruct");

    }

    public JSONObject generateAbortRecoveryOwnerData(String prevOwnerAddress, String oldOwnerAddress,
                                                     String newOwnerAddress, String recoveryAddress) {
        return getRecoveryOperationTypedData(prevOwnerAddress, oldOwnerAddress, newOwnerAddress, recoveryAddress,
                "AbortRecoveryStruct");

    }

    public JSONObject getRecoveryOperationTypedData(String prevOwnerAddress, String oldOwnerAddress, String newOwnerAddress,
                                                    String recoveryAddress, String primaryType) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{\n" +
                    "      types: {\n" +
                    "        EIP712Domain: [{ name: 'verifyingContract', type: 'address' }],\n" +
                    "        " + primaryType + ": [\n" +
                    "          { name: 'prevOwner', type: 'address' },\n" +
                    "          { name: 'oldOwner', type: 'address' },\n" +
                    "          { name: 'newOwner', type: 'address' }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      primaryType: '" + primaryType + "',\n" +
                    "      domain: {\n" +
                    "        verifyingContract: " + recoveryAddress + "\n" +
                    "      },\n" +
                    "      message: {\n" +
                    "        prevOwner: \"" + prevOwnerAddress + "\",\n" +
                    "        oldOwner: \"" + oldOwnerAddress + "\",\n" +
                    "        newOwner: \"" + newOwnerAddress + "\"\n" +
                    "      }\n" +
                    "    }");
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }
}