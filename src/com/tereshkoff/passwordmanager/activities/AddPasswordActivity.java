package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.models.GroupsList;

public class AddPasswordActivity extends Activity{

    private GroupsList groupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpassword_activity);

        Intent i = getIntent();
        groupsList = (GroupsList) i.getSerializableExtra("groupsList");

    }
}
