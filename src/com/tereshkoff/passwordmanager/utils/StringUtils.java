package com.tereshkoff.passwordmanager.utils;

public class StringUtils {

    public static String removeWhitespaces(String str)
    {
        return str.replaceAll("\\s+", "");
    }

}
