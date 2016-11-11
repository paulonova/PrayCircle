//package se.android.praycircle.praycircle.adapters;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * Created by Paulo Vila Nova on 2016-11-11.
// */
//
//public class MyViewPagerAdapter extends PagerAdapter {
//
//    private LayoutInflater layoutInflater;
//    Activity activity;
//    private int[] layouts;
//
//    public MyViewPagerAdapter(Activity activity, int[] layouts) {
//        this.activity = activity;
//        this.layouts = layouts;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(layouts[position], container, false);
//        container.addView(view);
//
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return false;
//    }
//}
