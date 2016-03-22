package com.tereshkoff.passwordmanager.utils;

import java.util.Random;

public class RandomUtils {

    public static Integer getRandomNumber(Integer min, Integer max)
    {
        Random rn = new Random();

        return (rn.nextInt(max - min + 1) + min);
    }

}
