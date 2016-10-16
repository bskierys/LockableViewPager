/*
* author: Bartlomiej Kierys
* date: 2016-10-16
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample.slides;

import android.os.Bundle;

/**
 * TODO: Generic description. Replace with real one.
 */
public class SlideProvider {
    public static AdviceSlideFragment newAdvice(int slideNumber, int backgroundColor, String advice) {
        AdviceSlideFragment fragment = new AdviceSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor, advice);
        return fragment;
    }

    public static IntroductionSlideFragment newIntroduction(int slideNumber, int backgroundColor) {
        IntroductionSlideFragment fragment = new IntroductionSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor);
        return fragment;
    }

    public static TapToUnlockSlideFragment newTapToUnlock(int slideNumber, int backgroundColor) {
        TapToUnlockSlideFragment fragment = new TapToUnlockSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor);
        return fragment;
    }
}
