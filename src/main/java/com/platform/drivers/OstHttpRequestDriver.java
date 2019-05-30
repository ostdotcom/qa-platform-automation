package com.platform.drivers;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.platform.managers.TestDataManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

/**
 * HttpRequestClient to supports get and post requests with api signing.
 */
public class OstHttpRequestDriver {
    private static final String API_SIGNATURE = "api_signature";
    private String apiEndpoint;
    private long timeout;
    private OkHttpClient client;
    private static final Escaper FormParameterEscaper = UrlEscapers.urlFormParameterEscaper();
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String API_KEY = "api_key";
    private static final String API_REQUEST_TIMESTAMP = "api_request_timestamp";
    private static final String API_SIGNATURE_KIND = "api_signature_kind";
    private static final String SIG_TYPE = "OST1-PS";



    static class HttpParam {
        private String paramName;
        private String paramValue;

        public HttpParam() {

        }

        public HttpParam(String paramName, String paramValue) {
            this.paramName = paramName;
            this.paramValue = paramValue;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

    }

    public OstHttpRequestDriver(String baseUrl) {
        this.apiEndpoint = baseUrl;
        this.timeout = 30;

        //To-Do: Discuss Dispatcher config with Team.
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(500);
        dispatcher.setMaxRequestsPerHost(150);

        client = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(10, 2, TimeUnit.MINUTES))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .retryOnConnectionFailure(false)
                .build();

    }

    private static String GET_REQUEST = "GET";
    private static String POST_REQUEST = "POST";
    private static String SocketTimeoutExceptionString = "{'success':'false','err':{'code':'REQUEST_TIMEOUT','internal_id':'SDK(TIMEOUT_ERROR)','msg':'','error_data':[]}}";
    private static String IOExceptionString = "{'success':'false','err':{'code':'IOException','internal_id':'SDK(IO_EXCEPTION)','msg':'','error_data':[]}}";
    private static String NetworkExceptionString = "{'success':'false','err':{'code':'NO_NETWORK','internal_id':'SDK(NO_NETWORK)','msg':'','error_data':[]}}";

    public JsonObject get(String resource, Map<String, Object> queryParams, String secretKey) throws IOException {
        return send(GET_REQUEST, resource, queryParams, secretKey);
    }

    public JsonObject post(String resource, Map<String, Object> queryParams, String secretKey) throws IOException {
        return send(POST_REQUEST, resource, queryParams, secretKey);
    }

    private JsonObject send(String requestType, String resource, Map<String, Object> mapParams, String secretKey) throws IOException {
        // Basic Sanity.
        if (!requestType.equalsIgnoreCase(POST_REQUEST) && !requestType.equalsIgnoreCase(GET_REQUEST)) {
            throw new IOException("Invalid requestType");
        }
        if (null == mapParams) {
            mapParams = new HashMap<String, Object>();
        }

        // Start Building the request, url of request and request form body.
        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl baseUrl = HttpUrl.parse(apiEndpoint + resource);
        HttpUrl.Builder urlBuilder = baseUrl.newBuilder(resource);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (null == urlBuilder) {
            throw new IOException("Failed to instantiate HttpUrl.Builder. resource or Api Endpoint is incorrect.");
        }

        // Evaluate the url generated so far.
        HttpUrl url = urlBuilder.build();

        // Start Building Query-String Input Buffer by parsing the url.
        Buffer qsInputBuffer = new Buffer();

        qsInputBuffer.writeUtf8(resource);
        qsInputBuffer.writeByte('?');

        //Reset urlBuilder.
        urlBuilder = baseUrl.newBuilder();

        ArrayList<HttpParam> params = new ArrayList<HttpParam>();
        String paramKey;
        String paramVal;

        params = buildNestedQuery(params, "", mapParams);

        // Add params to url/form-body & qsInputBuffer.
        Iterator it = params.iterator();
        boolean firstParam = true;
        while (it.hasNext()) {
            HttpParam pair = (HttpParam) it.next();

            paramKey = pair.getParamName();
            paramVal = pair.getParamValue();

            paramKey = specialCharacterEscape(paramKey);
            paramVal = specialCharacterEscape(paramVal);

            if (!firstParam) {
                qsInputBuffer.writeByte('&');
            }
            firstParam = false;

            qsInputBuffer.writeUtf8(paramKey);
            qsInputBuffer.writeByte('=');
            qsInputBuffer.writeUtf8(paramVal);
            System.out.println("paramKey " + paramKey + " paramVal " + paramVal);

            if (GET_REQUEST.equalsIgnoreCase(requestType)) {
                urlBuilder.addEncodedQueryParameter(paramKey, paramVal);
            } else {
                formBodyBuilder.addEncoded(paramKey, paramVal);
            }
        }


        paramKey = API_SIGNATURE;
        paramVal = signQueryParams(qsInputBuffer,secretKey);
        if (GET_REQUEST.equalsIgnoreCase(requestType)) {
            urlBuilder.addEncodedQueryParameter(paramKey, paramVal);
        } else {
            formBodyBuilder.addEncoded(paramKey, paramVal);
        }

        // Build the url.
        url = urlBuilder.build();
        System.out.println("url = " + url.toString());

        // Set url in requestBuilder.
        requestBuilder.url(url);

        // Build the request Object.
        Request request;
        if (GET_REQUEST.equalsIgnoreCase(requestType)) {
            requestBuilder.get().addHeader("Content-Type", "application/x-www-form-urlencoded");
            requestBuilder.get().addHeader("User-Agent", "ost-sdk-android-2-1.0");
        } else {
            FormBody formBody = formBodyBuilder.build();
            requestBuilder.post(formBody);
        }
        request = requestBuilder.build();

        // Make the call and execute.
        String responseBody;
        Call call = client.newCall(request);
        try {
            okhttp3.Response response = call.execute();
            responseBody = getResponseBodyAsString(response);
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException occurred.");
            responseBody = SocketTimeoutExceptionString;
        }

        JsonObject jsonResponse = buildApiResponse(responseBody);

        return jsonResponse;
    }


