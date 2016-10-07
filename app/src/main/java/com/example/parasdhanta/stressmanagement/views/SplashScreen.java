package com.example.parasdhanta.stressmanagement.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.managers.BaseActivity;

/**
 * Created by Paras Dhanta on 9/29/2016.
 */

public class SplashScreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                //closing the current activity
                finish();
            }
        },2*1000);//wait for 2 seconds

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
