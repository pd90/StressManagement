package com.example.parasdhanta.stressmanagement.database;

import com.example.parasdhanta.stressmanagement.utils.Utils;

/**
 * Created by Paras Dhanta on 11/24/2016.
 */

public class RealmUtils {
    private final String TAG = Utils.class.getSimpleName();
    private static RealmUtils instance =null;
    private RealmUtils()
    {
        //no instantiation
    }
    public static RealmUtils getInstance()
    {
        if(instance == null)
        {
            instance = new RealmUtils();
        }
        return  instance;
    }
}
