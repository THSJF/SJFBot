package com.sohu.inputmethod.sogou;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sohu.inputmethod.sogou.jb.WordStock;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainAdapter extends BaseAdapter {
    private List<String> list;

    public MainAdapter() {
        list = Arrays.asList(MainActivity.instance.wordStork.w.keySet().toArray(new String[0]));
    }

    @Override
    public void notifyDataSetChanged() {
        list = Arrays.asList(MainActivity.instance.wordStork.w.keySet().toArray(new String[0]));
        super.notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int p1) {
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
        holder.text.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView text;
    }
}
