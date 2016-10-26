package com.github.bskierys.sample.slides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bskierys.lockableviewpager.sample.R;

public class ChooseSidePopFragment extends Fragment {
    private int position;

    private NumberedSlideFragment.OnSlideInteractionListener mListener;

    public ChooseSidePopFragment() {
        // Required empty public constructor
    }

    public static ChooseSidePopFragment newInstance() {
        return new ChooseSidePopFragment();
    }

    public void setPosition(int position) {
        this.position = position;
        // TODO: 2016-10-26 refresh view
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pop_choose_side, container, false);
        return rootView;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NumberedSlideFragment.OnSlideInteractionListener) {
            mListener = (NumberedSlideFragment.OnSlideInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NumberedSlideFragment" +
                                               ".OnSlideInteractionListener");
        }
    }

    @Override public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
