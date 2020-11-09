package com.sohu.inputmethod.sogou;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sohu.inputmethod.sogou.R;
import com.sohu.inputmethod.sogou.jb.Entry;
import java.util.ArrayList;

public class EntryAdapter extends BaseAdapter{
    
    private ArrayList<Entry> list;
    
    public EntryAdapter(ArrayList<Entry> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Entry getItem(int p1) {
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
        holder.text.setText(list.get(position).toString());
        return convertView;
    }

    private class ViewHolder {
        TextView text;
    }
}
