package com.tereshkoff.passwordmanager.models;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@SuppressWarnings("serial")
public class GroupsList implements Serializable {

    List<Group> groups = new ArrayList<Group>();

    public GroupsList(List<Group> groups) {
        this.groups = groups;
    }

    public GroupsList()
    {
        //read and parse groups from file
    }

    public Group getGroupByName(String name)
    {
        Group outGroup = new Group();

        for(Group group : groups)
        {
            if (group.getName().equals(name))
                outGroup = group;
        }

        return outGroup;
    }

    public void add(Group group)
    {
        groups.add(group);
    }

    public List<Group> getGroups()
    {
        return groups;
    }

    public void save()
    {

    }

    public PasswordList getAllPasswords()
    {
        PasswordList passwordList = new PasswordList();

        for(Group group : groups)
        {
            for(Password password : group.getPasswordList().getPasswordList())
            {
                passwordList.add(password);
            }
        }

        return passwordList;
    }

    public List<String> getGroupsNames()
    {
        List<String> names = new ArrayList<String>();

        for(Group group : groups)
        {
            names.add(group.getName());
        }

        return names;
    }

    public boolean existsPasswordWithId(Integer id)
    {
        PasswordList allPasswords = getAllPasswords();

        for(Password pw : allPasswords.getPasswordList())
        {
            if (pw.getId() == id)
                return true;
        }

        return false;
    }

    public void editPassword(String group, PasswordList passwordList, Password inputPassword)
    {
        for (Password pw : passwordList.getPasswordList())
        {
            if (pw.getId().equals(inputPassword.getId()))
            {
                //pw = inputPassword;
                passwordList.remove(pw);
                break;
            }
        }

        for (Group checkGroup : groups)
        {
            if (checkGroup.getName().equals(group))
            {
                PasswordList passwordList1 = checkGroup.getPasswordList();
                passwordList1.add(inputPassword);
            }
        }
    }

    public void removePassword(Password toRemove)
    {
        for (Group group : groups)
        {
            for(Password password : group.getPasswordList().getPasswordList())
            {
                if (password.getId().equals(toRemove.getId()))
                {
                    group.getPasswordList().getPasswordList().remove(password);
                    break;
                }
            }
        }
    }

}
