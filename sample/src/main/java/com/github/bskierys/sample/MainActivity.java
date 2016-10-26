package com.github.bskierys.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.bskierys.lockableviewpager.PositionLockableViewPager;
import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;
import com.github.bskierys.sample.slides.AdviceSlideFragment;
import com.github.bskierys.sample.slides.IntroductionSlideFragment;
import com.github.bskierys.sample.slides.LockAnyPositionSlideFragment;
import com.github.bskierys.sample.slides.NumberedSlideFragment;
import com.github.bskierys.sample.slides.TapToUnlockSlideFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements NumberedSlideFragment.OnSlideInteractionListener,
        PositionLockableViewPager.LockedDirectionListener {

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter pagerAdapter;

    @BindView(R.id.block_view_pager) PositionLockableViewPager viewPager;
    @BindView(R.id.btn_previous) NavigationButton previousButton;
    @BindView(R.id.btn_next) NavigationButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addLockedDirectionListener(this);
        viewPager.lockPosition(3,SwipeDirection.RIGHT);

        // TODO: 2016-10-26 rx binding
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {
                pagerAdapter.handlePositionShown(position);
            }

            @Override public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.btn_previous) public void onGoLeft() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }

    @OnLongClick(R.id.btn_previous) public boolean onBlockLeft() {
        if (viewPager.isLocked(SwipeDirection.LEFT)) {
            viewPager.unlockCurrentPosition(SwipeDirection.LEFT);
        } else {
            viewPager.lockCurrentPosition(SwipeDirection.LEFT);
        }
        return true;
    }

    @OnClick(R.id.btn_next) public void onGoRight() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    @OnLongClick(R.id.btn_next) public boolean onBlockRight() {
        if (viewPager.isLocked(SwipeDirection.RIGHT)) {
            viewPager.unlockCurrentPosition(SwipeDirection.RIGHT);
        } else {
            viewPager.lockCurrentPosition(SwipeDirection.RIGHT);
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
                previousButton.setBlocked(true);
                nextButton.setBlocked(false);
                break;
            case RIGHT:
                previousButton.setBlocked(false);
                nextButton.setBlocked(true);
                break;
            case ALL:
                previousButton.setBlocked(true);
                nextButton.setBlocked(true);
                break;
            case NONE:
                previousButton.setBlocked(false);
                nextButton.setBlocked(false);
                break;
        }
    }

    // TODO: 2016-10-16 detect when user wants to go to blocked screen
    // TODO: 2016-10-16 alternative -> rxjava
    // TODO: 2016-10-16 block quick scroll in viewpager
    // TODO: 2016-10-16 allow changing threshold on one slide
    // TODO: 2016-10-16 own ic_launcher

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private int currentlyShownPosition = -1;
        List<NumberedSlideFragment> items;

        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            items = new ArrayList<>();
            items.add(AdviceSlideFragment.newInstance(0, Color.RED, "nawiguj suwając palcem"));
            items.add(IntroductionSlideFragment.newInstance(1, Color.BLACK));
            items.add(AdviceSlideFragment.newInstance(2, Color.RED, "przytrzymaj dluzej aby zablokowac"));
            items.add(TapToUnlockSlideFragment.newInstance(3, Color.BLUE));

            int[] blockablePostions = new int[6];
            for (int i = 0; i < 6; i++) {
                blockablePostions[i] = i;
            }
            items.add(LockAnyPositionSlideFragment.newInstance(4, Color.CYAN, blockablePostions));
            items.add(AdviceSlideFragment.newInstance(5, Color.RED, "lista bibliotek"));
        }

        @Override public Fragment getItem(int position) {
            return items.get(position);
        }

        public void handlePositionShown(int position) {
            if(position == currentlyShownPosition) {
                return;
            }

            if (currentlyShownPosition >= 0 && currentlyShownPosition < items.size()) {
                items.get(currentlyShownPosition).hidden();
            }
            if (position >= 0 && position < items.size()) {
                items.get(position).shown();
            }
            this.currentlyShownPosition = position;
        }

        @Override public int getCount() {
            return 6;
        }
    }
}
