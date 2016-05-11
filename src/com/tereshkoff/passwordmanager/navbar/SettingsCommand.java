package com.tereshkoff.passwordmanager.navbar;

import android.content.Context;
import android.content.Intent;
import com.tereshkoff.passwordmanager.activities.PrefActivity;
import com.tereshkoff.passwordmanager.activities.SyncSettings;

public class SettingsCommand implements ICommand {

    private Context context;

    public SettingsCommand(Context context) {
        this.context = context;
    }

    public SettingsCommand() {
    }

    @Override
    public void execute() {
        Intent intent = new Intent(context, PrefActivity.class);
        context.startActivity(intent);
    }
}