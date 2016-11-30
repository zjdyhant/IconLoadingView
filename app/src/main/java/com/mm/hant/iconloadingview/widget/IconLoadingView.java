package com.mm.hant.iconloadingview.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mm.hant.iconloadingview.R;

/**
 * Created by hantao on 16/11/14.
 * <p>
 * 陆鲸加动画
 */

public class IconLoadingView extends RelativeLayout {

    private ImageView mImageViewCenter;
    private ProgressView mProgressView;
    private RelativeLayout mRelativeLayoutContainer;
    private ViewGroup mViewParent;

    private ObjectAnimator mObjectAnimator;
    private float mDensity;


    public IconLoadingView(Context context) {
        this(context, null);
    }

    public IconLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initData();
        initObserver();
    }

    private void initData() {
        mDensity = getResources().getDisplayMetrics().density;
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_view, this, true);
        mRelativeLayoutContainer = (RelativeLayout) view.findViewById(R.id.rly_container);
        mImageViewCenter = (ImageView) view.findViewById(R.id.img_center);
        mProgressView = (ProgressView) view.findViewById(R.id.lj_progress_view);
        mObjectAnimator = ObjectAnimator.ofFloat(mProgressView, "rotation", 0, 360);
        mObjectAnimator.setDuration(2000);
        mObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconLoadingView);
        Drawable src = typedArray.getDrawable(R.styleable.IconLoadingView_Icon);
        if(src != null){
            mImageViewCenter.setImageDrawable(src);
        }
        int foregroundColor = typedArray.getColor(R.styleable.IconLoadingView_ForegroundProgressColor,
                Color.parseColor("#0093ff"));
        mProgressView.setForegroundProgressColor(foregroundColor);
        int backgroundColor = typedArray.getColor(R.styleable.IconLoadingView_BackgroundProgressColor,
                Color.parseColor("#dfdfdf"));
        mProgressView.setBackgroundProgressColor(backgroundColor);
        mObjectAnimator.start();

    }

    private void initObserver() {
        ViewTreeObserver observer = mRelativeLayoutContainer.getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getMeasuredHeight() != 0) {
                        mProgressView.setStrokeWidth(mRelativeLayoutContainer.getMeasuredWidth() / 25);
                        LayoutParams layoutParams = (LayoutParams) mImageViewCenter.getLayoutParams();
                        layoutParams.width = mRelativeLayoutContainer.getMeasuredWidth() * 3 / 5;
                        layoutParams.height = mRelativeLayoutContainer.getMeasuredHeight() * 3 / 5;
                        mImageViewCenter.setLayoutParams(layoutParams);
                        mImageViewCenter.setVisibility(VISIBLE);
                        mProgressView.setVisibility(VISIBLE);
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
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            mObjectAnimator.start();

        } else {
            mObjectAnimator.end();
        }
    }

    public IconLoadingView setIcon(int resId) {
        mImageViewCenter.setImageResource(resId);
        return this;
    }

    public IconLoadingView setForegroundProgressColor(int color) {
        mProgressView.setForegroundProgressColor(color);
        return this;
    }

    public IconLoadingView setBackgroundProgressColor(int color) {
        mProgressView.setBackgroundProgressColor(color);
        return this;
    }

    /**
     * 将LJLoadingView显示到屏幕中间。控件宽高默认  50dp*50dp
     */
    public void attachToWindowCenter(View view) {
        this.attachToView(view, 50, 50, 0, 0, 0, 0);
    }

    /**
     * 将LJLoadingView显示到屏幕中间。
     *
     * @param width  控件宽度  dp
     * @param height 控件高度  dp
     */
    public void attachToWindowCenter(View view, int width, int height) {
        this.attachToView(view, width, height, 0, 0, 0, 0);
    }

    /**
     * 将LJLoadingView在屏幕上移除
     */
    public void detachFromWindow() {
        detachFromView();
    }

    /**
     * 将LJLoadingView 显示到控件上
     *
     * @param view 要显示LJLoadingView的View
     */
    public void attachToView(View view) {
        this.attachToView(view, 0, 0);
    }

    public void attachToView(View view, int width, int height) {
        this.attachToView(view, width, height, 0, 0, 0, 0);
    }

    public void attachToView(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.attachToView(view, 0, 0, marginLeft, marginTop, marginRight, marginBottom);
    }

    /**
     * 将LJLoadingView 显示到控件上  根据margin自动控制大小与位置，会显示到控件的中间.
     * ps:只能被attach一次
     *
     * @param view         要显示LJLoadingView的View
     * @param marginLeft   marginLeft
     * @param marginTop    marginTop
     * @param marginRight  marginRight
     * @param marginBottom marginBottom
     */
    public void attachToView(View view, int width, int height, int marginLeft, int marginTop, int marginRight, int marginBottom) {

        if (mViewParent == null) {
            mViewParent = (ViewGroup) view.getParent();
        }
        if (mViewParent.indexOfChild(this) >= 0) {
            if (!mRelativeLayoutContainer.isShown()) {
                mRelativeLayoutContainer.setVisibility(VISIBLE);
            }
            return;
        }
        int viewWidth;
        int viewHeight;
        if (width == 0 || height == 0) {
            viewWidth = view.getWidth();
            viewHeight = view.getHeight();
        } else {
            viewWidth = (int) (width * mDensity + 0.5);
            viewHeight = (int) (height * mDensity + 0.5);
        }
        int progressBarWidth = (int) (viewWidth - (marginLeft + marginRight) * mDensity);
        int progressBarHeight = (int) (viewHeight - (marginTop + marginBottom) * mDensity);
        if (mViewParent instanceof RelativeLayout) {
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mViewParent.addView(this);
            LayoutParams layoutParams;
            if (progressBarWidth < progressBarHeight) {
                layoutParams = new LayoutParams(progressBarWidth, progressBarWidth);
                layoutParams.setMargins(view.getLeft() + (marginLeft + marginRight) / 2, view.getTop() + (viewHeight / 2 - progressBarWidth / 2), 0, 0);
            } else {
                layoutParams = new LayoutParams(progressBarHeight, progressBarHeight);
                layoutParams.setMargins(view.getLeft() + viewWidth / 2 - progressBarHeight / 2, view.getTop() + (marginTop + marginBottom) / 2, 0, 0);
            }
            mRelativeLayoutContainer.setLayoutParams(layoutParams);
            mObjectAnimator.start();
        } else {
            int index = mViewParent.indexOfChild(view);
            setLayoutParams(view.getLayoutParams());
            mViewParent.removeView(view);
            mViewParent.addView(this, index);
            view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(view, 0);
            LayoutParams layoutParams;
            layoutParams = new LayoutParams(progressBarWidth < progressBarHeight ? progressBarWidth : progressBarHeight,
                    progressBarWidth < progressBarHeight ? progressBarWidth : progressBarHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRelativeLayoutContainer.setLayoutParams(layoutParams);
            mObjectAnimator.start();
        }
    }

    /**
     * 将LJLoadingView 从view上移除
     */
    public void detachFromView() {
        mRelativeLayoutContainer.setVisibility(GONE);
    }
}
