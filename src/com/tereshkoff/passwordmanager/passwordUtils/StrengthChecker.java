package com.tereshkoff.passwordmanager.passwordUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrengthChecker {

    public static Integer getStrength(String password)
    {
        int score = 0;
        Map<String, Integer> patterns = new HashMap<String, Integer>();

        patterns.put("\\d", 4);
        patterns.put("[a-zA-Z]", 9);
        patterns.put("[!,@,#,$,%,^,&,*,?,_,~]", 13);

        for (Map.Entry entry : patterns.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }

        for (Map.Entry entry : patterns.entrySet())
        {
            Pattern p = Pattern.compile(entry.getKey().toString());
            Matcher m = p.matcher(password);

            int count = 0;
            while (m.find())
                count++;

            score += ((count * (int)entry.getValue())*0.66);
        }

        return score;
    }

}
