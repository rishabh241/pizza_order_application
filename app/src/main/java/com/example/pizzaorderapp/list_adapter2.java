package com.example.pizzaorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_adapter2 extends ArrayAdapter<list_item2> {
    public list_adapter2(Context context, ArrayList<list_item2> list2){
        super(context,R.layout.list_item2,list2);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView2, @NonNull ViewGroup parent) {
        list_item2 list_item2 = getItem(position);
        if(convertView2==null){
            convertView2 = LayoutInflater.from(getContext()).inflate(R.layout.list_item2,parent,false);
        }
        TextView namee = convertView2.findViewById(R.id.pizzaNamee);
        TextView descr = convertView2.findViewById(R.id.descr);
//        AutoCompleteTextView size = convertView.findViewById(R.id.InputSize);
//        size.setDropDownAnchor(list_item2.size);
        namee.setText(list_item2.namee);
        descr.setText(list_item2.descr);
        return convertView2;
    }
}
