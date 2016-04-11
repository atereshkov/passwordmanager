package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.dropbox.AsyncResponse;
import com.tereshkoff.passwordmanager.dropbox.DownloadFile;
import com.tereshkoff.passwordmanager.dropbox.ListFiles;
import com.tereshkoff.passwordmanager.dropbox.UploadFile;
import com.tereshkoff.passwordmanager.utils.Constants;
import com.tereshkoff.passwordmanager.utils.TimeUtils;

import java.util.ArrayList;

public class SyncSettings extends Activity implements AsyncResponse {

    private LinearLayout container;
    private DropboxAPI<AndroidAuthSession> dropboxApi;
    private boolean isUserLoggedIn;
    private Button loginBtn;
    private Button downloadButton;
    private Button uploadButton;
    private TextView isAuthView;
    private TextView lastSync;

    private final static String DROPBOX_FILE_DIR = "/HPassword/";
    private final static String DROPBOX_NAME = "dropbox_prefs";
    private final static String ACCESS_KEY = "9w8m73yj04vho5l";
    private final static String ACCESS_SECRET = "xtlw9ot6sgse4kg";
    private final static Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncsettings_activity);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        isAuthView = (TextView) findViewById(R.id.isAuthView);
        container = (LinearLayout) findViewById(R.id.container_files);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        lastSync = (TextView) findViewById(R.id.lastSync);

        //noinspection ConstantConditions
        getActionBar().setDisplayHomeAsUpEnabled(true);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUserLoggedIn)
                {
                    //dropboxApi.getSession().unlink();
                    //loggedIn(false);
                } else
                {
                    dropboxApi.getSession().startAuthentication(SyncSettings.this);
                    //dropboxApi.getSession().startOAuth2Authentication(MyActivity.this);
                }

            }
        });

        AppKeyPair appKeyPair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);

        AndroidAuthSession session;
        prefs = getSharedPreferences(DROPBOX_NAME, 0);
        String key = prefs.getString(ACCESS_KEY, null);
        String secret = prefs.getString(ACCESS_SECRET, null);

        if (key != null && secret != null) {
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, token);
            isAuthView.setText("Авторизация успешна.");
            lastSync.setText("Последняя синхронизация: " + prefs.getString("lastSync", null));
            loggedIn(true);
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
            isAuthView.setText("Авторизуйтесь, нажав на кнопку.");
            lastSync.setText("Последняя синхронизация: " + prefs.getString("lastSync", null));
            loggedIn(false);
        }
        dropboxApi = new DropboxAPI<AndroidAuthSession>(session);


    }

    public void Files()
    {
        ListFiles listFiles = new ListFiles(dropboxApi, DROPBOX_FILE_DIR, handler);
        listFiles.execute();
    }

    public void Upload(View view)
    {
        UploadFile uploadFile = new UploadFile(this, dropboxApi, DROPBOX_FILE_DIR, Constants.DAFAULT_DBFILE_NAME);
        uploadFile.delegate = this;
        uploadFile.execute();
    }

    public void Download(View view)
    {
        DownloadFile downloadFile = new DownloadFile(this, dropboxApi, DROPBOX_FILE_DIR, Constants.DAFAULT_DBFILE_NAME);
        downloadFile.delegate = this;
        downloadFile.execute();
    }

    public void processFinish(Boolean output){
        if (output)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastSync", TimeUtils.getCurrentTimeInString());
            editor.commit();
            lastSync.setText("Последняя синхронизация: " + prefs.getString("lastSync", null));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AndroidAuthSession session = dropboxApi.getSession();
        if (dropboxApi.getSession().authenticationSuccessful()) {
            try {
                dropboxApi.getSession().finishAuthentication();
                TokenPair tokens = dropboxApi.getSession().getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ACCESS_KEY, tokens.key);
                editor.putString(ACCESS_SECRET, tokens.secret);
                editor.commit();

                String accessToken = dropboxApi.getSession().getOAuth2AccessToken();

                loggedIn(true);
            } catch (IllegalStateException e) {
                Toast.makeText(this, "Error during Dropbox authentication",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            ArrayList<String> result = message.getData().getStringArrayList("data");

            for (String fileName : result)
            {
                TextView textView = new TextView(SyncSettings.this);
                textView.setText(fileName);
                container.addView(textView);
            }
        }
    };


    public void loggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
        loginBtn.setEnabled(!userLoggedIn);
        downloadButton.setEnabled(userLoggedIn);
        uploadButton.setEnabled(userLoggedIn);
    }

    public boolean isDropboxLinked() {
        return dropboxApi != null && (dropboxApi.getSession().isLinked() || dropboxApi.getSession().authenticationSuccessful());
    }

}
