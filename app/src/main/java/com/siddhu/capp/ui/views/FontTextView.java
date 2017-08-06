package com.siddhu.capp.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.siddhu.capp.utils.FontManager;


public class FontTextView extends AppCompatTextView {
    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTypeface(FontManager.readTypeFace(context.getApplicationContext(), attrs));
    }
}
