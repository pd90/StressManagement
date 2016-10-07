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
/*    //method saves the user in the firebase database
    public void saveUser(LoginUser userObject)
    {
        Firebase myFirebaseRef = new Firebase(Constants.FIREBASE_DB_URL);
        myFirebaseRef = myFirebaseRef.child("users").child(userObject.getUserName());
        if (userObject!=null)
        myFirebaseRef.setValue(userObject);
    }*/
}
