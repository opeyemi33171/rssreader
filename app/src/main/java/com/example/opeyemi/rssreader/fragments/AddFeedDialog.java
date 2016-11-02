package com.example.opeyemi.rssreader.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.opeyemi.rssreader.R;
import com.example.opeyemi.rssreader.datamodels.Source;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

/**
 * Created by opeyemi on 28/08/2016.
 */
public class AddFeedDialog extends android.support.v4.app.DialogFragment {

    private String selectedColor;
    private LineColorPicker colorPicker;
    private Realm realm;
    private boolean duplicateName = false;

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
        realm = Realm.getDefaultInstance();
        super.onViewCreated(view, savedInstanceState);


        RealmQuery<Source> query = realm.where(Source.class);
        final RealmResults<Source> sources = query.findAll();

        colorPicker = (LineColorPicker) view.findViewById(R.id.picker);
        colorPicker.setColors(new int[] {Color.parseColor("#66b9c3"),Color.parseColor("#66c39e"),Color.parseColor("#c366ba"),Color.parseColor("#bac366"),Color.parseColor("#3e649f"),Color.parseColor("#649f3e"),Color.parseColor("#9f3e64"),Color.parseColor("#ffa500"), Color.parseColor("#551a8b"), Color.parseColor("#d9b19c"), Color.parseColor("#9cd9d0"),
        Color.parseColor("#d99cc4"), Color.parseColor("#d99ca5"), Color.parseColor("#3e9f79")});

        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
                selectedColor = Integer.toHexString(i);
            }
        });



        getDialog().setTitle(getArguments().getString("TITLE"));

        final EditText sourceName = (EditText)view.findViewById(R.id.sourceName);
        final EditText sourceUrl = (EditText)view.findViewById(R.id.sourceUrl);

        Button createButton = (Button)view.findViewById(R.id.CreateButton);
        Button cancelButton = (Button)view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Source source: sources ){
                    if(source.getName().equalsIgnoreCase(sourceName.getText().toString())){
                        duplicateName = true;
                    }
                    else{
                        duplicateName = false;
                        break;
                    }
                }
                if(duplicateName){
                    sourceName.setText("");
                    Toast.makeText(getActivity(),"Source name has been taken", Toast.LENGTH_LONG).show();
                    dismiss();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("SOURCE_NAME", sourceName.getText().toString());
                    intent.putExtra("SOURCE_URL", sourceUrl.getText().toString());
                    intent.putExtra("SOURCE_COLOR", selectedColor);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

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
