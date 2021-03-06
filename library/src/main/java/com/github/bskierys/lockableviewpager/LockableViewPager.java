/*
* author: Bartlomiej Kierys
* date: 2016-09-15
* email: bskierys@gmail.com
*/
package com.github.bskierys.lockableviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager that can be locked in both direction to prevent user from accessing another page. By default pager allows
 * user to see little of next page by swiping through screen just before it gets him back to current page. This
 * behaviour can be changed through {@link #setThreshold(int)} method.
 */
public class LockableViewPager extends ViewPager {
    private static final int DEFAULT_THRESHOLD = 300;

    private int currentPage;
    private float initialXValue;
    private int threshold = DEFAULT_THRESHOLD;
    private SwipeDirection direction = SwipeDirection.ALL;
    private final OnPageChangeListener lockPageChangeListener = new OnPageChangeListener() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset == 0f) {
                currentPage = position;
            }
            if (direction == SwipeDirection.LEFT || direction == SwipeDirection.NONE) {
                if (currentPage == position && positionOffset > 0) {
                    setCurrentItem(currentPage, true);
                }
            }
            if (direction == SwipeDirection.RIGHT || direction == SwipeDirection.NONE) {
                if (currentPage - 1 == position && positionOffset > 0) {
                    setCurrentItem(currentPage, true);
                }
            }
        }

        @Override public void onPageSelected(int position) {}

        @Override public void onPageScrollStateChanged(int state) {}
    };

    public LockableViewPager(Context context) {
        super(context);
        this.addOnPageChangeListener(lockPageChangeListener);
    }

    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addOnPageChangeListener(lockPageChangeListener);
    }

    /**
     * Method that allows you to control how much of next screen user can peek before viewpager swaps him back to
     * current page.
     *
     * @param threshold the higher the value, the more of next page can user peek. Negative values will block user from
     * returning to previous page once he started to swipe
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override public boolean onTouchEvent(MotionEvent ev) {
        return isSwipeAllowed(ev) && super.onTouchEvent(ev);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwipeAllowed(ev) && super.onInterceptTouchEvent(ev);
    }

    private boolean isSwipeAllowed(MotionEvent ev) {
        if (direction == SwipeDirection.ALL) {
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = ev.getX();
            return true;
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = ev.getX() - initialXValue;
                if (direction == SwipeDirection.RIGHT && diffX > threshold) {
                    return false;
                } else if (direction == SwipeDirection.LEFT && diffX < -threshold) {
                    return false;
                } else if (direction == SwipeDirection.NONE && (diffX > threshold || diffX < -threshold)) {
                    return false;
                }
            } catch (Exception exception) {
                Logger.e(exception, "Exception occurred while determining allowed swipe");
            }
        }
        return true;
    }

    /**
     * Use this method to lock swiping through viewpager. Does nothing if direction is already locked.
     *
     * @param direction Direction that should be blocked
     */
    public void lock(SwipeDirection direction) {
        if (direction == SwipeDirection.LEFT || direction == SwipeDirection.RIGHT) {
            if (this.direction == SwipeDirection.NONE || this.direction == getOppositeDirection(direction)) {
                Logger.d("This direction is already blocked");
            } else if (this.direction == direction) {
                this.direction = SwipeDirection.NONE;
            } else if (this.direction == SwipeDirection.ALL) {
                this.direction = getOppositeDirection(direction);
            }
        } else if (direction == SwipeDirection.ALL) {
            this.direction = SwipeDirection.NONE;
        }
    }

    /**
     * Use this method to unlock swiping through viewpager. Does nothing if direction is already unlocked.
     *
     * @param direction Direction that should be unlocked
     */
    public void unlock(SwipeDirection direction) {
        if (direction == SwipeDirection.LEFT || direction == SwipeDirection.RIGHT) {
            if (this.direction == SwipeDirection.ALL || this.direction == direction) {
                Logger.d("This direction is not locked. So why unlock?");
            } else if (this.direction == getOppositeDirection(direction)) {
                this.direction = SwipeDirection.ALL;
            } else if (this.direction == SwipeDirection.NONE) {
                this.direction = direction;
            }
        } else if (direction == SwipeDirection.ALL) {
            this.direction = SwipeDirection.ALL;
        }
    }

    /**
     * @param direction Direction to check
     * @return Tells if direction is currently unlocked
     */
    public boolean isLocked(SwipeDirection direction) {
        return this.direction == SwipeDirection.NONE || this.direction == getOppositeDirection(direction);
    }

    private SwipeDirection getOppositeDirection(SwipeDirection direction) {
        switch (direction) {
            case LEFT:
                return SwipeDirection.RIGHT;
            case RIGHT:
                return SwipeDirection.LEFT;
            case ALL:
                return SwipeDirection.NONE;
            case NONE:
                return SwipeDirection.ALL;
            default:
                return SwipeDirection.ALL;
        }
    }

    /**
     * Sets allowed direction. F. ex. if you set direction right viewPager would be only available to be swiped right
     *
     * @param direction Direction that viewpager is allowed to be swiped
     */
    public void setAllowedDirection(SwipeDirection direction) {
        this.direction = direction;
    }
}