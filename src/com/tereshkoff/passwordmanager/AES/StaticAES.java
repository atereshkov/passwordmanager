package com.tereshkoff.passwordmanager.AES;

public class StaticAES {

    public static AESEncrypter encrypter;

    public StaticAES()
    {
        try {
            encrypter = new AESEncrypter("PWMANAGERSUPERPASSWORD");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static AESEncrypter getEncrypter() {
        return encrypter;
    }
}
