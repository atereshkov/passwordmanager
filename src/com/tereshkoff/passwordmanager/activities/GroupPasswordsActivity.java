package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.adapters.PasswordsAdapter;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.json.JsonParser;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.utils.Constants;

import java.io.IOException;

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

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addPasswordIntent = new Intent(getApplicationContext(), AddPasswordActivity.class);
                addPasswordIntent.putExtra("groupsList", groupsList);
                addPasswordIntent.putExtra("addToGroup", selectedGroup.toString());
                startActivityForResult(addPasswordIntent, 0);

            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case 0: // for addpasword activity
                if (data == null) {return;}

                groupsList = (GroupsList) data.getExtras().getSerializable("groupsList");

                if (data != null)
                {
                    Toast.makeText(getApplicationContext(), "Пароль успешно добавлен в группу " + selectedGroup.getName(),
                            Toast.LENGTH_SHORT).show();

                    try
                    {
                        JsonFilesWorker.saveToFile(Constants.DAFAULT_DBFILE_NAME, groupsList);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile(Constants.PWDIRECTORY, Constants.DAFAULT_DBFILE_NAME));
                    groupAdapter.refreshEvents(selectedGroup.getPasswordList().getPasswordList());
                    //groupAdapter = new PasswordsAdapter(this, android.R.layout.simple_list_item_1,
                            //selectedGroup.getPasswordList().getPasswordList());
                    finish();
                }

                break;

            case 1: // for password editing activity (if del)
                if (data == null) {return;}
                Password editedPassword = (Password) data.getExtras().getSerializable("password");
                String oldGroup = data.getExtras().getString("oldGroup");
                Boolean toRemove = data.getExtras().getBoolean("isToRemove");

                if (toRemove)
                {
                    groupsList.removePassword(editedPassword);
                    Toast.makeText(getApplicationContext(), "Пароль успешно удален!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    groupsList.editPassword(
                            editedPassword.getGroupName(),
                            groupsList.getGroupByName(oldGroup).getPasswordList(),
                            editedPassword);

                    //Toast.makeText(getActivity(), "Пароль изменен!", Toast.LENGTH_SHORT).show();
                }

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
                finish();

                break;

        }

    }


}
