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
import android.widget.ListView;
import com.sohu.inputmethod.sogou.jb.Entry;
import java.io.IOException;
import java.util.ArrayList;

public class EntryActivity extends Activity {
    public ListView lv;
    public EntryAdapter ea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String key = getIntent().getStringExtra("key");
        if (key == null) {
            finish();
        }
        final ArrayList<Entry> entrys = MainActivity.instance.wordStork.w.get(key);
        ea = new EntryAdapter(entrys);
        lv = (ListView) findViewById(R.id.activity_mainListView);
        lv.setAdapter(ea);
        lv.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                    Intent i = new Intent(EntryActivity.this, NodeActivity.class);
                    i.putExtra("key", key);
                    i.putExtra("pos", p3);
                    startActivity(i);
                }
            });
        lv.setOnItemLongClickListener(new OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    new AlertDialog.Builder(EntryActivity.this)
                        .setTitle("删除")
                        .setMessage("确定删除" + entrys.get(p3) + "吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                entrys.remove(p3);
                                update();
                            }
                        }).setNegativeButton("取消", null).show();
                    return true;
                }
            });   
        findViewById(R.id.activity_main_Button1).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    entrys.add(new Entry());
                    update();
                }
            });

    }

    private void update() {
        try {
            MainActivity.save();
        } catch (IOException e) {}
        ea.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ea.notifyDataSetChanged();
    }
}
