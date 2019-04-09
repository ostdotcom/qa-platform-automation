package com.platform.drivers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.platform.constants.Constant;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ResultDriver {


    public static boolean get_success_status(JsonObject result)
    {
        return result.get("success").getAsBoolean();
    }

    public static int get_auxiliary_chain_id(JsonObject result)
    {
        return result.getAsJsonObject("data").getAsJsonObject("token")
                .getAsJsonArray("auxiliary_chains").get(0)
                .getAsJsonObject().get("chain_id").getAsInt();
    }

    public static int get_origin_chain_id(JsonObject result) {
        return result.getAsJsonObject("data").getAsJsonObject("token")
                .getAsJsonObject( "origin_chain").get("chain_id").getAsInt();
    }


    /**
     * This function will verify json schema depending on the "result_type"
     * @param response - Json object which will come in response after API call
     */
    public static void verify_json_schema(JsonObject response) {

        JsonParser jsonParser = new JsonParser();
        Object obj=null;

        try {
            if(get_success_status(response)==false)
            {
               // Write code here to verify generic error message
            }
            else
            {
                switch (get_result_type(response))
                {
                    // Add more response schema entity here
                    case Constant.RESULT_TYPE.USER:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.USER));
                        break;

                    case Constant.RESULT_TYPE.USERS:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.USERS));
                        break;

                    case Constant.RESULT_TYPE.TOKEN:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.TOKEN));
                        break;
                    default:
                        throw new AssertionError("Result type '"+get_result_type(response)+"' does not matching with any stored schema.");
                }
            }

            JSONObject jsonSchema = new JSONObject(
                    new JSONTokener(obj.toString()));
            JSONObject jsonResponse = new JSONObject(
                    new JSONTokener(jsonParser.parse(String.valueOf(response)).toString()));

            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonResponse);

        }
        catch (FileNotFoundException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }
        catch (ValidationException v)
        {
            System.out.println(v.getMessage());
            Assert.fail(v.getMessage());
        }
    }


    public static String get_result_type(JsonObject result)
    {
        return result.getAsJsonObject("data").get("result_type").getAsString();
    }

}