package com.example.parasdhanta.stressmanagement.presenters;

import android.app.Activity;
import android.content.Context;

import com.example.parasdhanta.stressmanagement.interactors.EmailLoginInteractor;
import com.example.parasdhanta.stressmanagement.interactors.EmailLoginInteractorImpl;
import com.example.parasdhanta.stressmanagement.interfaces.login.EmailLoginView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Paras Dhanta on 10/20/2016.
 */

public class EmailLoginPresenterImpl implements EmailLoginPresenter,EmailLoginInteractor.OnLoginFinishedListener,
        EmailLoginInteractor.OnSignUpFinishedListener {

    private EmailLoginView emailLoginView;

    private EmailLoginInteractor emailLoginInteractor;

    public EmailLoginPresenterImpl(EmailLoginView emailLoginView)
    {
         this.emailLoginView = emailLoginView;
         this.emailLoginInteractor = new EmailLoginInteractorImpl();
    }
    @Override
    public void onEmailIdError() {
         if(emailLoginView!=null) {
             emailLoginView.showEmailError();
             emailLoginView.hideProgress();
         }
    }

    @Override
    public void onPasswordError() {
         if(emailLoginView!=null){
             emailLoginView.showPasswordError();
             emailLoginView.hideProgress();
         }

    }

    @Override
    public void onSignUpSuccess() {
        if(emailLoginView!=null){
            emailLoginView.hideProgress();
            emailLoginView.navigateToOtherScr();
        }
    }

    @Override
    public void onSignUpFailedError() {
        if(emailLoginView!=null){
            emailLoginView.showSignUpFailedError();
            emailLoginView.hideProgress();
        }
    }
    @Override
    public void onLoginSuccess() {
        if(emailLoginView!=null){
            emailLoginView.hideProgress();
            emailLoginView.navigateToOtherScr();
        }
    }

    @Override
    public void onStartProgress() {
        emailLoginView.showProgress();
    }



    @Override
    public void onAuthFailedError() {
        if(emailLoginView!=null){
            emailLoginView.showAuthFailedError();
            emailLoginView.hideProgress();
        }
    }

    @Override
    public void validateLoginCredentials(String emailId, String password, FirebaseAuth mAuth, Activity context) {
        if(emailLoginView!=null) {
            emailLoginView.showProgress();
        }
        // verfy data on the server through the interactor
        emailLoginInteractor.login(emailId,password,EmailLoginPresenterImpl.this,mAuth,context);
    }

    @Override
    public void signUpCredentials(String emailId, String password, FirebaseAuth mAuth, Activity context) {
        if(emailLoginView!=null) {
            emailLoginView.showProgress();
        }
        // create a new user on the server
        emailLoginInteractor.signUp(emailId,password,EmailLoginPresenterImpl.this,mAuth,context);
    }

    @Override
    public void onDestroy() {
        emailLoginView = null;
    }
}
