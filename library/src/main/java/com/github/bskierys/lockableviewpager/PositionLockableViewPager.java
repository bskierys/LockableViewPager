/*
* author: Bartlomiej Kierys
* date: 2016-10-15
* email: bskierys@gmail.com
*/
package com.github.bskierys.lockableviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Extension for {@link LockableViewPager}. Main difference to it's parent is that it can be locked to given direction
 * and position. F. ex. if you lock viewpager on position 2 for swiping right, you will be able to come back to all
 * previous positions but you will never be able to pass position 2.
 */
public class PositionLockableViewPager extends LockableViewPager {
    private HashMap<Integer, SwipeDirection> lockedPositions = new HashMap<>();
    private int currentPosition;

    public PositionLockableViewPager(Context context) {
        super(context);
        init();
    }

    public PositionLockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        resetCurrentPosition();
        this.addOnPageChangeListener(new PositionChangedListener());
    }

    @Override public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set adapter along with start position
     *
     * @param adapter
     * @param startPosition
     */
    public void setAdapter(PagerAdapter adapter, int startPosition) {
        this.setAdapter(adapter);
        resetCurrentPosition();
        resetLockedPositions();
        setCurrentItem(startPosition);
    }

    void resetCurrentPosition() {
        this.currentPosition = -1;
    }

    void resetLockedPositions() {
        lockedPositions = new HashMap<>();
        propagateLockedDirectionChanged(SwipeDirection.NONE);
    }

    /**
     * Simulates swiping to given position. Should be used for testing purposes
     *
     * @param position Number of position that view should be swiped to
     */
    public void swipeToPosition(int position) {
        setCurrentItem(position, true);
        if (position != currentPosition) {
            setCurrentItemInternal(position);
        }
    }

    private void setCurrentItemInternal(int position) {
        currentPosition = position;
        updateSwipeLock();
        propagateLockedDirectionChanged(getLockedDirection());

        if (shouldLog) {
            Logger.d("Page scrolled to position: %d", position);
        }
    }

    private void updateSwipeLock() {
        int currentPosition = getCurrentItem();
        if (lockedPositions.containsKey(currentPosition)) {
            SwipeDirection lockedDirection = lockedPositions.get(currentPosition);
            setAllowedDirection(SwipeDirection.getOppositeDirection(lockedDirection));
        } else {
            unlockInternal(SwipeDirection.ALL);
        }
    }

    /**
     * Locks current position for specific direction.User will not be able to pass this one with a swipe.
     */
    public void lockCurrentPosition(SwipeDirection direction) {
        lockedPositions.put(getCurrentItem(), direction);
        if (direction != getLockedDirection()) {
            lockInternal(direction);
            propagateLockedDirectionChanged(getLockedDirection());
        }
    }

    /**
     * Unlocks current position for swiping in specific direction
     */
    public void unlockCurrentPosition(SwipeDirection direction) {
        int position = getCurrentItem();

        if (lockedPositions.containsKey(position)) {
            lockedPositions.remove(position);
        }

        unlockInternal(direction);
        if (direction != getLockedDirection()) {
            propagateLockedDirectionChanged(getLockedDirection());
        }
    }

    /**
     * Locks given position for specific direction.User will not be able to pass this one with a swipe.
     */
    public void lockPosition(int position, SwipeDirection direction) {
        if (shouldLog) {
            Logger.d("Locking position: %d for swiping in direction: %s", position, direction.toString());
        }

        if (position == getCurrentItem()) {
            lockCurrentPosition(direction);
        } else {
            lockedPositions.put(position, direction);
        }
    }

    /**
     * Unlocks given position for swiping in specific direction
     */
    public void unlockPosition(int position, SwipeDirection direction) {
        if (shouldLog) {
            Logger.d("Unlocking position: %d for swiping in direction: %s", position, direction.toString());
        }

        if (position == getCurrentItem()) {
            unlockCurrentPosition(direction);
        } else {
            if (lockedPositions.containsKey(position)) {
                lockedPositions.remove(position);
            }
        }
    }

    /**
     * @param position Position to check
     * @param direction Direction to check
     * @return Whether position is locked in that direction
     */
    public boolean isPositionLocked(int position, SwipeDirection direction) {
        return lockedPositions.containsKey(position) && lockedPositions.get(position) == direction;
    }

    /**
     * @param position position to check
     * @return Direction that is locked for given position
     */
    public SwipeDirection getLockedDirectionForPosition(int position) {
        if (lockedPositions.containsKey(position)) {
            return lockedPositions.get(position);
        } else {
            return SwipeDirection.NONE;
        }
    }

    // TODO: 2016-10-26 doc
    // TODO: 2016-10-26 test
    public void setLockedDirectionForPosition(int position, SwipeDirection direction){
        lockedPositions.put(position, direction);
    }

    private class PositionChangedListener extends ViewPager.SimpleOnPageChangeListener {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset == 0f && position != currentPosition) {
                setCurrentItemInternal(position);
            }
        }
    }
}

