package com.platform.utils;

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
        return process().get("address").toString();
    }


    /**
     * Provide Public & Private key in form of JSONObject
     * @return
     */
    public JSONObject getNewEthKeys()
    {
        return process();
    }


    private static JSONObject process(){

        String seed = UUID.randomUUID().toString();
        JSONObject processJson = new JSONObject();

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            String sPrivatekeyInHex = privateKeyInDec.toString(16);
            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();

            processJson.put("address", "0x" + sAddress);
            processJson.put("privatekey", sPrivatekeyInHex);

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
