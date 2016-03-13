package com.tereshkoff.passwordmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.tereshkoff.passwordmanager.R;

import java.util.List;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<Group> objects;

    BoxAdapter(Context context, List<Group> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.group_item, parent, false);
        }

        Group p = getProduct(position);

        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.getName());
        //((TextView) view.findViewById(R.id.tvPrice)).setText(p.price + "");
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

        return view;
    }

    // товар по позиции
    Group getProduct(int position) {
        return ((Group) getItem(position));
    }
}