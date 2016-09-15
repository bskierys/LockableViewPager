package pl.ipebk.lockableviewpager.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.ipebk.lockableviewpager.LockableViewPager;
import pl.ipebk.lockableviewpager.SwipeDirection;

public class MainActivity extends AppCompatActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final String[] PAGE_NAMES = { "menzurka", "paradygmat", "karmelitka", "terpentyna", "zalewajka" };

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;


    @BindView(R.id.block_view_pager) LockableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setThreshold(-300);
    }

    @OnClick(R.id.btn_block_left) public void onBlockLeft(Button self) {
        if(viewPager.isLocked(SwipeDirection.LEFT)){
            viewPager.unlock(SwipeDirection.LEFT);
            self.setText("LOCK LEFT");
        } else {
            viewPager.lock(SwipeDirection.LEFT);
            self.setText("UNLOCK LEFT");
        }
    }

    @OnClick(R.id.btn_block_right) public void onBlockRight(Button self) {
        if(viewPager.isLocked(SwipeDirection.RIGHT)){
            viewPager.unlock(SwipeDirection.RIGHT);
            self.setText("LOCK RIGHT");
        } else {
            viewPager.lock(SwipeDirection.RIGHT);
            self.setText("UNLOCK RIGHT");
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(PAGE_NAMES[position]);
        }

        @Override
        public int getCount() {
            return PAGE_NAMES.length;
        }
    }
}
