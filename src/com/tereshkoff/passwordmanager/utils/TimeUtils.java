package com.tereshkoff.passwordmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    public static String getCurrentTimeInString()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }

}
