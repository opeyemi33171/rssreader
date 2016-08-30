package com.example.opeyemi.rssreader.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.opeyemi.rssreader.Articles_Activity;
import com.example.opeyemi.rssreader.R;
import com.example.opeyemi.rssreader.adapters.SourceAdapter;
import com.example.opeyemi.rssreader.datamodels.Source;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by opeyemi on 15/08/2016.
 */
public class SourceFragment extends Fragment {

    public static final int SOURCE_FRAGMENT = 1;
    private String title;
    private int page;

    private final ArrayList<Source> sources = new ArrayList<>();
    private  SourceAdapter adapter;

    private Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("PAGE_NUM", 1);
        title = getArguments().getString("TITLE");

    }


    public static SourceFragment newInstance(int page, String title) {

        SourceFragment sources = new SourceFragment();

        Bundle args = new Bundle();
        args.putInt("PAGE_NUM", page);
        args.putString("TITLE", title);

        sources.setArguments(args);
        return sources;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = (View) inflater.inflate(R.layout.source_fragment, container, false);

        RealmConfiguration config = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();


        ListView sourceReel = (ListView) view.findViewById(R.id.sourceReel);
        adapter = new SourceAdapter(getContext(), sources);
        sourceReel.setAdapter(adapter);


        realm.beginTransaction();
        Source theVerge = realm.createObject(Source.class);
        theVerge.setName("THE VERGE");
        theVerge.setUrl("http://www.theverge.com/rss/index.xml");
        theVerge.setFavorite(false);


        Source bbc = realm.createObject(Source.class);
        bbc.setName("BBC");
        bbc.setUrl("http://feeds.bbci.co.uk/news/rss.xml?edition=uk");
        bbc.setFavorite(false);

        Source githubFeed = realm.createObject(Source.class);
        githubFeed.setName("GITHUB");
        githubFeed.setUrl("https://github.com/opeyemi33171.private.atom?token=AI63bi9oTTWVMvBOl32vW6YjqyiKCeX2ks61vX4awA==");
        githubFeed.setFavorite(false);

        realm.commitTransaction();

        sources.add(theVerge);
        sources.add(bbc);
        sources.add(githubFeed);

        sourceReel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent nav = new Intent(getActivity(), Articles_Activity.class);
                nav.putExtra("URL", sources.get(i).getUrl());
                startActivity(nav);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFeedDialog();
            }
        });
        return view;
    }

    public void showAddFeedDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddFeedDialog dialog = AddFeedDialog.newInstance("ADD FEED");
        dialog.setTargetFragment(this,SOURCE_FRAGMENT);
        dialog.show(fm, "ADD_FEED");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){



            realm.beginTransaction();
            Source newSource = realm.createObject(Source.class);
            newSource.setName(data.getStringExtra("SOURCE_NAME").toString());
            newSource.setUrl( data.getStringExtra("SOURCE_URL").toString());
            newSource.setFavorite(false);
            realm.commitTransaction();

            adapter.add(newSource);

        }
    }
}
