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
import android.widget.EditText;
import android.widget.ListView;
import com.sohu.inputmethod.ExceptionCatcher;
import com.sohu.inputmethod.sogou.R;
import com.sohu.inputmethod.sogou.jb.ItemType;
import com.sohu.inputmethod.sogou.jb.Node;
import java.io.IOException;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.LinearLayout;

public class NodeActivity extends Activity {

    private ListView lv;
    private NodeAdapter na;
    private ArrayList<Node> nodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_edit);
        lv = (ListView) findViewById(R.id.node_editListView);
        int pos = getIntent().getIntExtra("pos", -1);
        String key = getIntent().getStringExtra("key");
        if (pos == -1 || key == null) {
            finish();
        }
        nodes = MainActivity.instance.wordStork.w.get(key).get(pos).e;
        na = new NodeAdapter(nodes);
        lv.setAdapter(na);

        lv.setOnItemLongClickListener(new OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    new AlertDialog.Builder(NodeActivity.this)
                        .setTitle("删除")
                        .setMessage("确定删除" + nodes.get(p3) + "吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nodes.remove(p3);
                                try {
                                    MainActivity.save();
                                } catch (IOException e) {}
                                na.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", null).show();
                    return true;
                }
            });
        LinearLayout ll = findViewById(R.id.node_editLinearLayout);
        for (int i = 0;i < ll.getChildCount();++i) {
            ll.getChildAt(i).setOnClickListener(click);
        }
    }

    OnClickListener click = new OnClickListener(){

        @Override
        public void onClick(View p1) {
            switch (p1.getId()) {
                case R.id.node_editButton_txt:
                    final EditText et = new EditText(NodeActivity.this);
                    new AlertDialog.Builder(NodeActivity.this).setView(et).setTitle("编辑").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface p11, int p2) {
                                nodes.add(new Node(et.getText().toString(), ItemType.TXT));
                            }
                        }).setNegativeButton("取消", null).show();
                    break;
                case R.id.node_editButton_img:
                    selectImage();
                    break; 
                case R.id.node_editButton_qid:
                    nodes.add(new Node("", ItemType.QID));
                    break;
                case R.id.node_editButton_qname:
                    nodes.add(new Node("", ItemType.QNAME));
                    break;
                case R.id.node_editButton_gid:
                    nodes.add(new Node("", ItemType.GID));
                    break; 
                case R.id.node_editButton_gname:
                    nodes.add(new Node("", ItemType.GNAME));
                    break;  
                case R.id.node_editButton_at_qid:
                    nodes.add(new Node("", ItemType.AT_QID));
                    break;     
            }
            update();
        }
    };

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 9961);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data.getData() != null) {
            if (requestCode == 9961) {
                //    uploadBmpAbsPath = ContentHelper.absolutePathFromUri(getActivity(), data.getData());//= Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/picTool/search_tmp.png";
                final String path = ContentHelper.absolutePathFromUri(this, data.getData());
                if (path == null) {
                    showToast("选择图片出错");
                    return;
                }
                int index = path.indexOf("sanae_data") + "sanae_data".length();
                nodes.add(new Node(path.substring(index), ItemType.IMG));
                update();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            showToast("取消选择图片");
        } else {
            selectImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void update() {
        try {
            MainActivity.save();
        } catch (IOException e) {}
        na.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        na.notifyDataSetChanged();
    }
}
