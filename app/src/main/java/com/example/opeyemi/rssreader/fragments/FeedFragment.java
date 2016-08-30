package com.example.opeyemi.rssreader.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;
import com.example.opeyemi.rssreader.R;
import com.example.opeyemi.rssreader.adapters.FeedItemAdapter;
import com.example.opeyemi.rssreader.adapters.FeedSourceAdapter;
import com.example.opeyemi.rssreader.datamodels.Source;
import com.example.opeyemi.rssreader.datamodels.SourceItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by opeyemi on 15/08/2016.
 */
public class FeedFragment extends Fragment {

    private Thread requestThread;
    private View view;
    private RecyclerRefreshLayout pullToRefreshLayout;

    private String title;
    private int page;

    private RealmConfiguration config;
    private  Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("PAGE_NUM", 0);
        title = getArguments().getString("TITLE");

        config = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

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

        view = (View)inflater.inflate(R.layout.feed_fragment,container, false);

        pullToRefreshLayout = (RecyclerRefreshLayout)view.findViewById(R.id.refresh_layout);
        pullToRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh();

            }
        });
        pullToRefresh();


        return view;
    }

public void pullToRefresh() {

    RealmQuery<Source> query = realm.where(Source.class);
    RealmResults<Source> favoriteSources = query.equalTo("favorite", true).findAll();

    final ArrayList<SourceItem> sourceItems = new ArrayList<>();
    final FeedItemAdapter adapter = new FeedItemAdapter(getContext(), sourceItems);

    ListView feedListView = (ListView) view.findViewById(R.id.feedListView);
    feedListView.setAdapter(adapter);

    final ArrayList<String> urls = new ArrayList<>();

    for (Source source : favoriteSources) {
        urls.add(source.getUrl());
    }


    requestThread = new Thread() {
        @Override
        public void run() {
            for (final String url : urls)
                try {
                    InputStream inputStream = new URL(url).openConnection().getInputStream();
                    Feed feed = EarlParser.parseOrThrow(inputStream, 0);

                    for (Item item : feed.getItems()) {

                        SourceItem sourceItem = new SourceItem();
                        sourceItem.setName(item.getTitle());
                        sourceItem.setDescription(item.getDescription());
                        sourceItem.setIcon(item.getImageLink());
                        sourceItem.setLink(item.getLink());
                        sourceItem.setParentSource(feed.getTitle());
                        sourceItem.setDate(item.getPublicationDate());


                        sourceItems.add(sourceItem);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataFormatException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(sourceItems, new Comparator<SourceItem>() {
                        public int compare(SourceItem first, SourceItem second) {
                            return first.getDate().compareTo(second.getDate());
                        }
                    });
                    adapter.notifyDataSetChanged();
                    pullToRefreshLayout.setRefreshing(false);
                }
            });

        }



    };
    requestThread.start();
}

}
