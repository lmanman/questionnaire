package com.visionet.letsdesk.app.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class  RichTextPreUtil {
	public static String preImgTag(String contentHtml){
		Document doc = Jsoup.parseBodyFragment(contentHtml);
		Elements elements = doc.body().getElementsByTag("img");
		if(elements.size() > 0){
			for (Element ele : elements) {
				ele.attr("style","max-width:100%;padding:0px;");
			}
			return doc.body().html();
		}
		
		return null;
	}
}
