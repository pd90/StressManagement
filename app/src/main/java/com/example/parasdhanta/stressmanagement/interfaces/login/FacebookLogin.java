package com.example.parasdhanta.stressmanagement.interfaces.login;

import com.facebook.AccessToken;

/**
 * Created by Paras Dhanta on 9/30/2016.
 */
// the view methods for facebook login
public interface FacebookLogin {

    public void facebookSdkInit();

    public void firebaseDbInit();

    public void onFacebookLoginClicked();

    public void handleFaceboookAccessToken(AccessToken token);

    public void checkUserLogin();
}
