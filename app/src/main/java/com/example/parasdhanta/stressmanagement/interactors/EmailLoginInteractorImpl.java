package com.example.parasdhanta.stressmanagement.interactors;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Paras Dhanta on 10/20/2016.
 */

public class EmailLoginInteractorImpl implements EmailLoginInteractor {

    @Override
    public void login(String emailId, final String password, final OnLoginFinishedListener listener, final FirebaseAuth mAuth, final Activity context) {
        //authenticate user
       mAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("test","tester");
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        listener.onStartProgress();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                //throw error
                                listener.onPasswordError();
                            } else {
                                listener.onAuthFailedError();
                            }
                        } else {
                            listener.onLoginSuccess();
                        }
                    }
                });
    }

    @Override
    public void signUp(String emailId, final String password, final OnSignUpFinishedListener listener, FirebaseAuth mAuth, Activity context) {
        //create a new user on the server
        mAuth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                listener.onStartProgress();
                if (!task.isSuccessful()) {
                    // there was an error
                    if (password.length() < 6) {
                        //throw error
                        listener.onPasswordError();
                    } else {
                        listener.onSignUpFailedError();
                    }
                } else {
                    listener.onSignUpSuccess();
                }
            }
        });
    }

}
