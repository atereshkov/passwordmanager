package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.adapters.PasswordsAdapter;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.json.JsonParser;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.utils.Constants;

public class GroupPasswordsActivity extends Activity {

    private ListView listView1;
    private ImageButton floatButton;
    GroupsList groupsList;
    Group selectedGroup;

    PasswordsAdapter groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppw_activity);

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        listView1 = (ListView) findViewById(R.id.listView1);

        //groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile(Constants.PWDIRECTORY, Constants.DAFAULT_DBFILE_NAME));

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        groupsList = (GroupsList) i.getSerializableExtra("groupsList");
        selectedGroup = (Group) i.getSerializableExtra("selectedGroup");

        /*floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addPasswordIntent = new Intent(this, AddPasswordActivity.class);
                addPasswordIntent.putExtra("groupsList", groupsList);
                startActivityForResult(addPasswordIntent, 0);

            }
        });*/

        groupAdapter = new PasswordsAdapter(this, android.R.layout.simple_list_item_1,
                selectedGroup.getPasswordList().getPasswordList());
        listView1.setAdapter(groupAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                Intent passwordInformIntent = new Intent(getApplicationContext(), PasswordActivity.class);
                passwordInformIntent.putExtra("password",
                        groupsList.getAllPasswords().getPasswordById(parent.getItemAtPosition(position).toString()));
                passwordInformIntent.putExtra("groupsList", groupsList);
                startActivityForResult(passwordInformIntent, 1);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
