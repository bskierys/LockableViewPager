package com.github.bskierys.sample.slides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;
import com.github.bskierys.sample.NavigationButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseSidePopFragment extends Fragment {
    private int position;
    private PopInteractionListener listener;

    @BindView(R.id.btn_lock_left) NavigationButton leftButton;
    @BindView(R.id.btn_lock_right) NavigationButton rightButton;

    public ChooseSidePopFragment() {
        // Required empty public constructor
    }

    public static ChooseSidePopFragment newInstance() {
        return new ChooseSidePopFragment();
    }

    // TODO: 2016-10-26 should be in constructor and used one-time only
    public void setPosition(int position, PopInteractionListener listener) {
        this.position = position;
        this.listener = listener;
        SwipeDirection lockedDirection = listener.getLockedDirection();
        if(lockedDirection == SwipeDirection.RIGHT || lockedDirection == SwipeDirection.ALL) {
            rightButton.setBlocked(true);
        }else {
            rightButton.setBlocked(false);
        }

        if(lockedDirection == SwipeDirection.LEFT || lockedDirection == SwipeDirection.ALL) {
            leftButton.setBlocked(true);
        }else {
            leftButton.setBlocked(false);
        }
        // TODO: 2016-10-26 refresh view

    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pop_choose_side, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.btn_close) public void onClose() {
        // TODO: 2016-10-26 check for null
        listener.closePop();
    }

    @OnClick(R.id.btn_lock_right) public void onRight(NavigationButton self) {
        if(listener.getLockedDirection() == SwipeDirection.RIGHT ||
                listener.getLockedDirection() == SwipeDirection.ALL) {
            listener.unlockDirection(SwipeDirection.RIGHT);
            self.setBlocked(false);
        } else {
            listener.lockDirection(SwipeDirection.RIGHT);
            self.setBlocked(true);
        }
    }

    @OnClick(R.id.btn_lock_left) public void onLeft(NavigationButton self) {
        if(listener.getLockedDirection() == SwipeDirection.LEFT ||
                listener.getLockedDirection() == SwipeDirection.ALL) {
            listener.unlockDirection(SwipeDirection.LEFT);
            self.setBlocked(false);
        } else {
            listener.lockDirection(SwipeDirection.LEFT);
            self.setBlocked(true);
        }
    }

    public interface PopInteractionListener {
        void lockDirection(SwipeDirection direction);
        void unlockDirection(SwipeDirection direction);
        SwipeDirection getLockedDirection();
        void closePop();
    }
}
