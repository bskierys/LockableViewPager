/*
* author: Bartlomiej Kierys
* date: 2016-10-20
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * TODO: Generic description. Replace with real one.
 */
public class NavigationButton extends Button {
    private boolean isBlocked;

    public NavigationButton(Context context) {
        super(context);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        updateViewState();
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
        updateViewState();
    }

    private void updateViewState() {
        ColorDrawable drawable;
        if(isBlocked) {
            drawable = new ColorDrawable(Color.WHITE);
        } else {
            drawable = new ColorDrawable(Color.BLACK);
        }

        this.setBackground(drawable);
    }
}
