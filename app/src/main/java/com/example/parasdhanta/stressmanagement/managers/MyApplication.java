package com.example.parasdhanta.stressmanagement.managers;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.realm.Realm;

/**
 * Created by Paras Dhanta on 11/24/2016.
 */

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
