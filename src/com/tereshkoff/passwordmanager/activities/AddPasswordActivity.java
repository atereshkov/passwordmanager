package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.models.PasswordList;

import java.util.List;

public class AddPasswordActivity extends Activity{

    private GroupsList groupsList;
    private EditText usernameAddEdit;
    private EditText passwordAddEdit;
    private Spinner spinner;

    private String username;
    private String password;
    private String selectedGroup;

    List<String> groupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpassword_activity);

        usernameAddEdit = (EditText) findViewById(R.id.usernameAddEdit);
        passwordAddEdit = (EditText) findViewById(R.id.passwordAddEdit);
        spinner = (Spinner) findViewById(R.id.spinner);

        Intent i = getIntent();
        groupsList = (GroupsList) i.getSerializableExtra("groupsList");

        groupNames = groupsList.getGroupsNames();

        ArrayAdapter<?> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                selectedGroup = groupNames.get(selectedItemPosition);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void addPassword(View view)
    {
        password = usernameAddEdit.getText().toString();
        username = passwordAddEdit.getText().toString();

        Password newPassword = new Password(username, password);

        Group group = groupsList.getGroupByName(selectedGroup);
        PasswordList pwList = group.getPasswordList();
        pwList.add(newPassword);

        Intent intent = new Intent();
        intent.putExtra("groupsList", groupsList);

        setResult(RESULT_OK, intent);

        finish();

    }


}
