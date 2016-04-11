package com.tereshkoff.passwordmanager.dropbox;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.tereshkoff.passwordmanager.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadFile extends AsyncTask<Void, Void, Boolean> {

    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    private String filename;
    public AsyncResponse delegate = null;

    public DownloadFile(Context context, DropboxAPI<?> dropbox, String path, String filename)
    {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.path = path;
        this.filename = filename;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ Constants.PWDIRECTORY);

        FileOutputStream outputStream = null;
        try {
            File file = new File(direct+"/"+filename);

            outputStream = new FileOutputStream(file);
            DropboxAPI.DropboxFileInfo info = dropbox.getFile(path+filename, null, outputStream, null);
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
            Toast.makeText(context, "Failed to download file Something went wrong", Toast.LENGTH_LONG)
                    .show();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.processFinish(result);
        if (result) {
            Toast.makeText(context, "База успешно обновлена!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to download file", Toast.LENGTH_LONG)
                    .show();
        }
    }

}
