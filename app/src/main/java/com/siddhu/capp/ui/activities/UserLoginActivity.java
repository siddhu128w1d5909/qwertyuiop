package com.siddhu.capp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.constants.ErrorConstants;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginRequest;
import com.siddhu.capp.models.login.LoginResponse;
import com.siddhu.capp.presenter.LoginPresenter;
import com.siddhu.capp.presenter.LoginPresenterImpl;
import com.siddhu.capp.ui.views.FontButton;
import com.siddhu.capp.ui.views.FontTextView;
import com.siddhu.capp.ui.views.TextInputEditTextCustom;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.FieldValidationUtil;
import com.siddhu.capp.utils.KeyboardUtils;
import com.siddhu.capp.utils.Logger;
import com.siddhu.capp.utils.PreferenceUtil;
import com.siddhu.capp.utils.SessionUtils;
import com.siddhu.capp.utils.Utility;
import com.siddhu.capp.utils.ValidationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by baji_g on 7/26/2017.
 */
public class UserLoginActivity  extends AppCompatActivity implements View.OnFocusChangeListener,LoginPresenter.LoginView {
    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register,tv_forgotpwd,tv_dash;

    @BindView(R.id.activity_login_error_text_view)
    AppCompatTextView mErrorTextView;

    @BindView(R.id.activity_login_email_text_input_layout)
    TextInputLayout mEmailTextInputLayout;
    @BindView(R.id.activity_login_email_text_input_edit_text)
    TextInputEditText mEmailTextInputEditText;

    @BindView(R.id.activity_login_password_text_input_layout)
    TextInputLayout mPasswordTextInputLayout;
    @BindView(R.id.activity_login_password_text_input_edit_text)
    TextInputEditText mPasswordTextInputEditText;

    @BindView(R.id.activity_login_sign_in_button)
    AppCompatButton mSignInButton;

    @BindView(R.id.activity_login_register)
    AppCompatTextView mRegister;

    @BindView(R.id.activity_login_forgotpwd)
    AppCompatTextView mForgotPwd;

    @BindView(R.id.activity_login_dash)
    AppCompatTextView mDashBoard;
    private Unbinder mUnbinder;

    LoginPresenterImpl mLoginPresenter;

