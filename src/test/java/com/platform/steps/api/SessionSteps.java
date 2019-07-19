package com.platform.steps.api;

import com.google.gson.JsonObject;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.*;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import com.platform.utils.EthAddress;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SessionSteps  {

    String company_session_address;

    SessionDriver sessionDriver = new SessionDriver();
    String new_session_public;
    String new_session_private;
    String spendingLimit = "1000000000000000";
    Long expiryBlock = Long.valueOf(1000);

    UsersDriver usersDriver = new UsersDriver();
    DeviceManagerDriver deviceManagerDriver = new DeviceManagerDriver();

    private Base_API base;

    public SessionSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make GET request to get session for defined session address$")
    public void get_session_details_defined_session_address() {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("session_address", TestDataManager.economy1.session_address);
        try {
            base.response = base.sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get session details with session address as (.+)$")
    public void get_session_details_with_SessionAddress(String session_address) {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("session_address", session_address);
        try {
            base.response = base.sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get transactions list for a company$")
    public void get_sessions_list_for_company() {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        try {
            base.response = base.sessionsService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );

        company_session_address = sessionDriver.get_session_address_from_list(base.response,1);

    }

    @When("^I make GET request to get session details for any of the company's session$")
    public void get_session_Address_with_address() {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("session_address", company_session_address);
        try {
            base.response = base.sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make GET request to get Session list with limit as (.+)$")
    public void get_sessions_with_limit(Object limit) {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("limit",limit);
        try {
            base.response = base.sessionsService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @And("^I should get session list with (.+) sessions$")
    public void verify_session_list_count(int session_count) {
        Assert.assertEquals("Number of session should be: ", session_count,sessionDriver.get_number_of_sessions_from_list(base.response));
    }

    @When("^I make GET request to get session list with pagination identifier as (.+)$")
    public void get_sessions_list_with_pagination_identifier(String pagination_identifier) {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.company_Id);
        params.put("pagination_identifier",pagination_identifier);
        try {
            base.response = base.sessionsService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    @When("^I make POST request to Add session$")
    public void post_request_add_session() throws IOException {

        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);

        UserData.getInstance().user_token_holder = usersDriver.get_token_holder(base.response);

        JsonObject newSessionObject = new EthAddress().getNewEthKeys();
        new_session_public = newSessionObject.get(Constant.ETH.ADDRESS).getAsString();
        new_session_private = newSessionObject.get(Constant.ETH.PRIVATEKEY).getAsString();

        new_session_public = Keys.toChecksumAddress(new_session_public);

        // Get Chain details to get Current block
        ChainSteps chainSteps = new ChainSteps(base);
        chainSteps.get_chain_details_aux();

        // Get Current block from the above response
        ChainDriver chainDriver = new ChainDriver();
        base.current_block = chainDriver.get_current_block(base.response);


        //Get Device manager address
        usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);

        UserData.getInstance().device_manager_address = usersDriver.get_device_manager_address(base.response);


        // Get Nonce of device manager
        DeviceManagerSteps deviceManagerSteps = new DeviceManagerSteps(base);
        deviceManagerSteps.get_device_manager_with_userId(UserData.getInstance().user_id);
        String nonce = deviceManagerDriver.get_nonce(base.response);

        // Call data
        String callData = new GnosisSafe().getAuthorizeSessionExecutableData(new_session_public, spendingLimit, Long.valueOf(base.current_block)+expiryBlock+"");

        // Get Signature

        String transactionHash=null;
        // Get transaction HASH
        try {
            transactionHash = sessionAddTransactionHash(UserData.getInstance().user_token_holder,UserData.getInstance().device_manager_address,callData,nonce);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String signature = signWithDeviceKey(transactionHash);


        Map<String, Object> map = new OstPayloadBuilder()
                .setRawCalldata(new GnosisSafe().getAuthorizeSessionData(new_session_public, spendingLimit, Long.valueOf(base.current_block)+expiryBlock+""))
                .setCallData(callData)
                .setTo(UserData.getInstance().user_token_holder)
                .setSignatures( signature )
                .setSigners(Arrays.asList( UserData.getInstance().device_address_public))
                .setNonce( nonce )
                .build();

        base.response = sessionDriver.postAddSession(map,UserData.getInstance().user_id,UserData.getInstance().api_signer_private);
        System.out.println(base.response);

    }

    private String sessionAddTransactionHash(String tokenHolder, String device_manager_address, String callData, String nonce) throws Exception {
        JSONObject transactionObject = new GnosisSafe().getSafeTxData(tokenHolder, device_manager_address ,"0", callData, "0", "0", "0", "0", "0x0000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000", nonce);
        return new EIP712(transactionObject).toEIP712TransactionHash();
    }

    public String signWithDeviceKey(String messageHash) {
        byte[] data = Numeric.hexStringToByteArray(messageHash);
        return signWithDeviceKey(data);
    }

    /**
     * Sign data using the current device key of the user.
     * @param data byte[] of Hex String (messageHash) to sign.
     * @return signature
     */
    public String signWithDeviceKey(byte[] data) {
        //Get the keyId.

        ECKeyPair ecKeyPair;
        try
        {
            String privateKey = UserData.getInstance().device_address_private;
            ecKeyPair = ECKeyPair.create(Numeric.hexStringToByteArray(UserData.getInstance().device_address_private));
            //Sign the data.
            Sign.SignatureData signatureData = Sign.signMessage(data, ecKeyPair, false);
            return signatureDataToString(signatureData);
        } catch (Throwable th) {
            //Silence it.
            th.printStackTrace();
            return null;
        } finally {
            ecKeyPair = null;
        }
    }

    private static String signatureDataToString(Sign.SignatureData signatureData) {
        return Numeric.toHexString(signatureData.getR()) + Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getS())) + String.format("%02x",(signatureData.getV()));
    }

    public void get_session_with_user_and_sessionAddress(String user_Id, String session_address) {

        base.sessionsService = base.services.sessions;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", user_Id);
        params.put("session_address", session_address);
        try {
            base.response = base.sessionsService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }
}
