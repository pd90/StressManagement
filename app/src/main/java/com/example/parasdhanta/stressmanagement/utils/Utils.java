package com.example.parasdhanta.stressmanagement.utils;

/**
 * Created by Paras Dhanta on 10/3/2016.
 */

public class Utils {
    private final String TAG = Utils.class.getSimpleName();
    private static Utils instance =null;
    private Utils()
    {
        //no instantiation
    }
    public static Utils getInstance()
    {
        if(instance == null)
        {
            instance = new Utils();
        }
        return  instance;
    }

}
