package com.tereshkoff.passwordmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MyActivity extends Activity {

    private TextView textView1;
    private TextView textView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        try {
            AES d = new AES();

            System.out.println("Encrypted string:" + d.encrypt("Hello"));
            String encryptedText = d.encrypt("Hello");
            System.out.println("Decrypted string:" + d.decrypt(encryptedText));

            textView1.setText("Encrypted string:" + d.encrypt("Hello"));
            textView2.setText("Decrypted string:" + d.decrypt(encryptedText));
        }
        catch (Exception e)
        {

        }


    }
}
