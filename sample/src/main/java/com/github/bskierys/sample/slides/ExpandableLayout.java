/*
* author: Bartlomiej Kierys
* date: 2016-10-26
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample.slides;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.github.bskierys.lockableviewpager.sample.R;

// TODO: 2016-10-26 this would make a perfect simple library
/**
 * Implementation of {@link RelativeLayout} that can be expanded or collapsed and changes it size accordingly. Wrap original layout with it and set layout parameters to
 * wrap_content. Use {@link #show(boolean)} and {@link #hide(boolean)} methods to expand/collapse. Don't use visibility GONE on layout - hide it with parameter false in
 * your's activity onCreate instead. Set animation duration through 'duration' xml parameter or through {@link #setDuration(Integer)} method.
 */
public class ExpandableLayout extends RelativeLayout {
    private Boolean isAnimationRunning = false;
    private Boolean isOpened = false;
    private Integer duration;
    private Animation animation;
    private Animation.AnimationListener expandAnimationListener;
    private Animation.AnimationListener collapseAnimationListener;

    public ExpandableLayout(Context context) {
        super(context);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);

        if (isInEditMode()) {
            return;
        }

        duration = typedArray.getInt(R.styleable.ExpandableLayout_duration,
                                     getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));

        typedArray.recycle();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    private void expand(final View v) {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);

        animation = new Animation() {
            @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    isOpened = true;
                }
                v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override public boolean willChangeBounds() {
                return true;
            }
        };

        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        animation.setAnimationListener(expandAnimationListener);
        animation.setInterpolator(interpolator);
        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        animation = new Animation() {
            @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override public boolean willChangeBounds() {
                return true;
            }
        };

        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        animation.setAnimationListener(collapseAnimationListener);
        animation.setInterpolator(interpolator);
        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    public Boolean isOpened() {
        return isOpened;
    }

    /**
     * Expands layout with or without animation.
     *
     * @param animate whether to use animation.
     */
    public void show(boolean animate) {
        if (!isAnimationRunning) {
            if (animate) {
                expand(this);
                isAnimationRunning = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        isAnimationRunning = false;
                    }
                }, duration);
            } else {
                setVisibility(VISIBLE);
                isOpened = true;
            }
        }
    }

    /**
     * Collapses layout with or without animation.
     *
     * @param animate whether to use animation.
     */
    public void hide(boolean animate) {
        if (!isAnimationRunning) {
            if (animate) {
                collapse(this);
                isAnimationRunning = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        isAnimationRunning = false;
                    }
                }, duration);
            } else {
                setVisibility(GONE);
                isOpened = false;
            }
        }
    }

    public void setExpandAnimationListener(Animation.AnimationListener expandAnimationListener) {
        this.expandAnimationListener = expandAnimationListener;
    }

    public void setCollapseAnimationListener(Animation.AnimationListener collapseAnimationListener) {
        this.collapseAnimationListener = collapseAnimationListener;
    }
}
