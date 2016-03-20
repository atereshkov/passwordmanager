package com.tereshkoff.passwordmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.PasswordsAdapter;

import java.io.IOException;

public class OneTabActivity extends Fragment {

    private ListView listView1;
    private ImageButton floatButton;
    GroupsList groupsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_one, container, false);

        floatButton = (ImageButton) V.findViewById(R.id.imageButton);
        listView1 = (ListView) V.findViewById(R.id.listView1);

        JsonFilesWorker.createFile("database.json");

        groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile("/PWManager/", "database.json"));

        try {
            JsonFilesWorker.saveToFile("test.json", groupsList);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        PasswordsAdapter groupAdapter = new PasswordsAdapter(getActivity(), android.R.layout.simple_list_item_1,
                groupsList.getAllPasswords().getPasswordList());
        listView1.setAdapter(groupAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                //intent.putExtra("passwordList", groupsList.getGroupByName(parent.getItemAtPosition(position).toString()));
                //startActivity(intent);

            }
        });

        return V;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
