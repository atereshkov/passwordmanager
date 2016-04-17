package com.tereshkoff.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.tereshkoff.passwordmanager.AES.AES;
import com.tereshkoff.passwordmanager.AES.AESEncrypter;
import com.tereshkoff.passwordmanager.AES.StaticAES;
import com.tereshkoff.passwordmanager.activities.*;
import com.tereshkoff.passwordmanager.login.LoginActivity;
import com.tereshkoff.passwordmanager.utils.Dialogs;


public class MyActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        showLoginActivity();

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

    public void openSettingsActivity(MenuItem item){
        Intent intent = new Intent(this, PrefActivity.class);
        startActivity(intent);
    }

    public void onExitClick(MenuItem item) {
        finish();
    }

    public void onAboutClick(MenuItem item) throws PackageManager.NameNotFoundException {
        String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        Dialogs.makeAlertDialog(this, "О приложении",
                "Менеджер паролей HPassword\n" +
                "Автор: Александр Терешков\n" +
                        "Версия: " +versionName+"",
                "Закрыть");
    }

    public void openSyncSettingsActivity(MenuItem item) {

        Intent intent = new Intent(this, SyncSettings.class);
        startActivity(intent);

    }

    public void showLoginActivity()
    {
        // SHOW FIRSTLOGIN ACTIVITY IF NOT VISITED BEFORE
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean hasVisited = preferences.getBoolean("hasVisited", false);

        if (!hasVisited) // if first one
        {
            Intent intent = new Intent(this, FirstLoginActivity.class);
            startActivity(intent);
        }
        else // if NOT first one visited
        {
            Boolean masterpw = false;
            if (sp != null)
            {
                masterpw = sp.getBoolean("rememberMasterPassword", false);
            }
            else
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

            if (!masterpw)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }

        //SharedPreferences.Editor e = preferences.edit(); // for test
        //e.putBoolean("hasVisited", false);
        //e.commit();

    }

}
