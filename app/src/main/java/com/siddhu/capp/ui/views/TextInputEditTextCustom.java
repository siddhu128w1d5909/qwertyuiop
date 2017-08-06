package com.siddhu.capp.ui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by balaji_sh on 9/12/2016.
 */

/**
 * Changes in the focus of change and input the contents are to determine whether the clean icon on the right of the display
 */
public class TextInputEditTextCustom extends TextInputEditText {
    private Drawable mRightDrawable;
    private boolean isHasFocus;

    public TextInputEditTextCustom(Context context) {
        super(context);
        init();
    }

    public TextInputEditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextInputEditTextCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = this.getCompoundDrawables();

        //Obtain right Drawable
        //We arranged in a layout file in android:drawableRight
        mRightDrawable = drawables[2];

        //Set the focus change monitoring
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        //Set the EditText text change monitoring
        this.addTextChangedListener(new TextWatcherImpl());
        //Initialization of clean icon is not visible to the right
        setClearDrawableVisible(false);
    }


    /**
     * When the finger is lifted position in the clean icon in the region
     * We see this as the removal operation
     * getWidth():Get the width of the control
     * event.getX():Coordinate when lifting (change the coordinates are relative to the control itself.)
     * getTotalPaddingRight():The clean icon to the right edge of the left edge of the control distance
     * getPaddingRight():The clean icon to the right edge of the right edge of the control distance
     * Therefore:
     * getWidth() - getTotalPaddingRight()Express:
     * The left edge of the control to the clean icon on the left of the area
     * getWidth() - getPaddingRight()Express:
     * The icon on the left to the right edge of the control region of the clean
     * So between the two regions is just the clean icon in the region
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }

    }
    public void onFocusChange_Custom(View v, boolean hasFocus) {
        isHasFocus = hasFocus;
        if (isHasFocus) {
            boolean isVisible = getText().toString().length() >= 1;
            setClearDrawableVisible(isVisible);
        } else {
            setClearDrawableVisible(false);
        }
    }
    //When judging whether the right display icon in the clean input end
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            setClearDrawableVisible(isVisible);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }

    //Show or hide the clean Icon
    public void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        //Use the code sets the control left, top, right, and bottom Icon
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }

    // Display an animated, to prompt the user to enter
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    //The number of duplicate CycleTimes animation
    private Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

}