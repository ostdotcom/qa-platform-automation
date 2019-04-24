package com.platform.constants;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Constant {



    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static final String ENVIRONMENT = System.getProperty("env",getEnvironment());
    public static final String PROJECTOS = System.getProperty("os.name");



    public static class TestDataFilePath{

        public static final String TESTDATAFILE = PROJECTPATH + java.io.File.separatorChar + "config"+ java.io.File.separatorChar+ ENVIRONMENT + java.io.File.separatorChar +"test_data.json";

    }

    public static class RESULT_TYPE
    {
        public static final String USER = "user";
        public static final String USERS = "users";
        public static final String CHAIN = "chain";
        public static final String PRICEPOINT = "price_point";
        public static final String TOKEN = "token";
        public static final String RULE = "rules";
        public static final String DEVICE = "device";
        public static final String DEVICES = "devices";
        public static final String DEVICEMANAGER = "device_manager";
        public static final String SESSION = "session";
        public static final String SESSIONS = "sessions";
        public static final String RECOVERYOWNER = "recovery_owner";
        public static final String BALANCE = "balance";
        public static final String TRANSACTION = "transaction";
        public static final String TRANSACTIONS = "transactions";
    }


    public static class RESULT_SCHEMA
    {
        private static final String SCHEMAFOLDER = PROJECTPATH+ java.io.File.separatorChar + "src/main/resources/result_schemas";
        public static final String DETAILEDERROR = SCHEMAFOLDER+ java.io.File.separatorChar + "detailed_error_entity.json";
        public static final String USER = SCHEMAFOLDER+ java.io.File.separatorChar + "user_entity.json";
        public static final String USERS = SCHEMAFOLDER+ java.io.File.separatorChar + "users_entity.json";
        public static final String TOKEN = SCHEMAFOLDER+ java.io.File.separatorChar + "token_entity.json";
        public static final String DEVICE = SCHEMAFOLDER+ java.io.File.separatorChar + "device_entity.json";
        public static final String DEVICES = SCHEMAFOLDER+ java.io.File.separatorChar + "devices_entity.json";
        public static final String TRANSACTION = SCHEMAFOLDER+ java.io.File.separatorChar + "transaction_entity.json";
        public static final String TRANSACTIONS = SCHEMAFOLDER+ java.io.File.separatorChar + "transactions_entity.json";
        public static final String SESSION = SCHEMAFOLDER+ java.io.File.separatorChar + "session_entity.json";
        public static final String SESSIONS = SCHEMAFOLDER+ java.io.File.separatorChar + "sessions_entity.json";
        public static final String PRICEPOINT = SCHEMAFOLDER+ java.io.File.separatorChar + "price_point_entity.json";
        public static final String DEVICEMANAGER = SCHEMAFOLDER+ java.io.File.separatorChar + "device_manager_entity.json";
        public static final String CHAIN = SCHEMAFOLDER+ java.io.File.separatorChar + "chain_entity.json";
        public static final String RECOVERYOWNER = SCHEMAFOLDER+ java.io.File.separatorChar + "recovery_owner_entity.json";
        public static final String RULE = SCHEMAFOLDER+ java.io.File.separatorChar + "rules_entity.json";

    }

    public static class TRANSACTIONS
    {
        public static final String PAY = "pay";
        public static final String DIRECTTRANSFERS = "directTransfers";

        private static final int WAITING_BLOCK_CONFIRMATION = 6;
        private static final int BLOCKHIGHT = 3;        //Time in sec
        public static final int TOTALWAIT = 30;       //Time in sec
        public static final int CONFIRMATION_TIME = (WAITING_BLOCK_CONFIRMATION*BLOCKHIGHT);
    }

    private static String getEnvironment()  {
        String env = null;
        try {
            JsonObject jsonObject= new Gson().fromJson(new FileReader("config/config.json"),JsonObject.class);
             env = jsonObject.get("environment").getAsString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return env;
    }

}
