package com.tereshkoff.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.tereshkoff.passwordmanager.login.LoginActivity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

public class MyActivity extends Activity {

    private TextView textView1;
    private TextView textView2;
    private ListView listView1;
    private ImageButton floatButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        floatButton = (ImageButton) findViewById(R.id.imageButton);

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
                Toast.makeText(getApplicationContext(),
                        "Button is clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void onExitClick(MenuItem item){
        finish();
    }

}
