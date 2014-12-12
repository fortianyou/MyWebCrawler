package com.ict.parser;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.ict.entity.HtmlContent;

public class SinaParser implements HtmlParser{

	private Logger logger = Logger.getLogger(SinaParser.class);
	private String html = ""; 
	
	@Override
	public HtmlContent parser(URL url, String charset) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public HtmlContent parser(String url,String html) {
		this.html = html;
		HtmlContent htmlcnt = new HtmlContent();
		
		TagNode tag = cleaner.clean(html);
		Object[] nodes;
		try {
			nodes = tag.evaluateXPath("//head");
			TagNode head = (TagNode) nodes[0];
			
			htmlcnt.setSource("sina");
			
			String cnt = getAttribute(head,"//meta[@http-equiv=\"Content-type\"]","content");
			if( !cnt.equals("")){
				cnt = cnt.substring(cnt.indexOf("charset=")+8);
				htmlcnt.setCharset(cnt);
			}else{
				cnt = getAttribute(head,"//meta[@http-equiv=\"Content-Type\"]","content");
				if( !cnt.equals("")){
					cnt = cnt.substring(cnt.indexOf("charset=")+8);
					htmlcnt.setCharset(cnt);
				}else
					htmlcnt.setCharset("");
			}
			
			//获取关键字
			cnt = getAttribute(head,"//meta[@name=\"tags\"]","content");
			if(!cnt.equals("")){
				cnt = cnt.replace(",", " ");
				htmlcnt.setKeywords(cnt);
			}
			//获取摘要
			cnt = getAttribute(head,"//meta[@name=\"description\"]","content");
			htmlcnt.setAbstract(cnt);
			
			cnt = getAttribute(head,"//meta[@property=\"og:url\"]","content");
			if( cnt.equals("")){
				htmlcnt.setUrl(url);
				cnt = url;
			}
			else
				htmlcnt.setUrl(cnt);
			String title = getTagText(head,"//title");
			
			int idx = cnt.lastIndexOf("/");
			idx = cnt.lastIndexOf("/", idx - 1);
			String htmlid = cnt.substring(idx+1);
			String col = cnt.substring(cnt.lastIndexOf("http://sports.sina.com.cn/") + "http://sports.sina.com.cn/".length(),idx);
			
			htmlcnt.setHtmlId(htmlid);
			htmlcnt.setColumn(col);
			htmlcnt.setFileId(col + "/"+htmlid);
			String str = htmlcnt.getHtmlId();
			idx = str.indexOf("/");
			String date = str.substring(0,idx);
			String hh = str.substring(idx+1, idx + 3);
			String mm = str.substring(idx + 3, idx + 5);
			htmlcnt.setPublishTime(date + " "+ hh + ":" +mm);
			
			
			cnt = getAttribute(head,"//meta[@property=\"og:title\"]","content");
			htmlcnt.setTitle(cnt);
			
			//nodes = tag.evaluateXPath("//body//div[@id=\"J_Article_Wrap\"]");
			nodes = tag.evaluateXPath("//body");
			TagNode mTag = (TagNode) nodes[0];
			
			cnt = getTagText(mTag,"//div[@id=\"artibodyTitle\"]");
			if( cnt.equals("") )
				cnt = getTagText(mTag,"//div[@id=\"artibodyTitle\"]/h1");
			htmlcnt.setTitle(cnt);
			
			
//			cnt = getTagText(mTag,"//span[@id=\"pub_date\"]");
//			if( !cnt.equals(""))
//				htmlcnt.setPublishTime(cnt);
//			else{
//				
//			}
			
//			cnt = getTagText(mTag,"//div[@id=\"media_comment\"]/span[@class=\"f_red\"]");
//			cnt = cnt.replaceAll("[^0-9]", "");
//			if( !cnt.equals(""))
//				htmlcnt.setCommentCount(Integer.parseInt(cnt));
//			
//			
//			cnt = getTagText(mTag,"//div[@id=\"J_Post_Box_Count\"]//span[@class=\"f_red\"]");
//			cnt = cnt.replaceAll("[^0-9]", "");
//			if( !cnt.equals(""))
//				htmlcnt.setRepostCount(Integer.parseInt(cnt));//使用Repost来表示评论
			
			nodes = mTag.evaluateXPath("//div[@id=\"artibody\"]//p");
			if( nodes == null || nodes.length == 0 ){
				cnt = getTagText(mTag, "//div[@id=\"article\"]//h1");
				if(! cnt.equals(""))
					htmlcnt.setTitle(cnt);
				else{
					if( title.equals("" )){
						htmlcnt.setTitle("");
					}else{
						idx = title.indexOf("_");
						if( idx > 0 )
							htmlcnt.setTitle(title.substring(0,idx));
						else
							htmlcnt.setTitle(title);
					}
				}
				nodes = mTag.evaluateXPath("//div[@id=\"article\"]//p");
			}
			
			if( nodes == null || nodes.length == 0 ){
				cnt ="";
			}
			else{
				cnt = "";
				for(Object o :nodes){
				TagNode t = (TagNode) o;
				cnt += t.getText().toString().trim()+"\n";
				}
			}
			
			if( cnt.equals("")){
				logger.warn("url "+ htmlcnt.getUrl() + ", doesn't find content");
				logger.warn("url the page may change pattern.");
				return null;
			}
			else
				htmlcnt.setContent(cnt);
			
			
			
		} catch (XPatherException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
		
		
		return htmlcnt;
	}

	@Override
	public String getHtml() {
		return html;
	}

}
