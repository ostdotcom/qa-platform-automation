package com.platform.utils;

import com.google.gson.JsonObject;
import com.platform.constants.Constant;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;
import org.web3j.crypto.*;

public class EthAddress {

    /**
     * Provide new valid Ethereum address
     * @return - etherum address
     */
    public String getNewEthAddress()
    {
        return process().get("address").getAsString();
    }


    /**
     * Provide Public & Private key in form of JSONObject
     * @return
     */
    public JsonObject getNewEthKeys()
    {
        return process();
    }


    private static JsonObject process(){

        String seed = UUID.randomUUID().toString();
        JsonObject processJson = new JsonObject();

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            String sPrivatekeyInHex = privateKeyInDec.toString(16);
            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();

            processJson.addProperty(Constant.ETH.ADDRESS, "0x" + sAddress);
            processJson.addProperty(Constant.ETH.PRIVATEKEY, "0x"+sPrivatekeyInHex);
            System.out.println(processJson);

        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return processJson;
    }
}
