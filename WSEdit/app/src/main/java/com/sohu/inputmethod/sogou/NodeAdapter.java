package com.sohu.inputmethod.sogou;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sohu.inputmethod.sogou.R;
import com.sohu.inputmethod.sogou.jb.Node;
import java.util.ArrayList;

public class NodeAdapter extends BaseAdapter {

    private ArrayList<Node> list;

    public NodeAdapter(ArrayList<Node> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Node getItem(int p1) {
        return list.get(p1);
    }

    @Override
    public long getItemId(int p1) {
        return list.get(p1).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = MainActivity.instance.getLayoutInflater().inflate(R.layout.word_stock_item, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String node = list.get(position).toString();
        holder.text.setText(node.equals("")?list.get(position).t.toString():node);
        return convertView;
    }

    private class ViewHolder {
        TextView text;
    }
}
