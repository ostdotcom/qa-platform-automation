package com.platform.base;

import com.google.gson.JsonObject;
import com.ost.OSTSDK;
import com.platform.managers.EconomyManager;
import com.platform.managers.TestDataManager;

public  class Base_API {

    public  OSTSDK ostObj;
    public  com.ost.services.Manifest services;


    public  com.ost.services.Users usersService;
    public  com.ost.services.Devices devicesService;
    public  com.ost.services.Sessions sessionsService;
    public  com.ost.services.DeviceManagers deviceManagersService;
    public  com.ost.services.Rules rulesService;
    public  com.ost.services.PricePoints pricePointsService;
    public  com.ost.services.Transactions transactionsService;
    public  com.ost.services.Balance balancesService;
    public  com.ost.services.RecoveryOwners recoveryOwnersService;
    public  com.ost.services.Tokens tokensService;
    public  com.ost.services.Chains chainsService;
    public JsonObject response;
    public JsonObject api_signer_object;
    public String deviceAddress;


}