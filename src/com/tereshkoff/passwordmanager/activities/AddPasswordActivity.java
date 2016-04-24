package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
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
import com.tereshkoff.passwordmanager.utils.InputValidation;
import com.tereshkoff.passwordmanager.utils.RandomUtils;

import java.util.List;

public class AddPasswordActivity extends Activity{

    private GroupsList groupsList;
    private EditText usernameAddEdit;
    private EditText passwordAddEdit;
    private Spinner spinner;
    private ProgressBar strengthBar;
    private EditText siteAddEdit;
    private EditText notesAddEdit;
    private EditText emailAddEdit;
    private TextView notesTextView;
    private TextView emailTextView;
    private TextView siteTextView;

    private String username;
    private String password;
    private String notes;
    private String site;
    private String email;
    private String selectedGroup;
    private String addToGroup;

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
        siteAddEdit = (EditText) findViewById(R.id.siteAddEdit);
        emailAddEdit = (EditText) findViewById(R.id.emailAddEdit);
        notesAddEdit = (EditText) findViewById(R.id.notesAddEdit);
        notesTextView = (TextView) findViewById(R.id.notesTextView);
        siteTextView = (TextView) findViewById(R.id.siteTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);

        Intent i = getIntent();
        groupsList = (GroupsList) i.getSerializableExtra("groupsList");
        addToGroup = i.getStringExtra("addToGroup");

        groupNames = groupsList.getGroupsNames();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<?> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setSelection(groupNames.indexOf(addToGroup));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                selectedGroup = groupNames.get(selectedItemPosition);

                hideElements(selectedItemPosition);

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

        emailAddEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!InputValidation.isEmailValid(emailAddEdit.getText().toString()))
                {
                    emailAddEdit.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
                else
                {
                    emailAddEdit.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

        siteAddEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!InputValidation.isUrlValid(siteAddEdit.getText().toString()))
                {
                    siteAddEdit.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
                else
                {
                    siteAddEdit.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

    }

    public void addPassword(View view)
    {
        password = passwordAddEdit.getText().toString();
        username = usernameAddEdit.getText().toString();
        email = emailAddEdit.getText().toString();
        site = siteAddEdit.getText().toString();
        notes = notesAddEdit.getText().toString();

        if (!InputValidation.isEmailValid(email))
        {
            Toast.makeText(this, "Проверьте правильность введенного Email", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!InputValidation.isUrlValid(site))
        {
            Toast.makeText(this, "Проверьте правильность ссылки на сайт", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer randomID = RandomUtils.getRandomNumber(0, Constants.MAX_RANDOM_ID);

        if (groupsList.existsPasswordWithId(randomID))
        {
            randomID = RandomUtils.getRandomNumber(0, Constants.MAX_RANDOM_ID); // try to generate new random value
        }

        Password newPassword = new Password(username, password, selectedGroup, randomID,
                                            site, email, notes);

        Group group = groupsList.getGroupByName(selectedGroup);
        PasswordList pwList = group.getPasswordList();
        pwList.add(newPassword);

        Intent intent = new Intent();
        intent.putExtra("groupsList", groupsList);

        setResult(RESULT_OK, intent);

        finish();

    }

    public void generateRandomPassword(View view)
    {
        passwordAddEdit.setText(RandomUtils.getRandomPassword(12));
        showPasswordCheckBox.setChecked(true);
        passwordAddEdit.setTransformationMethod(null);
        passwordAddEdit.setSelection(passwordAddEdit.getText().length());
    }

    private void hideElements(int selectedItemPosition)
    {
        switch(selectedItemPosition)
        {
            case 0: // Social
                siteAddEdit.setVisibility(View.VISIBLE);
                emailAddEdit.setVisibility(View.VISIBLE);
                siteTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                break;
            case 1: // Email
                siteAddEdit.setVisibility(View.VISIBLE);
                emailAddEdit.setVisibility(View.VISIBLE);
                siteTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                break;
            case 2: // WebSites
                siteAddEdit.setVisibility(View.VISIBLE);
                emailAddEdit.setVisibility(View.VISIBLE);
                siteTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                break;
            case 3: // PC
                siteAddEdit.setVisibility(View.GONE);
                emailAddEdit.setVisibility(View.GONE);
                siteTextView.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);
                break;
            case 4: // PIN-CODE
                siteAddEdit.setVisibility(View.GONE);
                emailAddEdit.setVisibility(View.GONE);
                siteTextView.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);

                break;
            case 5: // Wi-Fi
                siteAddEdit.setVisibility(View.GONE);
                emailAddEdit.setVisibility(View.GONE);
                siteTextView.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);
                break;
            case 6: // Other
                siteAddEdit.setVisibility(View.VISIBLE);
                emailAddEdit.setVisibility(View.VISIBLE);
                siteTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                break;
        }
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
