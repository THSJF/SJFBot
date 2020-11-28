package com.sohu.inputmethod.sogou.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.sohu.inputmethod.sogou.MainActivity;
import com.sohu.inputmethod.sogou.R;
import com.sohu.inputmethod.sogou.jb.Entry;
import com.sohu.inputmethod.sogou.jb.WordStock;
import java.util.ArrayList;

public class MainListAdapter extends BaseExpandableListAdapter {

    private WordStock stock;

    private ArrayList<String> parentList;

    public MainListAdapter(WordStock stk) {
        stock = stk;
        parentList = new ArrayList<String>(stk.words.keySet());
    }
    //得到子item需要关联的数据
    @Override
    public Entry getChild(int groupPosition, int childPosition) {
        String get = parentList.get(groupPosition);
        if (get == null) {
            return null;
        }
        ArrayList<Entry> get2 = stock.words.get(get);
        if (get2 == null) {
            return null;
        }
        return get2.get(childPosition);
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Entry entry = getChild(groupPosition, childPosition);
        ViewHolderChild holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_adapter_child, null);
            holder = new ViewHolderChild();
            holder.tvGoodwill = (TextView) convertView.findViewById(R.id.main_adapter_childTextView_goodwill);
            holder.tvContent = (TextView) convertView.findViewById(R.id.main_adapter_childTextView_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        holder.tvGoodwill.setText(
            String.format("[%s%s%s%s%s]%s%s%s",
                          entry.isFlag(Entry.E) ?"E": "_",
                          entry.isFlag(Entry.N) ?"N": "_",
                          entry.isFlag(Entry.H) ?"H": "_",
                          entry.isFlag(Entry.L) ?"L": "_",
                          entry.isFlag(Entry.X) ?"X": "_",
                          entry.isFlag(Entry.AUTO_RECALL) ?"[auto recall]": "",
                          entry.isFlag(Entry.QUOTE) ?"[quote]": "",
                          entry.isFlag(Entry.AT) ?"[at]": ""));
        holder.tvContent.setText(entry.getCharSequence());
        return convertView;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Entry> get = stock.words.get(parentList.get(groupPosition));
        return get == null ?0: get.size();
    }

    //获取当前父item的数据
    @Override
    public String getGroup(int groupPosition) {
        if (parentList == null) {
            return null;
        }
        return parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parentList == null ?0: parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //设置父item组件
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderParent holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_adapter_parent, null);
            holder = new ViewHolderParent();
            holder.tvKey = (TextView) convertView.findViewById(R.id.main_adapter_parentTextView);
            holder.ibEdit = (ImageButton) convertView.findViewById(R.id.main_adapter_parentImageButton);
            holder.ibEdit.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        new AlertDialog.Builder(MainActivity.instance)
                            .setTitle("编辑")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(MainActivity.instance)
                                        .setTitle("删除")
                                        .setMessage("确定删除" + parentList.get(groupPosition) + "吗")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                stock.words.remove(parentList.get(groupPosition));
                                                MainActivity.instance.update();
                                            }
                                        }).setNegativeButton("取消", null).show();
                                }
                            })
                            .setNegativeButton("修改", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface p1, int p2) {
                                    final EditText et = new EditText(MainActivity.instance);
                                    new AlertDialog.Builder(MainActivity.instance)
                                        .setTitle("修改")
                                        .setView(et)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String oldKey = parentList.get(groupPosition);
                                                stock.words.put(et.getText().toString(), stock.words.remove(oldKey));
                                                MainActivity.instance.update();
                                            }
                                        }).setNegativeButton("取消", null).show();
                                }
                            })
                            .setNeutralButton("添加", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface p1, int p2) {
                                    stock.words.get(parentList.get(groupPosition)).add(new Entry());
                                    MainActivity.instance.update();
                                }
                            }).show();
                    }
                });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderParent) convertView.getTag();
        }
        holder.tvKey.setText(getGroup(groupPosition));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        parentList = new ArrayList<String>(stock.words.keySet());
    }

    private class ViewHolderParent {
        private TextView tvKey;
        private ImageButton ibEdit;
    }

    private class ViewHolderChild {
        private TextView tvGoodwill;
        private TextView tvContent;
    }
}

