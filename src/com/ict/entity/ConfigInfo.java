package com.ict.entity;

public class ConfigInfo {
	private String DNS;
	private String col;
	private int htmlid;
	private String charset;
	private String docPath;
	private String htmlPath;
	public String getDNS() {
		return DNS;
	}
	public void setDNS(String dNS) {
		DNS = dNS;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public int getHtmlid() {
		return htmlid;
	}
	public void setHtmlid(int htmlid) {
		this.htmlid = htmlid;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getHtmlPath() {
		return htmlPath;
	}
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	
}
