package com.siddhu.capp.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.siddhu.capp.R;

/**
 * Created by dhiman_da on 7/27/2016.
 */
public class UnTouchableFrameLayout extends FrameLayout {
    private static final boolean DEFAULT_TOUCHABLE = true;
    private boolean isTouchable = DEFAULT_TOUCHABLE;

    public UnTouchableFrameLayout(Context context) {
        this(context, null);
    }

    public UnTouchableFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public UnTouchableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UnTouchableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnTouchableFrameLayout);
            touchable = typedArray.getBoolean(R.styleable.UnTouchableFrameLayout_touchable_frame_layout, DEFAULT_TOUCHABLE);
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
