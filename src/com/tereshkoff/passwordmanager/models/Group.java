package com.tereshkoff.passwordmanager.models;

public class Group {

    private String name;
    private PasswordList passwordList = new PasswordList();

    public Group(String name) {
        this.name = name;
    }

    public Group() { }

    public Group(String name, PasswordList passwordList)
    {
        this.name = name;
        this.passwordList = passwordList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public PasswordList getPasswordList() {
        return passwordList;
    }
}
