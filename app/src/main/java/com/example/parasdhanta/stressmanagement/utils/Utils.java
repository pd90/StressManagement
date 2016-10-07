package com.example.parasdhanta.stressmanagement.utils;

import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.pojos.LoginUser;

/**
 * Created by Paras Dhanta on 10/3/2016.
 */

public class Utils {
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
    // save profile data to shared preferences

}
