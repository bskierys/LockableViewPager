/*
* author: Bartlomiej Kierys
* date: 2016-09-15
* email: bskierys@gmail.com
*/
package com.github.bskierys.lockableviewpager;

/**
 * TODO: Generic description. Replace with real one.
 */
public enum SwipeDirection {
    ALL,
    LEFT,
    RIGHT,
    NONE;

    public static SwipeDirection getOppositeDirection(SwipeDirection direction) {
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
}
