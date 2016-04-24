package com.tereshkoff.passwordmanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean isEmailValid (String emailAddress)
    {
        boolean isEmail = false;

        String emailPattern2 = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" +
                "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." +
                "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"+
                "([a-zA-Z0-9]+[\\w-]+\\.)+[a-zA-Z]{1}[a-zA-Z0-9-]{1,23})$";

        Pattern r = Pattern.compile(emailPattern2);
        Matcher m = r.matcher(emailAddress);

        if (m.find())
        {
            isEmail = true;
        }

        if (emailAddress.isEmpty())
        {
            return true;
        }

        return isEmail;
    }

    public static boolean isUrlValid (String URL)
    {
        boolean isUrl = false;

        String URLPattern = "(([\\w]+:)?//)?(([\\d\\w]|%[a-fA-f\\d]{2,2})+(:([\\d\\w]|%[a-fA-f\\d]{2,2})+)?@)?([\\d\\w][-\\d\\w]{0,253}[\\d\\w]\\.)+[\\w]{2,63}(:[\\d]+)?(/([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)*(\\?(&?([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})=?)*)?(#([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)?";

        Pattern r = Pattern.compile(URLPattern);
        Matcher m = r.matcher(URL);

        if (m.find())
        {
            isUrl = true;
        }

        if (URL.isEmpty())
        {
            return true;
        }

        return isUrl;
    }

}
