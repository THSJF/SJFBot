package com.meng.modules.bilibili.self.javabean;

import com.meng.tools.GSON;

public class UploadPicResult {
	public int img_width;
	public int img_height;
	public String img_src;
	public float img_size;
	
	@Override
	public String toString() {
		return GSON.toJson(this);
	}
}
