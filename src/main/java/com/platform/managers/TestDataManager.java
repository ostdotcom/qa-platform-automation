package com.platform.managers;

import com.google.gson.Gson;
import com.platform.constants.Constant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TestDataManager {


    public static final TestDataManager data=loadData();

    /**
     * This function will return the test_data.json file as Java object.
     * @return
     */
    private static TestDataManager loadData()  {


        try {
            return new Gson().fromJson(new FileReader(Constant.TestDataFilePath.TESTDATAFILE),TestDataManager.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new TestDataManager();
        }
    }


    /**
     * Add key which are presents in test_data.json file
     * For nested json object, create a new class and define all the key in that class. Ex. EconomyManager
     */
    public String apiEndpoint;
    public List<EconomyManager> economy;


    public static EconomyManager economy1 = TestDataManager.data.economy.get(0);

}
