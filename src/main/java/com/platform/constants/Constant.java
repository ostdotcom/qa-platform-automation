package com.platform.constants;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Constant {



    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static final String ENVIRONMENT = System.getProperty("env",getEnvironment());
    public static final String ECONOMY = System.getProperty("token",getToken());
    public static final String PROJECTOS = System.getProperty("os.name");
    public static final String BROWSER = System.getProperty(BROWSER_SPECIFICATION.BROWSERKEY,BROWSER_SPECIFICATION.BROWSERDEFAULTVALUE);


    public static class TestDataFilePath{

        public static final String TESTDATAFILE = PROJECTPATH + java.io.File.separatorChar + "config"+ java.io.File.separatorChar+ ENVIRONMENT + java.io.File.separatorChar +"test_data.json";
        public static final String USERTEMPDATAFILE = PROJECTPATH + java.io.File.separatorChar + "config"+ java.io.File.separatorChar+ "user_temp.json";

    }

    public static class STATUS
    {
        public static final String CREATED = "CREATED";
        public static final String ACTIVATING = "ACTIVATING";
        public static final String ACTIVATED = "ACTIVATED";
        public static final String REGISTERED = "REGISTERED";
        public static final String AUTHORIZING = "AUTHORIZING";
        public static final String AUTHORIZED = "AUTHORIZED";
        public static final String REVOKING = "REVOKING";
        public static final String RECOVERING = "RECOVERING";
        public static final String REVOKED = "REVOKED";
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
        public static final int TOTALWAIT = 60;       //Time in sec
        public static final int CONFIRMATION_TIME = (WAITING_BLOCK_CONFIRMATION*BLOCKHIGHT);
    }

    public static class ETH
    {
        public static final String ADDRESS="address";
        public static final String PRIVATEKEY = "privatekey";
    }

    public static class BROWSER_SPECIFICATION{

        public static final String CHROME = "chrome";
        public static final String FIREFOX = "firefox";
        public static final String IE = "ie";
        public static final String SAFARI = "safari";
        public static final String BROWSERKEY = "browser";
        public static final String BROWSERDEFAULTVALUE = "chrome";
        public static final String PROPERTYKEYCHROME = "webdriver.chrome.driver";
        public static final String PROPERTYKRYFIREFOX = "webdriver.gecko.driver";
        public static final String CHROMEDRIVERLINUX = "src/test/resources/browser_drivers/linux/chromedriver_76";
        public static final String CHROMEDRIVERWINDOWS = "src/test/resources/browser_drivers/windows/chromedriver.exe";
        public static final String GECKODRIVERLINUX = "src/test/resources/browser_drivers/linux/geckdriver";
        public static final String GECKODRIVERWINDOWS = "src/test/resources/browser_drivers/windows/geckdriver.exe";
        public static final String CHROMEDRIVERMAC = "src/test/resources/browser_drivers/mac/chromedriver_75";
        public static final String GECKODRIVERMAC = "src/test/resources/browser_drivers/mac/geckodriver";

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

    private static String getToken() {
        String env = null;
        try {
            JsonObject jsonObject= new Gson().fromJson(new FileReader("config/config.json"),JsonObject.class);
            env = jsonObject.get("token").getAsString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return env;
    }

}
