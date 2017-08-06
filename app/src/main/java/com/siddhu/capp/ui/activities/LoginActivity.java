package com.siddhu.capp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.constants.ErrorConstants;
import com.siddhu.capp.models.login.LoginResponse;
import com.siddhu.capp.presenter.LoginPresenter;
import com.siddhu.capp.presenter.LoginPresenterImpl;
import com.siddhu.capp.ui.views.FontButton;
import com.siddhu.capp.ui.views.FontTextView;
import com.siddhu.capp.ui.views.TextInputEditTextCustom;
import com.siddhu.capp.ui.views.UnTouchableLinearLayout;
import com.siddhu.capp.utils.FieldValidationUtil;
import com.siddhu.capp.utils.KeyboardUtils;
import com.siddhu.capp.utils.SessionUtils;
import com.siddhu.capp.utils.ValidationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by baji_g on 1/6/2017.
 */
public class LoginActivity extends CollegeMainActivity implements View.OnFocusChangeListener,LoginPresenter.LoginView {
    private static final String LOGIN_EXTRA_PARAM = "from_activity";
    private boolean mSetResult = false;
    private Unbinder mUnbinder;
    private boolean mShowPassword = false;
    private final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.activity_login_root_layout)
    UnTouchableLinearLayout mRooTouchableLinearLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_login_error_text_view)
    FontTextView mInvalidEmailTextView;

    @BindView(R.id.activity_login_email_text_input_layout)
    TextInputLayout mEmailTextInputLayout;
    @BindView(R.id.activity_login_email_text_input_edit_text)
    TextInputEditTextCustom mEmailTextInputEditText;

    @BindView(R.id.activity_login_password_text_input_layout)
    TextInputLayout mPasswordTextInputLayout;
    @BindView(R.id.activity_login_password_text_input_edit_text)
    TextInputEditText mPasswordTextInputEditText;

    @BindView(R.id.activity_login_password_show_text_view)
    ImageView mShowImageView;

    @BindView(R.id.activity_login_password_cross_icon)
    ImageView mPasswordCrossIcon;

    @BindView(R.id.activity_login_sign_in_button)
    FontButton mSignInButton;

    @BindView(R.id.activity_login_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.activity_login_forgot_password_layout)
    UnTouchableLinearLayout mForgotPassword;

    @BindView(R.id.activity_login_trouble_logging_layout)
    UnTouchableLinearLayout mTroubleLogging;

    LoginPresenterImpl mLoginPresenter;

    public static Intent getLoginIntent(@NonNull Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);

        mEmailTextInputEditText.setOnFocusChangeListener(this);
        mPasswordTextInputEditText.setOnFocusChangeListener(this);

        setSupportActionBar(mToolbar);
       /* getSupportActionBar().setTitle(Utility.getTitleTypeFace(this, getResources().getString(R.string.welcome_back)));
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        final Spannable spannableString = new SpannableString(getString(R.string.whoops_this_email_address_is_invalid));
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getBaseContext(), R.color.error_text_color)),
                0, 7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mInvalidEmailTextView.setText(spannableString);

        final Typeface archerSemibold = Typeface.createFromAsset(getAssets(), "fonts/Archer-Semibold.otf");
        mEmailTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        mPasswordTextInputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEmailTextInputLayout.setTypeface(archerSemibold);
        mEmailTextInputEditText.setTypeface(archerSemibold);
        mPasswordTextInputLayout.setTypeface(archerSemibold);
        mPasswordTextInputEditText.setTypeface(archerSemibold);

        mLoginPresenter = new LoginPresenterImpl();
        mSignInButton.setEnabled(false);
        KeyboardUtils.showKeyboard(LoginActivity.this, mEmailTextInputEditText);
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
            mSignInButton.setEnabled(false);
            mLoginPresenter.login(mEmailTextInputEditText.getText().toString().trim(),
                    mPasswordTextInputEditText.getText().toString());
        }

        KeyboardUtils.hideKeyboard(this);
    }

    @SuppressWarnings("SameReturnValue")
    @OnEditorAction(R.id.activity_login_password_text_input_edit_text)
    boolean onPasswordDoneClicked(final TextView textView, int i, KeyEvent event) {
        onSignInClicked();
        return true;
    }

    @OnTextChanged(R.id.activity_login_email_text_input_edit_text)
    void onEmailTextChanged(CharSequence s, int i1, int i2, int i3) {
        if (s.toString().trim().length() == 0) {
            mSignInButton.setEnabled(false);
            mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
                    R.color.gray_disabled));
        }
        validateOntextchange(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), true);
    }

    @OnTextChanged(R.id.activity_login_password_text_input_edit_text)
    void onPasswordTextChanged(CharSequence s, int i1, int i2, int i3) {
        if (s.toString().length() == 0) {
            mShowImageView.setVisibility(View.GONE);
            mPasswordCrossIcon.setVisibility(View.GONE);
            mSignInButton.setEnabled(false);
            mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
                    R.color.gray_disabled));
        } else {
            mShowImageView.setVisibility(View.VISIBLE);
            mPasswordCrossIcon.setVisibility(View.VISIBLE);
        }
        validateOntextchange(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), false);
    }


    private boolean validate(final String email, final String password, boolean isEmail) {
        boolean validate;
        mSignInButton.setEnabled(false);
        mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
                R.color.gray_disabled));
        validate = ValidationUtils.isValidEmail(email);
        if (!validate) {
            //mEmailTextInputLayout.setError("Please enter valid email id");
            FieldValidationUtil.changeEditTextColor(true,mEmailTextInputEditText,this);
            FieldValidationUtil.showErrorTextView(true,mInvalidEmailTextView,"please enter valid email id");
            if (isEmail) {
                return false;
            }
        } else {
            //mEmailTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false,mEmailTextInputEditText,this);
            FieldValidationUtil.showErrorTextView(false,mInvalidEmailTextView,"");
        }

        if (!isEmail) {
            validate = ValidationUtils.hasAtLeastSixChar(password);
            if (!validate) {
                //mPasswordTextInputLayout.setError("Please enter valid password");
                FieldValidationUtil.changeEditTextColor(true,mPasswordTextInputEditText,this);
                FieldValidationUtil.showErrorTextView(true,mInvalidEmailTextView,"please enter valid password");
                return false;
            } else {
                //mPasswordTextInputLayout.setError(null);
                FieldValidationUtil.changeEditTextColor(false,mPasswordTextInputEditText,this);
                FieldValidationUtil.showErrorTextView(false,mInvalidEmailTextView,"");
            }
        }
        validate = ValidationUtils.isValidEmail(email) &&
                ValidationUtils.hasAtLeastSixChar(password);

        if (validate) {
            mSignInButton.setEnabled(true);
            mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
                    R.color.colorAccent));
        }

        return validate;
    }
    private void validateOntextchange(final String email, final String password, boolean isEmail) {
        boolean validate;
        mSignInButton.setEnabled(false);
        mInvalidEmailTextView.setVisibility(View.GONE);
        mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
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
            mSignInButton.setEnabled(true);
            mSignInButton.setTextColor(ContextCompat.getColor(CollegeApplicationClass.getInstance(),
                    R.color.colorAccent));
        }
    }

    @OnClick(R.id.activity_login_password_cross_icon)
    void onPasswordClicked() {
        mPasswordTextInputEditText.setText("");
    }

    @OnClick(R.id.activity_login_password_show_text_view)
    void onShowHidePassword() {
        mShowPassword = !mShowPassword;

        if (mShowPassword) {
            mPasswordTextInputEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mShowImageView.setImageResource(R.drawable.ic_password_eye_close);


        } else {
            mPasswordTextInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mShowImageView.setImageResource(R.drawable.ic_password_eye_open);
        }

        mPasswordTextInputEditText.setSelection(mPasswordTextInputEditText.getText().toString().length());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onLoginSuccessful(LoginResponse response) {
        mInvalidEmailTextView.setVisibility(View.GONE);
        SessionUtils.saveSession(CollegeApplicationClass.getInstance(), response);
        if (mSetResult) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            startActivity(HomeActivity.getHomeIntent(CollegeApplicationClass.getInstance()));
        }
    }

    @Override
    public void onLoginFailed(com.siddhu.capp.models.error.APIError apiError) {
        if (apiError != null && apiError.status() == ErrorConstants.ERROR_CODE_401) {
            String[] data = {
                    "Uh oh!", // Title
                    apiError.message(), // Message
                    "ok", // Button
                    "try again" // Button
            };

           /* Utility.showAlertMessage(this, data, new Utility.OnDialogClickListener() {
            @Override
            public void OnNegativeClick() {
                mSignInButton.setEnabled(true);
            }

            @Override
            public void OnCancelDialogClick() {
                //This is a cancel dialog
            }

            @Override
            public void OnPositiveClick() {
                onSignInClicked();
            }
        });*/
        }
    }

   /* @Override
    public void onLoginFailed(APIError apiError) {
        if (apiError != null && apiError.status() == ErrorConstants.ERROR_CODE_401) {
            String[] data = {
                    "Uh oh!", // Title
                    apiError.message(), // Message
                    "ok", // Button
                    "try again" // Button
            };
           *//* Utility.showdialog(this, data, new Utility.OnDialogClickListener() {
                @Override
                public void OnNegativeClick() {
                    mSignInButton.setEnabled(true);
                }

                @Override
                public void OnCancelDialogClick() {
                    //This is a cancel dialog
                }

                @Override
                public void OnPositiveClick() {
                    onSignInClicked();
                }
            });*//*
        }

    }*/

    @Override
    public void onLoginFailed(Throwable throwable) {
        mInvalidEmailTextView.setVisibility(View.GONE);
        if (throwable != null) {
            Toast.makeText(CollegeApplicationClass.getInstance(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mSignInButton.setEnabled(true);
    }


    @Override
    public void showHideProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mRooTouchableLinearLayout.setTouchable(!show);
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

    private void changeEmailTextOnFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            mEmailTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false,mEmailTextInputEditText,this);
        }
        if (!hasFocus && mEmailTextInputEditText.getText().toString().length() > 0) {
            validate(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), true);
        }
        mEmailTextInputEditText.onFocusChange_Custom(view, hasFocus);
    }

    private void changePasswordTextOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            mPasswordTextInputLayout.setError(null);
            FieldValidationUtil.changeEditTextColor(false,mPasswordTextInputEditText,this);

            if (mPasswordTextInputEditText.getText().toString().length() > 0) {
                mPasswordCrossIcon.setVisibility(View.VISIBLE);
                mShowImageView.setVisibility(View.VISIBLE);
            }
        } else {
            mPasswordCrossIcon.setVisibility(View.GONE);
            mShowImageView.setVisibility(View.GONE);
        }
        if (!hasFocus && mPasswordTextInputEditText.getText().toString().length() > 0) {
            validate(mEmailTextInputEditText.getText().toString(), mPasswordTextInputEditText.getText().toString(), false);
            mShowImageView.setVisibility(View.VISIBLE);
        }
    }

}
