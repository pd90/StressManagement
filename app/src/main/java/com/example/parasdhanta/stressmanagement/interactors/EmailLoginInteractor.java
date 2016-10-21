package com.example.parasdhanta.stressmanagement.interactors;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Paras Dhanta on 10/20/2016.
 */

public interface EmailLoginInteractor {
     public interface OnLoginFinishedListener {
         void onEmailIdError();

         void onPasswordError();

         void onLoginSuccess();

         void onStartProgress();

         void onAuthFailedError();
     }
    public interface OnSignUpFinishedListener extends OnLoginFinishedListener {

        void onSignUpSuccess();

        void onSignUpFailedError();
    }

    void login(String emailId, String password, OnLoginFinishedListener listener, FirebaseAuth mAuth,Activity context);

    void signUp(String emailId, String password, OnSignUpFinishedListener listener, FirebaseAuth mAuth,Activity context);

}
