package com.tereshkoff.passwordmanager.utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {

    public static Integer getRandomNumber(Integer min, Integer max)
    {
        Random rn = new Random();

        return (rn.nextInt(max - min + 1) + min);
    }

    public static String getRandomPassword(Integer length)
    {
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
        {
            sb.append(Constants.RANDOM_PW_SYMBOLS.charAt(rnd.nextInt(Constants.RANDOM_PW_SYMBOLS.length())));
        }

        return sb.toString();
    }

}
