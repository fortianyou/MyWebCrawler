package com.ict.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ict.entity.ConfigInfo;


public class ConfigReader {
static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	
	//xml配置文件路径
	private String xmlFilePath;
	
	/**
	 * 构造函数
	 * @param xmlpathString xml文件路径
	 */
	public ConfigReader(String xmlpathString) {
		xmlFilePath = xmlpathString;
	}
	
	/**
	 * 读取配置文件 并返回配置信息类
	 * @return 读取成功返回ConfigInfo 否则返回null
	 */
	
	public ConfigInfo ReadConfig() {
		
		ConfigInfo retConfigInfo = null;
		try {
			File xmlFile = new File(xmlFilePath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(xmlFile);
			retConfigInfo = new ConfigInfo();
			logger.info("正在解析配置文件");

			
			Element DNSElm = (Element)doc.selectSingleNode("//DNS");
			if (null == DNSElm) {
				logger.error("xml文档没有找到//DNS节点");
				return null;
			}
			retConfigInfo.setDNS(DNSElm.getText());
			
			Element colElm = (Element) doc.selectSingleNode("//column");
			if (null == colElm) {
				logger.error("xml文档没有找到//column节点");
				return null;
			}
			retConfigInfo.setCol(colElm.getText());
			
			Element htmlIdElm = (Element)doc.selectSingleNode("//html_id");
			if (null == htmlIdElm) {
				logger.error("xml文档没有找到//html_id节点");
				return null;
			}
			retConfigInfo.setHtmlid(Integer.parseInt(htmlIdElm.getText()));
			
			Element charsetElm = (Element) doc.selectSingleNode("//charset");
			if(null == charsetElm){
				logger.error("xml文档没有找到//charset节点");
				return null;
			}
			retConfigInfo.setCharset(charsetElm.getText());
			
			Element docElm = (Element) doc.selectSingleNode("//doc_path");
			if (null == docElm) {
				logger.error("xml文档没有找到//doc_path节点");
				return null;
			}
			retConfigInfo.setDocPath(docElm.getText());
			
			Element htmlElm = (Element)doc.selectSingleNode("//html_path");
			if(null == htmlElm){
				logger.error("xml文档没有找到//html_path节点");
				return null;
			}
			retConfigInfo.setHtmlPath(htmlElm.getText());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("解析xml配置文件遇到异常",e);
			return null;
		}
		
		logger.info("配置文件解析成功");
		return retConfigInfo;
	}
}
