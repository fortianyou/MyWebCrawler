package com.ict.parser;

import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;

import com.ict.entity.HtmlContent;

public interface HtmlParser {
	static HtmlCleaner cleaner = new HtmlCleaner();
	HtmlContent parser(URL url,String charset) ;
	HtmlContent parser(String url,String html);
	String getHtml();
}
