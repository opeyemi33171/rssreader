package com.example.opeyemi.rssreader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.opeyemi.rssreader.fragments.FeedFragment;
import com.example.opeyemi.rssreader.fragments.SourceFragment;

/**
 * Created by opeyemi on 25/08/2016.
 */
public class FeedSourceAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public static int NUM_PAGES = 2;

   public FeedSourceAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public int getCount() {

        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return SourceFragment.newInstance(0, "SOURCES");

            case 1:
                return FeedFragment.newInstance(1, "FEEDS");

            default:
                return null;
        }
    }
}
