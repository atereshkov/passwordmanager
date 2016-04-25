package com.tereshkoff.passwordmanager.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Password implements Serializable {

    private String groupName;
    private String password;
    private String username;
    private String notes;
    private String site;
    private String email;
    private Integer id;
    private Integer iconID;

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

    public Password(String username, String password, String groupName, Integer id,
                    String site, String email, String notes, int iconID)
    {
        this.password = password;
        this.groupName = groupName;
        this.username = username;
        this.id = id;
        this.email = email;
        this.site = site;
        this.notes = notes;
        this.iconID = iconID;
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

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }

    public String getSite() {
        return site;
    }

    public Integer getIconID() {
        return iconID;
    }
}
