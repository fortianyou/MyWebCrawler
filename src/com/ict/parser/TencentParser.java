package com.ict.parser;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.ict.entity.HtmlContent;

public class TencentParser implements HtmlParser{

	private Logger logger = Logger.getLogger(TencentParser.class);
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
			
			htmlcnt.setSource("tencent");
			
			String cnt = getAttribute(head,"//meta[@http-equiv=\"Content-Type\"]","content");
			if( !cnt.equals("")){
				cnt = cnt.substring(cnt.indexOf("charset=")+8);
				htmlcnt.setCharset(cnt);
			}else{
				cnt = getAttribute(head,"//meta[@http-equiv=\"Content-type\"]","content");
				if( !cnt.equals("")){
					cnt = cnt.substring(cnt.indexOf("charset=")+8);
					htmlcnt.setCharset(cnt);
				}else
					htmlcnt.setCharset("");
			}
			
			//获取关键字
			cnt = getAttribute(head,"//meta[@name=\"keywords\"]","content");
			if(!cnt.equals("")){
				String str[] = cnt.split(",");
				cnt = "";
				for(String s:str){
					if( s.length() < 6)
					cnt += " " + s;
				}
				cnt = cnt.trim();
				htmlcnt.setKeywords(cnt);
			}
			//获取摘要
			cnt = getAttribute(head,"//meta[@name=\"Description\"]","content");
			htmlcnt.setAbstract(cnt);
		
			htmlcnt.setUrl(url);
			cnt = url;

		//	String title = getTagText(head,"//title");
			
			int idx = cnt.lastIndexOf("/");
			idx = cnt.lastIndexOf("/", idx - 1);
			String htmlid = cnt.substring(idx+1);
			String fileid = cnt.substring(cnt.lastIndexOf("http://sports.qq.com/") + "http://sports.qq.com/".length());
			htmlcnt.setFileId(fileid);
			htmlcnt.setHtmlId(htmlid);
			
			//nodes = tag.evaluateXPath("//body//div[@id=\"J_Article_Wrap\"]");
			nodes = tag.evaluateXPath("//body//div[@id=\"C-Main-Article-QQ\"]");
			if( nodes == null || nodes.length == 0 ){
				logger.warn("url "+ htmlcnt.getUrl() + ", doesn't find content");
				logger.warn("url the page may change pattern.");
				return null;
			}
			TagNode mTag = (TagNode) nodes[0];
			
			cnt = getTagText(mTag,"//div[@class=\"hd\"]/h1");
//			if( cnt.equals("") )
//				cnt = getTagText(mTag,"//div[@id=\"artibodyTitle\"]/h1");
			htmlcnt.setTitle(cnt);
			
			cnt = getTagText(mTag,"//div[@class=\"hd\"]//span[@bosszone=\"ztTopic\"]/a");
			htmlcnt.setColumn(cnt);
			cnt = getTagText(mTag,"//div[@class=\"hd\"]//span[@class=\"article-time\"]");
			htmlcnt.setPublishTime(cnt);
			
			cnt = getTagText(mTag,"//div[@class=\"hd\"]//a[@id=\"cmtNum\"]");
			cnt = cnt.replaceAll("[^0-9]", "");
			if( !cnt.equals(""))
				htmlcnt.setCommentCount(Integer.parseInt(cnt));
//			
//			
//			cnt = getTagText(mTag,"//div[@id=\"J_Post_Box_Count\"]//span[@class=\"f_red\"]");
//			cnt = cnt.replaceAll("[^0-9]", "");
//			if( !cnt.equals(""))
//				htmlcnt.setRepostCount(Integer.parseInt(cnt));//使用Repost来表示评论
			
			nodes = mTag.evaluateXPath("//div[@id=\"Cnt-Main-Article-QQ\"]//p[@style=\"TEXT-INDENT: 2em\"]");
			
			if( nodes == null || nodes.length == 0 ){
				cnt ="";
			}
			else{
				cnt = "";
				for(Object o :nodes){
					TagNode t = (TagNode) o;
					Object[] obj = t.evaluateXPath("//script");
					if( obj == null || obj.length == 0 )
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
