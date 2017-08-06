package com.siddhu.capp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final String TAG = ValidationUtils.class.getSimpleName();
    private ValidationUtils(){

    }

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String  VALID_FIRST_NAME="[a-zA-Z]*";
    private static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final Random rnd = new Random();
    private static ProgressDialog mProgressDialog;

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
    public static boolean isValidEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            String emailTemp = email.replaceFirst("\\s+$", "");
            final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            final Matcher matcher = pattern.matcher(emailTemp);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /**
     * Checks whether the first name is valid or not.
     *
     * @param firstName first name text entered by the user
     */
    public static boolean isValidFirstName(String firstName) {
        if (!TextUtils.isEmpty(firstName)) {
            return firstName.matches(VALID_FIRST_NAME);
        } else {
            return false;
        }
    }

    /**
     * Checks whether the last name is valid or not.
     *
     * @param lastName last name text entered by the user
     */
    public static boolean isValidLastName(String lastName) {
        if (!TextUtils.isEmpty(lastName)) {
            return lastName.matches(VALID_FIRST_NAME);
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
    public static boolean hasAtLeastSixChar(String passwordText) {
        return passwordText.length() >= 6;
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

            if (Character.isDigit(singleChar)&& (passwordText.replaceAll("-", "").length() == 10)) {
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
        final InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }


    public static boolean isValidPasswordRegex(String password) {
        Pattern re = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).*");
        Matcher m = re.matcher(password);
        return m.find();
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

    public static void showProgressDialog(Context context, String msg) {
        try {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } catch (Exception e) {
            Logger.log(TAG,e);
        }
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, "Please wait...");
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            Logger.log(TAG,e);
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
                formattedValue = df.format(dblVar);
            } catch (Exception pe) {
                Logger.log(TAG,pe);
                return formattedValue;
            }
        }
        return formattedValue;
    }

    public static boolean isValidateCreditCardNumber(String numberString) {
        final int[] ints = new int[numberString.length()];
        for (int i = 0; i < numberString.length(); i++) {
            ints[i] = Integer.parseInt(numberString
                    .substring(i, i + 1));
        }
        for (int i = ints.length - 2;
             i >= 0; i -= 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidStreetAddress(String streetAddress) {
        if (!TextUtils.isEmpty(streetAddress)) {
            return streetAddress.matches("[a-zA-Z 0-9'-_&$()]*");
        } else {
            return false;
        }
    }

    public static boolean isValidZipCode(String passwordText) {
        return passwordText.length() >= 5;
    }

    public static boolean isValidCityAddress(String cityAddress) {
        if (!TextUtils.isEmpty(cityAddress)) {
            return cityAddress.matches("[a-zA-Z]*");
        } else {
            return false;
        }
    }


    public static boolean hasUnits(String unit) {
        if (!TextUtils.isEmpty(unit)) {
            return unit.matches("[0-9]");
        } else {
            return false;
        }

    }

    public static boolean isValidState(String state) {
        if (!TextUtils.isEmpty(state)) {
            return state.matches("[a-zA-Z]*");
        } else {
            return false;
        }
    }
}
