package com.example.parasdhanta.stressmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.pojos.LoginUser;

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
    /* save profile data to shared preferences*/
    public void saveUserProfileSharedPrefs(Context mContext,String prefType,Object object) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(prefType, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (object instanceof LoginUser) {
            editor.putString(Constants.PREF_KEY_USERNAME, ((LoginUser) object).getUserName());
            editor.putString(Constants.PREF_KEY_EMAIL, ((LoginUser) object).getEmailId());
            editor.commit();
            Log.d(TAG, "saved success");
        }

     }
}
