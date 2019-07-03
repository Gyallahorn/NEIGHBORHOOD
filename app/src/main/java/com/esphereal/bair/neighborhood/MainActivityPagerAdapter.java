package com.esphereal.bair.funloot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public MainActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsListFragment news = new NewsListFragment();
                return news;
            case 1:
                TrackerFragment trackerFragment = new TrackerFragment();
                return trackerFragment;
            case 2:
                DiscountFragment discountFragment = new DiscountFragment();
                return discountFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
