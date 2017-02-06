package se.android.praycircle.praycircle.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import se.android.praycircle.praycircle.fragments.MyPrayersFragment;
import se.android.praycircle.praycircle.fragments.PrayerGroupsFragment;
import se.android.praycircle.praycircle.fragments.PrayerAlarmFragment;

/** * Created by Paulo Vila Nova on 2016-11-11.
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
                MyPrayersFragment tab1 = new MyPrayersFragment();
                return tab1;
            case 1:
                PrayerGroupsFragment tab2 = new PrayerGroupsFragment();
                return tab2;
            case 2:
                PrayerAlarmFragment tab3 = new PrayerAlarmFragment();
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
