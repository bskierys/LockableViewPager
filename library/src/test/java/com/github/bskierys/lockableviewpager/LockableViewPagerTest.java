package com.github.bskierys.lockableviewpager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class LockableViewPagerTest {
    private LockableViewPager viewPager;

    @Before public void setUp() throws Exception {
        viewPager = new LockableViewPager(RuntimeEnvironment.application);
    }

    @Test public void testLockRight() throws Exception {
        viewPager.lock(SwipeDirection.RIGHT);

        assertTrue(viewPager.isLocked(SwipeDirection.RIGHT));
    }

    @Test public void testLockLeft() throws Exception {
        viewPager.lock(SwipeDirection.LEFT);

        assertTrue(viewPager.isLocked(SwipeDirection.LEFT));
    }

    @Test public void testLockBothSides() throws Exception {
        viewPager.lock(SwipeDirection.ALL);

        assertTrue(viewPager.isLocked(SwipeDirection.LEFT));
        assertTrue(viewPager.isLocked(SwipeDirection.RIGHT));
    }

    @Test public void testUnlockLeft() throws Exception {
        viewPager.lock(SwipeDirection.ALL);
        viewPager.unlock(SwipeDirection.LEFT);

        assertFalse(viewPager.isLocked(SwipeDirection.LEFT));
    }

    @Test public void testUnlockRight() throws Exception {
        viewPager.lock(SwipeDirection.ALL);
        viewPager.unlock(SwipeDirection.RIGHT);

        assertFalse(viewPager.isLocked(SwipeDirection.RIGHT));
    }

    @Test public void testUnlockAll() throws Exception {
        viewPager.lock(SwipeDirection.ALL);
        viewPager.unlock(SwipeDirection.ALL);

        assertFalse(viewPager.isLocked(SwipeDirection.RIGHT));
        assertFalse(viewPager.isLocked(SwipeDirection.LEFT));
    }

    @Test public void testSetDirection() throws Exception {
        viewPager.setAllowedDirection(SwipeDirection.RIGHT);

        assertFalse(viewPager.isLocked(SwipeDirection.RIGHT));
        assertTrue(viewPager.isLocked(SwipeDirection.LEFT));
    }

    @Test public void testLockWithDirection() throws Exception {
        viewPager.setAllowedDirection(SwipeDirection.NONE);

        assertTrue(viewPager.isLocked(SwipeDirection.RIGHT));
        assertTrue(viewPager.isLocked(SwipeDirection.LEFT));
    }

    @Test public void testIsAllLocked() throws Exception {
        viewPager.lock(SwipeDirection.ALL);

        assertTrue(viewPager.isLocked(SwipeDirection.ALL));
    }

    @Test public void testIsNoneLocked() throws Exception {
        viewPager.lock(SwipeDirection.NONE);

        assertTrue(viewPager.isLocked(SwipeDirection.NONE));
    }
}