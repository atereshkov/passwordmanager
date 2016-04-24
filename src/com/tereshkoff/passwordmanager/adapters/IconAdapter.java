package com.tereshkoff.passwordmanager.adapters;


import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tereshkoff.passwordmanager.R;

import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends ArrayAdapter<String> {

    List<Integer> pagesImg = new ArrayList<>();

    public IconAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.icon_row, parent, false);
        /*TextView label = (TextView) row.findViewById(R.id.company);
        label.setText(strings[position]);

        TextView sub = (TextView) row.findViewById(R.id.sub);
        sub.setText(subs[position]);*/

        TypedArray ar = row.getResources().obtainTypedArray(R.array.iconImages);
        for (int i = 0; i < ar.length(); i++)
            pagesImg.add(ar.getResourceId(i, 0));

        ImageView icon = (ImageView) row.findViewById(R.id.iconImage);
        icon.setImageResource(pagesImg.get(position));

        return row;
    }
}
