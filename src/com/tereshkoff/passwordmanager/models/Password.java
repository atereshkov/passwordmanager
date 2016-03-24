package com.tereshkoff.passwordmanager.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Password implements Serializable {

    private String groupName;
    private String password;
    private String username;
    private Integer id;

    public Password(String username, String password)
    {
        this.password = password;
        this.username = username;
    }

    public Password(String username, String password, String groupName, Integer id)
    {
        this.password = password;
        this.groupName = groupName;
        this.username = username;
        this.id = id;
    }

    public Password() { }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public String getGroupName() {
        return groupName;
    }
}
