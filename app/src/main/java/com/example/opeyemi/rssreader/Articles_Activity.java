package com.example.opeyemi.rssreader;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;
import com.example.opeyemi.rssreader.adapters.SourceItemAdapter;
import com.example.opeyemi.rssreader.datamodels.Source;
import com.example.opeyemi.rssreader.datamodels.SourceItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Articles_Activity extends AppCompatActivity {


    private Realm realm = Realm.getDefaultInstance();

    private Source selectedSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_);

        final Intent intent = getIntent();

        RealmQuery query = realm.where(Source.class);
        RealmResults<Source> results = query.equalTo("url",getIntent().getStringExtra("URL")).findAll();

        selectedSource = results.first();

        final ArrayList<SourceItem> items = new ArrayList<>();
        final SourceItemAdapter adapter = new SourceItemAdapter(this, items);

        final ListView articleReel = (ListView)findViewById(R.id.articleReel);

        articleReel.setAdapter(adapter);


        Thread requestThread = new Thread() {

            @Override
            public void run() {

                try {
                    InputStream inputStream = new URL(intent.getStringExtra("URL")).openConnection().getInputStream();
                    Feed feed = EarlParser.parseOrThrow(inputStream,0);

                    for(Item item : feed.getItems()){

                        SourceItem sourceItem = new SourceItem();
                        sourceItem.setName(item.getTitle());
                        sourceItem.setDescription(item.getDescription());
                        sourceItem.setIcon(item.getImageLink());
                        sourceItem.setLink(item.getLink());


                        items.add(sourceItem);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               adapter.notifyDataSetChanged();
                            }
                        });


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataFormatException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }



            }
        };

        requestThread.start();

        articleReel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(Articles_Activity.this, Uri.parse(items.get(i).getLink()));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_menu, menu);

        MenuItem item  = menu.findItem(R.id.favourite_icon);

        if(selectedSource == null)return true;

        if(selectedSource.getFavorite()){

            item.setIcon(R.drawable.ic_favorite_white_24dp);

        }

        else{

            item.setIcon(R.drawable.ic_favorite_border_white_24dp);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(selectedSource.getFavorite()){

                item.setIcon(R.drawable.ic_favorite_border_white_24dp);

            }

            else{

                item.setIcon(R.drawable.ic_favorite_white_24dp);
            }

        realm.beginTransaction();
        selectedSource.setFavorite(!selectedSource.getFavorite());
        realm.commitTransaction();



        return true;

    }



}
