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
import com.example.opeyemi.rssreader.datamodels.SourceItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by opeyemi on 11/08/2016.
 */
public class SourceItemAdapter extends ArrayAdapter<SourceItem> {


    public SourceItemAdapter(Context context, ArrayList<SourceItem> sources) {
        super(context, 0, sources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SourceItem source = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_item_row, parent, false);
        }

        TextView sourceName = (TextView)convertView.findViewById(R.id.articleName);
        TextView sourceDescription = (TextView)convertView.findViewById(R.id.sourceDescription);
        ImageView sourceIcon = (ImageView)convertView.findViewById(R.id.articleIcon);

        sourceName.setText(source.getName());
        sourceDescription.setText(Html.fromHtml(source.getDescription()));
        Picasso.with(getContext())
                .load(source.getIcon())
                .resize(600,300).centerInside()
                .into(sourceIcon);



        return convertView;
    }
}
