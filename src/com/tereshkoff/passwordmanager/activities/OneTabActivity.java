package com.tereshkoff.passwordmanager.activities;

import android.content.Intent;
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
import com.tereshkoff.passwordmanager.utils.Constants;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.json.JsonParser;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.adapters.PasswordsAdapter;

import java.io.IOException;

public class OneTabActivity extends Fragment {

    private ListView listView1;
    private ImageButton floatButton;
    GroupsList groupsList;

    PasswordsAdapter groupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.tab_one, container, false);

        floatButton = (ImageButton) V.findViewById(R.id.imageButton);
        listView1 = (ListView) V.findViewById(R.id.listView1);

        //JsonFilesWorker.createDefaultBase("database.json");  // TODO: DELETE! THIS IS JUST FOR TEST

        groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile(Constants.PWDIRECTORY, Constants.DAFAULT_DBFILE_NAME));

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), JsonFilesWorker.readFile("/PWManager/", "database.json"), Toast.LENGTH_LONG).show();

                Intent addPasswordIntent = new Intent(getActivity(), AddPasswordActivity.class);
                addPasswordIntent.putExtra("groupsList", groupsList);
                startActivityForResult(addPasswordIntent, 101);

            }
        });

        groupAdapter = new PasswordsAdapter(getActivity(), android.R.layout.simple_list_item_1,
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {return;}
        //String name = data.getExtra("groupsList");

        groupsList = (GroupsList) data.getExtras().getSerializable("groupsList");

        if (data != null)
        {
            Toast.makeText(getActivity(), "Пароль успешно добавлен!", Toast.LENGTH_SHORT).show();

            try
            {
                JsonFilesWorker.saveToFile(Constants.DAFAULT_DBFILE_NAME, groupsList);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile(Constants.PWDIRECTORY, Constants.DAFAULT_DBFILE_NAME));
            groupAdapter.refreshEvents(groupsList.getAllPasswords().getPasswordList());
        }


    }
}
