package com.tereshkoff.passwordmanager.navbar;

import android.content.Context;
import android.content.pm.PackageManager;
import com.tereshkoff.passwordmanager.utils.Dialogs;

public class AboutCommand implements ICommand {

    private Context context;

    public AboutCommand(Context context) {
        this.context = context;
    }

    public AboutCommand() {
    }

    @Override
    public void execute() {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Dialogs.makeAlertDialog(context, "О приложении",
                "Менеджер паролей HPassword\n" +
                        "Автор: Александр Терешков\n" +
                        "Версия: " +versionName+"",
                "Закрыть");
    }
}