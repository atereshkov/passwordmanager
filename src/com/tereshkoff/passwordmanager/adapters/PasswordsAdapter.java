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
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.Password;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PasswordsAdapter extends ArrayAdapter<Password> {

    private List<Password> items;

    List<Integer> pagesImg = new ArrayList<>();

    public PasswordsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PasswordsAdapter(Context context, int resource, List<Password> items) {
        super(context, resource, items);
        this.items = items;
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
            List<String> groupStr = new ArrayList<>();
            groupStr.add("Социальные сети");
            groupStr.add("Электронная почта");
            groupStr.add("Веб-сайты");
            groupStr.add("Компьютер");
            groupStr.add("PIN-коды");
            groupStr.add("Wi-Fi");
            groupStr.add("Прочее");

            TypedArray ar = v.getResources().obtainTypedArray(R.array.groupImages);
            for (int i = 0; i < ar.length(); i++)
            {
                pagesImg.add(ar.getResourceId(i, 0));
            }

            ImageView imageView9 = (ImageView) v.findViewById(R.id.imageView9);

            imageView9.setImageResource(pagesImg.get(groupStr.indexOf(p.getGroupName())));

            TextView tt1 = (TextView) v.findViewById(R.id.pwTitle);
            TextView tt2 = (TextView) v.findViewById(R.id.pwPass);
            ImageView imgView = (ImageView) v.findViewById(R.id.ivImage); //  TODO: GET IMAGE FROM PASSWORD

            if (imgView != null)
            {
               //imgView.setImageResource(); load from list (resources like in firstLoginActivity)
            }

            if (tt1 != null)
            {
                tt1.setText(p.getUsername());
            }

            if (tt2 != null)
            {
                tt2.setText(p.getSite());
            }
        }

        return v;
    }

    public void refreshEvents(List<Password> items)
    {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

}
