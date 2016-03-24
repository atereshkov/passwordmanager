package com.tereshkoff.passwordmanager.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PasswordList implements Serializable {

    private List<Password> passwordList = new ArrayList<Password>();
    private String name;
    //private Integer count;

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

    public Integer getCount() {
        return passwordList.size();
    }

    public void remove(Password password)
    {
        if (passwordList.contains(password))
        {
            passwordList.remove(password);
        }
    }

    public Password getPasswordById(Integer id)
    {
        for (Password pw : passwordList)
        {
            if (pw.getId() == id)
            {
                return pw;
            }
        }

        return null;
    }

    public Password getPasswordById(String id)
    {
        int intId = Integer.parseInt(id);
        for (Password pw : passwordList)
        {
            if (pw.getId() == intId)
            {
                return pw;
            }
        }

        return null;
    }

    public void edit(Password inputPassword)
    {
        for (Password pw : passwordList)
        {
            if (pw.getId().equals(inputPassword.getId()))
            {
                pw = inputPassword;
                int k = 3;
                break;
            }
        }
    }

}
