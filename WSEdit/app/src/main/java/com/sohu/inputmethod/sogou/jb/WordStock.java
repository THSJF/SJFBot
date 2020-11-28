package com.sohu.inputmethod.sogou.jb;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TreeMap;

public class WordStock {
    @SerializedName("w") public TreeMap<String,ArrayList<Entry>> words = new TreeMap<>();

    public void save() throws IOException {
        {
            File jsonFile = new File("/storage/emulated/0/AppProjects/sanae_data/persistent/dynamic_word_stock.json");
            FileOutputStream fos = new FileOutputStream(jsonFile);
            fos.write(formatJson(new Gson().toJson(this)).getBytes(StandardCharsets.UTF_8));
            fos.close();
        }
        {
            File jsonFile = new File("/storage/emulated/0/AppProjects/sanae_data/backup/dynamic_word_stock" + (System.currentTimeMillis() / 1000 / 60) + ".json");
            FileOutputStream fos = new FileOutputStream(jsonFile);
            fos.write(formatJson(new Gson().toJson(this)).getBytes(StandardCharsets.UTF_8));
            fos.close();        
        } 
    }

    public static WordStock read() throws IOException {
        File jsonFile = new File("/storage/emulated/0/AppProjects/sanae_data/persistent/dynamic_word_stock.json");
        FileInputStream fis = new FileInputStream(jsonFile);
        byte[] bytes = new byte[(int)jsonFile.length()];
        fis.read(bytes);
        return new Gson().fromJson(new String(bytes, StandardCharsets.UTF_8), WordStock.class);
    }

    public static String formatJson(String content) {
        if (content == null) {
            return "{}";
        }
        if (content != null) {
            return content;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int count = 0;
        while (index < content.length()) {
            char ch = content.charAt(index);
            if (ch == '{' || ch == '[') {
                sb.append(ch);
                sb.append('\n');
                count++;
                for (int i = 0; i < count; i++) {                   
                    sb.append('\t');
                }
            } else if (ch == '}' || ch == ']') {
                sb.append('\n');
                count--;
                for (int i = 0; i < count; i++) {                   
                    sb.append('\t');
                }
                sb.append(ch);
            } else if (ch == ',') {
                sb.append(ch);
                sb.append('\n');
                for (int i = 0; i < count; i++) {                   
                    sb.append('\t');
                }
            } else {
                sb.append(ch);              
            }
            index++;
        }
        return sb.toString();
	}
}
