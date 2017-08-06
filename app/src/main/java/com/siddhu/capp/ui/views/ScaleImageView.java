package com.siddhu.capp.ui.views;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.siddhu.capp.utils.Logger;


public class ScaleImageView extends AppCompatImageView implements View.OnTouchListener{
    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private static final int CLICK = 3;

    private int mode = NONE;

    // Remember some things for zooming
    private PointF last = new PointF();
    private PointF start = new PointF();

    private Matrix matrix;

    private float mMinScale = 1f;
    private float mMaxScale = 3f;
    private float[] mVales;

    private int mViewWidth;
    private int mViewHeight;

    private float mSaveScale = 1f;

    private float mOriginalWidth;
    private float mOriginalHeight;

    private int oldMeasuredHeight;

    private ScaleGestureDetector mScaleDetector;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);

        // Rescales image on rotation
        if (oldMeasuredHeight == mViewWidth &&
                oldMeasuredHeight == mViewHeight ||
                mViewWidth == 0 ||
                mViewHeight == 0)

            return;

        oldMeasuredHeight = mViewHeight;

        if (Float.compare(mSaveScale, 1f) == 0) {
            //Fit to screen.
            float scale;
            Drawable drawable = getDrawable();
            if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0)
                return;

            int bmWidth = drawable.getIntrinsicWidth();
            int bmHeight = drawable.getIntrinsicHeight();

            Logger.d("bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

            float scaleX = (float) mViewWidth / (float) bmWidth;
            float scaleY = (float) mViewHeight / (float) bmHeight;

            scale = Math.min(scaleX, scaleY);
            matrix.setScale(scale, scale);

            // Center the image
            float redundantYSpace = (float) mViewHeight - (scale * (float) bmHeight);
            float redundantXSpace = (float) mViewWidth - (scale * (float) bmWidth);

            redundantYSpace /= (float) 2;
            redundantXSpace /= (float) 2;

            matrix.postTranslate(redundantXSpace, redundantYSpace);

            mOriginalWidth = mViewWidth - 2 * redundantXSpace;
            mOriginalHeight = mViewHeight - 2 * redundantYSpace;

            setImageMatrix(matrix);
        }

        fixTranslation();

    }

    private void init(Context context) {
        super.setClickable(true);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        matrix = new Matrix();
        mVales = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
    }

    public void setMaxZoom(float maxScale) {
        mMaxScale = maxScale;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

            mScaleDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    last.set(curr);
                    start.set(last);
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        float deltaX = curr.x - last.x;
                        float deltaY = curr.y - last.y;
                        float fixTransX = getFixDragTranslation(deltaX, mViewWidth, mOriginalWidth * mSaveScale);
                        float fixTransY = getFixDragTranslation(deltaY, mViewHeight, mOriginalHeight * mSaveScale);
                        matrix.postTranslate(fixTransX, fixTransY);
                        fixTranslation();
                        last.set(curr.x, curr.y);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    mode = NONE;
                    int xDiff = (int) Math.abs(curr.x - start.x);
                    int yDiff = (int) Math.abs(curr.y - start.y);
                    if (xDiff < CLICK && yDiff < CLICK) {
                        performClick();
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    break;
                default:
                    break;

            }

            setImageMatrix(matrix);
            invalidate();
            // true indicate event was handled
            return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = detector.getScaleFactor();
            float origScale = mSaveScale;
            mSaveScale *= mScaleFactor;

            if (mSaveScale > mMaxScale) {
                mSaveScale = mMaxScale;
                mScaleFactor = mMaxScale / origScale;
            } else if (mSaveScale < mMinScale) {
                mSaveScale = mMinScale;
                mScaleFactor = mMinScale / origScale;
            }

            if (mOriginalWidth * mSaveScale <= mViewWidth || mOriginalHeight * mSaveScale <= mViewHeight) {
                matrix.postScale(mScaleFactor, mScaleFactor, mViewWidth / 2, mViewHeight / 2);
            } else {
                matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
            }

            fixTranslation();

            return true;
        }
    }

    private void fixTranslation() {
        matrix.getValues(mVales);

        float transX = mVales[Matrix.MTRANS_X];
        float transY = mVales[Matrix.MTRANS_Y];

        float fixTransX = getFixTranslation(transX, mViewWidth, mOriginalWidth * mSaveScale);
        float fixTransY = getFixTranslation(transY, mViewHeight, mOriginalHeight * mSaveScale);

        if (Float.compare(fixTransX, 0f)!= 0 || Float.compare(fixTransY, 0f)!= 0 )
            matrix.postTranslate(fixTransX, fixTransY);
    }


    private float getFixTranslation(float trans, float viewSize, float contentSize) {
        float minTrans, maxTrans;

        if (contentSize <= viewSize) {
            minTrans = 0;
            maxTrans = viewSize - contentSize;
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if (trans < minTrans) {
            return -trans + minTrans;
        }

        if (trans > maxTrans) {
            return -trans + maxTrans;
        }

        return 0;

    }

    private float getFixDragTranslation(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0;
        }

        return delta;
    }
}
