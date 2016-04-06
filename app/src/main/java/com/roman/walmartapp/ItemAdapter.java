package com.roman.walmartapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

class ItemAdapter extends BaseAdapter {

    ArrayList<ItemObject> list;
    Context context;

    ItemAdapter(Context c, ArrayList<ItemObject> itemObject){
        this.context = c;
        this.list = itemObject;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row, parent, false);

        TextView itemNameText = (TextView) row.findViewById(R.id.rowTextView);
        TextView itemPriceText = (TextView) row.findViewById(R.id.rowPriceTextView);
        ImageView img = (ImageView) row.findViewById(R.id.imageView);

        ItemObject theObject = list.get(position);
        itemNameText.setText(theObject.itemName);
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        itemPriceText.setText(priceFormat.format(Double.parseDouble(theObject.priceText)));
        img.setImageBitmap(theObject.thumbnail);

        return row;
    }
}