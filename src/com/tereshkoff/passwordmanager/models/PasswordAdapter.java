package com.tereshkoff.passwordmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tereshkoff.passwordmanager.R;

import java.util.List;

public class PasswordAdapter extends ArrayAdapter<Password> {

    public PasswordAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    public PasswordAdapter(Context context, int resource, List<Password> items)
    {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.password_item, null);
        }

        Password p = getItem(position);

        if (p != null)
        {
            TextView tt1 = (TextView) v.findViewById(R.id.pwDescr);

            if (tt1 != null)
            {
                tt1.setText(p.getUsername());
            }
        }

        return v;
    }



}
