package com.tereshkoff.passwordmanager;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.models.GroupAdapter;
import com.tereshkoff.passwordmanager.models.GroupsList;

public class TwoTabActivity extends Activity {

    private ListView listView1;
    private ImageButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_two);

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        listView1 = (ListView) findViewById(R.id.listView1);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        JsonFilesWorker.readFile("/PWManager/", "database.json"), Toast.LENGTH_LONG).show();
            }
        });

        JsonFilesWorker.createFile("database.json");
        GroupsList groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile("/PWManager/", "database.json"));

        GroupAdapter groupAdapter = new GroupAdapter(this, android.R.layout.simple_list_item_1, groupsList.getGroups());
        listView1.setAdapter(groupAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                //intent.putExtra("passwordList", groupsList.getGroupByName(parent.getItemAtPosition(position).toString()));
                //startActivity(intent);

            }
        });

    }
}
