package com.ict.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ict.entity.HtmlContent;

public class XMLFileWriter {
	private Logger logger = Logger.getLogger(XMLFileWriter.class);
	private String path;
	
	public XMLFileWriter(String _path){
		path = _path;
	}
	public boolean dumpHtml2File(List<HtmlContent> htmlList, String filename){
		boolean ans = true;
		if (! FileDirUtil.PathIsExist(path)) {
			
			if ( ! FileDirUtil.CreateDirectory(path)) {
				logger.error("创建文件夹失败");
				ans = false;
			}
		}
		
		Document dom = DocumentHelper.createDocument();
		Element root = dom.addElement("news_list");
	
		root.addAttribute("size", ""+htmlList.size());
		Element elm = null;
		for( HtmlContent html: htmlList){
			Element htmlElm = root.addElement("news");
			elm = htmlElm.addElement("id");
			elm.setText(html.getHtmlId());
			
			elm = htmlElm.addElement("column");
			elm.setText(html.getColumn());
			
			elm = htmlElm.addElement("charset");
			elm.setText(html.getCharset());
			
			elm = htmlElm.addElement("source");
			elm.setText(html.getSource());
			
			elm = htmlElm.addElement("url");
			elm.setText(html.getUrl());
			
			elm = htmlElm.addElement("title");
			elm.setText(html.getTitle());
			
			elm = htmlElm.addElement("keywords");
			elm.setText(html.getKeywords());
			
			elm = htmlElm.addElement("publish_time");
			elm.setText(html.getPublishTime());
			
			elm = htmlElm.addElement("abstract");
			elm.setText(html.getAbstract());
			
			elm = htmlElm.addElement("content");
			elm.setText(html.getContent());
			
			elm = htmlElm.addElement("click_count");
			elm.setText(Integer.toString(html.getClickCount()));
			
			elm = htmlElm.addElement("repost_count");
			elm.setText( Integer.toString(html.getRepostCount()));
			
			elm = htmlElm.addElement("comment_count");
			elm.setText(Integer.toString(html.getCommentCount()));
		}
		
		XMLWriter outputWriter = null;
		try {
			FileOutputStream fOStream = new FileOutputStream(new File(path + "/" + filename ));
		
			OutputFormat outputFormat = new OutputFormat(" ", true);
			
			outputWriter = new XMLWriter(fOStream, outputFormat);
			outputWriter.write(dom);
			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			ans = false;
		} finally{
			if( outputWriter != null )
				try {
					outputWriter.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					ans = false;
				}
		}
		return ans;
	}
}
