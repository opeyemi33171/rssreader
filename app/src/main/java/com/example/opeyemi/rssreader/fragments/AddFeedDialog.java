package com.example.opeyemi.rssreader.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.opeyemi.rssreader.R;

/**
 * Created by opeyemi on 28/08/2016.
 */
public class AddFeedDialog extends android.support.v4.app.DialogFragment {

    public AddFeedDialog(){

    }

    public static AddFeedDialog newInstance(String title){
        AddFeedDialog dialog = new AddFeedDialog();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        dialog.setArguments(args);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return  inflater.inflate(R.layout.add_feed_dialog,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(getArguments().getString("TITLE"));

        EditText sourceName = (EditText)view.findViewById(R.id.sourceName);
        EditText sourceUrl = (EditText)view.findViewById(R.id.sourceUrl);

        Button createButton = (Button)view.findViewById(R.id.CreateButton);
        Button cancelButton = (Button)view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra()

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        sourceName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
