package com.siddhu.capp.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.siddhu.capp.utils.FontManager;


public class FontButton extends AppCompatButton {
    public FontButton(Context context) {
        this(context, null);
    }

    public FontButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTypeface(FontManager.readTypeFace(context.getApplicationContext(), attrs));
    }
}
