package com.platform.base;

import com.google.gson.JsonObject;
import com.ost.OSTSDK;
import com.platform.managers.EconomyManager;
import com.platform.managers.TestDataManager;

public  class Base_API {




    // In Economy list, whichever economy is on 0th position will be considered as economy_1 and then it will increment by position. (economy_2)

    public static OSTSDK ostObj;
    public static com.ost.services.Manifest services;


    public static com.ost.services.Users usersService;
    public static com.ost.services.Devices devicesService;
    public static com.ost.services.Sessions sessionsService;
    public static com.ost.services.DeviceManagers deviceManagersService;
    public static com.ost.services.Rules rulesService;
    public static com.ost.services.PricePoints pricePointsService;
    public static com.ost.services.Transactions transactionsService;
    public static com.ost.services.Balance balancesService;
    public static com.ost.services.RecoveryOwners recoveryOwnersService;
    public static com.ost.services.Tokens tokensService;
    public static com.ost.services.Chains chainsService;
    public static JsonObject response;




}
