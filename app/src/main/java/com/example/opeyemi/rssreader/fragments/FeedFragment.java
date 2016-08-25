package com.example.opeyemi.rssreader.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.opeyemi.rssreader.R;

/**
 * Created by opeyemi on 15/08/2016.
 */
public class FeedFragment extends Fragment {


    private String title;
    private int page;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("PAGE_NUM", 0);
        title = getArguments().getString("TITLE");


    }

    public static FeedFragment newInstance(int page, String title){
        FeedFragment feeds = new FeedFragment();

        Bundle args = new Bundle();
        args.putInt("PAGE_NUM", page);
        args.putString("TITLE", title);
        feeds.setArguments(args);
        return feeds;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.feed_fragment, container, false);
    }




}