    private String signQueryParams(Buffer qsInputBuffer, String secretKey) {
        // Generate Signature for Params.
        byte[] bytes = qsInputBuffer.readByteArray();
        System.out.println("bytes to sign: " + new String(bytes, UTF_8));
        // Encryption of bytes

        String signature = sign(bytes, secretKey);
        System.out.println("signature:" + signature);
        return signature;
    }

    private String sign(byte[] dataToSign, String secretKey) {
        //key = OstAndroidSecureStorage.getInstance(OstSdk.getContext(), mUserId).decrypt(osk.getData());
        String api_signer_private = secretKey;

        ECKeyPair ecKeyPair = null;
        ecKeyPair = ECKeyPair.create(Numeric.hexStringToByteArray(api_signer_private));

        //Sign the data
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(dataToSign, ecKeyPair);
        return signatureDataToString(signatureData);
    }

    private static String signatureDataToString(Sign.SignatureData signatureData) {
        return Numeric.toHexString(signatureData.getR()) + Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getS())) + String.format("%02x", (signatureData.getV()));
    }

    private static String SOMETHING_WRONG_RESPONSE = "{'success': false, 'err': {'code': 'SOMETHING_WENT_WRONG', 'internal_id': 'SDK(SOMETHING_WENT_WRONG)', 'msg': '', 'error_data':[]}}";

    private static String getResponseBodyAsString(okhttp3.Response response) {
        // Process the response.
        String responseBody;
        if (response.body() != null) {
            try {
                responseBody = response.body().string();
                if (responseBody.length() > 0) {
                    return responseBody;
                }
            } catch (IOException e) {
                // Silently handle the error.
                e.printStackTrace();
            }
        }
        return null;
    }

    private static JsonObject buildApiResponse(String jsonString) {
          final Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, JsonObject.class);
        } catch (Exception var2) {
            var2.printStackTrace();
            System.out.println("Failed to parse response. local responseBody:\n" + SOMETHING_WRONG_RESPONSE + "\n");
            return gson.fromJson(SOMETHING_WRONG_RESPONSE, JsonObject.class);
        }

    }

    private static ArrayList<HttpParam> buildNestedQuery(ArrayList<HttpParam> params, String paramKeyPrefix, Object paramValObj) {

        if (paramValObj instanceof Map) {

            //            sort map.
            Map<String, Object> sortedMap = new TreeMap<String, Object>((Map<? extends String, ?>) paramValObj);
            for (Object paramPair : sortedMap.entrySet()) {
                Map.Entry pair = (Map.Entry) paramPair;
                String key = (String) pair.getKey();
                Object value = pair.getValue();
                String prefix = "";
                if (paramKeyPrefix.isEmpty()) {
                    prefix = key;
                } else {
                    prefix = paramKeyPrefix + "[" + key + "]";
                }

                params = buildNestedQuery(params, prefix, value);
            }

        } else if (paramValObj instanceof Collection) {
            Iterator<Object> iterator = ((Collection) paramValObj).iterator();

            while (iterator.hasNext()) {
                Object value = iterator.next();
                String prefix = paramKeyPrefix + "[]";
                params = buildNestedQuery(params, prefix, value);
            }
        } else {
            if (paramValObj != null) {
                params.add(new HttpParam(paramKeyPrefix, paramValObj.toString()));
            } else {
                params.add(new HttpParam(paramKeyPrefix, ""));
            }
        }
        return params;
    }

    private static String specialCharacterEscape(String stringToEscape) {
        stringToEscape = FormParameterEscaper.escape(stringToEscape);
        stringToEscape = stringToEscape.replace("*", "%26");
        return stringToEscape;
    }

    public HashMap<String,Object> getPrequisite(String userID, String deviceAddress, String api_signer_add)
    {
        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put(API_KEY, String.format("%s.%s.%s.%s", TestDataManager.economy1.tokenId, userID,
                deviceAddress,
                api_signer_add
        ));
        requestMap.put(API_REQUEST_TIMESTAMP, String.valueOf((int) (System.currentTimeMillis() / 1000)));
        requestMap.put(API_SIGNATURE_KIND, SIG_TYPE);

        return requestMap;
    }
}