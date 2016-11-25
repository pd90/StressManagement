package com.example.parasdhanta.stressmanagement.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.parasdhanta.stressmanagement.R;
import com.example.parasdhanta.stressmanagement.interfaces.login.EmailLoginView;
import com.example.parasdhanta.stressmanagement.managers.BaseActivity;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenter;
import com.example.parasdhanta.stressmanagement.presenters.EmailLoginPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Paras Dhanta on 9/29/2016.
 */

public class SignUpActivity extends BaseActivity implements EmailLoginView{
    private static final String TAG = SignUpActivity.class.getSimpleName();
    @BindView(R.id.email_signup) EditText emailFrSignUp;
    @BindView(R.id.password_signup) EditText passwordFrSignUp;
    @BindView(R.id.already_memeber) Button loginButton;
    @BindView(R.id.sign_up_button) Button signUpButton;
    @BindView(R.id.progressbar_signup)
    ProgressBar progressSignUp;

    private FirebaseAuth mAuth;

    EmailLoginPresenter emailLoginPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.sign_up;
    }

    @Override
    protected void setAppToolBar() {

    }

    public void init()
    {
        mainAppController.addActivity(this);
        setContentView(getLayoutResource());
        initButterKnife();
        mAuth = firebaseDbInit();
        emailLoginPresenter = new EmailLoginPresenterImpl(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Nullable
    @Optional
    @OnClick({R.id.sign_up_button,R.id.already_memeber})
    public void signUp(View v) {
        if(v.getId()==R.id.sign_up_button) {
            String inputEmail = emailFrSignUp.getText().toString();
            String inputPassword = passwordFrSignUp.getText().toString();

                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(getApplicationContext(), R.string.input_email, Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(inputPassword)) {
                    Toast.makeText(getApplicationContext(), R.string.input_password, Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.password_too_short, Toast.LENGTH_LONG).show();
                    return;
                }
            emailLoginPresenter.signUpCredentials(inputEmail,inputPassword,mAuth,SignUpActivity.this);
        }
        else{
            mainAppController.finishCurrentActivity(this);
        }
    }

    @Override
    public void showProgress() {
        progressSignUp.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressSignUp.setVisibility(View.GONE);
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
        // not required here
    }

    @Override
    public void navigateToOtherScr() {
       // move to the next screen
        Toast.makeText(getApplicationContext(),R.string.sign_up_success_message,Toast.LENGTH_LONG).show();
        mainAppController.finishCurrentActivity(this);
    }

    @Override
    public void showSignUpFailedError() {
        Toast.makeText(getApplicationContext(),R.string.sign_up_error,Toast.LENGTH_SHORT).show();

    }
}
