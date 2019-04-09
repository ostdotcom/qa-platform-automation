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
        public static final String RULE = "rule";
        public static final String DEVICE = "device";
        public static final String DEVICES = "devices";
        public static final String DEVICEMANAGER = "device_manager";
        public static final String SESSION = "session";
        public static final String SESSIONS = "sessions";
        public static final String RECOVERY = "recovery_owner";
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