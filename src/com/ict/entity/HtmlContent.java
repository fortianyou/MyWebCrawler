package com.ict.entity;

public class HtmlContent {
	//��ҳ��Ӧurl
	private String url;
	//��ҳ��Դ�Ż�
	private String source;
	//��ҳ�ַ�����
	private String charset;
	
	private String fileId;
	//��ҳ����id
	private String htmlId;
	//��ҳר��
	private String column;
	//���±���
	private String title;
	//���·���ʱ��
	private String publishTime;
	//����ժҪ
	private String _abstract;
	//��������
	private String content;

	//�����
	private int clickCount;
	//������
	private int repostCount;
	//�ظ���
	private int commentCount;
	//�ؼ���
	private String keywords;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getHtmlId() {
		return htmlId;
	}
	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getAbstract() {
		return _abstract == null ? "":_abstract;
	}
	public void setAbstract(String _abstract) {
		this._abstract = _abstract;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public int getRepostCount() {
		return repostCount;
	}
	public void setRepostCount(int repostCount) {
		this.repostCount = repostCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getKeywords() {
		return keywords == null? "":keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}
