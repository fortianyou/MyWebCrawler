package com.ict.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ict.entity.ConfigInfo;


public class ConfigReader {
static Logger logger = Logger.getLogger(ConfigReader.class.getName());
	
	//xml�����ļ�·��
	private String xmlFilePath;
	
	/**
	 * ���캯��
	 * @param xmlpathString xml�ļ�·��
	 */
	public ConfigReader(String xmlpathString) {
		xmlFilePath = xmlpathString;
	}
	
	/**
	 * ��ȡ�����ļ� ������������Ϣ��
	 * @return ��ȡ�ɹ�����ConfigInfo ���򷵻�null
	 */
	
	public ConfigInfo ReadConfig() {
		
		ConfigInfo retConfigInfo = null;
		try {
			File xmlFile = new File(xmlFilePath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(xmlFile);
			retConfigInfo = new ConfigInfo();
			logger.info("���ڽ��������ļ�");

			
			Element DNSElm = (Element)doc.selectSingleNode("//DNS");
			if (null == DNSElm) {
				logger.error("xml�ĵ�û���ҵ�//DNS�ڵ�");
				return null;
			}
			retConfigInfo.setDNS(DNSElm.getText());
			
			Element colElm = (Element) doc.selectSingleNode("//column");
			if (null == colElm) {
				logger.error("xml�ĵ�û���ҵ�//column�ڵ�");
				return null;
			}
			retConfigInfo.setCol(colElm.getText());
			
			Element htmlIdElm = (Element)doc.selectSingleNode("//html_id");
			if (null == htmlIdElm) {
				logger.error("xml�ĵ�û���ҵ�//html_id�ڵ�");
				return null;
			}
			retConfigInfo.setHtmlid(Integer.parseInt(htmlIdElm.getText()));
			
			Element charsetElm = (Element) doc.selectSingleNode("//charset");
			if(null == charsetElm){
				logger.error("xml�ĵ�û���ҵ�//charset�ڵ�");
				return null;
			}
			retConfigInfo.setCharset(charsetElm.getText());
			
			Element docElm = (Element) doc.selectSingleNode("//doc_path");
			if (null == docElm) {
				logger.error("xml�ĵ�û���ҵ�//doc_path�ڵ�");
				return null;
			}
			retConfigInfo.setDocPath(docElm.getText());
			
			Element htmlElm = (Element)doc.selectSingleNode("//html_path");
			if(null == htmlElm){
				logger.error("xml�ĵ�û���ҵ�//html_path�ڵ�");
				return null;
			}
			retConfigInfo.setHtmlPath(htmlElm.getText());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("����xml�����ļ������쳣",e);
			return null;
		}
		
		logger.info("�����ļ������ɹ�");
		return retConfigInfo;
	}
}
