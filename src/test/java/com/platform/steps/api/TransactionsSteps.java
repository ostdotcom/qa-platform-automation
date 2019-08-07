package com.platform.steps.api;


import com.google.gson.Gson;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.*;

import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import com.platform.utils.AssertionUtils;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;


import java.util.*;

public class TransactionsSteps {


    private Base_API base;

    public TransactionsSteps(Base_API base) {
        this.base = base;
    }


    String transactionId;
    String from_userId;
    String company_old_Balance;
    String company_new_Balance;
    String user_new_Balance;
    String user_old_Balance;
    int transaction_count;
    
    TransactionsDriver transactionsDriver = new TransactionsDriver();
    BalanceDriver balanceDriver = new BalanceDriver();

    TokenDriver tokenDriver = new TokenDriver();
    PricePointDriver pricePointDriver = new PricePointDriver();
    ResultDriver resultDriver = new ResultDriver();


    private String getBalance(String user_id) {

        base.balancesService = base.services.balance;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", user_id);

        // Wait till the unsettled balance is cleared. Then only get the available balance of user
        AssertionUtils.repeatWhenFailedForSeconds(Constant.TRANSACTIONS.TOTALWAIT, ()->
        {
        try {
            params.put("user_id", user_id);
            base.response = base.balancesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
        Assert.assertEquals(true,balanceDriver.is_unsettled_balance_zero(base.response));
        });
        return balanceDriver.get_available_balance(base.response);
    }

    @When("^I make POST request of Company transfers (.+) UBT in wei to user via direct transfer method$")
    public void execute_transaction_c2u_direct(String ubtInWei) {

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(ubtInWei);
        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

        company_new_Balance = transactionsDriver.subtract(company_old_Balance,ubtInWei);
        user_new_Balance = transactionsDriver.add(user_old_Balance,ubtInWei);

    }

    @When("^I make POST request of Company transfers (.+) (.+) in wei to user via pay method$")
    public void  execute_transaction_c2u_pay(String usdInWei, String fiatCurrency) {

        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        user_old_Balance = getBalance(TestDataManager.economy1.user_Id);

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);

        //Get Conversion factor & Stake currency (Base token) for calculation of USD to UBT
        TokensSteps tokensSteps = new TokensSteps(base);
        tokensSteps.get_tokens();
        String conversion_factor = tokenDriver.get_conversion_factor(base.response);
        String base_token = tokenDriver.get_base_token(base.response);
        int base_token_decimals = tokenDriver.get_token_decimal(base.response);


        // Get current price
        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_price(base.response,base_token,fiatCurrency);
        int fiat_decimals = pricePointDriver.get_fiat_decimals(base.response,base_token);


        String transferredUbt = transactionsDriver.get_ubt_from_usd(usdInWei,base_token_decimals,pricePoint,fiat_decimals,conversion_factor);

        System.out.println("About to transfer UBT: "+transferredUbt);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(usdInWei);
        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setPayCurrencyCode(fiatCurrency)
                .setAmount(amount)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

        company_new_Balance = transactionsDriver.subtract(company_old_Balance,transferredUbt);
        user_new_Balance = transactionsDriver.add(user_old_Balance,transferredUbt);
    }

    @And("^Company's balance should be debited$")
    public void verify_company_balance() {

        String balance = getBalance(TestDataManager.economy1.company_Id);
        System.out.println("new Company Balance :"+balance);
        Assert.assertEquals(company_new_Balance, balance);
        }

    @And("^User's balance should be credited$")
    public void verify_user_balance() {
        System.out.println("new User Balance :"+getBalance(TestDataManager.economy1.user_Id));
        Assert.assertEquals("Updated User balance should be ",user_new_Balance, getBalance(TestDataManager.economy1.user_Id));
    }

    @And("^I should get Transaction status as (.+)$")
    public void verify_transaction_status(String transaction_status) throws InterruptedException {

        Thread.sleep(Constant.TRANSACTIONS.CONFIRMATION_TIME*1000);
        base.transactionsService = base.services.transactions;
        transactionId = transactionsDriver.getTransactionId(base.response);
        from_userId = transactionsDriver.getFromUserId(base.response);
        HashMap <String,Object> params = new HashMap<String,Object>();

        AssertionUtils.repeatWhenFailedForSeconds(Constant.TRANSACTIONS.TOTALWAIT, ()->
        {
            params.put("user_id", from_userId);
            params.put("transaction_id",transactionId);
            try {
                base.response = base.transactionsService.get(params);
            } catch (OSTAPIService.MissingParameter missingParameter) {
                missingParameter.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OSTAPIService.InvalidParameter invalidParameter) {
                invalidParameter.printStackTrace();
            }
            System.out.println("base.response: " + base.response.toString() );
            Assert.assertEquals(transaction_status,transactionsDriver.getTransactionStatus(base.response));
        });
    }

