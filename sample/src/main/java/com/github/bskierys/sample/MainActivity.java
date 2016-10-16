package com.github.bskierys.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.bskierys.lockableviewpager.PositionLockableViewPager;
import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;
import com.github.bskierys.sample.slides.NumberedSlideFragment;
import com.github.bskierys.sample.slides.SlideProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements NumberedSlideFragment.OnSlideInteractionListener, PositionLockableViewPager.LockedDirectionListener {

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @BindView(R.id.block_view_pager) PositionLockableViewPager viewPager;
    @BindView(R.id.btn_previous) View previousButton;
    @BindView(R.id.btn_next) View nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addLockedDirectionListener(this);
    }

    @OnClick(R.id.btn_previous) public void onGoLeft() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
    }

    @OnLongClick(R.id.btn_previous) public boolean onBlockLeft() {
        if (viewPager.isLocked(SwipeDirection.LEFT)) {
            viewPager.unlock(SwipeDirection.LEFT);
        } else {
            viewPager.lock(SwipeDirection.LEFT);
        }
        return true;
    }

    @OnClick(R.id.btn_next) public void onGoRight() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
    }

    @OnLongClick(R.id.btn_next) public boolean onBlockRight() {
        if (viewPager.isLocked(SwipeDirection.RIGHT)) {
            viewPager.unlock(SwipeDirection.RIGHT);
        } else {
            viewPager.lock(SwipeDirection.RIGHT);
        }
        return true;
    }

    @Override public void lockPosition(int position, SwipeDirection direction) {
        viewPager.lockPosition(position, direction);
    }

    @Override public void unlockPosition(int position, SwipeDirection direction) {
        viewPager.unlockPosition(position, direction);
    }

    @Override public SwipeDirection getLockedDirection(int position) {
        return viewPager.getLockedDirectionForPosition(position);
    }

    // TODO: 2016-10-16 include rxjava somehow
    @Override public void onLockedDirectionChanged(SwipeDirection lockedDirection) {
        //Timber.d("Locked direction: %s", direction.toString());
        switch (lockedDirection) {
            case LEFT:
                previousButton.setEnabled(false);
                nextButton.setEnabled(true);
                break;
            case RIGHT:
                previousButton.setEnabled(true);
                nextButton.setEnabled(false);
                break;
            case ALL:
                previousButton.setEnabled(false);
                nextButton.setEnabled(false);
                break;
            case NONE:
                previousButton.setEnabled(true);
                nextButton.setEnabled(true);
                break;
        }
    }

    // TODO: 2016-10-16 detect when user wants to go to blocked screen
    // TODO: 2016-10-16 alternative -> rxjava
    // TODO: 2016-10-16 block quick scroll in viewpager
    // TODO: 2016-10-16 allow changing threshold on one slide
    // TODO: 2016-10-16 own ic_launcher

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return SlideProvider.newAdvice(position, Color.RED, "nawiguj suwając palcem");
                }
                case 1: {
                    return SlideProvider.newIntroduction(position, Color.BLACK);
                }
                case 2: {
                    return SlideProvider.newAdvice(position, Color.RED, "przytrzymaj dluzej aby zablokowac");
                }
                case 3: {
                    return SlideProvider.newTapToUnlock(position, Color.BLUE);
                }
                case 4: {
                    // TODO: 2016-10-16 lockAnyPositionSlide
                    return SlideProvider.newAdvice(position, Color.CYAN, "tutaj mozemy blokowac dowolny fragment");
                }
                case 5: {
                    // TODO: 2016-10-16 creditsSlide
                    return SlideProvider.newAdvice(position, Color.RED, "lista bibliotek");
                }
            }
            return null;
        }

        @Override public int getCount() {
            return 6;
        }
    }
}
