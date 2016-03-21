package com.tereshkoff.passwordmanager.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Password implements Serializable {

    private Group group;
    private String password;
    private String username;

    public Password(String username, String password)
    {
        this.password = password;
        this.username = username;
    }

    public Password(String password, Group group, String username)
    {
        this.password = password;
        this.group = group;
        this.username = username;
    }

    public Password() { }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
