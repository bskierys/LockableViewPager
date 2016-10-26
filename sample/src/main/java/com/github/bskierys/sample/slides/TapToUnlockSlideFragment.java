/*
* author: Bartlomiej Kierys
* date: 2016-10-16
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample.slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TODO: Generic description. Replace with real one.
 */
public class TapToUnlockSlideFragment extends NumberedSlideFragment {
    @BindView(R.id.btn_change_lock) TextView changeLockButton;

    public static TapToUnlockSlideFragment newInstance(int slideNumber, int backgroundColor) {
        TapToUnlockSlideFragment fragment = new TapToUnlockSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor);
        return fragment;
    }

    @Override protected View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sub_fragment_tap_unlock_slide, container, false);
        ButterKnife.bind(this, rootView);

        updateButtonState();

        return rootView;
    }

    private void updateButtonState(){
        // TODO: 2016-10-16 name not updated when button click
        if(listener.getLockedDirection(slideNumber) == SwipeDirection.RIGHT) {
            changeLockButton.setText("odblokuj");
        } else {
            changeLockButton.setText("zablokuj znowu");
        }
    }

    @OnClick(R.id.btn_change_lock) public void onChangeLockState() {
        if(listener.getLockedDirection(slideNumber) == SwipeDirection.RIGHT) {
            listener.unlockPosition(slideNumber, SwipeDirection.ALL);
        } else {
            listener.lockPosition(slideNumber, SwipeDirection.RIGHT);
        }
        updateButtonState();
    }
}
