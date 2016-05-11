package com.tereshkoff.passwordmanager.navbar;

import android.app.Activity;
import android.content.Context;

public class ExitCommand implements ICommand {

    private Context context;

    public ExitCommand(Context context) {
        this.context = context;
    }

    public ExitCommand() {
    }

    @Override
    public void execute() {
        ((Activity) context).finish();
    }
}