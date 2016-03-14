package com.tereshkoff.passwordmanager;

import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.models.PasswordList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static GroupsList getGroupsList(String str)
    {
        GroupsList groupsList = new GroupsList();
        try {

            JSONObject jsonObject = new JSONObject(str);
            JSONArray groups = jsonObject.getJSONArray("groups");

            for (int i = 0; i < groups.length(); i++)
            {
                JSONObject group = groups.getJSONObject(i);
                JSONArray jsonPasswords = group.getJSONArray("passwords");

                PasswordList passwordList = new PasswordList();

                for (int z = 0; z < jsonPasswords.length(); z++)
                {
                    JSONObject row = jsonPasswords.getJSONObject(z);
                    String username = row.getString("username");
                    String password = row.getString("password");
                    passwordList.add(new Password(username, password));
                }

                groupsList.add(new Group(group.getString("name"), passwordList));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return groupsList;
    }

}
