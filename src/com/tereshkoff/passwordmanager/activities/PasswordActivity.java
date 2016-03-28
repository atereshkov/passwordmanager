package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;

import java.util.List;

public class PasswordActivity extends Activity {

    private EditText usernameAddEdit;
    private EditText passwordAddEdit;
    private Spinner spinner;

    private String username;
    private String password;
    private String selectedGroup;

    private Password editPassword;
    private CheckBox showPasswordCheckBox;

    List<String> groupNames;
    private GroupsList groupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);

        usernameAddEdit = (EditText) findViewById(R.id.usernameAddEdit);
        passwordAddEdit = (EditText) findViewById(R.id.passwordAddEdit);
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        spinner = (Spinner) findViewById(R.id.spinner);

        Intent i = getIntent();
        editPassword = (Password) i.getSerializableExtra("password");
        groupsList = (GroupsList) i.getSerializableExtra("groupsList");

        groupNames = groupsList.getGroupsNames();

        ArrayAdapter<?> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        selectedGroup = editPassword.getGroupName();
        int selectGroupPosition = groupNames.indexOf(editPassword.getGroupName());
        spinner.setSelection(selectGroupPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                selectedGroup = groupNames.get(selectedItemPosition);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPasswordCheckBox.isChecked())
                {
                    passwordAddEdit.setTransformationMethod(null);
                }
                else
                {
                    passwordAddEdit.setTransformationMethod(new PasswordTransformationMethod());
                }
                passwordAddEdit.setSelection(passwordAddEdit.getText().length());
            }
        });

        usernameAddEdit.setText(editPassword.getUsername());
        passwordAddEdit.setText(editPassword.getPassword());

    }

    public void editPassword(View view)
    {
        password = passwordAddEdit.getText().toString();
        username = usernameAddEdit.getText().toString();
        int oldID = editPassword.getId();
        String oldGroup = editPassword.getGroupName();

        editPassword = new Password(username, password, selectedGroup, oldID);

        Intent intent = new Intent();
        intent.putExtra("password", editPassword);
        intent.putExtra("oldGroup", oldGroup);

        setResult(RESULT_OK, intent);

        finish();
    }

}
