package com.tereshkoff.passwordmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tereshkoff.passwordmanager.R;
import org.w3c.dom.Text;

import java.util.List;

public class PasswordsAdapter extends ArrayAdapter<Password> {

    public PasswordsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PasswordsAdapter(Context context, int resource, List<Password> items) {
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
            TextView tt2 = (TextView) v.findViewById(R.id.pwPrice); // change

            if (tt1 != null)
            {
                tt1.setText(p.getUsername());
            }

            if (tt2 != null)
            {
                tt2.setText(p.getPassword());
            }
        }

        return v;
    }

}
