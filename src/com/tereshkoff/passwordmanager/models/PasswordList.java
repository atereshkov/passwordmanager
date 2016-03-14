package com.tereshkoff.passwordmanager.models;

import java.util.ArrayList;
import java.util.List;

public class PasswordList {

    private List<Password> passwordList = new ArrayList<Password>();
    private String name;

    public PasswordList()
    {

    }

    public void add(Password password)
    {
        passwordList.add(password);
    }

    public PasswordList(String name, List<Password> passwordList)
    {
        this.passwordList = passwordList;
        this.name = name;
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public String getName() {
        return name;
    }
}
