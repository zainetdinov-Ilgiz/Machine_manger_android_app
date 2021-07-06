package com.example.machine_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<View> {

    ArrayList<View> objects;
    //Context context;
    public CustomArrayAdapter( Context context,  ArrayList<View> objects) {
        super(context, android.R.layout.activity_list_item,objects);
        //this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = (View) getItem(position);
         if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout2, null);
        }
        return convertView;
    }
}
