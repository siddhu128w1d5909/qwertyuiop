package com.siddhu.capp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;

import com.siddhu.capp.R;


public class FontManager {

    private static final String TAG = FontManager.class.getSimpleName();

    private FontManager() {
        sDemoFontFontManager = this;
    }
    public static FontManager getInstance() {
        return sDemoFontFontManager;
    }
    private static final String FONT_FOLDER_IN_ASSETS = "fonts/";
    private static FontManager sDemoFontFontManager = new FontManager();
    private ArrayMap<String, Typeface> mTypeFaceArrayMap = new ArrayMap<>();

    public Typeface getTypeface(@NonNull Context context, @NonNull String fontFileName) {
        Typeface typeface = mTypeFaceArrayMap.get(fontFileName);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), FONT_FOLDER_IN_ASSETS + fontFileName);
                mTypeFaceArrayMap.put(fontFileName, typeface);
            } catch (RuntimeException rte) {
                Logger.log(TAG, rte);
            }
        }

        return typeface;
    }

    public static Typeface readTypeFace(@NonNull Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FontView);

        String fontFile = null;

        final int indexCount = styledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; ++i) {
            int attr = styledAttributes.getIndex(i);
            if (R.styleable.FontView_fontFile == attr) {
                fontFile = styledAttributes.getString(attr);
            }
        }
        styledAttributes.recycle();
        if (fontFile != null) {
            return FontManager.getInstance().getTypeface(context, fontFile);
        }
        return Typeface.DEFAULT;
    }
}

