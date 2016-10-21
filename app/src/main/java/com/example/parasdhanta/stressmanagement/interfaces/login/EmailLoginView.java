package com.example.parasdhanta.stressmanagement.interfaces.login;

/**
 * Created by Paras Dhanta on 10/18/2016.
 */

public interface EmailLoginView {

        void showProgress();

        void hideProgress();

        void showEmailError();

        void showPasswordError();

        void showAuthFailedError();

        void navigateToOtherScr();

        void showSignUpFailedError();
}
