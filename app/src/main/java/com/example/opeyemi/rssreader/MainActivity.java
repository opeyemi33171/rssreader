package com.example.opeyemi.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.opeyemi.rssreader.adapters.FeedSourceAdapter;
import com.example.opeyemi.rssreader.adapters.SourceAdapter;
import com.example.opeyemi.rssreader.datamodels.Source;
import com.example.opeyemi.rssreader.fragments.AddFeedDialog;
import com.example.opeyemi.rssreader.fragments.SourceFragment;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sources");
        setSupportActionBar(toolbar);


         TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);

         ViewPager fragmentViewPager = (ViewPager)findViewById(R.id.fragment_reel);
         FeedSourceAdapter adapter = new FeedSourceAdapter(getSupportFragmentManager());

         fragmentViewPager.setAdapter(adapter);
         tabLayout.setupWithViewPager(fragmentViewPager);

    }
}
