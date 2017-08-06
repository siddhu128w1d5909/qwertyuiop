package com.siddhu.capp.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;

import com.siddhu.capp.utils.FontManager;


public class FontCheckedTextView extends AppCompatCheckedTextView {
    public FontCheckedTextView(Context context) {
        this(context, null);
    }

    public FontCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FontCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTypeface(FontManager.readTypeFace(context.getApplicationContext(), attrs));
    }
}
