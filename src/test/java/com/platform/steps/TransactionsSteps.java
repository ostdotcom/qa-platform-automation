package com.platform.steps;


import com.google.gson.Gson;
import com.ost.services.OSTAPIService;
import com.ost.services.Transactions;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.BalanceDriver;
import com.platform.drivers.ResultDriver;
import com.platform.drivers.TokenDriver;
import com.platform.drivers.TransactionsDriver;

import com.platform.managers.TestDataManager;
import com.platform.utils.AssertionUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsSteps extends Base_API {

    String transactionId;
    String from_userId;
    String company_old_Balance;
    String company_new_Balance;
    String user_new_Balance;
    String user_old_Balance;
    int transaction_count;


    Transactions transactionsService = services.transactions;



    private String getBalance(String user_id) {

        balancesService = services.balance;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", user_id);

        // Wait till the unsettled balance is cleared. Then only get the available balance of user
        AssertionUtils.repeatWhenFailedForSeconds(Constant.TRANSACTIONS.TOTALWAIT, ()->
        {
        try {
            params.put("user_id", user_id);
            response = balancesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
        Assert.assertEquals(true,BalanceDriver.is_unsettled_balance_zero(response));
        });
        return BalanceDriver.get_available_balance(response);
    }

    @When("^I make POST request of Company transfers (.+) UBT in wei to user via direct transfer method$")
    public void execute_transaction_c2u_direct(String ubtInWei) {

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(ubtInWei);
        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

        company_new_Balance = TransactionsDriver.subtract(company_old_Balance,ubtInWei);
        user_new_Balance = TransactionsDriver.add(user_old_Balance,ubtInWei);

    }

    @When("^I make POST request of Company transfers (\\d+) USD in wei to user via pay method$")
    public void execute_transaction_c2u_pay(String usdInWei) {

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);

        //Get latest pricer for calculation of USD to UBT
        TokensSteps tokensSteps = new TokensSteps();
        tokensSteps.get_tokens();
        String conversion_factor = TokenDriver.get_conversion_factor(response);
        String pricePoint = "0.027953";
        String transferredUbt = TransactionsDriver.get_ubt_from_usd(usdInWei,pricePoint,conversion_factor);

        System.out.println("about to transfer UBT: "+transferredUbt);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(usdInWei);
        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setAmount(amount)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

        company_new_Balance = TransactionsDriver.subtract(company_old_Balance,transferredUbt);
        user_new_Balance = TransactionsDriver.add(user_old_Balance,transferredUbt);
    }

    @And("^Company's balance should be debited$")
    public void verify_company_balance() {

        String balance = getBalance(TestDataManager.economy1.company_Id);
        System.out.println("new Company Balance :"+balance);
        Assert.assertEquals(company_new_Balance, balance);

        //"Updated company balance should be ",
    }

    @And("^User's balance should be credited$")
    public void verify_user_balance() {
        System.out.println("new User Balance :"+getBalance(TestDataManager.economy1.user_Id));
        Assert.assertEquals("Updated User balance should be ",user_new_Balance, getBalance(TestDataManager.economy1.user_Id));
    }

    @And("^I should get Transaction status as (.+)$")
    public void verify_transaction_status(String transaction_status) throws InterruptedException {

        Thread.sleep(Constant.TRANSACTIONS.CONFIRMATION_TIME*1000);
        transactionsService = services.transactions;
        transactionId = TransactionsDriver.getTransactionId(response);
        from_userId = TransactionsDriver.getFromUserId(response);
        HashMap <String,Object> params = new HashMap<String,Object>();

        AssertionUtils.repeatWhenFailedForSeconds(Constant.TRANSACTIONS.TOTALWAIT, ()->
        {
            params.put("user_id", from_userId);
            params.put("transaction_id",transactionId);
            try {
                response = transactionsService.get(params);
            } catch (OSTAPIService.MissingParameter missingParameter) {
                missingParameter.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OSTAPIService.InvalidParameter invalidParameter) {
                invalidParameter.printStackTrace();
            }
            System.out.println("response: " + response.toString() );
            Assert.assertEquals(transaction_status,TransactionsDriver.getTransactionStatus(response));
        });
    }

    @When("^I make GET request to get transaction details with defined user id and transaction id$")
    public void get_transaction_details_with_defined_userid_transactionId() {

        transactionsService = services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("transaction_id",TestDataManager.economy1.transaction_Id);
         try {
                response = transactionsService.get(params);
            } catch (OSTAPIService.MissingParameter missingParameter) {
                missingParameter.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OSTAPIService.InvalidParameter invalidParameter) {
                invalidParameter.printStackTrace();
            }
            System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get Transaction list for defined user$")
    public void get_transactions_list() {
        transactionsService = services.transactions;

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        try {
            response = transactionsService.getList( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request of Company transfers (\\d+) UBT in wei to same user multiple times via direct transfer method$")
    public void execute_transaction_multiple_transfers_DT(String ubtInWei) {
        int numberOfTransfers = 3;
        transactionsService = services.transactions;

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);


        ArrayList<String> amount= new ArrayList<String>();
        ArrayList<String> to_user_token_holder= new ArrayList<String>();
        for(int i=0; i<numberOfTransfers;i++) {
            amount.add(ubtInWei);
            to_user_token_holder.add(TestDataManager.economy1.user_TH);
        }

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .setUser2TokenHolderAddress(to_user_token_holder)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

        company_new_Balance = TransactionsDriver.subtract(company_old_Balance,ubtInWei);
        user_new_Balance = TransactionsDriver.add(user_old_Balance,ubtInWei);
    }


    @When("^I make POST request of Company transfers (\\d+) USD in wei to same user multiple times via pay method$")
    public void execute_transactions_multiple_transfers_pay(String usdInWei) {

        int numberOfTransfers = 3;
        transactionsService = services.transactions;

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);


        ArrayList<String> amount= new ArrayList<String>();
        ArrayList<String> to_user_token_holder= new ArrayList<String>();
        for(int i=0; i<numberOfTransfers;i++) {
            amount.add(usdInWei);
            to_user_token_holder.add(TestDataManager.economy1.user_TH);
        }

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setAmount(amount)
                .setUser2TokenHolderAddress(to_user_token_holder)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

        company_new_Balance = TransactionsDriver.subtract(company_old_Balance,usdInWei);
        user_new_Balance = TransactionsDriver.add(user_old_Balance,usdInWei);
    }

    @When("^I make POST request of Company transfer more than its balance to user via direct transfer method$")
    public void execute_transaction_insufficient_balance_c2u_DT() {
        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        System.out.println("company old balance: "+company_old_Balance);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(TransactionsDriver.add(company_old_Balance,"10"));

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setAmount(amount)
                .build();

        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

    }

    @When("^I make POST request to execute transaction with (.+) and (.+) and (.+) via direct transfer method$")
    public void execute_transaction_meta_property_c2u_DT(String meta_name, String meta_type, String meta_details) {
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setMeta_name(meta_name)
                .setMeta_type(meta_type)
                .setMeta_details(meta_details)
                .build();

        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with direct transfer's rule via pay method$")
    public void execute_transaction_DTrule_c2u_pay() {
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setToAddress(TestDataManager.economy1.directTransfer_TR)
                .build();

        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with pay's rule via Direct transfer method$")
    public void execute_transaction_PayRule_c2u_Dt() {
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setToAddress(TestDataManager.economy1.pricer_TR)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

    }

    @When("^I make POST request to execute transaction from user's id or company to user transaction via direct transfer method$")
    public void execute_transaction_from_userId_DT() {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setUserId(TestDataManager.economy1.user_Id)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with rule address as (.+) via direct method$")
    public void execute_transaction_invalid_ruleAddress_DT(String toAddress) {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setToAddress(toAddress)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with token holder address as (.+) via direct method$")
    public void execute_transaction_invalid_token_holder_DT(String tokenHolder) {

        ArrayList<String> th= new ArrayList<String>();
        th.add(tokenHolder);
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setUser2TokenHolderAddress(th)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with method name as (.+) via direct method$")
    public void execute_transaction_invalid_method_name(String methodName) {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setRuleName(methodName)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }


    @When("^I make POST request of execute transaction with amount as (.+) via direct transfer method$")
    public void execute_transaction_amount_c2u_DT(String ubtInWei) {
        ArrayList<String> amount= new ArrayList<String>();
        amount.add(ubtInWei);
        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with pricer as(.+) via pay method$")
    public void execute_transaction_pricer_c2u_Pay(String pricer) {
        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
               .setOstToUsd(pricer)
                .build();

        System.out.println(params);
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with currency as (.+) via pay method$")
    public void execute_transaction_currency_c2u_DT(String currencyCode) {

        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setPayCurrencyCode(currencyCode)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request of execute transaction with method name (.+) via pay method$")
    public void execute_transaction_method_c2u_pay(String methodName) {

        transactionsService = services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder()
                .setRuleName(methodName)
                .build();
        try {
            response = transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get transaction details with defined user and transaction id as (.+).$")
    public void get_transaction_transaction_id(String transactionId) {

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("transaction_id",transactionId);
        try {
            response = transactionsService.get(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );

    }

    @And("^I should get total transaction count$")
    public void get_transaction_count() {
        transaction_count = TransactionsDriver.get_transaction_count(response);
    }

    @When("^I make GET request to get transactions with filter as all the statuses$")
    public void get_transaction_all_filters() {
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);

        ArrayList<Object> statusArray = new ArrayList<>();
        statusArray.add("CREATED");
        statusArray.add("SUBMITTED");
        statusArray.add("SUCCESS");
        statusArray.add("FAILED");
        params.put("status", statusArray);

        try {
            response = transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get the same transaction count as early$")
    public void verify_transaction_count() {
        Assert.assertEquals("Total transaction count with filters: ",transaction_count,TransactionsDriver.get_transaction_count(response));
    }

    @When("^I make GET request to get transaction list with limit as (.+)$")
    public void get_transaction_list_with_limit(Object limit) {
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("limit",limit);

        try {
            response = transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @And("^I should get the Transaction list with number of transaction as (.+)$")
    public void verify_transaction_list_with_limit(String expected_limit) {

        String actual_limit = TransactionsDriver.get_list_number_of_transaction(response);
        if(expected_limit.equals(actual_limit))
        {
            Assert.assertTrue(true);
        }
        else
        {
            if(ResultDriver.pagination_identifier_present(response))
            {
                Assert.fail("Limit is not validate");
            }
            else
            {
                System.out.println("Limit is not matching. This is because Economy might not have these many users created");
            }
        }
    }

    @When("^I make GET request to get Transaction list with pagination identifier as (.+)$")
    public void get_transactions_list_with_pagination_identifier(String pagination_identifier) {

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("pagination_identifier",pagination_identifier);

        try {
            response = transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make POST request to execute transaction with multiple meta properties$")
    public void get_transaction_with_multiple_meta_properties(DataTable dataTable) {

        HashMap <String,Object> params = new HashMap<String,Object>();

        ArrayList<HashMap<String, Object>> metaPropertyArray = new ArrayList<HashMap<String, Object>>();

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for(int i=0; i<list.size(); i++) {
            System.out.println(list.get(i).get("meta_name"));
            System.out.println(list.get(i).get("meta_type"));
            System.out.println(list.get(i).get("meta_details"));

            HashMap <String,Object> metaPropertyArrayParams = new HashMap<String,Object>();
            metaPropertyArrayParams.put("name", list.get(i).get("meta_name")); //like, download IMP : Max length 25 characters (numbers alphabets spaces _ - allowed)
            metaPropertyArrayParams.put("type", list.get(i).get("meta_type")); // user_to_user, company_to_user, user_to_company
            metaPropertyArrayParams.put("details",list.get(i).get("meta_details")); // memo field to add additional info about the transaction .  IMP : Max length 120 characters (numbers alphabets spaces _ - allowed)
            metaPropertyArray.add(metaPropertyArrayParams);
        }
        params.put("user_id", TestDataManager.economy1.company_Id);

        Gson gsonObj = new Gson();
        String metaPropertyArrayJsonStr = gsonObj.toJson(metaPropertyArray);
        params.put("meta_property", metaPropertyArrayJsonStr);

        try {
            response = transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @When("^I make GET request to get transactions list details with defined user id$")
    public void get_transactions_list_with_userId() {

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        try {
            response = transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("response: " + response.toString() );
    }

    @Then("^I should get full list of transaction with pagination identifier$")
    public void get_transaction_list_with_pagination_identifier() {

        String pagination_identifier;
        int transaction_count = TransactionsDriver.get_transaction_count(response);
        int count = transaction_count/10;       //We are not adding limit so 10 is default limit

        if(count<=0)
        {
            Assert.assertEquals(false,ResultDriver.pagination_identifier_present(response));
        }
        else
        {
            for(int i = 0; i<count; i++)
            {
                pagination_identifier = ResultDriver.get_pagination_identifier(response);
                get_transactions_list_with_pagination_identifier(pagination_identifier);
            }
            Assert.assertEquals("Pagination identifier should not present: ",false,ResultDriver.pagination_identifier_present(response));
        }


    }
}
