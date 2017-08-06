package com.siddhu.capp.utils;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.siddhu.capp.R;
import com.siddhu.capp.ui.activities.UserRegistrationActivity;


/**
 * Created by chethan_d on 8/26/2016.
 */
public class FieldValidationUtil {

    /**
     * changes TextInputEditText text color if input filed is invalid
     *
     * @param isInvalidInput    whether user has entered valid input
     * @param textInputEditText target view to change text color
     */
    public static void changeEditTextColor(boolean isInvalidInput, TextInputEditText textInputEditText, Context context) {
        if (isInvalidInput) {
            textInputEditText.setTextColor(ContextCompat.getColor(context, R.color.error_text_color));
        } else {
            textInputEditText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
    }

    public static void changeEditTextColor(boolean isInvalidInput, AutoCompleteTextView textInputEditText, Context context) {
        if (isInvalidInput) {
            textInputEditText.setTextColor(ContextCompat.getColor(context, R.color.error_text_color));
        } else {
            textInputEditText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
    }

    /**
     * show / hide error textview and set text
     *
     * @param setVisible flag to show/hide error textview
     * @param textView   target textview to show error
     * @param text       actual text to be shown
     */
    public static void showErrorTextView(boolean setVisible, TextView textView, String text) {
        if (setVisible) {

           /* final Spannable spannableString = new SpannableString("Whoops! ".concat(text));
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), R.color.error_text_color)),
                    0, 7,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);*/
            textView.setText(text.toLowerCase());
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static void changeEditTextLyoutColor(Context context, TextInputEditText mInputEditText, TextInputLayout mTextInputLayout, AppCompatTextView errorTextView, String errorText) {
        errorTextView.setVisibility(View.VISIBLE);
       // errorTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
        errorTextView.setTextColor(ContextCompat.getColor(context,R.color.red));
        errorTextView.setPadding(5,5,5,5);
        errorTextView.setText(errorText);
        mInputEditText.setTextColor(ContextCompat.getColor(context,R.color.red));
    }
}
