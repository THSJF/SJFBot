package com.meng.modules.bilibili.self.javabean;

import com.meng.tools.JsonHelper;

public class UploadPicResult {
	public int img_width;
	public int img_height;
	public String img_src;
	public float img_size;
	
	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
