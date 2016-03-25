package com.shui.util;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.shui.util.common.FreemarkerUtils;

public class FreemarkerClient {
	@Test
	public void create() {
		String path = this.getClass().getResource("/").getFile() + "/ftl/";
		FreemarkerUtils.initTemplate(path, "page.ftl", null);
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "this is freemarker title");
		map.put("body", "this is freemarker body");
		FreemarkerUtils.analysisTemplate(path + "/html/a.html", map, null);
	}
}
