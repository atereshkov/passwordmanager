package com.tereshkoff.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.login.LoginActivity;
import com.tereshkoff.passwordmanager.models.*;


public class MyActivity extends Activity {

    private ListView listView1;
    private ImageButton floatButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        listView1 = (ListView) findViewById(R.id.listView1);

        try {
           /* AES d = new AES();

            System.out.println("Encrypted string:" + d.encrypt("Hello"));
            String encryptedText = d.encrypt("Hello");
            System.out.println("Decrypted string:" + d.decrypt(encryptedText));

            textView1.setText("Encrypted string:" + encryptedText);
            textView2.setText("Decrypted string:" + d.decrypt(encryptedText));*/

            /*String message = "MESSAGE";
            String password = "PASSWORD";

            AESEncrypter encrypter = new AESEncrypter(password);
            String encrypted = encrypter.encrypt(message);
            String decrypted = encrypter.decrypt(encrypted);

            System.out.println("Encrypt(\"" + message + "\", \"" + password + "\") = \"" + encrypted + "\"");
            System.out.println("Decrypt(\"" + encrypted + "\", \"" + password + "\") = \"" + decrypted + "\"");

            textView1.setText("Encrypt(\"" + message + "\", \"" + password + "\") = \"" + encrypted + "\"");
            textView2.setText("Decrypt(\"" + encrypted + "\", \"" + password + "\") = \"" + decrypted + "\"");*/

        }
        catch (Exception e)
        {

        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),
                        JsonFilesWorker.readFile("/PWManager/", "database.json"), Toast.LENGTH_LONG).show();
            }
        });

        JsonFilesWorker.createFile("database.json");

        GroupsList groupsList = JsonParser.getGroupsList(JsonFilesWorker.readFile("/PWManager/", "database.json"));
        //groupsList.add(new Group("Kek"));
        //groupsList.add(new Group("SKKS"));

        GroupAdapter groupAdapter = new GroupAdapter(this, android.R.layout.simple_list_item_1, groupsList.getGroups());

        listView1.setAdapter(groupAdapter);
    }

    // (Environment.getExternalStorageDirectory().getAbsolutePath());

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void onExitClick(MenuItem item){
        finish();
    }

}
