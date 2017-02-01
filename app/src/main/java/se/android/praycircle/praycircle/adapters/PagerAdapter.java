package se.android.praycircle.praycircle.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import se.android.praycircle.praycircle.fragments.Fragment1;
import se.android.praycircle.praycircle.fragments.Fragment2;
import se.android.praycircle.praycircle.fragments.Fragment3;

/**
 * Created by Paulo Vila Nova on 2016-11-11.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment1 tab1 = new Fragment1();
                return tab1;
            case 1:
                Fragment2 tab2 = new Fragment2();
                return tab2;
            case 2:
                Fragment3 tab3 = new Fragment3();
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
