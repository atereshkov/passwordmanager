package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.models.PasswordList;
import com.tereshkoff.passwordmanager.passwordUtils.StrengthChecker;
import com.tereshkoff.passwordmanager.utils.ColorConstants;
import com.tereshkoff.passwordmanager.utils.Constants;
import com.tereshkoff.passwordmanager.utils.RandomUtils;

import java.util.List;

public class AddPasswordActivity extends Activity{

    private GroupsList groupsList;
    private EditText usernameAddEdit;
    private EditText passwordAddEdit;
    private Spinner spinner;
    private ProgressBar strengthBar;

    private String username;
    private String password;
    private String selectedGroup;

    private CheckBox showPasswordCheckBox;

    List<String> groupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpassword_activity);

        usernameAddEdit = (EditText) findViewById(R.id.usernameAddEdit);
        passwordAddEdit = (EditText) findViewById(R.id.passwordAddEdit);
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        strengthBar = (ProgressBar) findViewById(R.id.strengthBar);
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

        passwordAddEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strengthBar.getProgressDrawable().setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strengthBar.setProgress(StrengthChecker.getStrength(passwordAddEdit.getText().toString()));

                if (strengthBar.getProgress() > 25)
                {
                    strengthBar.getProgressDrawable().setColorFilter(
                            Color.parseColor(ColorConstants.ORANGE), android.graphics.PorterDuff.Mode.SRC_IN);
                }

                if (strengthBar.getProgress() > 25)
                {
                    strengthBar.getProgressDrawable().setColorFilter(
                            Color.parseColor(ColorConstants.YELLOW), android.graphics.PorterDuff.Mode.SRC_IN);
                }

                if (strengthBar.getProgress() > 50)
                {
                    strengthBar.getProgressDrawable().setColorFilter(
                            Color.parseColor(ColorConstants.LIGHT_GREEN), android.graphics.PorterDuff.Mode.SRC_IN);
                }

                if (strengthBar.getProgress() > 75)
                {
                    strengthBar.getProgressDrawable().setColorFilter(
                            Color.parseColor(ColorConstants.GREEN), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
        });

    }

    public void addPassword(View view)
    {
        password = passwordAddEdit.getText().toString();
        username = usernameAddEdit.getText().toString();
        Integer randomID = RandomUtils.getRandomNumber(0, Constants.MAX_RANDOM_ID);

        if (groupsList.existsPasswordWithId(randomID))
        {
            randomID = RandomUtils.getRandomNumber(0, Constants.MAX_RANDOM_ID); // try to generate new random value
        }

        Password newPassword = new Password(username, password, selectedGroup, randomID);

        Group group = groupsList.getGroupByName(selectedGroup);
        PasswordList pwList = group.getPasswordList();
        pwList.add(newPassword);

        Intent intent = new Intent();
        intent.putExtra("groupsList", groupsList);

        setResult(RESULT_OK, intent);

        finish();

    }


}
