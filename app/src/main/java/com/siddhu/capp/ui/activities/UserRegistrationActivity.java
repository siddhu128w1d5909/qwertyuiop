package com.siddhu.capp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.constants.ErrorConstants;
import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.UserAddress;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.network.ApiService;
import com.siddhu.capp.network.ApiUtils;
import com.siddhu.capp.network.NetworkUtility;
import com.siddhu.capp.presenter.RegistrationPresenter;
import com.siddhu.capp.presenter.RegistrationPresenterImpl;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.FieldValidationUtil;
import com.siddhu.capp.utils.UrlUtility;
import com.siddhu.capp.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static butterknife.OnItemSelected.Callback.NOTHING_SELECTED;

/**
 * Created by baji_g on 7/25/2017.
 */

public class UserRegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener,RegistrationPresenter.RegistrationView {
    private Unbinder unbinder;

    @BindView(R.id.activity_reg_fname_text_input_layout)
    TextInputLayout mFnameTextInputLayout;
    @BindView(R.id.et_first_name)
    TextInputEditText mFirstName;

    @BindView(R.id.activity_reg_lname_text_input_layout)
    TextInputLayout mLnameTextInputLayout;
    @BindView(R.id.et_last_name)
    TextInputEditText mLasName;

    @BindView(R.id.activity_reg_email_text_input_layout)
    TextInputLayout mEmailTextInputLayout;
    @BindView(R.id.et_email)
    TextInputEditText mEmail;

    @BindView(R.id.activity_reg_pwd_text_input_layout)
    TextInputLayout mPwdTextInputLayout;
    @BindView(R.id.et_password)
    TextInputEditText mPassword;

    @BindView(R.id.activity_reg_street_text_input_layout)
    TextInputLayout mStreetTextInputLayout;
    @BindView(R.id.et_address_line1)
    TextInputEditText mAddressLine1;

    @BindView(R.id.activity_reg_city_text_input_layout)
    TextInputLayout mCityTextInputLayout;
    @BindView(R.id.et_address_line2)
    TextInputEditText mAddressLine2;

    @BindView(R.id.activity_reg_mobile_text_input_layout)
    TextInputLayout mMobileTextInputLayout;
    @BindView(R.id.et_mobile_Number)
    TextInputEditText mMobileNumber;

    @BindView(R.id.activity_reg_pincode_text_input_layout)
    TextInputLayout mPincodeTextInputLayout;
    @BindView(R.id.et_pincode)
    TextInputEditText mPincode;

    @BindView(R.id.fname_error)
    AppCompatTextView mFnameErrorTv;
    @BindView(R.id.lname_error)
    AppCompatTextView mLnameErrorTv;
    @BindView(R.id.email_error)
    AppCompatTextView mEmailErrorTv;
    @BindView(R.id.pwd_error)
    AppCompatTextView mPwdErrorTv;
    @BindView(R.id.street_error)
    AppCompatTextView mStreetErrorTv;
    @BindView(R.id.city_error)
    AppCompatTextView mCityErrorTv;
    @BindView(R.id.mobile_error)
    AppCompatTextView mMobileErrorTv;
    @BindView(R.id.pincode_error)
    AppCompatTextView mPincodeErrorTv;


    @BindView(R.id.header_txt)
    AppCompatTextView mHeaderText;
    @BindView(R.id.header_back_btn)
    AppCompatImageView mHeaderBackBtn;


    @BindView(R.id.blood_spinner)
    Spinner mBloodSpinner;
    @BindView(R.id.branch_spinner)
    Spinner mBranchSpinner;
    @BindView(R.id.btn_register)
    AppCompatButton mRegisterBtn;

    @BindView(R.id.tv_login)
    AppCompatTextView mLoginBtn;

    ArrayAdapter<CharSequence> adapter;
    ApiService apiService;
    private String mBranchSelection;
    private String mBloodSelection;
    private Unbinder mUnbinder;
    private String [] mBloodGroupArray,mBranchArray;
    private List<CharSequence> mBloodGroupitems,mBranchitems;
    public static Intent getRegisterIntent(@NonNull Context context) {
        Intent lIntent = new Intent(context, UserRegistrationActivity.class);
        //lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(lIntent);
        return lIntent;
    }
    public RegistrationPresenterImpl mRegistrationPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUnbinder = ButterKnife.bind(this);
        mRegistrationPresenter = new RegistrationPresenterImpl();

