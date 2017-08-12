package com.siddhu.capp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.siddhu.capp.R;
import com.siddhu.capp.models.RegisterRequest;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;


public class Utility {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+-\\.]+(\\+-\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String POSTALCODE_PATTERN = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
    private static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final Random rnd = new Random();
    private static final String TAG = Utility.class.getSimpleName();
    public static final boolean ENABLE_PUBLIC_ENCRYPTION = false;
    private static Date startDate;
    private static Date endDate;
    public static final boolean ENABLE_INAUTH = true;
    public static final String RSA = "RSA/ECB/PKCS1Padding";//JKN
    public static final InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            /*for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;*/
            if (source.equals("") || source.equals("€") || source.equals("£")) { // for backspace
                return source;
            }
            if (source.toString().matches("[\\x00-\\x7F]+")) {
                return source;
            }
            return "";
        }
    };
    private static ProgressDialog mProgressDialog;
    private static AlertDialog mDialog;
    private static Dialog outsideUKDialog;

    /**
     * Generates a random string of specified length and returns it.
     * The returned string is used as correlation id, send as part of requestBody in addCard and
     * addLoyaltyCard service call.
     *
     * @param len The length of the random string
     * @return Returns a random string of length len
     */
    public static String randomString(int len) {
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /**
     * Checks whether the entered email is a valid one or not using
     * pattern matcher.
     *
     * @param email Email id entered by the user
     * @return Returns true if the email id matches the pattern, otherwise returns false.
     */
    public static boolean isValidEmail(final String email) {
        String temp = email;
        if (!TextUtils.isEmpty(temp)) {
            temp = temp.replaceFirst("\\s+$", "");
            final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            final Matcher matcher = pattern.matcher(temp);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /**
     * Checks whether the password contains at least eight characters or not.
     *
     * @param passwordText password text entered by the user
     * @return true if the password contains at least eight characters else returns false.
     */
    public static boolean hasAtLeastEightChar(String passwordText) {
        return passwordText.length() >= 8;
    }

    /**
     * Checks whether the password has upper case character or not.
     *
     * @param passwordText password text entered by the user
     * @return true if the password contains an upper case character else returns false.
     */
    public static boolean hasUpperCase(String passwordText) {
        int stringLength = passwordText.length();
        while (stringLength != 0) {
            final char singleChar = passwordText.charAt(stringLength - 1);
            stringLength--;
            if (Character.isUpperCase(singleChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the password has lower case character or not.
     *
     * @param passwordText password text entered by the user
     * @return true if the password contains an lower case character else returns false.
     */
    public static boolean hasLowerCase(String passwordText) {
        int stringLength = passwordText.length();
        while (stringLength != 0) {
            final char singleChar = passwordText.charAt(stringLength - 1);
            stringLength--;
            if (Character.isLowerCase(singleChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the password contains a number or not.
     *
     * @param passwordText password text entered by the user
     * @return true if the password contains an lower case character else returns false.
     */
    public static boolean hasNumber(String passwordText) {
        int stringLength = passwordText.length();
        while (stringLength != 0) {
            final char singleChar = passwordText.charAt(stringLength - 1);
            stringLength--;
            if (Character.isDigit(singleChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the password contains a special character or not.
     *
     * @param s password text entered by the user
     * @return true if the password contains a special character, else returns false.
     */
    public static boolean hasSpecialChar(String s) {
        int stringLength = s.length();
        while (stringLength != 0) {
            final char singleChar = s.charAt(stringLength - 1);
            stringLength--;
            if (!Character.isLetterOrDigit(singleChar) && !s.matches("[a-zA-Z.? ]*")) {
                return true;
            }

        }
        return false;
    }

    /**
     * Checks whether the password contains a special character or not.
     *
     * @param passwordText password text entered by the user
     * @return true if the password contains a special character, else returns false.
     */
    public static boolean hasSpecialCharWithSpaceAllowed(String passwordText) {
        int stringLength = passwordText.length();
        while (stringLength != 0) {
            final char singleChar = passwordText.charAt(stringLength - 1);
            stringLength--;
            if (!Character.isLetterOrDigit(singleChar) && !passwordText.matches("[ \\s a-zA-Z0-9.? ]*")) {
                return true;
            }

        }
        return false;
    }

    public static boolean isAsciiPrintable(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!isAsciiPrintable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch <= 126;
    }

    /**
     * Hides the software keyboard.
     *
     * @param activity Current activity instance
     */
    public static void hideSoftKeyboard(Activity activity) {
        try {
            final InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        } catch (Exception e) {
          //  Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }


    public static boolean isValidPasswordRegex(String password) {
        Pattern re = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).*");
        Matcher m = re.matcher(password);
        return m.find();
    }

    /**
     * Takes password string as an input and checks the validity of it
     * If it is valid, then returns true, otherwise returns false.
     *
     * @param password password string entered by user
     * @return
     */
    public static boolean isValidPassword(String password) {
        if ((hasNumber(password) || hasSpecialChar(password)) && hasAtLeastEightChar(password))
            if (hasLowerCase(password)) if (hasUpperCase(password)) return true;
        return false;
    }

    /**
     * Returns the device id to the application.
     *
     * @param context current application context.
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * Returns the type of card from the card number entered by the user
     *
     * @param credCardNumber Card number entered by the user
     * @return Returns the type of card
     */
   /* public static String getCardType(final String credCardNumber) {
        String creditCard = credCardNumber.trim();

        if (creditCard.startsWith("4")) {
            return AppConstants.VISA;
        }
        if (creditCard.startsWith("5")) {
            int prefix = Integer.parseInt(creditCard.substring(0, 2));
            if (prefix >= 51 && prefix <= 59) {
                return AppConstants.MASTERCARD;
            }
        }
        if (creditCard.startsWith("34") || creditCard
                .startsWith("37")) {
            return AppConstants.AMEX;
        }
        return AppConstants.NONE;
    }*/

    /**
     * Returns network connectivity information
     *
     * @param context The application context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        } else {
            final ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }


    public static void showProgressDialog(Context context, String msg, final Call apiCall, boolean isCancelable) {
        try {
            // if (mProgressDialog == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProgressDialog = new ProgressDialog(context, R.style.alertDialogTheme);
            } else {
                mProgressDialog = new ProgressDialog(context);//, R.style.alertDialogTheme);

            }
            /*} else {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }*/
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(isCancelable);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if(apiCall != null && !apiCall.isCanceled()) {
                        Log.e("Progress Canceled", String.valueOf(apiCall.isExecuted()));
                        Log.e("Progress Canceled", apiCall.getClass().getSimpleName());
                        apiCall.cancel();
                    }
                }
            });
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            width = width - 40;
            mProgressDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
         //   Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }

    /**
     * This method to cancel the api request while dismissing progress dialog.
     * @param apiCall
     * */
    public static void showProgressDialog(Context context, Call apiCall, boolean isCancelable) {
        showProgressDialog(context, "Please wait...", apiCall, isCancelable);
    }

    public static void showProgressDialog(Context context) {
            showProgressDialog(context, "Please wait...", null, false);
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            //Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }

    /**
     * This method is generic, used by the application has to display messages using AlertDialog.
     * The alert dialog will have only "OK" button. On click of "OK" the alert dialog will be
     * removed.
     *
     * @param message alert message to be displayed
     */
    public static void showAlertMessage(final Activity activity, final String message) {
        showAlertMessage(activity, message);
    }
    /**
     * This method is generic, used by the application has to display messages using AlertDialog.
     * The alert dialog will have only "OK" button. On click of "OK" the alert dialog will be
     * removed.
     *
     * @param message alert message to be displayed
     */
    public static void showAlertMessage(final Activity activity, final String message,  DialogInterface.OnClickListener listener) {
        showAlertMessage(activity, message,listener );
    }



    public static void setStartTime() {
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.getTime();
        Log.i(TAG, "startTime: " + startDate.toString());
    }

    public static long setEndTime() {
        Calendar calendar = Calendar.getInstance();
        endDate = calendar.getTime();
        Log.i(TAG, "endTime: " + endDate.toString());
        long diff = endDate.getTime() - startDate.getTime();
        return diff;
    }

    /**
     * This method will show the alert message to the user if it uses the application
     * outside of the UK region.
     * If user is in Fuelfinder screen then on click ok OK button, it should stay in same screen.
     * Otherwise user should navigate back to dashboard screen.
     * If the app is accessed outside UK, then user can do
     * @param context
     * @param message
     */



    public static void showAlertMessage(final Context context, final String message, final boolean dismiss) {
        try {
            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context,
                    R.style.alertDialogTheme);

            alertDialog.setMessage(message);
            alertDialog.setCancelable(dismiss);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            mDialog = alertDialog.show();
        } catch (Exception e) {
           // Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }




    /**
     * This method is generic, used by the application has to display messages using AlertDialog.
     * The alert dialog will have only "OK" button. On click of "OK" the alert dialog will be
     * removed.
     *
     * @param message alert message to be displayed
     */
    public static void showAlertMessage(final Activity activity, final String message, String title, DialogInterface.OnClickListener clickListener) {
        if (activity != null && !activity.isFinishing()) {
            if (mDialog != null && mDialog.isShowing()) {
                try {
                    mDialog.dismiss();
                } catch (Exception e) {
                  //  Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
                }
            }

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity,
                    R.style.alertDialogTheme);
            alertDialog.setMessage(message).setCancelable(false);
            if (!TextUtils.isEmpty(title)) {
                alertDialog.setTitle(title);
            }
            alertDialog.setPositiveButton("OK", clickListener);
            mDialog = alertDialog.show();
        } else {
            Log.e(TAG, "Activity object null");
        }
    }





    /**
     * Takes a number string and the format(means after decimal point how many digits we should show)
     * as an input and returns a formatted number accordingly.
     *
     * @param number decimal number as a string
     * @param format decimal number format
     * @return Returns formatted decimal number
     */
    public static String formatDecimal(String number, String format) {
        String formattedValue = "";
        if (!TextUtils.isEmpty(number)) {
            try {
                Double dblVar = Double.parseDouble(number);
                DecimalFormat df = new DecimalFormat(format);
                System.out.println("Value: " + df.format(dblVar));
                formattedValue = df.format(dblVar);
            } catch (Exception pe) {
               // Log.e(AppConstants.EXCPTN_TAG, pe.getMessage(), pe);
                return formattedValue;
            }
        }
        return formattedValue;
    }


    public static void showAlertMessage(Context context, String message) {

        try {
            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context,
                    R.style.alertDialogTheme);
            alertDialog.setMessage(message).setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            mDialog = alertDialog.show();
        } catch (Exception e) {
           // Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }

    public static void showErrorView(AppCompatTextView mErrorTextView, String mErrorMsg) {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(mErrorMsg);
    }

    /**
     * Changes the border color of the edit text to red and sets the validation text in it.
     * This generic method is called when user taps on continue button without entering any
     * details in editText.
     *
     * @param editText       EditText on focus whose border color has to be changed.
     * @param errorTextView  error textView which displays error messages below editText
     * @param validationText error message
     */
    public static void changeEditTextBgWithValidations(Context context, EditText editText,
                                                       TextView errorTextView, String validationText) {
       // editText.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_redborder));
        editText.setTextColor(ContextCompat.getColor(context, R.color.error_text_color));
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(validationText);
    }

    public static void handleErrorResponse(Activity activity, Response<?> response) {
        RegisterRequest failureResponse = new RegisterRequest();
        try {
                 failureResponse = GsonUtil.fromJson(response.errorBody().string(), RegisterRequest.class);
                showAlertMessage(activity, response.raw().message());
        } catch (IOException e) {
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
    }
}


