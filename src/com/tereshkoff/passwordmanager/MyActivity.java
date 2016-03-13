package com.tereshkoff.passwordmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.login.LoginActivity;
import com.tereshkoff.passwordmanager.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {

    private ListView listView1;
    private ImageButton floatButton;

    Button buttonOpenDialog;
    Button buttonUp;
    TextView textFolder;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;

    File root;
    File curFolder;

    private List<String> fileList = new ArrayList<String>();

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
                Toast.makeText(getApplicationContext(),
                        "Button is clicked", Toast.LENGTH_LONG).show();
            }
        });

        GroupsList groupsList = new GroupsList();
        groupsList.add(new Group("Kek"));
        groupsList.add(new Group("SKKS"));

        GroupAdapter groupAdapter = new GroupAdapter(this, android.R.layout.simple_list_item_1, groupsList.getGroups());

        listView1.setAdapter(groupAdapter);

        buttonOpenDialog = (Button) findViewById(R.id.opendialog);
        buttonOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
            }
        });

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;


    }


    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(MyActivity.this);
                dialog.setContentView(R.layout.dialog_activity);
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()) {
                            ListDir(selected);
                        } else {
                            Toast.makeText(MyActivity.this, selected.toString() + " selected",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });

                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    void ListDir(File f) {
        if(f.equals(root)) {
            buttonUp.setEnabled(false);
        } else {
            buttonUp.setEnabled(true);
        }

        curFolder = f;
        //textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();

        for(File file : files) {
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
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
