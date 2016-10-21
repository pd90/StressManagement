package com.example.parasdhanta.stressmanagement.views;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.constants.Constants;
import com.example.parasdhanta.stressmanagement.interfaces.login.EmailLoginView;
import com.example.parasdhanta.stressmanagement.interfaces.login.FacebookLogin;
import com.example.parasdhanta.stressmanagement.managers.BaseActivity;
import com.example.parasdhanta.stressmanagement.pojos.LoginUser;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenter;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenterImpl;
import com.example.parasdhanta.stressmanagement.utils.Utils;
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
import butterknife.Optional;
import de.greenrobot.event.EventBus;


/**
 * Created by Paras Dhanta on 9/29/2016.
 */

public class LoginActivity extends BaseActivity implements FacebookLogin,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,EmailLoginView {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private Firebase myFirebaseRef;

    @BindView(R.id.button_facebook_sign_in) public Button fbLoginButton;
    @BindView(R.id.login_with_google) public SignInButton googleLoginButton;
    @BindView(R.id.progressbar_signin) public ProgressBar progressBar;
    @BindView(R.id.email) public EditText inputEmail;
    @BindView(R.id.password) public EditText inputPassword;
    @BindView(R.id.sign_in_button) public Button loginButton;
    @BindView(R.id.sign_up) public Button signUpButton;

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

    LoginUser userObject;

    /*Event bus object init*/

    EventBus eventBus;

    EmailLoginPresenter emailLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mGoogleLoginButton = (SignInButton) findViewById(R.id.login_with_google);
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleLoginClicked = true;

                getGoogleOAuthTokenAndLogin();
            }
        });
        Toast.makeText(getApplicationContext(),"create",Toast.LENGTH_SHORT).show();

    }

    //initialize facebook SDK Firebase db and butterknife here
    public void init() {
        userObject = createUserObject();
        eventBus = getEventBus();
        facebookSdkInit();
        mAuth = firebaseDbInit();
        setContentView(R.layout.sign_in);
        initButterKnife();
        emailLoginPresenter = new EmailLoginPresenterImpl(this);
    }
    @Nullable
    @Optional
    @OnClick({R.id.button_facebook_sign_in,R.id.sign_in_button,R.id.sign_up})
    public void buttonClick(View v) {
        if(v.getId()==R.id.button_facebook_sign_in) {
            onFacebookLoginClicked();

        }
        else if(v.getId()==R.id.sign_up) {
            Intent intent  = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
        else {
            /*login with email and password*/

            String emailId = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

            if(TextUtils.isEmpty(emailId))
            {
                Toast.makeText(getApplicationContext(),R.string.input_email,Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),R.string.input_password,Toast.LENGTH_LONG).show();
                return;
            }
            mainAppController.dismissKeyboard(this);
            emailLoginPresenter.validateLoginCredentials(emailId,password,mAuth,LoginActivity.this);
        }
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
        userObject = createUserObject();
        userObject.setEmailId(userData.getEmail());
        userObject.setUserId(userData.getUid());
        userObject.setUserName(userData.getDisplayName());
    }

    @Override
    public void checkUserLogin() {
        //method to check if the user is registered in the system, if logged in the user will be redirected to the main screen
        if (mAuth != null) {
            //open the next activity from here
            Toast.makeText(getApplicationContext(),"logged in",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkUserLogin();
    }

    @Override
    protected void onResume() {
        //called when a dialog is closed and activity is resumed, otherwise on start of other activity called after onStart
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        //called when activity completely goes out of focus
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
            //handle facebook result
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void handleGoogleSignInResult(Intent data)
    {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        GoogleSignInAccount acct = result.getSignInAccount();
        userObject.setUserName(acct.getDisplayName());
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        userObject.setEmailId(acct.getEmail());
        String personId = acct.getId();
        userObject.setImageUrl(String.valueOf(acct.getPhotoUrl()));
        firebaseAuthWithGoogle(acct);

        //save profile data in shared prefs for further use
        Utils.getInstance().saveUserProfileSharedPrefs(this,Constants.LOGIN_PREFERENCES,userObject);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
         progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmailError() {
        Toast.makeText(getApplicationContext(),R.string.wrong_emailid,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordError() {
        Toast.makeText(getApplicationContext(),R.string.password_too_short,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthFailedError() {
        Toast.makeText(getApplicationContext(),R.string.sign_up,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToOtherScr() {
        // move to the next screen
        Toast.makeText(getApplicationContext(),"login successful",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSignUpFailedError() {
       // not required here
    }

    @Override
    protected void onDestroy() {
        emailLoginPresenter.onDestroy();
        super.onDestroy();
    }
}
