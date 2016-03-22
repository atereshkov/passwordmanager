package com.tereshkoff.passwordmanager.json;

import android.os.Environment;
import android.util.Log;
import com.tereshkoff.passwordmanager.utils.Constants;
import com.tereshkoff.passwordmanager.models.Group;
import com.tereshkoff.passwordmanager.models.GroupsList;
import com.tereshkoff.passwordmanager.models.PasswordList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;

public class JsonFilesWorker {

    public static void createDefaultBase(String filename)
    {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ Constants.PWDIRECTORY);

        if(!direct.exists())
        {
            direct.mkdir();
        }

        File file = new File(direct, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("{\n" +
                    "  \"groups\": [\n" +
                    "  {\n" +
                    "    \"name\": \"Социальные сети\",\n" +
                    "    \"passwords\": [\n" +
                    "\t\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"Электронная почта\",\n" +
                    "    \"passwords\": [\n" +
                    "      {\n" +
                    "        \"username\": \"Weiss\",\n" +
                    "\t\t\"id\": 15192,\n" +
                    "        \"password\": \"sdfssd11312\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"username\": \"Williams\",\n" +
                    "\t\t\"id\": 51231,\n" +
                    "        \"password\": \"4e324234\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"Веб-сайты\",\n" +
                    "    \"passwords\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"Компьютер\",\n" +
                    "    \"passwords\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"PIN-коды\",\n" +
                    "    \"passwords\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"Wi-Fi\",\n" +
                    "    \"passwords\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"Прочее\",\n" +
                    "    \"passwords\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  }\n" +
                    "  ]\n" +
                    "}");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("Error", "******* File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static void saveToFile(String filename, GroupsList groupsList) throws IOException
    {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ Constants.PWDIRECTORY);
        File file = new File(direct, filename);

        JSONObject obj = new JSONObject();
        JSONArray groups = new JSONArray();

        for (Group gr : groupsList.getGroups())
        {
            PasswordList passwordList = gr.getPasswordList();

            JSONObject group = new JSONObject();
            JSONArray pwArray = new JSONArray();

            for (int j = 0; j < passwordList.getPasswordList().size(); j++)
            {
                JSONObject pw = new JSONObject();
                pw.put("username", passwordList.getPasswordList().get(j).getPassword());
                pw.put("password", passwordList.getPasswordList().get(j).getUsername());
                pw.put("id", passwordList.getPasswordList().get(j).getId());
                pwArray.add(pw);
            }

            group.put("passwords", pwArray);
            group.put("name", gr.getName());
            groups.add(group);

        }
        obj.put("groups", groups);

        //FileWriter fileWriter = new FileWriter(file);
        //fileWriter.write(obj.toJSONString());

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(obj.toString());
        bw.close();
    }

    public GroupsList getGroupsFromFile(String str)
    {
        GroupsList groupsList = new GroupsList();



        return groupsList;
    }

    public static String readFile(String directory, String filename)  //  /PWManager/
    {
        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath() + directory, filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                text.append(line);
                //Log.i("Test", "text : "+text+" : end");
                //text.append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

}
