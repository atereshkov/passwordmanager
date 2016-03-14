package com.tereshkoff.passwordmanager.models;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.tereshkoff.passwordmanager.R;

public class PasswordActivity extends Activity {

    private ListView pwListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);

        pwListView = (ListView) findViewById(R.id.pwListView);

        //PasswordAdapter passwordAdapter = new PasswordAdapter(this, android.R.layout.simple_list_item_1, null);
        //pwListView.setAdapter(passwordAdapter);

    }
}
