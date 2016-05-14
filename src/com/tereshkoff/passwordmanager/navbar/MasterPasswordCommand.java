package com.tereshkoff.passwordmanager.navbar;

import android.content.Context;
import android.content.Intent;
import com.tereshkoff.passwordmanager.activities.MasterPasswordActivity;

public class MasterPasswordCommand implements ICommand {

    private Context context;

    public MasterPasswordCommand(Context context) {
        this.context = context;
    }

    public MasterPasswordCommand() {
    }

    @Override
    public void execute() {
        Intent intent = new Intent(context, MasterPasswordActivity.class);
        context.startActivity(intent);
    }
}