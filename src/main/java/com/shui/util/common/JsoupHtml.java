package com.shui.util.common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupHtml {

	public static void getByTag(String uris, int begin, int end, String tag) {
		Document doc;
		String str = "";
		for (int i = begin; i <= end; i++) {
			if (i % 2 == 1) {
				str += "<div style=\"text-align:left;width:100%;height:100%;\"><p><br /><br /><br /></p>";
			} else {

			}
			try {
				String p;
				if (i < 10) {
					p = "000" + i;
				} else if (i < 100) {
					p = "00" + i;
				} else {
					p = "0" + i;
				}

				doc = Jsoup.connect(uris + p + ".xhtml").get();
				Elements links = doc.getElementsByTag(tag);
				for (Element link : links) {
					str += "<div style=\"text-align:center;\"><span style=\"line-height:2;\">";
					str += link.text();
					str += "</span></div>";
				}
				if (i % 2 == 1) {
					str += "<p style=\"text-align:center;\"><span><br /></span> </p>";
				} else {
					str += "<p><br /></p></div>\n\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(str);
	}

	public static void main(String[] args) {
		JsoupHtml.getByTag("http://www.test2shui.com.cn/shqh/Text/part", 5,
				300, "p");
	}
}
