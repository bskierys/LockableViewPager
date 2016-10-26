/*
* author: Bartlomiej Kierys
* date: 2016-10-15
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample.slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.bskierys.lockableviewpager.sample.R;
import com.github.bskierys.sample.slides.NumberedSlideFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO: Generic description. Replace with real one.
 */
public class AdviceSlideFragment extends NumberedSlideFragment {
    private static final String ARG_ADVICE = "advice";

    private String advice;
    @BindView(R.id.txt_advice) TextView adviceView;

    public static AdviceSlideFragment newInstance(int slideNumber, int backgroundColor, String advice) {
        AdviceSlideFragment fragment = new AdviceSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor, advice);
        return fragment;
    }

    void setArguments(Bundle args, int slideNumber, int backgroundColor, String advice) {
        args.putString(AdviceSlideFragment.ARG_ADVICE, advice);
        super.setArguments(args, slideNumber, backgroundColor);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            advice = getArguments().getString(ARG_ADVICE);
        }
    }

    @Override protected View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sub_fragment_advice_slide, container, false);
        ButterKnife.bind(this, rootView);

        adviceView.setText(advice);

        return rootView;
    }
}
