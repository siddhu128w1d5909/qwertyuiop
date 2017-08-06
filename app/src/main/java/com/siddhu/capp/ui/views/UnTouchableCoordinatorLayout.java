package com.siddhu.capp.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.siddhu.capp.R;

/**
 * Created by dhiman_da on 7/27/2016.
 */
public class UnTouchableCoordinatorLayout extends CoordinatorLayout {
    private static final boolean DEFAULT_TOUCHABLE = true;
    private boolean isTouchable = DEFAULT_TOUCHABLE;

    public UnTouchableCoordinatorLayout(Context context) {
        this(context, null);
    }

    public UnTouchableCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public UnTouchableCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnTouchableLinearLayout);
            touchable = typedArray.getBoolean(R.styleable.UnTouchableCoordinatorLayout_touchable_coordinate_layout, DEFAULT_TOUCHABLE);
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

    private void setTouchable(boolean touchable) {
        isTouchable = touchable;
    }
}
