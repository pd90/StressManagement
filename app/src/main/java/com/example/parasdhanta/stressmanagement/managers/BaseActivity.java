package com.example.parasdhanta.stressmanagement.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.parasdhanta.stressmanagement.activities.LoginActivity;
import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.fragments.DescriptionFragment;
import com.example.parasdhanta.stressmanagement.interfaces.StartFragment;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenter;
import com.firebase.client.Firebase;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Paras Dhanta on 8/22/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements StartFragment {
    //add further implementation here
    EventBus eventBus;

    //Application controller reference
    public ActivityController mainAppController;
    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;
    EmailLoginPresenter emailLoginPresenter;

    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);

  /*  @Override
    public abstract boolean onOptionsItemSelected(MenuItem item);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the toolbar of app
        mainAppController = ActivityController.getInstance();
    }

    protected abstract int getLayoutResource();

    protected abstract void setAppToolBar();

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

    public void initButterKnife() {
        ButterKnife.bind(this);
        mainAppController.addActivity(this);
    }

    /*show snackbar in child activities*/
    public void showSnackBar(CoordinatorLayout coordinatorLayout, String text, int length) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, text, length);
        snackbar.show();
    }

    /*start new activity*/
    public void startNewActivity(Activity currentActivity, Class nextActivity, Parcelable parcel) {
        Intent intent = new Intent(currentActivity, nextActivity);
        if (currentActivity instanceof LoginActivity) {
            intent.putExtra("user_object", parcel);
        }
        startActivity(intent);
    }

    /*Load fragment from activity*/

    @Override
    public Fragment loadFragmentInstance(String title, String content) {
        final BaseFragment fragment = new DescriptionFragment();
        return fragment;
    }

    public String getAppName(Context ctx) {
        Resources appR = ctx.getResources();
        CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                "string", ctx.getPackageName()));

        return (String) txt;
    }

    /**
     * download image logic using glide library
     */
    public void imageLogic(CircleImageView imgView, Activity activity, String url) {
        Glide.with(activity)
                .load(url)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        //callback if required
                    }
                });

        //set the image on imageview

        Glide.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);
    }

    /*change text on the google sign in button*/
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Search all the views inside SignInButton for TextView

        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            // if the view is instance of TextView then change the text SignInButton
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setPadding(0,0,25,0);
                return;
            }
        }
    }
}