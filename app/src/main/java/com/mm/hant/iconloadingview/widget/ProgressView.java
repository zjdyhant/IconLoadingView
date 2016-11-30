package com.mm.hant.iconloadingview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.mm.hant.iconloadingview.R;

/**
 * Created by hantao on 16/11/20.
 * 与LoadingView  结合使用
 */
class ProgressView extends View {

    private RectF mRectF;
    private Paint mPaintBackground;
    private Paint mPaintForeground;
    private float mRotate;
    private float mDensity;
    private int mStrokeWidth;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = getResources().getDisplayMetrics().density;
        mStrokeWidth = (int) (4 * mDensity);
        mPaintBackground = new Paint();
        mPaintBackground.setColor(getResources().getColor(R.color.color_divider));
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setStrokeWidth(mStrokeWidth);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStrokeCap(Paint.Cap.ROUND);
        mPaintForeground = new Paint();
        mPaintForeground.setColor(getResources().getColor(R.color.color_blue));
        mPaintForeground.setStyle(Paint.Style.STROKE);
        mPaintForeground.setStrokeWidth(mStrokeWidth);
        mPaintForeground.setAntiAlias(true);
        mPaintForeground.setStrokeCap(Paint.Cap.ROUND);
        ViewTreeObserver observer = getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getMeasuredHeight() != 0) {
                        mRectF = new RectF(0, 0, getWidth(), getHeight());
                        mRectF.inset(mStrokeWidth / 2, mStrokeWidth / 2);
                        ViewTreeObserver observer = getViewTreeObserver();
                        if (observer != null) {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                observer.removeGlobalOnLayoutListener(this);
                            } else {
                                observer.removeOnGlobalLayoutListener(this);
                            }
                        }
                    }
                }
            });
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRotate < 180) {
            canvas.drawArc(mRectF, -90, mRotate, false, mPaintBackground);
        } else {
            if (mRotate < 270) {
                canvas.drawArc(mRectF, mRotate / 2 - 180, mRotate / 2 + 90, false, mPaintBackground);
                canvas.drawArc(mRectF, mRotate / 2 - 180, 4 * mRotate / 3 - 240, false, mPaintForeground);
            } else {
                canvas.drawArc(mRectF, mRotate * 7 / 2 - 990, 900 - mRotate * 5 / 2, false, mPaintBackground);
                canvas.drawArc(mRectF, mRotate * 7 / 2 - 990, 480 - mRotate * 4 / 3, false, mPaintForeground);
            }
        }
    }

    @Override
    public void setRotation(float rotation) {
        mRotate = rotation;
        invalidate();
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mPaintBackground.setStrokeWidth(mStrokeWidth);
        mPaintForeground.setStrokeWidth(mStrokeWidth);
    }

    public void setForegroundProgressColor(int color){
        mPaintForeground.setColor(color);
    }

    public void setBackgroundProgressColor(int color){
        mPaintBackground.setColor(color);
    }
}
