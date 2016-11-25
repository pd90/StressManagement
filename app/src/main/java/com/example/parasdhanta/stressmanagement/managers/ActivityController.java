package com.example.parasdhanta.stressmanagement.managers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by Paras Dhanta on 10/19/2016.
 */

public class ActivityController implements ActivityAddPopInterface {

    private static final ActivityController instance = new ActivityController();

    //arraylist of all the activity instances
    static ArrayList<BaseActivity> allActivityList;

    private ActivityController() {
        //for singleton purpose non instantiable
    }

    @Nullable
    public synchronized static ActivityController getInstance() {
        allActivityList = new ArrayList<BaseActivity>();
        if (instance != null) {
            return instance;
        }
        return null;
    }

    /**
     * this method adds the current activity to the arraylist
     *
     * @param activity
     */
    @Override
    public void addActivity(BaseActivity activity) {
        allActivityList.add(activity);
    }

    /**
     * this method finishes the current activity that has been passed to it
     *
     * @param activity
     */
    @Override
    public void finishCurrentActivity(BaseActivity activity) {
        activity.finish();
    }

    /**
     * this method forcefully destroys the application by destroying all the activities
     */
    @Override
    public void destroyApplicationForcefully() {
        //TO-DO
    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }


}
