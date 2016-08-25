package com.example.opeyemi.rssreader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.opeyemi.rssreader.R;
import com.example.opeyemi.rssreader.datamodels.Source;

import java.util.ArrayList;

/**
 * Created by opeyemi on 11/08/2016.
 */
public class SourceAdapter extends ArrayAdapter<Source> {

    public SourceAdapter(Context context, ArrayList<Source> source) {
        super(context, 0, source);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Source source = getItem(position);

        if(convertView == null ){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_row, parent , false);
        }

        TextView sourceName = (TextView)convertView.findViewById(R.id.sourceName);
        sourceName.setText(source.getName());

        return convertView;
    }
}
