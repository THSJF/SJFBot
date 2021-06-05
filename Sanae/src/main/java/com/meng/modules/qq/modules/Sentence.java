package com.meng.modules.qq.modules;
import com.google.gson.annotations.SerializedName;
import com.meng.config.SanaeData;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.FileWatcher;
import com.meng.tools.SJFRandom;
import java.io.File;
import java.util.ArrayList;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class Sentence extends BaseModule implements IGroupMessageEvent {
    @SanaeData("sentence.json")
    private Sentences sens = new Sentences();
    private File content;
    public Sentence(SBot b) {
        super(b);
        content = new File("C://Program Files/sanae_data/persistent/" + getSanaeValue("sens"));
        FileWatcher.getInstance().addOnFileChangeListener(content, new Runnable(){

                @Override
                public void run() {
                    reload();
                }
            });
    }

    @Override
    public BaseModule reload() {
        return load();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (event.getMessage().contentToString().equals(".sentence")) {
            sendMessage(event.getGroup(), SJFRandom.randomSelect(sens.sentences).toString());
            return true;
        }
        return false;
    }

    public static class Sentences {
        @SerializedName("l")
        public ArrayList<SingleSentence> sentences = new ArrayList<>();
    }

    public static class SingleSentence {
        @SerializedName("s")
        public String text;
        @SerializedName("t")
        public String tag;

        @Override
        public String toString() {
            return text;
        }  
    }
}
