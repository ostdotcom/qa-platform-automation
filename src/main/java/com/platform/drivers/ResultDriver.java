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
import java.util.ArrayList;
import java.util.List;

public class ResultDriver {


    public boolean get_success_status(JsonObject result)
    {
        return result.get("success").getAsBoolean();
    }


    /**
     * This function will verify json schema depending on the "result_type"
     * @param response - Json object which will come in response after API call
     */
    public void verify_json_schema(JsonObject response) {

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

                    case Constant.RESULT_TYPE.SESSION:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.SESSION));
                        break;

                    case Constant.RESULT_TYPE.SESSIONS:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.SESSIONS));
                        break;

                    case Constant.RESULT_TYPE.PRICEPOINT:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.PRICEPOINT));
                        break;

                    case Constant.RESULT_TYPE.DEVICEMANAGER:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.DEVICEMANAGER));
                        break;


                    case Constant.RESULT_TYPE.CHAIN:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.CHAIN));
                        break;


                    case Constant.RESULT_TYPE.RULE:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.RULE));
                        break;


                    case Constant.RESULT_TYPE.RECOVERYOWNER:
                        obj= jsonParser.parse(new FileReader(Constant.RESULT_SCHEMA.RECOVERYOWNER));
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
            Assert.fail("Error: "+v.getCausingExceptions().toString() + "Message: "+ v.getMessage());

        }
    }


    public String get_result_type(JsonObject result)
    {
        return result.getAsJsonObject("data").get("result_type").getAsString();
    }

    private  boolean is_detailed_error_present(JsonObject result){
        return result.getAsJsonObject("err").has("error_data");
    }

    public  String get_error_code(JsonObject response) {
        return response.getAsJsonObject("err").get("code").getAsString();
    }



    public  boolean pagination_identifier_present(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("meta").getAsJsonObject("next_page_payload").has("pagination_identifier");
    }

    public  String get_pagination_identifier(JsonObject response) {
        return response.getAsJsonObject("data").getAsJsonObject("meta").getAsJsonObject("next_page_payload").get("pagination_identifier").getAsString();
    }

}