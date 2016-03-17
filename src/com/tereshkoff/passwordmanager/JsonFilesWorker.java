package com.tereshkoff.passwordmanager;

import android.os.Environment;
import android.util.Log;
import com.tereshkoff.passwordmanager.models.GroupsList;
import java.io.*;
import java.text.ParseException;

public class JsonFilesWorker {

    public static void createFile(String filename)
    {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/PWManager/");

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
                    "    \"name\": \"FirstGroup\",\n" +
                    "    \"passwords\": [\n" +
                    "      {\n" +
                    "        \"username\": \"Campbell\",\n" +
                    "        \"password\": \"ZXFasdfsd\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"username\": \"Hall\",\n" +
                    "        \"password\": \"2134fZXFas\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"username\": \"Valentine\",\n" +
                    "        \"password\": \"4643fZx11\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"SecondGroup\",\n" +
                    "    \"passwords\": [\n" +
                    "      {\n" +
                    "        \"username\": \"Marshall\",\n" +
                    "        \"password\": \"2314532ZX\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"username\": \"Weiss\",\n" +
                    "        \"password\": \"sdfssd11312\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"username\": \"Williams\",\n" +
                    "        \"password\": \"4e324234\"\n" +
                    "      }\n" +
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
