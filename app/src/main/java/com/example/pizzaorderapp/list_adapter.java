package com.example.pizzaorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_adapter extends ArrayAdapter<list_item> {
  public list_adapter(Context context, ArrayList<list_item> list){
      super(context,R.layout.list_item,list);
  }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      list_item list_item = getItem(position);
      if(convertView==null){
          convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
      }
        TextView name = convertView.findViewById(R.id.pizzaName);
        TextView descr = convertView.findViewById(R.id.pizzaDesc);
        name.setText(list_item.pizza_name);
        descr.setText(list_item.pizza_desc);
        return convertView;
    }
}