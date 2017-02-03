package se.android.praycircle.praycircle.fragments;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.adapters.MyPrayersAdapter;
import se.android.praycircle.praycircle.helpers.VarHolder;
import se.android.praycircle.praycircle.objects.MyPrayerSubject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPrayersFragment extends Fragment {

    @BindView(R.id.recyclerViewMyPrayersList)  RecyclerView recyclerViewMyPrayersList;
    @BindView(R.id.swipeRefreshLayoutMyPrayersList)  SwipeRefreshLayout swipeRefreshLayoutMyPrayersList;

    private MyPrayersAdapter myPrayersAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<MyPrayerSubject> myPrayerSubjectsList;
    private long sleep = 2000;


    public MyPrayersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        ButterKnife.bind(this, rootView);

        setSomeDataInVArHolder();
        myPrayersAdapter = new MyPrayersAdapter(myPrayerSubjectsList, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewMyPrayersList.setHasFixedSize(true);
        recyclerViewMyPrayersList.setLayoutManager(mLayoutManager);

        recyclerViewMyPrayersList.setAdapter(myPrayersAdapter);


        swipeRefreshLayoutMyPrayersList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(sleep);
                                    if(swipeRefreshLayoutMyPrayersList.isRefreshing()){
                                        swipeRefreshLayoutMyPrayersList.setRefreshing(false);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();



            }
        });

        return rootView;
    }

    public void setSomeDataInVArHolder(){
        myPrayerSubjectsList = new ArrayList<>();

        for (int i = 0; i < 3 ; i++) {

            MyPrayerSubject myPrayerSubject = new MyPrayerSubject();
            myPrayerSubject.setName("Paulo Vila Nova");
            myPrayerSubject.setDate("2017/02/03");
            myPrayerSubject.setId("0123");
            myPrayerSubject.setImageUrl("");
            myPrayerSubject.setPrayerTitle("For my Family");
            myPrayerSubject.setPraySubject("Please Lord help my family to get throw this hard time and bless my kids, in the name of Jesus, amen ");
            myPrayerSubjectsList.add(myPrayerSubject);
        }




    }

}
