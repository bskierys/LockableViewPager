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

import com.github.bskierys.lockableviewpager.sample.R;

import butterknife.ButterKnife;

/**
 * TODO: Generic description. Replace with real one.
 */
public class IntroductionSlideFragment extends NumberedSlideFragment {
    public static IntroductionSlideFragment newInstance(int slideNumber, int backgroundColor) {
        IntroductionSlideFragment fragment = new IntroductionSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor);
        return fragment;
    }

    @Override protected View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sub_fragment_introduction_slide, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }
}
