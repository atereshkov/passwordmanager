package com.tereshkoff.passwordmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.Group;

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
            TextView tt2 = (TextView) v.findViewById(R.id.tvPrice);

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

}
