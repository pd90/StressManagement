package com.example.parasdhanta.stressmanagement.managers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenter;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by Paras Dhanta on 8/22/2016.
 */
public class BaseActivity extends AppCompatActivity {
    //add further implementation here
    EventBus eventBus;

    //Application controller reference
    public ActivityController mainAppController;
    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;
    EmailLoginPresenter emailLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mainAppController = ActivityController.getInstance();
    }

    @Override
    public void onBackPressed() {
        mainAppController.finishCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public EventBus getEventBus() {
        eventBus = EventBus.getDefault();
        return eventBus;
    }
    public FirebaseAuth firebaseDbInit() {
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase(Constants.FIREBASE_DB_URL);
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public void initButterKnife()
    {
        ButterKnife.bind(this);
        mainAppController.addActivity(this);
    }

    /*show snackbar in child activities*/
    public void showSnackBar(CoordinatorLayout coordinatorLayout,String text,int length){
        Snackbar snackbar = Snackbar.make(coordinatorLayout,text, length);
        snackbar.show();
    }

}