package com.example.parasdhanta.stressmanagement.presenters;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Paras Dhanta on 10/20/2016.
 */

public interface EmailLoginPresenter {

     void validateLoginCredentials(String emailId, String password, FirebaseAuth mAuth,Activity context);

     void signUpCredentials(String emailId, String password, FirebaseAuth mAuth,Activity context);

     void onDestroy();

}
