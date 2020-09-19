package com.meng.groupMsgProcess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meng.config.ConfigManager;
import com.meng.config.SanaeDataPack;
import java.util.ArrayList;
import java.util.HashMap;
import com.meng.BotWrapper;

public class ModuleSeq extends BaseModule {

	private ArrayList<SeqBean> seqs=new ArrayList<>();
	private HashMap<String, ArrayList<String>> jsonData = new HashMap<>();

    public ModuleSeq(BotWrapper bw) {
        super(bw);
    }
    
	@Override
	public BaseModule load() {
        jsonData = new Gson().fromJson(ConfigManager.instence.getSeq(), new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
   		for (String key : jsonData.keySet()) {
			ArrayList<String> al=jsonData.get(key);
			String[] content=al.toArray(new String[al.size()]);
			int flag=0;
			if (key.startsWith("time")) {
				flag = 1;
			} else if (key.startsWith("menger")) {
				flag = 2;
			}
			seqs.add(new SeqBean(content, flag));
		}
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		for (SeqBean sb:seqs) {
			if (msg.equals(sb.content[0])) {
				sb.pos = 0;
			}
			if (msg.equals(sb.content[sb.pos])) {
				if (sb.flag == 1) {
					ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opDecTime).write(fromGroup).write(fromQQ));
				} else if (sb.flag == 2) {
					ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opIncMengEr).write(fromGroup).write(fromQQ));
				}
				++sb.pos;			
				if (sb.pos < sb.content.length) {
					++sb.pos;
					if (sb.pos >= sb.content.length - 1) {
						sb.pos = 0;
					}
					if (sb.pos == 0) {
						wrapper.getAutoreply().sendGroupMessage(fromGroup, sb.content[sb.content.length - 1]);
					} else {
						wrapper.getAutoreply().sendGroupMessage(fromGroup, sb.content[sb.pos - 1]);
					}
				}
				break;
			}
		}
		return false;
	}

	private class SeqBean {
		public String[] content;
		public int pos=0;
		public int flag=0;
		public SeqBean(String[] array, int flag) {
			content = array;
			this.flag = flag;
		}
	}
}
