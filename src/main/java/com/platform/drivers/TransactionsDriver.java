package com.platform.drivers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.platform.constants.Constant;
import com.platform.managers.TestDataManager;
import cucumber.api.java.hu.Ha;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionsDriver {


    public static String getTransactionId(JsonObject response) {
       return response.getAsJsonObject("data").getAsJsonObject("transaction").get("id").getAsString();
    }

    public static String getFromUserId(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("transaction").getAsJsonArray("transfers").get(0).getAsJsonObject().get("from_user_id").getAsString();
    }

    public static String getTransactionStatus(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("transaction").get("status").getAsString();
    }

    public static String subtract(String oldBalance, String transferredUBT) {

        BigDecimal expectedValue = new BigDecimal(oldBalance).subtract(new BigDecimal(transferredUBT));
        return expectedValue.toString();
    }

    public static String add(String oldBalance, String transferredUBT) {
        BigDecimal expectedValue = new BigDecimal(oldBalance).add(new BigDecimal(transferredUBT));
        return expectedValue.toString();
    }

    public static String get_ubt_from_usd(String usdInWei, String pricePoint, String conversion_factor) {
        BigInteger expectedValue = new BigDecimal(usdInWei).divide(new BigDecimal(pricePoint),5, RoundingMode.HALF_EVEN).multiply(new BigDecimal(conversion_factor)).toBigInteger();
        return expectedValue.toString();
    }

    public static int get_transaction_count(JsonObject response) {

        return  response.getAsJsonObject("data").getAsJsonObject("meta").get("total_no").getAsInt();
    }

    public static String get_list_number_of_transaction(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("transactions").size()+"";
    }

    public static String subtract(String oldBalance, String transferredUBT, int numOfTransfers) {

        BigDecimal expectedValue = new BigDecimal(oldBalance).subtract(new BigDecimal(transferredUBT).multiply(new BigDecimal(numOfTransfers)));
        return expectedValue.toString();
    }

    public static String add(String oldBalance, String transferredUBT,int numOfTransfers) {
        BigDecimal expectedValue = new BigDecimal(oldBalance).add(new BigDecimal(transferredUBT).multiply(new BigDecimal(numOfTransfers)));
        return expectedValue.toString();
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


    public static class GetTransactionsParams
    {


        public GetTransactionsParams()
        {

        }
    }
}
