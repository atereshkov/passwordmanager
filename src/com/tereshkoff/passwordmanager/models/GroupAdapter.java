package com.tereshkoff.passwordmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.R;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public GroupAdapter(Context context, int resource, List<Group> items) {
        super(context, resource, items);
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

            if (tt1 != null)
            {
                tt1.setText(p.getName());
            }
        }

        return v;
    }

}
