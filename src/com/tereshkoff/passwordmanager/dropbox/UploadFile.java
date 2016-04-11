package com.tereshkoff.passwordmanager.dropbox;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.tereshkoff.passwordmanager.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class UploadFile extends AsyncTask<Void, Void, Boolean> {

    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    private String filename;

    public UploadFile(Context context, DropboxAPI<?> dropbox,
                               String path, String filename) {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.path = path;
        this.filename = filename;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ Constants.PWDIRECTORY);
        File file = new File(direct, filename);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            dropbox.putFileOverwrite(path + filename, fileInputStream,
                    file.length(), null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "File Uploaded Sucesfully!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to upload file", Toast.LENGTH_LONG)
                    .show();
        }
    }
}