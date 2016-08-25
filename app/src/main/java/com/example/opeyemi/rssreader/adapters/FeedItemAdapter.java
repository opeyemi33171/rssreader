package com.example.opeyemi.rssreader.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opeyemi.rssreader.R;
import com.example.opeyemi.rssreader.datamodels.Source;
import com.example.opeyemi.rssreader.datamodels.SourceItem;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by opeyemi on 25/08/2016.
 */
public class FeedItemAdapter extends ArrayAdapter<SourceItem> {

    public FeedItemAdapter(Context context, List<SourceItem> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SourceItem item = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_item_row, parent, false);
        }


        TextView sourceName = (TextView)convertView.findViewById(R.id.articleName);
        TextView sourceDescription = (TextView)convertView.findViewById(R.id.sourceDescription);
        ImageView sourceIcon = (ImageView)convertView.findViewById(R.id.articleIcon);

        sourceName.setText(item.getName());
        sourceDescription.setText(Html.fromHtml(item.getDescription()));
        Picasso.with(getContext())
                .load(item.getIcon())
                .resize(600,300).centerInside()
                .into(sourceIcon);

        return convertView;
    }
}
