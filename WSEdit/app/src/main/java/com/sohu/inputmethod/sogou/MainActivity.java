package com.sohu.inputmethod.sogou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.sohu.inputmethod.ExceptionCatcher;
import com.sohu.inputmethod.sogou.adapters.MainListAdapter;
import com.sohu.inputmethod.sogou.jb.Entry;
import com.sohu.inputmethod.sogou.jb.WordStock;
import java.io.IOException;
import java.util.ArrayList;
import com.leon.lfilepickerlibrary.utils.StringUtils;

public class MainActivity extends Activity { 

    public WordStock wordstock = new WordStock();
    public static MainActivity instance;
    public ExpandableListView lv;
    public MainListAdapter mla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ExceptionCatcher.getInstance().init();
        setContentView(R.layout.activity_main);
        SharedPreferenceHelper.init(this, "sjf");
        if (StringUtils.isEmpty(SharedPreferenceHelper.getValue("lastpath"))) {
            SharedPreferenceHelper.putValue("lastpath", "/storage/emulated/0/");
        }
        try {
            wordstock = WordStock.read();
        } catch (IOException e) {}
        mla = new MainListAdapter(wordstock);
        lv = findViewById(R.id.activity_mainListView);
        lv.setAdapter(mla);
        lv.setOnChildClickListener(new OnChildClickListener(){

                @Override
                public boolean onChildClick(ExpandableListView p1, View p2, final int groupP, final int childP, long p5) {

                    new AlertDialog.Builder(MainActivity.instance)
                        .setTitle("编辑")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(MainActivity.instance)
                                    .setTitle("删除")
                                    .setMessage("确定删除" + mla.getChild(groupP, childP).getCharSequence() + "吗")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            wordstock.words.get(mla.getGroup(groupP)).remove(childP);
                                            MainActivity.instance.update();
                                        }
                                    }).setNegativeButton("取消", null).show();
                            }
                        })
                        .setNegativeButton("修改", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2) {
                                Intent i = new Intent(MainActivity.this, NodeActivity.class);
                                i.putExtra("key", mla.getGroup(groupP));
                                i.putExtra("pos", childP);
                                startActivity(i);
                            }
                        }).show();


                    return false;
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
                                wordstock.words.put(et.getText().toString(), new ArrayList<Entry>());
                                update();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                }
            });
    }

    public void update() {
        try {
            wordstock.save();
        } catch (IOException e) {}
        mla.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mla.notifyDataSetChanged();
    }
} 
