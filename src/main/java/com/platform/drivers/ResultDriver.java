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
                if(is_detailed_error_present(response))
                {
                    obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.DETAILEDERROR));
                }
                else {
                   // obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.GENERICERROR));
                }
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

                    case Constant.RESULT_TYPE.DEVICE:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.DEVICE));
                        break;

                    case Constant.RESULT_TYPE.DEVICES:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.DEVICES));
                        break;

                    case Constant.RESULT_TYPE.TRANSACTION:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.TRANSACTION));
                        break;

                    case Constant.RESULT_TYPE.TRANSACTIONS:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.TRANSACTIONS));
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

    private static boolean is_detailed_error_present(JsonObject result){
        return result.getAsJsonObject("err").has("error_data");
    }

    public static String get_error_code(JsonObject response) {
        return response.getAsJsonObject("err").get("code").getAsString();
    }

    public static String get_user_type(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("user").get("type").getAsString();
    }

    public static int get_list_number_of_users(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonArray("users").size();
    }

    public static boolean pagination_identifier_present(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("meta").getAsJsonObject("next_page_payload").has("pagination_identifier");
    }

    public static String get_pagination_identifier(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("meta").getAsJsonObject("next_page_payload").get("pagination_identifier").getAsString();
    }

    public static Object get_user_id(JsonObject response) {
            return response.getAsJsonObject("data").getAsJsonObject("user").get("id").getAsString();
    }
}