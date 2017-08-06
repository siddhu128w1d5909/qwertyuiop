package com.siddhu.capp.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.siddhu.capp.utils.FontManager;


/**
 * Created by dhiman_da on 7/27/2016.
 */
public class FontCheckBox extends AppCompatCheckBox {
    public FontCheckBox(Context context) {
        this(context, null);
    }

    public FontCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FontCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTypeface(FontManager.readTypeFace(context.getApplicationContext(), attrs));
    }
}
