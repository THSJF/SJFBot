package com.sohu.inputmethod.sogou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.sohu.inputmethod.ExceptionCatcher;
import com.sohu.inputmethod.sogou.jb.Entry;
import com.sohu.inputmethod.sogou.jb.WordStock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends Activity { 

    public static WordStock wordStork = new WordStock();
    public static MainActivity instance;
    public MainAdapter wsa;
    public ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ExceptionCatcher.getInstance().init();
        setContentView(R.layout.activity_main);
        try {
            wordStork = new Gson().fromJson(read(), WordStock.class);
        } catch (Exception e) {

        }
        wsa = new MainAdapter();
        lv = findViewById(R.id.activity_mainListView);
        lv.setAdapter(wsa);
        lv.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                    Intent i = new Intent(MainActivity.this, EntryActivity.class);
                    i.putExtra("key", wsa.getItem(p3));
                    startActivity(i);
                }
            });

        lv.setOnItemLongClickListener(new OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除")
                        .setMessage("确定删除" + wordStork.w.get(wsa.getItem(p3)) + "吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wordStork.w.remove(wsa.getItem(p3));
                                update();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                    dialog.show();
                    return true;
                }
            });  
        findViewById(R.id.activity_main_Button1).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    final EditText et = new EditText(MainActivity.this);
                    new AlertDialog.Builder(MainActivity.this)
                        .setTitle("添加")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wordStork.w.put(et.getText().toString(), new ArrayList<Entry>());
                                update();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                }
            });
    }

    private void update() {
        try {
            MainActivity.save();
        } catch (IOException e) {}
        wsa.notifyDataSetChanged();
    }

    public static void save() throws IOException {
        {
            File jsonFile = new File("/storage/emulated/0/AppProjects/SJFBot/word_stock.json");
            FileOutputStream fos = new FileOutputStream(jsonFile);
            fos.write(formatJson(new Gson().toJson(wordStork)).getBytes(StandardCharsets.UTF_8));
            fos.close();
        }
        {
            File jsonFile = new File("/storage/emulated/0/AppProjects/SJFBot/backup/word_stock" + System.currentTimeMillis() + ".json");
            FileOutputStream fos = new FileOutputStream(jsonFile);
            fos.write(formatJson(new Gson().toJson(wordStork)).getBytes(StandardCharsets.UTF_8));
            fos.close();        
        }
    }

    public static String read() throws IOException {
        File jsonFile = new File("/storage/emulated/0/AppProjects/SJFBot/word_stock.json");
        FileInputStream fis = new FileInputStream(jsonFile);
        byte[] bytes = new byte[(int)jsonFile.length()];
        fis.read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String formatJson(String content) {
        if (content == null) {
            return "{}";
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

    @Override
    protected void onResume() {
        super.onResume();
        wsa.notifyDataSetChanged();
    }
} 