    public static Intent getLoginIntent(@NonNull Context context) {
        Intent lIntent = new Intent(context, UserLoginActivity.class);
        lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(lIntent);
        return lIntent;
    }
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);
        mUnbinder = ButterKnife.bind(this);
        mEmailTextInputEditText.setOnFocusChangeListener(this);
        mPasswordTextInputEditText.setOnFocusChangeListener(this);
        mLoginPresenter = new LoginPresenterImpl();
      //  mSignInButton.setEnabled(false);
        KeyboardUtils.showKeyboard(this, mEmailTextInputEditText);
        onNewIntent(getIntent());
    }


    @Override
    public void onStart() {
        super.onStart();
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginPresenter.detachView();
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @OnClick(R.id.activity_login_sign_in_button)
    void onSignInClicked() {
        if (validate(mEmailTextInputEditText.getText().toString().trim(),
                mPasswordTextInputEditText.getText().toString(), false)) {
         //   mSignInButton.setEnabled(false);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(mEmailTextInputEditText.getText().toString().trim());
            loginRequest.setPassword( mPasswordTextInputEditText.getText().toString());
            mLoginPresenter.login(mEmailTextInputEditText.getText().toString().trim(),
                    mPasswordTextInputEditText.getText().toString());
        }

        KeyboardUtils.hideKeyboard(this);
    }

    @OnClick(R.id.activity_login_dash)
    void gotoDashBoard(){
        DashBoardActivity.getDashBoardIntent(this);
    }

    private boolean validate(final String email, final String password, boolean isEmail) {
        boolean validate;
       // mSignInButton.setEnabled(false);
        mSignInButton.setTextColor(ContextCompat.getColor(this,
                R.color.gray_disabled));
        validate = ValidationUtils.isValidEmail(email);
        if (!validate) {
            //mEmailTextInputLayout.setError("Please enter valid email id");
            FieldValidationUtil.changeEditTextColor(true,mEmailTextInputEditText,this);
            FieldValidationUtil.showErrorTextView(true,mErrorTextView,"please enter valid email id");
            if (isEmail) {
                return false;
            }
        } else {
            //mEmailTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false,mEmailTextInputEditText,this);
            FieldValidationUtil.showErrorTextView(false,mErrorTextView,"");
        }

        if (!isEmail) {
            validate = ValidationUtils.hasAtLeastSixChar(password);
            if (!validate) {
                //mPasswordTextInputLayout.setError("Please enter valid password");
                FieldValidationUtil.changeEditTextColor(true,mPasswordTextInputEditText,this);
                FieldValidationUtil.showErrorTextView(true,mErrorTextView,"please enter valid password");
                return false;
            } else {
                //mPasswordTextInputLayout.setError(null);
                FieldValidationUtil.changeEditTextColor(false,mPasswordTextInputEditText,this);
                FieldValidationUtil.showErrorTextView(false,mErrorTextView,"");
            }
        }
        validate = ValidationUtils.isValidEmail(email) &&
                ValidationUtils.hasAtLeastSixChar(password);

        if (validate) {
          //  mSignInButton.setEnabled(true);
            mSignInButton.setTextColor(ContextCompat.getColor(this,
                    R.color.colorAccent));
        }

        return validate;
    }

    @OnTextChanged(R.id.activity_login_email_text_input_edit_text)
    void onEmailTextChanged(CharSequence s, int i1, int i2, int i3) {
        if (s.toString().trim().length() == 0) {
          //  mSignInButton.setEnabled(false);
            mSignInButton.setTextColor(ContextCompat.getColor(this,
                    R.color.gray_disabled));
        }
        validateOntextchange(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), true);
    }

    @OnTextChanged(R.id.activity_login_password_text_input_edit_text)
    void onPasswordTextChanged(CharSequence s, int i1, int i2, int i3) {
        if (s.toString().length() == 0) {
          //  mSignInButton.setEnabled(false);
            mSignInButton.setTextColor(ContextCompat.getColor(this,
                    R.color.gray_disabled));
        }
        validateOntextchange(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), false);
    }

    @OnClick(R.id.activity_login_register)
    void gotoRegisterPage(){
        UserRegistrationActivity.getRegisterIntent(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @OnClick(R.id.activity_login_forgotpwd)
    void gotoForgotPwdPage(){
        ForgotpasswordActivity.getForgotPwdIntent(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.activity_login_email_text_input_edit_text:
                changeEmailTextOnFocusChange(view, hasFocus);
                break;
            case R.id.activity_login_password_text_input_edit_text:
                changePasswordTextOnFocusChange(hasFocus);
                break;
            default:
                break;
        }
    }

    private void validateOntextchange(final String email, final String password, boolean isEmail) {
        boolean validate;
       // mSignInButton.setEnabled(false);
        mErrorTextView.setVisibility(View.GONE);
        mSignInButton.setTextColor(ContextCompat.getColor(this,
                R.color.gray_disabled));
        validate = ValidationUtils.isValidEmail(email);
        if (validate) {
            mEmailTextInputLayout.setError(null);
        }
        if (!isEmail) {
            validate = ValidationUtils.hasAtLeastSixChar(password);
            if (validate) {
                mPasswordTextInputLayout.setError(null);
            }
        }
        validate = ValidationUtils.isValidEmail(email) &&
                ValidationUtils.hasAtLeastSixChar(password);

        if (validate) {
          //  mSignInButton.setEnabled(true);
            mSignInButton.setTextColor(ContextCompat.getColor(this,
                    R.color.colorAccent));
        }
    }

    private void changeEmailTextOnFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            mEmailTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false,mEmailTextInputEditText,this);
        }
        if (!hasFocus && mEmailTextInputEditText.getText().toString().length() > 0) {
            validate(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), true);
        }
       // mEmailTextInputEditText.onFocusChange(view, hasFocus);
    }

    private void changePasswordTextOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            mPasswordTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false, mPasswordTextInputEditText, this);
        }
        if (!hasFocus && mPasswordTextInputEditText.getText().toString().length() > 0) {
            validate(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), false);
        }
    }

    @Override
    public void onLoginSuccessful(LoginResponse response) {
        Utility.hideProgressDialog();
        Log.e("Error", response.toString());
        if(response != null) {
            mErrorTextView.setVisibility(View.GONE);
            PreferenceUtil.getInstance(CollegeApplicationClass.getInstance()).saveStringParam(AppConstants.SESSION_TOKEN, response.getToken());
            Log.e("session token",PreferenceUtil.getInstance(CollegeApplicationClass.getInstance()).getStringParam(AppConstants.SESSION_TOKEN));
            startActivity(DashBoardActivity.getDashBoardIntent(CollegeApplicationClass.getInstance()));
        }else{
            Utility.showAlertMessage(this,"Unable to connect...");
        }

    }

    @Override
    public void onLoginFailed(APIError apiError) {
        Utility.hideProgressDialog();
        Log.e("Error",apiError.toString() );
        if (apiError != null && apiError.status() == ErrorConstants.ERROR_CODE_401) {
            Utility.showAlertMessage(this,apiError.message().toString());
        }
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        mErrorTextView.setVisibility(View.GONE);
        Log.e("Error", throwable.getMessage());
        if (throwable != null) {
            Log.e("Error", throwable.getMessage());
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Utility.hideProgressDialog();
        }
     //   mSignInButton.setEnabled(true);
    }

    @Override
    public void showHideProgress(boolean show) {
        if(show)
        Utility.showProgressDialog(this);
    }
}
