package com.platform.steps.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ost.services.OSTAPIService;
import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.drivers.*;
import com.platform.managers.TestDataManager;
import com.platform.managers.UserData;
import com.platform.utils.AssertionUtils;
import com.platform.utils.EthAddress;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.*;

public class DevicesSteps {

    EthAddress ethAddress = new EthAddress();
    String deviceAddress;

    DevicesDriver devicesDriver = new DevicesDriver();
    ResultDriver resultDriver = new ResultDriver();
    UsersDriver usersDriver = new UsersDriver();
    DeviceManagerDriver deviceManagerDriver = new DeviceManagerDriver();
    String new_device_public;
    String new_device_private;
    String new_api_signer_public;
    String new_api_signer_private;
    String new_linked_address;

    private static String INITIATE_RECOVERY_STRUCT = "InitiateRecoveryStruct";
    private static String ABORT_RECOVERY_STRUCT = "AbortRecoveryStruct";


    private Base_API base;

    public DevicesSteps(Base_API base) {
        this.base = base;
    }


    @When("^I make POST request to create device with defined user$")
    public void create_device() {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    @And("^I should get newly created device details$")
    public void get_device_address() {
        base.devicesService = base.services.devices;
        deviceAddress = devicesDriver.get_device_address(base.response);
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);
//        JsonObject base.response = null;
        try {
            base.response = base.devicesService.get( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make POST request to create device with user as (.+)$")
    public void create_device_with_userId(String userId) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        base.deviceAddress = ethAddress.getNewEthKeys();
        params.put("address", base.deviceAddress.get(Constant.ETH.ADDRESS).getAsString());
        base.api_signer_object = ethAddress.getNewEthKeys();
        params.put("api_signer_address",base.api_signer_object.get(Constant.ETH.ADDRESS).getAsString());
       // System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make POST request to create device with device address as (.+)$")
    public void create_device_with_device_address(String deviceAddress) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",deviceAddress );
        params.put("api_signer_address", ethAddress.getNewEthAddress());
        System.out.println();
        System.out.println(params);

        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make POST request to create device with api signer address as (.+)$")
    public void create_device_with_api_signer(String api_signer_address) {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",TestDataManager.economy1.user_Id);
        params.put("address",ethAddress.getNewEthAddress() );
        params.put("api_signer_address", api_signer_address);
        System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get user device details for defined user and device address$")
    public void get_device_with_user_device_address() {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @And("^Device status should be (.+)$")
    public void get_device_status(String deviceStatus) {
        Assert.assertEquals(deviceStatus,devicesDriver.get_device_status(base.response));
    }

    @When("^I make GET request to get device details with user id as (.+)$")
    public void get_device_with_userID(String userId) {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userId);
        params.put("device_address", TestDataManager.economy1.device_address);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get device with device address as (.+)$")
    public void get_device_with_device_address(String deviceAddress) {

        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("device_address", deviceAddress);

        System.out.println(params);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get device lists for a defined user$")
    public void get_devices_list() {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        System.out.println(params);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get device lists with user id as (.+)$")
    public void get_device_list_with_userID(String userID) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", userID);
        System.out.println(params);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    @When("^I make GET request to get device list with limit as (.+)$")
    public void get_device_list_with_limit(Object limit) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("limit",limit);
        try {
            base.response = base.devicesService.getList( params );
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @And("^I should get the device list with (.+) members$")
    public void verify_list_with_limit(int expected_limit) {
        int actual_limit= devicesDriver.get_list_number_of_devices(base.response);
        if(expected_limit==actual_limit)
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
                System.out.println("Limit is not matching. This is because Economy might not have these many members created");
            }
        }
    }

    @When("^I make GET request to get device list with pagination identifier as (.+)$")
    public void get_device_list_with_pagination_identifier(String pagination_identifier) {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);
        params.put("pagination_identifier",pagination_identifier);
        try {
            base.response = base.devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make GET request to get devices list with defined devices address array$")
    public void get_devices_list_with_address_array() {
        base.devicesService = base.services.devices;
        HashMap <String,Object> params = new HashMap<String,Object>();
        params.put("user_id", TestDataManager.economy1.user_Id);

        ArrayList<Object> addressesArray = new ArrayList<Object>();
        addressesArray.add(TestDataManager.economy1.device_address);
        params.put("addresses", addressesArray);

        try {
            base.response = base.devicesService.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make POST request to authorize new device$")
    public void authorize_device_new_deviceID() throws IOException {



        //Get Device manager address
        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);

        UserData.getInstance().device_manager_address = usersDriver.get_device_manager_address(base.response);

        // Setting up new device for same user

        JsonObject deviceObject = ethAddress.getNewEthKeys();
        new_device_public = deviceObject.get(Constant.ETH.ADDRESS).getAsString();
        new_device_private = deviceObject.get(Constant.ETH.PRIVATEKEY).getAsString();

        JsonObject apiSignerObject = ethAddress.getNewEthKeys();
        new_api_signer_public = apiSignerObject.get(Constant.ETH.ADDRESS).getAsString();
        new_api_signer_private = apiSignerObject.get(Constant.ETH.PRIVATEKEY).getAsString();

        create_device_with_userId_deviceAdd(UserData.getInstance().user_id, new_device_public,new_api_signer_public);
        get_device_with_userId_deviceAdd(UserData.getInstance().user_id,new_device_public);
        System.out.println("New device is object is "+base.response.toString());


        // Generating raw call data and call data
        String rawCallData = new GnosisSafe().getAddOwnerWithThresholdCallData(new_device_public);
        String callData = new GnosisSafe().getAddOwnerWithThresholdExecutableData(new_device_public);


        String nonce;

        DeviceManagerSteps deviceManagerSteps = new DeviceManagerSteps(base);
        deviceManagerSteps.get_device_manager_with_userId(UserData.getInstance().user_id);
        nonce = deviceManagerDriver.get_nonce(base.response);


        String transactionHash=null;
        // Get transaction HASH
        try {
            transactionHash = DeviceAddTransactionHash(UserData.getInstance().device_manager_address,callData,nonce);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String signature = signWithDeviceKey(transactionHash);


        Map<String, Object> map = new OstPayloadBuilder()
                .setDataDefination("authorize_device".toUpperCase())
                .setRawCalldata(rawCallData)
                .setCallData(callData)
                .setSignatures(signature)
                .setTo(UserData.getInstance().device_manager_address)
                .setSigners(Arrays.asList(UserData.getInstance().device_address_public))
                .setNonce(String.valueOf(nonce))
                .build();

        base.response = devicesDriver.postAuthorizeDevice(map,UserData.getInstance().user_id,UserData.getInstance().api_signer_private);

        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    public void create_device_with_userId_deviceAdd(String userId, String deviceAddress)
    {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        params.put("address", deviceAddress);
        String api_signer_public = ethAddress.getNewEthAddress();
        params.put("api_signer_address",api_signer_public);
        // System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        System.out.println("base.response: " + base.response.toString() );
    }

    public void create_device_with_userId_deviceAdd(String userId, String deviceAddress, String apiSignerAdd)
    {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        params.put("address", deviceAddress);
        params.put("api_signer_address",apiSignerAdd);
        // System.out.println(params);
        try {
            base.response = base.devicesService.create( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    public void get_device_with_userId_deviceAdd(String userId, String deviceAddress)
    {
        base.devicesService = base.services.devices;
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user_id",userId);
        params.put("device_address", deviceAddress);
        try {
            base.response = base.devicesService.get( params );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSTAPIService.InvalidParameter invalidParameter) {
            invalidParameter.printStackTrace();
        } catch (OSTAPIService.MissingParameter missingParameter) {
            missingParameter.printStackTrace();
        }
        base.scenario.write("Params: \n"+params.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    public String DeviceAddTransactionHash(String deviceManagerAddress, String data, String nonce) throws Exception {
        JSONObject transactionObject = new GnosisSafe().getSafeTxData(deviceManagerAddress, deviceManagerAddress ,"0", data, "0", "0", "0", "0", "0x0000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000", nonce);
        return new EIP712(transactionObject).toEIP712TransactionHash();
    }




    public String signWithDeviceKey(String messageHash) {
        byte[] data = Numeric.hexStringToByteArray(messageHash);
        return signWithDeviceKey(data);

        // @Dev: don't worry about data here
        // Its just bytes of messageHash.
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




    public String signDataWithRecoveryKey(String messageHash) {
        byte[] data = Numeric.hexStringToByteArray(messageHash);
        return signDataWithRecoveryKey(data);

        // @Dev: don't worry about data here
        // Its just bytes of messageHash.
    }

    /**
     * Sign data using the current device key of the user.
     * @param data byte[] of Hex String (messageHash) to sign.
     * @return signature
     */
    public String signDataWithRecoveryKey(byte[] data) {
        //Get the keyId.

        ECKeyPair ecKeyPair;
        try
        {
            String privateKey = UserData.getInstance().recovery_owner_add_private;
            ecKeyPair = ECKeyPair.create(Numeric.hexStringToByteArray(privateKey));
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

    @When("^I make POST request to revoke existing device$")
    public void post_request_revoke_device() throws IOException {

        //Get device object and extract linked_address form it. Store it in UserData.getInstance()
        get_device_with_userId_deviceAdd(UserData.getInstance().user_id,UserData.getInstance().device_address_public);
        UserData.getInstance().linked_address = devicesDriver.get_linked_address(base.response);


        //Get new Authorised devices which is not stored in UserData.getInstance()

        // Authorize new device
        authorize_device_new_deviceID();
        String deviceToBeRevoked = devicesDriver.get_device_address(base.response);

        AssertionUtils.repeatWhenFailedForSeconds(100, ()->
        {
            get_device_with_userId_deviceAdd(UserData.getInstance().user_id, deviceToBeRevoked);
            Assert.assertEquals(true,devicesDriver.is_status(Constant.STATUS.AUTHORIZED,base.response));
        });


        // Creating callData and RawCall data
        String callData = new GnosisSafe().getRemoveOwnerWithThresholdExecutableData(UserData.getInstance().linked_address, deviceToBeRevoked);
        String rawCallData = new GnosisSafe().getRemoveOwnerWithThresholdCallData(UserData.getInstance().linked_address, deviceToBeRevoked);

        //Getting nonce from Device manager
        String nonce;
        DeviceManagerSteps deviceManagerSteps = new DeviceManagerSteps(base);
        deviceManagerSteps.get_device_manager_with_userId(UserData.getInstance().user_id);
        nonce = deviceManagerDriver.get_nonce(base.response);


        //Get Device manager address
        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);

        UserData.getInstance().device_manager_address = usersDriver.get_device_manager_address(base.response);


        // Generating transaction Hash

        String transactionHash=null;
        try {
            transactionHash = DeviceAddTransactionHash(UserData.getInstance().device_manager_address,callData,nonce);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String signature = signWithDeviceKey(transactionHash);


        Map<String, Object> map = new OstPayloadBuilder()
                .setDataDefination("revoke_device".toUpperCase())
                .setRawCalldata(rawCallData)
                .setCallData(callData)
                .setTo(UserData.getInstance().device_manager_address)
                .setSignatures(signature)
                .setSigners(Arrays.asList(UserData.getInstance().device_address_public))
                .setNonce(String.valueOf(nonce))
                .build();

        base.response = devicesDriver.postRevokeDevice(map,UserData.getInstance().user_id,UserData.getInstance().api_signer_private);

        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @When("^I make post request to initiate recovery$")
    public void post_request_recover_device() throws IOException {


        //Get device object and extract linked_address form it. Store it in UserData.getInstance()
        get_device_with_userId_deviceAdd(UserData.getInstance().user_id,UserData.getInstance().device_address_public);
        UserData.getInstance().linked_address = devicesDriver.get_linked_address(base.response);

        UsersSteps usersSteps = new UsersSteps(base);
        usersSteps.get_user_with_userID(UserData.getInstance().user_id);
        UserData.getInstance().recovery_address = usersDriver.get_recovery_address(base.response);


        //Get new Registered devices which is not stored in UserData.getInstance()

        JsonObject deviceObject = ethAddress.getNewEthKeys();
        new_device_public = deviceObject.get(Constant.ETH.ADDRESS).getAsString();
        new_device_private = deviceObject.get(Constant.ETH.PRIVATEKEY).getAsString();

        JsonObject apiSignerObject = ethAddress.getNewEthKeys();
        new_api_signer_public = apiSignerObject.get(Constant.ETH.ADDRESS).getAsString();
        new_api_signer_private = apiSignerObject.get(Constant.ETH.PRIVATEKEY).getAsString();

        create_device_with_userId_deviceAdd(UserData.getInstance().user_id, new_device_public,new_api_signer_public);
        get_device_with_userId_deviceAdd(UserData.getInstance().user_id,new_device_public);
        //new_linked_address = devicesDriver.get_linked_address(base.response);
        System.out.println("New device is object is "+base.response.toString());


        /**
         *  New device is just registered
         *  UserData.instance() is already authorized
         */

        String temp_device_public = new_device_public;
        String temp_device_private = new_device_private;
        String temp_api_signer_public = new_api_signer_public;
        String temp_api_signer_private = new_api_signer_private;


        new_device_public = UserData.getInstance().device_address_public;
        new_device_private = UserData.getInstance().device_address_private;
        new_api_signer_public = UserData.getInstance().api_signer_public;
        new_api_signer_private = UserData.getInstance().device_address_private;
        new_linked_address = UserData.getInstance().linked_address;


        UserData.getInstance().device_address_public = temp_device_public;
        UserData.getInstance().device_address_private = temp_device_private;
        UserData.getInstance().api_signer_public = temp_api_signer_public;
        UserData.getInstance().api_signer_private = temp_api_signer_private;
        UserData.getInstance().linked_address = null;


        /**
         * UserData.getInstance() is the registered and going to authorise
         *  At this stage new device is authorised which is going to revoke
         */

        //UserData.getInstance().device_address_private = te

        String prevOwner = new_linked_address;
        String oldOwner = new_device_public;
        String newOwner = UserData.getInstance().device_address_public;
        String recoveryAddress = UserData.getInstance().recovery_address;


        // Generating message hash
        JSONObject typedData = new DelayedRecoveryModule().getRecoveryOperationTypedData(prevOwner, oldOwner, newOwner, recoveryAddress, INITIATE_RECOVERY_STRUCT);
        String messageHash = getEIP712SignHash(typedData);


        String signature = signDataWithRecoveryKey(messageHash);


        Map<String, Object> map = new HashMap<>();
        map.put("to", UserData.getInstance().recovery_address);
        map.put("verifying_contract", UserData.getInstance().recovery_address);
        map.put("old_linked_address",prevOwner );
        map.put("old_device_address", oldOwner);
        map.put("new_device_address", newOwner);
        map.put("signature", signature);
        map.put("signer", UserData.getInstance().recovery_owner_add_public);


        base.response = devicesDriver.postInitiateRecovery(map,UserData.getInstance().user_id,UserData.getInstance().api_signer_private);

        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }


    private String getEIP712SignHash(JSONObject typedData) {
        try {
            return new EIP712(typedData).toEIP712TransactionHash();
        } catch (Exception e) {
            return null;
        }
    }

    @And("^Device should be in recovering state and another device in revoking state$")
    public void verify_device_status_after_initiate_recovery() throws InterruptedException {
        // As initiate recovery will take time to mine in block chain, putting sleep to transaction to confirm

        Thread.sleep((Constant.TRANSACTIONS.CONFIRMATION_TIME+50)*1000);

        AssertionUtils.repeatWhenFailedForSeconds(100, ()->
        {
            get_device_with_userId_deviceAdd(UserData.getInstance().user_id, new_device_public);
            Assert.assertEquals(true,devicesDriver.is_status(Constant.STATUS.REVOKING,base.response));
        });

        AssertionUtils.repeatWhenFailedForSeconds(100, ()->
        {
            get_device_with_userId_deviceAdd(UserData.getInstance().user_id, UserData.getInstance().device_address_public);
            Assert.assertEquals(true,devicesDriver.is_status(Constant.STATUS.RECOVERING,base.response));
        });
    }

    @When("^I make post request to abort recovery$")
    public void post_request_abort_recovery() throws IOException {

        String prevOwner = new_linked_address;
        String oldOwner = new_device_public;
        String newOwner = UserData.getInstance().device_address_public;
        String recoveryAddress = UserData.getInstance().recovery_address;


        // Generating message hash
        JSONObject typedData = new DelayedRecoveryModule().getRecoveryOperationTypedData(prevOwner, oldOwner, newOwner, recoveryAddress, ABORT_RECOVERY_STRUCT);
        String messageHash = getEIP712SignHash(typedData);


        String signature = signDataWithRecoveryKey(messageHash);


        Map<String, Object> map = new HashMap<>();
        map.put("to", UserData.getInstance().recovery_address);
        map.put("verifying_contract", UserData.getInstance().recovery_address);
        map.put("old_linked_address",prevOwner );
        map.put("old_device_address", oldOwner);
        map.put("new_device_address", newOwner);
        map.put("signature", signature);
        map.put("signer", UserData.getInstance().recovery_owner_add_public);


        base.response = devicesDriver.postAbortRecovery(map,UserData.getInstance().user_id,UserData.getInstance().api_signer_private);
        base.scenario.write("Params: \n"+map.toString()+"\n");
        String formattedData=new GsonBuilder().setPrettyPrinting()
                .create().toJson(base.response);
        base.scenario.write(formattedData+"\n");
        System.out.println("base.response: \n"+formattedData+"\n");
    }

    @And("^both the devices should be revert to their initial states$")
    public void verify_devices_after_abort_recovery() {

        AssertionUtils.repeatWhenFailedForSeconds(100, ()->
        {
            get_device_with_userId_deviceAdd(UserData.getInstance().user_id, new_device_public);
            Assert.assertEquals(true,devicesDriver.is_status(Constant.STATUS.AUTHORIZED,base.response));
        });

        AssertionUtils.repeatWhenFailedForSeconds(100, ()->
        {
            get_device_with_userId_deviceAdd(UserData.getInstance().user_id, UserData.getInstance().device_address_public);
            Assert.assertEquals(true,devicesDriver.is_status(Constant.STATUS.REGISTERED,base.response));
        });

    }
}
