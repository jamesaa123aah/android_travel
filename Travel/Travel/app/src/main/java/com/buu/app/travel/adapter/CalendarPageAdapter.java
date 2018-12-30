package com.buu.app.travel.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by king- on 2017/8/22.
 */

public class
CalendarPageAdapter extends PagerAdapter {
    private static final String TAG = "CalendarPageAdapter";
    private View[] views = new View[3];
    public CalendarPageAdapter(View[] views){
        this.views = views;
    }
    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(views[position]);
    }
}