    @When("^I make GET request to get transaction details with defined user id and transaction id$")
    public void get_transaction_details_with_defined_userid_transactionId() {

        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("transaction_id",TestDataManager.economy1.transaction_Id);
         try {
                base.response = base.transactionsService.get(params);
            } catch (OSTAPIService.MissingParameter missingParameter) {
                missingParameter.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OSTAPIService.InvalidParameter invalidParameter) {
                invalidParameter.printStackTrace();
            }
            System.out.println("response: " + base.response.toString() );
    }

    @When("^I make GET request to get Transaction list for defined user$")
    public void get_transactions_list() {
        base.transactionsService = base.services.transactions;

        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        try {
            base.response = base.transactionsService.getList( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request of Company transfers (.+) UBT in wei to same user (.+) times via direct transfer method$")
    public void execute_transaction_multiple_transfers_DT(String ubtInWei, int numberOfTransfers ) {
        base.transactionsService = base.services.transactions;

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
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

        company_new_Balance = transactionsDriver.subtract(company_old_Balance,ubtInWei,numberOfTransfers);
        user_new_Balance = transactionsDriver.add(user_old_Balance,ubtInWei,numberOfTransfers);
    }


    @When("^I make POST request of Company transfers (.+) USD in wei to same user (.+) times via pay method$")
    public void execute_transactions_multiple_transfers_pay(String usdInWei, int numberOfTransfers) {

        base.transactionsService = base.services.transactions;
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
        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setAmount(amount)
                .setUser2TokenHolderAddress(to_user_token_holder)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

        company_new_Balance = transactionsDriver.subtract(company_old_Balance,usdInWei);
        user_new_Balance = transactionsDriver.add(user_old_Balance,usdInWei);
    }

    @When("^I make POST request of Company transfer more than its balance to user via direct transfer method$")
    public void execute_transaction_insufficient_balance_c2u_DT() {

        base.transactionsService = base.services.transactions;
        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
        System.out.println("company old balance: "+company_old_Balance);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(transactionsDriver.add(company_old_Balance,"10"));

        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setAmount(amount)
                .build();

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

    }

    @When("^I make POST request to execute transaction with (.+) and (.+) and (.+) via direct transfer method$")
    public void execute_transaction_meta_property_c2u_DT(String meta_name, String meta_type, String meta_details) {
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setMeta_name(meta_name)
                .setMeta_type(meta_type)
                .setMeta_details(meta_details)
                .build();

        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with direct transfer's rule via pay method$")
    public void execute_transaction_DTrule_c2u_pay() {

        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setToAddress(TestDataManager.economy1.directTransfer_TR)
                .build();

        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with pay's rule via Direct transfer method$")
    public void execute_transaction_PayRule_c2u_Dt() {
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setToAddress(TestDataManager.economy1.pricer_TR)
                .build();
        base.transactionsService = base.services.transactions;
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

    }

    @When("^I make POST request to execute transaction from user's id or company to user transaction via direct transfer method$")
    public void execute_transaction_from_userId_DT() {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setUserId(TestDataManager.economy1.user_Id)
                .build();

        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with rule address as (.+) via direct method$")
    public void execute_transaction_invalid_ruleAddress_DT(String toAddress) {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setToAddress(toAddress)
                .build();

        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with token holder address as (.+) via direct method$")
    public void execute_transaction_invalid_token_holder_DT(String tokenHolder) {

        ArrayList<String> th= new ArrayList<String>();
        th.add(tokenHolder);
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setUser2TokenHolderAddress(th)
                .build();

        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with method name as (.+) via direct method$")
    public void execute_transaction_invalid_method_name(String methodName) {

        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setRuleName(methodName)
                .build();
        base.transactionsService = base.services.transactions;

        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }


    @When("^I make POST request of execute transaction with amount as (.+) via direct transfer method$")
    public void execute_transaction_amount_c2u_DT(String ubtInWei) {
        ArrayList<String> amount= new ArrayList<String>();
        amount.add(ubtInWei);
        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with pricer as(.+) via pay method$")
    public void execute_transaction_pricer_c2u_Pay(String pricer) {
        base.transactionsService = base.services.transactions;

        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
               .setOstToUsd(pricer)
                .build();

        System.out.println(params);
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with currency as (.+) via pay method$")
    public void execute_transaction_currency_c2u_DT(String currencyCode) {

        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setPayCurrencyCode(currencyCode)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request of execute transaction with method name (.+) via pay method$")
    public void execute_transaction_method_c2u_pay(String methodName) {

        PricePointSteps pricePointSteps = new PricePointSteps(base);
        pricePointSteps.get_price_point_with_aux_chain_id();
        String pricePoint = pricePointDriver.get_ost_price(base.response);

        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.PayParamsBuilder(pricePoint)
                .setRuleName(methodName)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get transaction details with defined user and transaction id as (.+).$")
    public void get_transaction_transaction_id(String transactionId) {

        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("transaction_id",transactionId);
        try {
            base.response = base.transactionsService.get(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

    }

    @And("^I should get total transaction count$")
    public void get_transaction_count() {
        transaction_count = transactionsDriver.get_transaction_count(base.response);
    }

    @When("^I make GET request to get transactions with filter as all the statuses$")
    public void get_transaction_all_filters() {

        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);

        ArrayList<Object> statusArray = new ArrayList<>();
        statusArray.add("CREATED");
        statusArray.add("SUBMITTED");
        statusArray.add("SUCCESS");
        statusArray.add("FAILED");
        params.put("status", statusArray);

        try {
            base.response = base.transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I should get the same transaction count as early$")
    public void verify_transaction_count() {
        Assert.assertEquals("Total transaction count with filters: ",transaction_count,transactionsDriver.get_transaction_count(base.response));
    }

    @When("^I make GET request to get transaction list with limit as (.+)$")
    public void get_transaction_list_with_limit(Object limit) {
        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("limit",limit);

        try {
            base.response = base.transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I should get the Transaction list with number of transaction as (.+)$")
    public void verify_transaction_list_with_limit(String expected_limit) {

        String actual_limit = transactionsDriver.get_list_number_of_transaction(base.response);
        if(expected_limit.equals(actual_limit))
        {
            Assert.assertTrue(true);
        }
        else
        {
            if(resultDriver.pagination_identifier_present(base.response))
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
        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("pagination_identifier",pagination_identifier);

        try {
            base.response = base.transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to execute transaction with multiple meta properties$")
    public void get_transaction_with_multiple_meta_properties(DataTable dataTable) {

        base.transactionsService = base.services.transactions;
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
            base.response = base.transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get transactions list details with defined user id$")
    public void get_transactions_list_with_userId() {

        base.transactionsService = base.services.transactions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        try {
            base.response = base.transactionsService.getList(params);
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @Then("^I should get full list of transaction with pagination identifier$")
    public void get_transaction_list_with_pagination_identifier() {

        base.transactionsService = base.services.transactions;
        String pagination_identifier;
        int transaction_count = transactionsDriver.get_transaction_count(base.response);
        int count = transaction_count/10;       //We are not adding limit so 10 is default limit

        if(count<=0)
        {
            Assert.assertEquals(false,resultDriver.pagination_identifier_present(base.response));
        }
        else
        {
            for(int i = 0; i<count; i++)
            {
                pagination_identifier = resultDriver.get_pagination_identifier(base.response);
                get_transactions_list_with_pagination_identifier(pagination_identifier);
            }
            Assert.assertEquals("Pagination identifier should not present: ",false,resultDriver.pagination_identifier_present(base.response));
        }
    }

    @When("^I make POST request of user transfers (.+) UBT in wei to another user vie direct transfer method$")
    public void execute_transaction_u2u_direct(String amountInWei) throws IOException, InterruptedException {

        //Update token holder in UserData.getInstance()
        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);

        UserData.getInstance().user_token_holder = new UsersDriver().get_token_holder(base.response);

        // We need to fund newly created user so, that user can make transfer to another one. Running company to user transaction
        ArrayList<String> fromAddress= new ArrayList<String>();
        fromAddress.add(UserData.getInstance().user_token_holder);
        execute_transaction_c2u_direct(amountInWei, fromAddress);
        verify_transaction_status("SUCCESS");
//

        //List<String> token holder addresses list to where amounts need to be sent
        List<String> tokenHolderAddresses = Arrays.asList(TestDataManager.economy1.user_TH);    //This user from test_data.json

        // List amount size should be same as list of addresses
        List<String> amounts = Arrays.asList(amountInWei);


        String ruleName = Constant.TRANSACTIONS.DIRECTTRANSFERS;
        String ruleAddress = TestDataManager.economy1.directTransfer_TR;

        String userId = UserData.getInstance().user_id;


        String callData = new DirectTransferHelper().getTransactionExecutableData(tokenHolderAddresses, amounts);
        String rawCallData = new DirectTransferHelper().getTransactionRawCallData(tokenHolderAddresses, amounts);
        String spendingBtAmountInWei = new DirectTransferHelper().calDirectTransferSpendingLimit(amounts);


        // Get nonce from sessions (Change the nonce value to fail this transaction on block chain)
        SessionSteps sessionSteps = new SessionSteps(base);
        sessionSteps.get_session_with_user_and_sessionAddress(UserData.getInstance().user_id, UserData.getInstance().session_address_public);

        SessionDriver sessionDriver = new SessionDriver();
        String nonce = sessionDriver.get_nonce(base.response);

        //Generating message hash
        String messageHash = createEIP1077TxnHash(callData, ruleAddress, Integer.parseInt(nonce));


        //Generating signature
        String signature = signWithSession(UserData.getInstance().session_address_private,messageHash);



        Map<String, Object> map = new TransactionsDriver.ExecuteRuleRequestBuilder()
                .setToAddress(ruleAddress)
                .setCallData(callData)
                .setNonce(nonce)
                .setRawCallData(rawCallData)
                .setSignature(signature)
                .setSigner(UserData.getInstance().session_address_public)
                //.setMetaProperty(mMeta)
                .build();


        base.response = transactionsDriver.postExecuteTransaction(map, UserData.getInstance().user_id,UserData.getInstance().api_signer_private);
        System.out.println(base.response);
    }

    private String createEIP1077TxnHash( String callData, String contractAddress, int keyNonce) {
        JSONObject jsonObject;
        String txnHash;
        try {
            jsonObject = new EIP1077.TransactionBuilder()
                    .setTo(contractAddress)
                    .setFrom(UserData.getInstance().user_token_holder)
                    .setCallPrefix(get_EXECUTABLE_CALL_PREFIX())
                    .setData(callData)
                    .setNonce(String.valueOf(keyNonce))
                    .build();
            System.out.println(jsonObject.toString());
            txnHash = new EIP1077(jsonObject).toEIP1077TransactionHash();
        } catch (Exception e) {
            return null;
        }
        return txnHash;
    }

    public String get_EXECUTABLE_CALL_PREFIX() {
        final String EXECUTABLE_CALL_STRING = "executeRule(address,bytes,uint256,bytes32,bytes32,uint8)";
        byte[] feed = EXECUTABLE_CALL_STRING.getBytes();
        String hash = null;
        try {
            hash = new SoliditySha3().soliditySha3(Numeric.toHexString(feed));
        } catch (Exception e) {
        }
        hash = hash.substring(0,10);
        return hash;
    }



    String signWithSession(String session_address_private, String hashToSign) {

        byte[] sessionKey = Numeric.hexStringToByteArray(session_address_private);
        ECKeyPair ecKeyPair = null;

        try{
            ecKeyPair = ECKeyPair.create(sessionKey);
            Sign.SignatureData signatureData = Sign.signMessage(Numeric.hexStringToByteArray(hashToSign), ecKeyPair, false);
            return signatureDataToString(signatureData);
        }
        catch (Throwable th) {
        //Silence it.
        th.printStackTrace();
        return null;
     } finally {
        ecKeyPair = null;
        clearBytes(sessionKey);
        }

    }

    private static String signatureDataToString(Sign.SignatureData signatureData) {
        return Numeric.toHexString(signatureData.getR()) + Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getS())) + String.format("%02x",(signatureData.getV()));
    }


    private static final byte[] nonSecret = ("BYTES_CLEARED_" + String.valueOf((int) (System.currentTimeMillis()))  ).getBytes();
    private static void clearBytes(byte[] secret) {
        if ( null == secret ) { return; }
        for (int i = 0; i < secret.length; i++) {
            secret[i] = nonSecret[i % nonSecret.length];
        }
    }


    public void execute_transaction_c2u_direct(String ubtInWei, ArrayList<String> user_th) {

        // No need to assert and verify this balance. so commenting out
//        company_old_Balance = getBalance(TestDataManager.economy1.company_Id);
//        user_old_Balance = getBalance(user_th.get(0));

        System.out.println("company old balance: "+company_old_Balance);
        System.out.println("user old balance: "+user_old_Balance);

        ArrayList<String> amount= new ArrayList<String>();
        amount.add(ubtInWei);
        base.transactionsService = base.services.transactions;
        HashMap<String, Object> params = new TransactionsDriver.DTParamsBuilder()
                .setAmount(amount)
                .setUser2TokenHolderAddress(user_th)
                .build();
        try {
            base.response = base.transactionsService.execute( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

//        company_new_Balance = transactionsDriver.subtract(company_old_Balance,ubtInWei);
//        user_new_Balance = transactionsDriver.add(user_old_Balance,ubtInWei);

    }

}
