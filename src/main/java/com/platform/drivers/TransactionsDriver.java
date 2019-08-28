package com.platform.drivers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.platform.constants.Constant;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import cucumber.api.java.hu.Ha;
import org.json.JSONObject;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionsDriver {

    OstHttpRequestDriver ostHttpRequestDriver = new OstHttpRequestDriver(TestDataManager.data.apiEndpoint);


    public JsonObject get_transaction_object(JsonObject response)
    {
        return response.getAsJsonObject("data").getAsJsonObject("transaction");
    }


    public  String getTransactionId(JsonObject response) {
       return get_transaction_object(response).get("id").getAsString();
    }

    public  String getFromUserId(JsonObject response) {
        return get_transaction_object(response).getAsJsonArray("transfers").get(0).getAsJsonObject().get("from_user_id").getAsString();
    }

    public  String getTransactionStatus(JsonObject response) {
        return get_transaction_object(response).get("status").getAsString();
    }

    public  String subtract(String oldBalance, String transferredUBT) {

        BigDecimal expectedValue = new BigDecimal(oldBalance).subtract(new BigDecimal(transferredUBT));
        return expectedValue.toString();
    }

    public  String add(String oldBalance, String transferredUBT) {
        BigDecimal expectedValue = new BigDecimal(oldBalance).add(new BigDecimal(transferredUBT));
        return expectedValue.toString();
    }

    public  String get_ubt_from_usd(String usdInWei,
                                    int base_token_decimals,
                                    String pricePoint,
                                    int fiat_decimals,
                                    String conversion_factor) {

        MathContext f_mc = new MathContext(fiat_decimals);
        MathContext b_mc = new MathContext(base_token_decimals);

        /**
         *  Formula to get BT from any fiat currency
         *
         *  BT => usd wei
         *
         *  BT => ost wei => usd wei
         *
         *  BT => ost wei = (1/(pricepoint* 10^decimal) * usd wei
         *
         *  BT => (CF * 10^bt_decimal) * ost wei => usd wei;
         *
         *  BT => (CF * 10^bt_decimal) * (1/(pricepoint* 10^decimal) * usd wei
         *
         *
         *  (Conversion factor) * (fiat amount in ETH) / (current pricer) = BT
         *
         *  (Conversion factor)* (fiat amount in ETH) / (current pricer) * Base token's Decimal = BT in wei
         *
         *  Now if amount is in wei then, convert it into BT
         *
         *  (Conversion factor)* (fiat amount in Wei * 10^-18 (in place of 18 use, fiat currency's decimal)) / (current pricer)
         *
         */

        BigDecimal amount = new BigDecimal(usdInWei).multiply((new BigDecimal(10)).pow(-fiat_decimals,f_mc));
        BigDecimal pricePointPrecision = new BigDecimal(pricePoint);
        BigDecimal cf = new BigDecimal(conversion_factor);
        BigDecimal decimals = new BigDecimal(10).pow(base_token_decimals);

        BigDecimal expectedValue = amount
                .divide(pricePointPrecision, 30, RoundingMode.UP)
                .multiply(cf)
                .multiply(decimals);


        return expectedValue.toBigInteger().toString();
    }

    public  int get_transaction_count(JsonObject response) {

        return  response.getAsJsonObject("data").getAsJsonObject("meta").get("total_no").getAsInt();
    }

    public  String get_list_number_of_transaction(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("transactions").size()+"";
    }

    public  String subtract(String oldBalance, String transferredUBT, int numOfTransfers) {

        BigDecimal expectedValue = new BigDecimal(oldBalance).subtract(new BigDecimal(transferredUBT).multiply(new BigDecimal(numOfTransfers)));
        return expectedValue.toString();
    }

    public  String add(String oldBalance, String transferredUBT,int numOfTransfers) {
        BigDecimal expectedValue = new BigDecimal(oldBalance).add(new BigDecimal(transferredUBT).multiply(new BigDecimal(numOfTransfers)));
        return expectedValue.toString();
    }

    public JsonObject postExecuteTransaction(Map<String, Object> requestMap,String userId, String secretKey) throws IOException {
        requestMap.putAll(ostHttpRequestDriver.getPrequisite(UserData.getInstance().user_id,UserData.getInstance().device_address_public,UserData.getInstance().api_signer_public));
        String resource = String.format("/users/%s/transactions", userId);
        return ostHttpRequestDriver.post(resource,requestMap,secretKey);
       // return ostHttpRequestDriver.post(String.format("/users/%s/transactions", mUserId), requestMap);
    }


    public static abstract class ExecuteTransactionParamBuilder {
        String userId;
        String toAddress;
        ArrayList<String> user2TokenHolderAddress= new ArrayList<>();
        ArrayList<String> amount = new ArrayList<>();
        String ruleName;
        String meta_name;
        String meta_type;
        String meta_details;

        public ExecuteTransactionParamBuilder() {

            this.userId = TestDataManager.economy1.company_Id;
            this.meta_name = "gtest";
            this.meta_type = "company_to_user";
            this.meta_details= "details of meta";
            this.user2TokenHolderAddress.add(TestDataManager.economy1.user_TH);
            this.amount.add("1");

        }

        public ExecuteTransactionParamBuilder setToAddress(String toAddress) {
            this.toAddress = toAddress;
            return this;
        }

        public ExecuteTransactionParamBuilder setUser2TokenHolderAddress(ArrayList<String> user2TokenHolderAddress) {
            this.user2TokenHolderAddress = user2TokenHolderAddress;
            return this;
        }

        public ExecuteTransactionParamBuilder setAmount(ArrayList<String> amount) {
            this.amount = amount;
            return this;
        }

        public ExecuteTransactionParamBuilder setRuleName(String ruleName) {
            this.ruleName = ruleName;
            return this;
        }

        public ExecuteTransactionParamBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }


//        public ExecuteTransactionParamBuilder setMethodName(String userId) {
//            this.userId = userId;
//            return this;
//        }

        public ExecuteTransactionParamBuilder setMeta_name(String meta_name) {
            this.meta_name = meta_name;
            return this;
        }

        public ExecuteTransactionParamBuilder setMeta_type(String meta_type) {
            this.meta_type = meta_type;
            return this;
        }


        public ExecuteTransactionParamBuilder setMeta_details(String meta_details) {
            this.meta_details = meta_details;
            return this;
        }


        public abstract HashMap<String, Object> build();
    }


    public static class PayParamsBuilder extends ExecuteTransactionParamBuilder
    {

        String payCurrencyCode;
        String ostToUsd;
        String tokenHolderSender;


        public PayParamsBuilder(String price_point) {
            super();
            this.ruleName=Constant.TRANSACTIONS.PAY;
            this.toAddress = TestDataManager.economy1.pricer_TR;
            this.payCurrencyCode = "USD";
            this.ostToUsd=Convert.toWei(price_point, Convert.Unit.ETHER).toBigInteger()+"";      //add price point API
            this.tokenHolderSender=TestDataManager.economy1.company_TH;
        }


        public PayParamsBuilder setTokenHolderSender(String tokenHolderSender) {
            this.tokenHolderSender = tokenHolderSender;
            return this;
        }



        public PayParamsBuilder setOstToUsd(String ostToUsd) {
            this.ostToUsd = ostToUsd;
            return this;
        }

        public PayParamsBuilder setPayCurrencyCode(String payCurrencyCode) {
            this.payCurrencyCode = payCurrencyCode;
            return this;
        }


        @Override
        public HashMap<String, Object> build()
        {
            HashMap <String,Object> metaProperty = new HashMap<String,Object>();
            metaProperty.put("name", meta_name); // like, download
            metaProperty.put("type", meta_type); // user_to_user, company_to_user, user_to_company
            metaProperty.put("details",meta_details); // memo field to add additional info about the transaction

            HashMap <String,Object> params = new HashMap<String,Object>();
            HashMap <String,Object> nestedparams = new HashMap<String,Object>();
            params.put("user_id", userId);
            params.put("to", toAddress);
            nestedparams.put("method", ruleName);
            ArrayList<Object> nestedarraylist = new ArrayList<Object>();
            Gson gsonObj = new Gson();
            nestedarraylist.add(tokenHolderSender);
            nestedarraylist.add(user2TokenHolderAddress);
            nestedarraylist.add(amount);
            nestedarraylist.add(payCurrencyCode);
            nestedarraylist.add(ostToUsd.trim());
            nestedparams.put("parameters", nestedarraylist);
            String jsonStr = gsonObj.toJson(nestedparams);
            params.put("raw_calldata", jsonStr);
            params.put("meta_property", metaProperty);
            System.out.println(params);
            return  params;
        }
    }

    public static class DTParamsBuilder extends ExecuteTransactionParamBuilder {

        public DTParamsBuilder() {
            super();
            this.ruleName = Constant.TRANSACTIONS.DIRECTTRANSFERS;
            this.toAddress = TestDataManager.economy1.directTransfer_TR;
            //Can add over here code for get toAddress from Token Rule API. What if someone does not provided toAddress in TestData.json?
        }

        @Override
        public HashMap<String, Object> build() {
            HashMap <String,Object> metaProperty = new HashMap<String,Object>();
            metaProperty.put("name", meta_name); // like, download
            metaProperty.put("type", meta_type); // user_to_user, company_to_user, user_to_company
            metaProperty.put("details",meta_details); // memo field to add additional info about the transaction

            HashMap <String,Object> params = new HashMap<String,Object>();
            HashMap <String,Object> nestedparams = new HashMap<String,Object>();
            params.put("user_id", userId);
            params.put("to", toAddress);
            nestedparams.put("method", ruleName);
            ArrayList<ArrayList> nestedarraylist = new ArrayList<ArrayList>();
            Gson gsonObj = new Gson();
            nestedarraylist.add(user2TokenHolderAddress);
            nestedarraylist.add(amount);
            nestedparams.put("parameters", nestedarraylist);
            String jsonStr = gsonObj.toJson(nestedparams);
            params.put("raw_calldata", jsonStr);
            params.put("meta_property", metaProperty);
            return params;
        }
    }

    public static class ExecuteRuleRequestBuilder {

        private static final String TO = "to";
        private static final String RAW_CALL_DATA = "raw_calldata";
        private static final String NONCE = "nonce";
        private static final String CALL_DATA = "calldata";
        private static final String SIGNATURE = "signature";
        private static final String SIGNER = "signer";
        private static final String MATA_PROPERTY = "meta_property";
        private String toAddress = "0x0";

        public ExecuteRuleRequestBuilder setToAddress(String toAddress) {
            this.toAddress = toAddress;
            return this;
        }

        public ExecuteRuleRequestBuilder setRawCallData(String rawCallData) {
            this.rawCallData = rawCallData;
            return this;
        }

        public ExecuteRuleRequestBuilder setNonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public ExecuteRuleRequestBuilder setCallData(String callData) {
            this.callData = callData;
            return this;
        }

        public ExecuteRuleRequestBuilder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public ExecuteRuleRequestBuilder setSigner(String signer) {
            this.signer = signer;
            return this;
        }

        public ExecuteRuleRequestBuilder setMetaProperty(Map<String, Object> metaProperty) {
            this.metaProperty = metaProperty;
            return this;
        }

        private String rawCallData = new String();
        private String nonce = "0";
        private String callData = "0x0";
        private String signature = "0x0";
        private String signer = "0x0";
        private Map<String, Object> metaProperty = new HashMap<>();


        public ExecuteRuleRequestBuilder() {
        }

        public Map<String, Object> build() {
            Map<String, Object> map = new HashMap<>();
            map.put(TO, toAddress);
            map.put(RAW_CALL_DATA, rawCallData);
            map.put(NONCE, nonce);
            map.put(CALL_DATA, callData);
            map.put(SIGNATURE, signature);
            map.put(SIGNER, signer);
            map.put(MATA_PROPERTY, metaProperty);
            return map;
        }
    }

}
