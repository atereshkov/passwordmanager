package com.tereshkoff.passwordmanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.tereshkoff.passwordmanager.AES.AES;
import com.tereshkoff.passwordmanager.AES.AESEncrypter;
import com.tereshkoff.passwordmanager.AES.StaticAES;
import com.tereshkoff.passwordmanager.activities.OneTabActivity;
import com.tereshkoff.passwordmanager.activities.TwoTabActivity;
import com.tereshkoff.passwordmanager.login.LoginActivity;
import com.tereshkoff.passwordmanager.utils.Dialogs;


public class MyActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {

            /*
            AES d = new AES();

            System.out.println("Encrypted string:" + d.encrypt("Hello"));
            String encryptedText = d.encrypt("Hello");
            System.out.println("Decrypted string:" + d.decrypt(encryptedText));

            String message = "MESSAGE";
            String password = "PASSWORD";

            AESEncrypter encrypter = new AESEncrypter(password);
            String encrypted = encrypter.encrypt(message);
            String decrypted = encrypter.decrypt(encrypted);

            System.out.println("Encrypt(\"" + message + "\", \"" + password + "\") = \"" + encrypted + "\"");
            System.out.println("Decrypt(\"" + encrypted + "\", \"" + password + "\") = \"" + decrypted + "\"");
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost); // https://code.google.com/p/android/issues/detail?id=78772
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Все пароли"),
                OneTabActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("По группам"),
                TwoTabActivity.class, null);

        //StaticAES aes = new StaticAES();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void onExitClick(MenuItem item) {
        finish();
    }

    public void onAboutClick(MenuItem item) throws PackageManager.NameNotFoundException {
        String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        Dialogs.makeAlertDialog(this, "О приложении",
                "Автор: Александр Терешков\n" +
                        "Версия: " +versionName+"",
                "Закрыть");
    }

}
