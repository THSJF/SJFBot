package com.sohu.inputmethod.sogou.jb;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import com.google.gson.annotations.SerializedName;

public class Node {

    @SerializedName("t") public int type;
    @SerializedName("c") public String content = "";

    public Node() { }

    public Node(ItemType type) {
        this.type = type.value();
    }

    public Node(String content) {
        if (content != null) {
            this.content = content;
        }
        this.type = ItemType.TXT.value();
    }

    public Node(String content, ItemType type) {
        if (content != null) {
            this.content = content;
        }
        this.type = type.value();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }  

    public CharSequence getCharSequence() {
        switch (ItemType.valueOf(type)) {
            case TXT:
                return content;
            case IMG:
                SpannableString ss = new SpannableString(" ");
                ss.setSpan(new ImageSpan(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile("/storage/emulated/0/AppProjects/sanae_data" + content), 500, 500), ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            case QID:
                return "[sender qq]";
            case QNAME:
                return "[sender name]";
            case GID:
                return "[from group]";
            case GNAME:
                return "[group name]";
            case RAN_INT:
                return "[random int,max = " + content + "]";
            case RAN_FLOAT:
                return "[random float,max = " + content + "]";
            case HASH_RAN_INT:
                return "[int by hash,max = " + content + "]";
            case HASH_RAN_FLOAT:
                return "[float by hash,max = " + content + "]";
            case IMG_FOLDER:
                return "[image folder:" + content + "]";
        }
        throw new IllegalArgumentException();
    }
}
