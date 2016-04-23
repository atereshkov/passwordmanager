package com.tereshkoff.passwordmanager.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {

    List<Group> items;

    List<Integer> pagesImg = new ArrayList<>();

    public GroupAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public GroupAdapter(Context context, int resource, List<Group> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.group_item, null);
        }

        Group p = getItem(position);

        if (p != null)
        {
            TextView tt1 = (TextView) v.findViewById(R.id.tvDescr);
            TextView tt2 = (TextView) v.findViewById(R.id.tvPrice);
            ImageView ivImage = (ImageView) v.findViewById(R.id.ivImage);

            Integer g = 0;
            for(Group group : items)
            {
                group.setImageIndex(g);
                g++;
            }

            TypedArray ar = v.getResources().obtainTypedArray(R.array.groupImages);
            for (int i = 0; i < ar.length(); i++)
                pagesImg.add(ar.getResourceId(i, 0));

            ivImage.setImageResource(pagesImg.get(p.getImageIndex()));

            if (tt1 != null)
            {
                tt1.setText(p.getName());
            }

            if (tt2 != null)
            {
                if (p.getPasswordList() != null)
                    tt2.setText(p.getPasswordList().getCount().toString());
            }

        }

        return v;
    }

    public void refreshEvents(List<Group> items)
    {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

}
