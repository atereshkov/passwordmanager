package com.tereshkoff.passwordmanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.tereshkoff.passwordmanager.R;

public class Dialogs {

    public static void makeAlertDialog(Context ctx, String title, String message, String buttonText)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_launcher)
                .setCancelable(false)
                .setNegativeButton(buttonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
