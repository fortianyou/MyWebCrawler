package com.ict.parser;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.ict.entity.HtmlContent;

public class HupuParser implements HtmlParser{

	private Logger logger = Logger.getLogger(HupuParser.class);
	public String innerhtml = "";
	private String getAttribute(TagNode tag, String xpath, String attr) throws XPatherException{
		Object[] nodes = null;
		nodes = tag.evaluateXPath(xpath);
		if( nodes == null || nodes.length == 0 ){
			return "";
		}
		else{
			TagNode meta = (TagNode) nodes[0];
			return meta.getAttributeByName(attr).trim();
		}
	}
	
	private String getTagText(TagNode tag, String xpath) throws XPatherException{
		Object[] nodes = null;
		nodes = tag.evaluateXPath(xpath);
		if( nodes == null || nodes.length == 0 ){
			return "";
		}
		else{
			TagNode t = (TagNode) nodes[0];
			return t.getText().toString().trim();
		}
	}
	
	@Override
	public HtmlContent parser(URL url, String charset) {
		HtmlContent htmlcnt = new HtmlContent();
		try {
			TagNode tag = cleaner.clean(url);
			innerhtml = cleaner.getInnerHtml(tag);
			Object[] nodes = tag.evaluateXPath("//head");
			TagNode head = (TagNode) nodes[0];
			
			htmlcnt.setSource("hupu");
			
			String cnt = getAttribute(head,"//meta[@http-equiv=\"Content-Type\"]","content");
			if( !cnt.equals("")){
				cnt = cnt.substring(cnt.indexOf("charset=")+8);
				htmlcnt.setCharset(cnt);
			}
			//获取关键字
			cnt = getAttribute(head,"//meta[@http-equiv=\"Keywords\"]","content");
			cnt = cnt.replace("-", " ");
			htmlcnt.setKeywords(cnt);
			//获取摘要
			cnt = getAttribute(head,"//meta[@http-equiv=\"Description\"]","content");
			htmlcnt.setAbstract(cnt);
			
			cnt = getAttribute(head,"//meta[@property=\"og:url\"]","content");
			htmlcnt.setUrl(cnt);
			
			int idx = cnt.lastIndexOf("/");
			String htmlid = cnt.substring(cnt.lastIndexOf("/")+1);
			String col = cnt.substring(cnt.lastIndexOf("/", idx -1 ) + 1,idx);
					
			htmlcnt.setHtmlId(htmlid);
			htmlcnt.setColumn(col);
			
			cnt = getAttribute(head,"//meta[@property=\"og:title\"]","content");
			htmlcnt.setTitle(cnt);

			cnt = getAttribute(head,"//meta[@property=\"og:description\"]","content");
			if( cnt.length() > htmlcnt.getAbstract().length() )
				htmlcnt.setAbstract(cnt);
			
			cnt = getAttribute(head,"//meta[@name=\"weibo:webpage:create_at\"]","content");
			htmlcnt.setPublishTime(cnt);
			
			nodes = tag.evaluateXPath("//body/div[@class=\"hp-wrap\"]/div[@class=\"voice-main\"]");
			TagNode mTag = (TagNode) nodes[0];
			
			cnt = getTagText(mTag,"/div[@class=\"artical-title\"]/div[@class=\"headline\"]");
			
			cnt = getTagText(mTag,"/div[@class=\"artical-title\"]//span[@id=\"pubtime_baidu\"]");
			if( !cnt.equals("") ){
				htmlcnt.setPublishTime(cnt);
			}
			
			cnt = getTagText(mTag,"/div[@class=\"artical-title\"]//a[@class=\"btn-viewComment\"]");
			cnt = cnt.replaceAll("[^0-9]", "");
			if( !cnt.equals(""))
				htmlcnt.setCommentCount(Integer.parseInt(cnt));
			
			cnt = getTagText(mTag,"/div[@class=\"artical-content\"]//div[@class=\"artical-main-content\"]");
			if( cnt.equals("")){
				logger.warn("url "+ url.toString() + ", doesn't find content");
				return null;
			}
			else
				htmlcnt.setContent(cnt);
			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			logger.warn("url "+ url.toString() + ", doesn't find content");
			return null;
		} catch (XPatherException e) {
			logger.error(e.getMessage(),e);
			logger.warn("url "+ url.toString() + ", doesn't find content");
			return null;
		}
		
		
		return htmlcnt;
	}

	@Override
	public String getHtml() {
		return "<!DOCTYPE html>\n<html>\n" + innerhtml + "\n</html>";
	}

	@Override
	/**
	 * 还没有实现该功能
	 */
	public HtmlContent parser(String url,String html) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
