package com.tereshkoff.passwordmanager.json;

import com.tereshkoff.passwordmanager.AES.AES;
import com.tereshkoff.passwordmanager.AES.AESEncrypter;
import com.tereshkoff.passwordmanager.AES.StaticAES;
import com.tereshkoff.passwordmanager.AES.UtilsEncryption;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.Password;
import com.tereshkoff.passwordmanager.models.PasswordList;
import com.tereshkoff.passwordmanager.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static GroupsList getGroupsList(String str)
    {
        GroupsList groupsList = new GroupsList();

        //AES d = new AES();

        try {
                //String key = Constants.PW_KEY_AES;
                //AESEncrypter encrypter = new AESEncrypter(key);

                JSONObject jsonObject = new JSONObject(str);
                JSONArray groups = jsonObject.getJSONArray("groups");

                for (int i = 0; i < groups.length(); i++) {
                    JSONObject group = groups.getJSONObject(i);
                    JSONArray jsonPasswords = group.getJSONArray("passwords");

                    PasswordList passwordList = new PasswordList();

                    for (int z = 0; z < jsonPasswords.length(); z++) {
                        String groupName = group.getString("name");
                        JSONObject row = jsonPasswords.getJSONObject(z);
                        String username = row.getString("username");
                        //String password = StaticAES.encrypter.decrypt(row.getString("password"));
                        String password = UtilsEncryption.decrypt(row.getString("password"));
                        //String password = d.decrypt(row.getString("password"));
                        //String password = row.getString("password");
                        String site = row.getString("site");
                        String notes = row.getString("notes");
                        String email = row.getString("email");
                        Integer id = row.getInt("id");
                        passwordList.add(new Password(username, password, groupName, id,
                                site, email, notes));
                    }

                    groupsList.add(new Group(group.getString("name"), passwordList));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        return groupsList;
    }

}
