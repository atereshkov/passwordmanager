package com.tereshkoff.passwordmanager.navbar;

import android.content.Context;
import android.content.Intent;
import com.tereshkoff.passwordmanager.activities.SyncSettings;

public class SyncCommand implements ICommand {

    private Context context;

    public SyncCommand(Context context) {
        this.context = context;
    }

    public SyncCommand() {
    }

    @Override
    public void execute() {
        Intent intent = new Intent(context, SyncSettings.class);
        context.startActivity(intent);
    }
}
