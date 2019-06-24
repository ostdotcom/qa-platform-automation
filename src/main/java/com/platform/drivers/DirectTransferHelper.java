package com.platform.drivers;

import com.platform.constants.Constant;
import com.platform.utils.CommonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DirectTransferHelper {

    public String getTransactionExecutableData(List<String> addressListArray, List<String> transferAmountArray) {
        List<Address> addressList = new ArrayList<>();
        List<Uint256> transferAmountList = new ArrayList<>();

        for (String address : addressListArray) {
            addressList.add(new Address(address));
        }
        for (String amount : transferAmountArray) {
            transferAmountList.add(new Uint256(new BigInteger(amount)));
        }
        Function function = new Function(
                Constant.TRANSACTIONS.DIRECTTRANSFERS,  // function we're calling
                Arrays.asList(new DynamicArray(addressList), new DynamicArray(transferAmountList)),
                Collections.emptyList());

        return FunctionEncoder.encode(function);
    }

    public String getTransactionRawCallData(List<String> tokenHolderAddresses, List<String> amounts) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", Constant.TRANSACTIONS.DIRECTTRANSFERS);
            CommonUtils commonUtils = new CommonUtils();
            JSONArray addressesArray = commonUtils.listToJSONArray(tokenHolderAddresses);
            JSONArray amountsArray = commonUtils.listToJSONArray(amounts);

            JSONArray parameters = new JSONArray();
            parameters.put(addressesArray);
            parameters.put(amountsArray);

            jsonObject.put("parameters", parameters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String calDirectTransferSpendingLimit(List<String> amounts) {
        BigInteger bigInteger = BigInteger.ZERO;
        for (String amount : amounts) {
            bigInteger = bigInteger.add(new BigInteger(amount));
        }
        return bigInteger.toString();
    }
}
