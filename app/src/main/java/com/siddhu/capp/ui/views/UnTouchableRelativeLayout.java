package com.siddhu.capp.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.siddhu.capp.R;

public class UnTouchableRelativeLayout extends RelativeLayout {
    private static final boolean DEFAULT_TOUCHABLE = true;
    private boolean isTouchable = DEFAULT_TOUCHABLE;

    public UnTouchableRelativeLayout(Context context) {
        this(context, null);
    }

    public UnTouchableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public UnTouchableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UnTouchableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isTouchable;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        boolean touchable = DEFAULT_TOUCHABLE;

        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnTouchableRelativeLayout);
            touchable = typedArray.getBoolean(R.styleable.UnTouchableRelativeLayout_touchable_relative_layout, DEFAULT_TOUCHABLE);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        setTouchable(touchable);
    }

    public boolean isTouchable() {
        return isTouchable;
    }

    public void setTouchable(boolean touchable) {
        isTouchable = touchable;
    }
}
