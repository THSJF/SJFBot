package com.sohu.inputmethod.sogou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.sohu.inputmethod.ExceptionCatcher;
import com.sohu.inputmethod.sogou.R;
import com.sohu.inputmethod.sogou.adapters.NodeAdapter;
import com.sohu.inputmethod.sogou.jb.Entry;
import com.sohu.inputmethod.sogou.jb.ItemType;
import com.sohu.inputmethod.sogou.jb.Node;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class NodeActivity extends Activity {

    private ListView lv;
    private NodeAdapter na;
    private ArrayList<Node> nodes;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_editor);
        lv = (ListView) findViewById(R.id.node_editorListView);
        int pos = getIntent().getIntExtra("pos", -1);
        String key = getIntent().getStringExtra("key");
        if (pos == -1 || key == null) {
            finish();
        }
        entry = MainActivity.instance.wordstock.words.get(key).get(pos);
        nodes = entry.entryList;
        na = new NodeAdapter(nodes);
        lv.setAdapter(na);

        RadioButton rbNormal = (RadioButton)findViewById(R.id.node_editor_RadioButton_normal);
        RadioButton rbAt = (RadioButton)findViewById(R.id.node_editor_RadioButton_at);
        RadioButton rbQuote = (RadioButton)findViewById(R.id.node_editor_RadioButton_quote);

        rbAt.setChecked(entry.isFlag(Entry.AT));
        rbQuote.setChecked(entry.isFlag(Entry.QUOTE));
        rbNormal.setChecked(entry.isFlag(Entry.NORMAL));

        rbNormal.setOnCheckedChangeListener(onCheckedChange);
        rbAt.setOnCheckedChangeListener(onCheckedChange);
        rbQuote.setOnCheckedChangeListener(onCheckedChange);

        CheckBox cbe = (CheckBox)findViewById(R.id.node_editor_CheckBox_easy);
        CheckBox cbn = (CheckBox)findViewById(R.id.node_editor_CheckBox_normal);
        CheckBox cbh = (CheckBox)findViewById(R.id.node_editor_CheckBox_hard);
        CheckBox cbl = (CheckBox)findViewById(R.id.node_editor_CheckBox_lunatic);
        CheckBox cbx = (CheckBox)findViewById(R.id.node_editor_CheckBox_extra);
        CheckBox cbRecall = (CheckBox)findViewById(R.id.node_editor_CheckBox_auto_recall);

        cbe.setChecked(entry.isFlag(Entry.E));
        cbn.setChecked(entry.isFlag(Entry.N));
        cbh.setChecked(entry.isFlag(Entry.H));
        cbl.setChecked(entry.isFlag(Entry.L));
        cbx.setChecked(entry.isFlag(Entry.X));

        cbRecall.setChecked(entry.isFlag(Entry.AUTO_RECALL));

        cbe.setOnCheckedChangeListener(onCheckedChange);
        cbn.setOnCheckedChangeListener(onCheckedChange);
        cbh.setOnCheckedChangeListener(onCheckedChange);
        cbl.setOnCheckedChangeListener(onCheckedChange);
        cbx.setOnCheckedChangeListener(onCheckedChange);
        cbRecall.setOnCheckedChangeListener(onCheckedChange);

        findViewById(R.id.node_editor_Button_add).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {
                    final ItemType[] its = ItemType.values();
                    String[] s = new String[its.length];
                    for (int i = 0;i < its.length;++i) {
                        s[i] = its[i].toString();
                    }
                    new AlertDialog.Builder(NodeActivity.this)
                        .setTitle("类型选择")
                        .setSingleChoiceItems(s, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText et = new EditText(NodeActivity.this);
                                switch (its[which]) {
                                    case TXT:
                                        new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface p11, int p2) {
                                                    nodes.add(new Node(et.getText().toString(), ItemType.TXT));
                                                    update();
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        break;
                                    case IMG:
                                        selectImage();
                                        break;
                                    case IMG_FOLDER:
                                        selectFolder();
                                        break;
                                    case QID:
                                        nodes.add(new Node(ItemType.QID));
                                        break;
                                    case QNAME:
                                        nodes.add(new Node(ItemType.QNAME));
                                        break;
                                    case GID:
                                        nodes.add(new Node(ItemType.GID));
                                        break; 
                                    case GNAME:
                                        nodes.add(new Node(ItemType.GNAME));
                                        break;  
                                    case RAN_INT:
                                        new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface p11, int p2) {
                                                    nodes.add(new Node(et.getText().toString(), ItemType.RAN_INT));
                                                    update();
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        break;
                                    case RAN_FLOAT:
                                        new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface p11, int p2) {
                                                    nodes.add(new Node(et.getText().toString(), ItemType.RAN_FLOAT));
                                                    update();
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        break;
                                    case HASH_RAN_INT:
                                        new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface p11, int p2) {
                                                    nodes.add(new Node(et.getText().toString(), ItemType.HASH_RAN_INT));
                                                    update();
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        break;
                                    case HASH_RAN_FLOAT:
                                        new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface p11, int p2) {
                                                    nodes.add(new Node(et.getText().toString(), ItemType.HASH_RAN_FLOAT));
                                                    update();
                                                }
                                            }).setNegativeButton("取消", null).show();
                                        break;
                                }
                                update();
                                dialog.dismiss();
                            }
                        }).show();
                }
            });
        lv.setOnItemLongClickListener(new OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    new AlertDialog.Builder(NodeActivity.this)
                        .setTitle("删除")
                        .setMessage("确定删除" + nodes.get(p3).getCharSequence() + "吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nodes.remove(p3);
                                update();
                            }
                        }).setNegativeButton("取消", null).show();
                    return true;
                }
            });
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChange = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton p1, boolean p2) {
            switch (p1.getId()) {
                case R.id.node_editor_RadioButton_normal:
                    if (!p2) {
                        return;
                    }
                    entry.clearFlagBit(Entry.AT);
                    entry.clearFlagBit(Entry.QUOTE);
                    entry.setFlagBit(Entry.NORMAL);
                    break;
                case R.id.node_editor_RadioButton_at:
                    if (!p2) {
                        return;
                    }
                    entry.clearFlagBit(Entry.QUOTE);
                    entry.clearFlagBit(Entry.NORMAL);
                    entry.setFlagBit(Entry.AT);
                    break;
                case R.id.node_editor_RadioButton_quote:
                    if (!p2) {
                        return;
                    }
                    entry.clearFlagBit(Entry.AT);
                    entry.clearFlagBit(Entry.NORMAL);
                    entry.setFlagBit(Entry.QUOTE);
                    break;
                case R.id.node_editor_CheckBox_easy:
                    if (p2) {
                        entry.setFlagBit(Entry.E);
                    } else {
                        entry.clearFlagBit(Entry.E);
                    }
                    break;
                case R.id.node_editor_CheckBox_normal:
                    if (p2) {
                        entry.setFlagBit(Entry.N);
                    } else {
                        entry.clearFlagBit(Entry.N);
                    }
                    break; 
                case R.id.node_editor_CheckBox_hard:
                    if (p2) {
                        entry.setFlagBit(Entry.H);
                    } else {
                        entry.clearFlagBit(Entry.H);
                    }
                    break; 
                case R.id.node_editor_CheckBox_lunatic:
                    if (p2) {
                        entry.setFlagBit(Entry.L);
                    } else {
                        entry.clearFlagBit(Entry.L);
                    }
                    break;
                case R.id.node_editor_CheckBox_extra:
                    if (p2) {
                        entry.setFlagBit(Entry.X);
                    } else {
                        entry.clearFlagBit(Entry.X);
                    }
                    break;
                case R.id.node_editor_CheckBox_auto_recall:
                    if (p2) {
                        entry.setFlagBit(Entry.AUTO_RECALL);
                    } else {
                        entry.clearFlagBit(Entry.AUTO_RECALL);
                    }
                    break;
            }
            update();
        }
    };

    private void selectFolder() {
        new LFilePicker()
            .withActivity(this)
            .withRequestCode(9962)
            .withStartPath(SharedPreferenceHelper.getValue("lastpath"))
            .withMutilyMode(false)
            .withChooseMode(false)
            .start();
    }

    private void selectImage() {
        new LFilePicker()
            .withActivity(this)
            .withRequestCode(9961)
            .withStartPath(SharedPreferenceHelper.getValue("lastpath"))
            .withMutilyMode(true)
            .withChooseMode(true)
            .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 9961) {
                List<String> list = data.getStringArrayListExtra("paths");
                for (String filePath : list) {
                    File source = new File(filePath);
                    File target = new File("/storage/emulated/0/AppProjects/sanae_data/image/" + source.getName());
                    int index = target.getAbsolutePath().indexOf("sanae_data") + "sanae_data".length();
                    nodes.add(new Node(target.getAbsolutePath().substring(index), ItemType.IMG));
                    update();
                    if (target.exists() && !source.equals(target)) {
                        target.delete();
                    }
                    try {
                        Files.copy(source.toPath(), target.toPath());
                    } catch (IOException е) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), е);
                    }
                }
            } else if (requestCode == 9962) {
                String path = data.getStringExtra("path");
                File source = new File(path);
                File target = new File("/storage/emulated/0/AppProjects/sanae_data/image/" + source.getName());
                int index = target.getAbsolutePath().indexOf("sanae_data") + "sanae_data".length();
                nodes.add(new Node(target.getAbsolutePath().substring(index), ItemType.IMG_FOLDER));
                update();
                if (target.exists() && !source.equals(target)) {
                    target.delete();
                }
                try {
                    Files.copy(source.toPath(), target.toPath());
                } catch (IOException e) {
                    ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void update() {
        try {
            MainActivity.instance.wordstock.save();
        } catch (IOException e) {}
        na.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        na.notifyDataSetChanged();
    }
}
