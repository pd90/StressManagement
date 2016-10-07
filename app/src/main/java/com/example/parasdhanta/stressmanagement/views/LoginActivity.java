package com.example.parasdhanta.stressmanagement.views;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.interfaces.login.FacebookLogin;
import com.example.parasdhanta.stressmanagement.managers.BaseActivity;
import com.example.parasdhanta.stressmanagement.pojos.LoginUser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Paras Dhanta on 9/29/2016.
 */

public class LoginActivity extends BaseActivity implements FacebookLogin,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private Firebase myFirebaseRef;
    @BindView(R.id.button_facebook_sign_in)
    public Button fbLoginButton;
    @BindView(R.id.login_with_google)
    public SignInButton googleLoginButton;
    /*client used to interact with google apis*/
    private GoogleApiClient mGoogleApiClient;

    /* Store the connection result from onConnectionFailed callbacks so that we can resolve them when the user clicks
    * sign-in. */
    private ConnectionResult mGoogleConnectionResult;

    /* Track whether the sign-in button has been clicked so that we know to resolve all issues preventing sign-in
     * without waiting. */
    private boolean mGoogleLoginClicked;

    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
    private boolean mGoogleIntentInProgress;

    /* Request code used to invoke sign in user interactions for Google+ */
    public static final int RC_GOOGLE_LOGIN = 1;

    private SignInButton mGoogleLoginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_main);
        mGoogleLoginButton = (SignInButton) findViewById(R.id.login_with_google);
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleLoginClicked = true;

                getGoogleOAuthTokenAndLogin();
            }
        });
    }

    //initialize facebook SDK Firebase db and butterknife here
    public void init() {
        facebookSdkInit();
        firebaseDbInit();
        ButterKnife.bind(this);
    }
    @Nullable
    @OnClick(R.id.button_facebook_sign_in)
    public void buttonClick(View v) {

        onFacebookLoginClicked();
    }


    @Override
    public void facebookSdkInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFaceboookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.getMessage());
                Toast.makeText(getApplicationContext(), "facebook" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void firebaseDbInit() {
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase(Constants.FIREBASE_DB_URL);
        mAuth = FirebaseAuth.getInstance();
    }

    //on facebook login button clicked
    @Override
    public void onFacebookLoginClicked() {
        LoginManager
                .getInstance()
                .logInWithReadPermissions(
                        this,
                        Arrays.asList("public_profile", "user_friends", "email")
                );
    }

    public LoginUser createUserObject() {
        return new LoginUser();
    }
    //facebook
    @Override
    public void handleFaceboookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // If sign in fails, display a message to the user. If sign in succeeds
        // the auth state listener will be notified and logic to handle the
        // signed in user can be handled in the listener.
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        //save the profile data in firebase database

                        saveFacebookData(task.getResult().getUser());


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    //Facebook save method
    public void saveFacebookData(FirebaseUser userData) {
        LoginUser userObject = createUserObject();
        userObject.setEmailId(userData.getEmail());
        userObject.setUserId(userData.getUid());
        userObject.setUserName(userData.getDisplayName());
//      Utils.getInstance().saveUser(userObject);
    }

    @Override
    public void checkUserLogin() {
        //method to check if the user is registered in the system, if logged in the user will be redirected to the main screen
        if (mAuth != null) {
            //open the next activity from here
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkUserLogin();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //initiate callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_LOGIN) {
            /* This was a request by the Google API */
            if (requestCode == RC_GOOGLE_LOGIN) {
                handleGoogleSignInResult(data);
            }
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void handleGoogleSignInResult(Intent data)
    {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        GoogleSignInAccount acct = result.getSignInAccount();
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        firebaseAuthWithGoogle(acct);
    }
    //google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Authentication pass.",
                                    Toast.LENGTH_SHORT).show();
                            //start activity here
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!mGoogleIntentInProgress) {
            /* Store the ConnectionResult so that we can use it later when the user clicks on the Google+ login button */
            mGoogleConnectionResult = connectionResult;

            if (mGoogleLoginClicked) {
                /* The user has already clicked login so we attempt to resolve all errors until the user is signed in,
                 * or they cancel. */
                resolveSignInError();
            } else {
                Log.e(TAG, connectionResult.toString());
            }
        }
    }
    //google- helper method to resolve the current connection error
    public void resolveSignInError()
    {
         if(mGoogleConnectionResult.hasResolution())
         {
             mGoogleIntentInProgress = true;
             try {
                 mGoogleConnectionResult.startResolutionForResult(this,RC_GOOGLE_LOGIN);
             } catch (IntentSender.SendIntentException e) {
                 // The intent was canceled before it was sent.  Return to the default
                 // state and attempt to connect to get an updated ConnectionResult.
                 mGoogleIntentInProgress = false;
                 mGoogleApiClient.connect();
             }
         }
    }
    //google- to get the Auth token in the background
    public void getGoogleOAuthTokenAndLogin()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);
    }
    //google
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getGoogleOAuthTokenAndLogin();
    }
    //google
    @Override
    public void onConnectionSuspended(int i) {

    }
}
