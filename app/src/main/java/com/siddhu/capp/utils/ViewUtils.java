package com.siddhu.capp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.siddhu.capp.ui.views.FontTextView;

public class ViewUtils {
    private static final int M_STR_MAX_CHAR_COUNT = 18;

    private ViewUtils(){

    }

    public static float dpToPixel(@NonNull Context context, float dp) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }


    public static String formatPLPTitle(String stringToFormat) {
        String stringToFormatTemp;
        if (stringToFormat.length() > M_STR_MAX_CHAR_COUNT) {
            stringToFormatTemp = stringToFormat.substring(0, M_STR_MAX_CHAR_COUNT) + "..." + " >";
        }else{
            stringToFormatTemp = stringToFormat + " " + " >";
        }
        return stringToFormatTemp;
    }

    public static String formatPLPTitleWithoutArrow(String stringToFormat) {
        if (stringToFormat.length() > M_STR_MAX_CHAR_COUNT) {
            return stringToFormat.substring(0, M_STR_MAX_CHAR_COUNT) + "...";
        }
        return stringToFormat;
    }

    public static void setColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if(i>=0)
            str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    //-------------maintaining case sensitivity
    public static void setColor(TextView view, String fulltext,String actualFullText, String subtext, int color) {
        view.setText(actualFullText, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if(i>=0)
            str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * removel underlines from FontTextView web link
     * @param fontTextView
     */
    public static void removeTextViewUnderLine(FontTextView fontTextView){
        Spannable s = new SpannableString(fontTextView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        fontTextView.setText(s);
    }

    public static int dp2px(int dp, Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int displaymetr = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return displaymetr;
    }
    public static float px2dp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    //----------custom class to remove web link underline
    @SuppressLint("ParcelCreator")
    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

}
