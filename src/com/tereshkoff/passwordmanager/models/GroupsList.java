package com.tereshkoff.passwordmanager.models;

import java.util.ArrayList;
import java.util.List;

public class GroupsList {

    List<Group> groups = new ArrayList<Group>();

    public GroupsList(List<Group> groups) {
        this.groups = groups;
    }

    public GroupsList()
    {
        //read and parse groups from file
    }

    public void add(Group group)
    {
        groups.add(group);
    }

    public List<Group> getGroups()
    {
        return groups;
    }
}