        mFirstName.setOnFocusChangeListener(this);
        mLasName.setOnFocusChangeListener(this);
        mEmail.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
        mAddressLine1.setOnFocusChangeListener(this);
        mAddressLine2.setOnFocusChangeListener(this);
        mMobileNumber.setOnFocusChangeListener(this);
        mPincode.setOnFocusChangeListener(this);

        mHeaderText.setText("Registration");

        mBloodGroupArray = getResources().getStringArray(R.array.blood_group);
        mBloodGroupitems = new ArrayList<CharSequence>(Arrays.asList(mBloodGroupArray));
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, mBloodGroupitems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mBloodSpinner.setAdapter(adapter);

        mBranchArray = getResources().getStringArray(R.array.branch_array);
        mBranchitems = new ArrayList<CharSequence>(Arrays.asList(mBranchArray));
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, mBranchitems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mBranchSpinner.setAdapter(adapter);

        //mRegisterBtn.setEnabled(false);
       // mBloodSpinner.setAdapter(adapter);
       // apiService = ApiUtils.getApiService();


    }


    @Override
    protected void onStart() {
        super.onStart();
        mRegistrationPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mRegistrationPresenter.detachView();
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @OnClick(R.id.header_back_btn)
        void backBtn(){
            finish();
    }
    @OnTextChanged(R.id.et_first_name)
    void onTextChanged(CharSequence s, int i1, int i2, int i3) {
        changeTextOnFocusChange(mFnameErrorTv, true,mFirstName);
    }

    @OnItemSelected(R.id.blood_spinner)
    public void onBloodGroupItemSelected (int position) {
        // you know which item is selected.
        mBloodSelection = mBloodSpinner.getSelectedItem().toString();
    }
    @OnItemSelected(value = R.id.blood_spinner,
            callback = NOTHING_SELECTED)
    public void onBloodGroupItemNotSelected () {
        // nothing is selected.
        mBloodSelection = mBloodSpinner.getSelectedItem().toString();
    }

    @OnItemSelected(R.id.branch_spinner)
    public void onBranchItemSelected (int position) {
        // you know which item is selected.
        mBranchSelection = mBranchSpinner.getSelectedItem().toString();
        Toast.makeText(this, "position: " + position +"==>+"+mBranchSelection, Toast.LENGTH_SHORT).show();
    }
    @OnItemSelected(value = R.id.branch_spinner,
            callback = NOTHING_SELECTED)
    public void onBranchItemNotSelected () {
        // nothing is selected.
        mBranchSelection = mBranchSpinner.getSelectedItem().toString();
        Toast.makeText(this, "position: " + mBranchSelection +"==>+"+mBranchSelection, Toast.LENGTH_SHORT).show();
    }

    @OnItemSelected(R.id.blood_spinner)
    public void onBloodItemSelected (int position) {
        // you know which item is selected.
        mBloodSelection = mBloodSpinner.getSelectedItem().toString();
        Toast.makeText(this, "position: " + position +"==>+"+mBloodSelection, Toast.LENGTH_SHORT).show();
    }
    @OnItemSelected(value = R.id.blood_spinner,
            callback = NOTHING_SELECTED)
    public void onBloodItemNotSelected () {
        // nothing is selected.
        mBloodSelection = mBloodSpinner.getSelectedItem().toString();
        Toast.makeText(this, "position: " + mBloodSelection +"==>+"+mBloodSelection, Toast.LENGTH_SHORT).show();
    }

     @OnClick(R.id.tv_login)
     void gotoLogin(){
         UserLoginActivity.getLoginIntent(this);
         finish();
         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
     }
    @OnClick(R.id.btn_register)
    public void register(){
        if(isValidated()){
            mRegisterBtn.setEnabled(false);

            mRegistrationPresenter.register(getRegistrationRequest());
           // makeRegisterServiceCall();
        }
        else{

        }
    }

    private void makeRegisterServiceCall() {
        final RegisterRequest registrationRequest = getRegistrationRequest();
        final ApiService serviceAPI = NetworkUtility.getServiceInstance(UrlUtility.getCommonRequestHeaders());
        final Call<RegisterRequest> registerRequestCall = serviceAPI.register(registrationRequest,UrlUtility.getCommonRequestHeaders());
        registerRequestCall.enqueue(new Callback<RegisterRequest>() {
            @Override
            public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                Utility.hideProgressDialog();

              //  final CommonResponse registarion = response.body();
                final RegisterRequest registarion = response.body();
                ResponseBody errorBody = response.errorBody();
                if (response.isSuccessful()) {
                    final RegisterRequest commonResponse = response.body();
                    handleSuccessResponse(commonResponse, response.message());
                } else {
                    Utility.handleErrorResponse(UserRegistrationActivity.this, response);
                }

            }

            @Override
            public void onFailure(Call<RegisterRequest> call, Throwable t) {

            }
        });
    }

    private void handleSuccessResponse(RegisterRequest commonResponse, String message) {

        if(commonResponse != null){
            if(commonResponse.getStatusCode().equalsIgnoreCase(AppConstants.STATUS_CODE_201)){
                Intent lIntent = new Intent(this, UserLoginActivity.class);
                lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(lIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }else {
            showAlertMessage(getString(R.string.message_txt), message);
        }


    }
    private void showAlertMessage(String title, String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserRegistrationActivity.this,
                R.style.alertDialogTheme);
        if (!TextUtils.isEmpty(title))
            alertDialog.setTitle(title);

        alertDialog.setMessage(message).setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.ok_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private RegisterRequest getRegistrationRequest() {
        UserAddress address = new UserAddress();
        address.setAddress(mAddressLine1.getText().toString());
        address.setAddress(mAddressLine2.getText().toString());
        address.setAddress(mPincode.getText().toString());
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname(mFirstName.getText().toString());
        registerRequest.setLastname(mLasName.getText().toString());
        registerRequest.setEmail(mEmail.getText().toString());
        registerRequest.setMobile(mMobileNumber.getText().toString());
        registerRequest.setPassword(mPassword.getText().toString());
        registerRequest.setUserAddress(address);
        return registerRequest;
    }

    private boolean isValidated() {
        boolean isValid = true;
        String mErrorMsg = "";
        if (TextUtils.isEmpty(mPassword.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mPassword,mPwdTextInputLayout,mPwdErrorTv,
                    getString(R.string.reg_invalid_password));
            isValid = false;
        } else if (!Utility.isValidPassword(mPassword.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mPassword,mPwdTextInputLayout,mPwdErrorTv,
                    getString(R.string.incorrect_password_error_text));
            isValid = false;
        }

        if (TextUtils.isEmpty(mEmail.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mEmail,mEmailTextInputLayout,mEmailErrorTv,
                    getString(R.string.reg_incorrect_email));
            isValid = false;
        } else if (!Utility.isValidEmail(mEmail.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mEmail,mEmailTextInputLayout,mEmailErrorTv,
                    getString(R.string.reg_incorrect_email));
            isValid = false;
        }


        if (TextUtils.isEmpty(mLasName.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mLasName,mLnameTextInputLayout,mLnameErrorTv,
                    getString(R.string.reg_invalid_last_name));
            isValid = false;
        } else if (TextUtils.isEmpty(mLasName.getText().toString().trim())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mLasName,mLnameTextInputLayout,mLnameErrorTv,
                    getString(R.string.reg_invalid_last_name));
            isValid = false;
        } else if (!Utility.isAsciiPrintable(mLasName.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mLasName,mLnameTextInputLayout,mLnameErrorTv,
                    getString(R.string.reg_invalid_last_name));
            isValid = false;
        }


        if (TextUtils.isEmpty(mFirstName.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mFirstName,mFnameTextInputLayout,mFnameErrorTv,
                    getString(R.string.reg_invalid_first_name));
            isValid = false;
        } else if (TextUtils.isEmpty(mFirstName.getText().toString().trim())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mFirstName,mFnameTextInputLayout,mFnameErrorTv,
                    getString(R.string.reg_invalid_first_name));
            isValid = false;
        } else if (!Utility.isAsciiPrintable(mFirstName.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mFirstName,mFnameTextInputLayout,mFnameErrorTv,
                    getString(R.string.reg_invalid_first_name));
            isValid = false;
        }

        if (TextUtils.isEmpty(mPincode.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mPincode,mPincodeTextInputLayout,mPincodeErrorTv,
                    getString(R.string.reg_invalid_pincode));
            isValid = false;
        } else if (TextUtils.isEmpty(mPincode.getText().toString().trim())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mPincode,mPincodeTextInputLayout,mPincodeErrorTv,
                    getString(R.string.reg_invalid_pincode));
            isValid = false;
        }else if (!Utility.isAsciiPrintable(mPincode.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mPincode,mPincodeTextInputLayout,mPincodeErrorTv,
                    getString(R.string.reg_invalid_pincode));
            isValid = false;
        }

        if (TextUtils.isEmpty(mAddressLine1.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mAddressLine1,mStreetTextInputLayout,mStreetErrorTv,
                    getString(R.string.reg_invalid_street_name));
            isValid = false;
        }else if (!Utility.isAsciiPrintable(mAddressLine1.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mAddressLine1,mStreetTextInputLayout,mStreetErrorTv,
                    getString(R.string.reg_invalid_street_name));
            isValid = false;
        }

        if (TextUtils.isEmpty(mAddressLine2.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mAddressLine2,mCityTextInputLayout,mCityErrorTv,
                    getString(R.string.reg_invalid_city_name));
            isValid = false;
        } else if (TextUtils.isEmpty(mAddressLine2.getText().toString().trim())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mAddressLine2,mCityTextInputLayout,mCityErrorTv,
                    getString(R.string.reg_invalid_city_name));
            isValid = false;
        }else if (!Utility.isAsciiPrintable(mAddressLine2.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mAddressLine2,mCityTextInputLayout,mCityErrorTv,
                    getString(R.string.reg_invalid_city_name));
            isValid = false;
        }

        if (TextUtils.isEmpty(mMobileNumber.getText())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mMobileNumber,mMobileTextInputLayout,mMobileErrorTv,
                    getString(R.string.reg_invalid_mobile_number));
            isValid = false;
        } else if (TextUtils.isEmpty(mMobileNumber.getText().toString().trim())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mMobileNumber,mMobileTextInputLayout,mMobileErrorTv,
                    getString(R.string.reg_invalid_mobile_number));
            isValid = false;
        }else if (!Utility.isAsciiPrintable(mMobileNumber.getText().toString())) {
            FieldValidationUtil.changeEditTextLyoutColor(this,mMobileNumber,mMobileTextInputLayout,mMobileErrorTv,
                    getString(R.string.reg_invalid_mobile_number));
            isValid = false;
        }else if(mMobileNumber.getText().toString().trim().length() < 9 ){
            FieldValidationUtil.changeEditTextLyoutColor(this,mMobileNumber,mMobileTextInputLayout,mMobileErrorTv,
                    getString(R.string.reg_invalid_mobile_number));
        }


        if(isValid){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        switch (view.getId()){
            case R.id.et_first_name:
                changeTextOnFocusChange(mFnameErrorTv,hasFocus,mFirstName);
                break;
            case R.id.et_last_name:
                changeTextOnFocusChange(mLnameErrorTv,hasFocus,mLasName);
                break;
            case R.id.et_email:
                changeTextOnFocusChange(mEmailErrorTv,hasFocus,mEmail);
                break;
            case R.id.et_password:
                changeTextOnFocusChange(mPwdErrorTv,hasFocus,mPassword);
                break;
            case R.id.et_address_line1:
                changeTextOnFocusChange(mStreetErrorTv,hasFocus,mAddressLine1);
                break;
            case R.id.et_address_line2:
                changeTextOnFocusChange(mCityErrorTv,hasFocus,mAddressLine1);
                break;
            case R.id.et_mobile_Number:
                changeTextOnFocusChange(mMobileErrorTv,hasFocus,mMobileNumber);
                break;
            case R.id.et_pincode:
                changeTextOnFocusChange(mPwdErrorTv,hasFocus,mPincode);
                break;
        }
    }

    private void changeTextOnFocusChange(AppCompatTextView mErrorTv, boolean hasFocus,TextInputEditText currentFocusedET) {
        if(hasFocus){
            mErrorTv.setVisibility(View.GONE);
            currentFocusedET.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }else{

        }
    }

    @Override
    public void onRegistrationSuccessful(RegisterRequest response) {
        UserLoginActivity.getLoginIntent(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onRegistrationFailed(APIError apiError) {
        if (apiError != null && apiError.status() == ErrorConstants.ERROR_CODE_401) {
            Utility.showAlertMessage(this,apiError.message().toString());
        }
    }

    @Override
    public void onRegistrationFailed(Throwable throwable) {
        if (throwable != null) {
            Toast.makeText(CollegeApplicationClass.getInstance(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mRegisterBtn.setEnabled(true);
    }

    @Override
    public void showHideProgress(boolean show) {

    }
}
