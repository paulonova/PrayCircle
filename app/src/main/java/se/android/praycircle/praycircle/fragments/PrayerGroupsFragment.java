package se.android.praycircle.praycircle.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.android.praycircle.praycircle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerGroupsFragment extends Fragment {


    public PrayerGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment2, container, false);

        return rootView;
    }

}
