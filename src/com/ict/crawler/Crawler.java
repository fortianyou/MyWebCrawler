package com.ict.crawler;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ict.entity.HtmlContent;
import com.ict.parser.HtmlParser;
import com.ict.parser.MainCrawler;
import com.ict.parser.SinaParser;
import com.ict.util.FileWriter;
import com.ict.util.XMLFileWriter;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class Crawler extends BreadthCrawler{
	private static Logger logger = Logger.getLogger(BreadthCrawler.class);

	private static List<HtmlContent> htmlList = new ArrayList<HtmlContent>();

	private static String charset;// = "gb2312";
	
	private static FileWriter fileWriter;// = new FileWriter("D:/研一课程/sports.sina.com.cn/");
	private static XMLFileWriter htmlWriter;// = new XMLFileWriter(path);
	private static String regex;
	private static String qregex;
	private static HtmlParser parser;
	private static int interval = 1000;
	private static int depth = 2;
	public static void setCharset(String charset) {
		Crawler.charset = charset;
	}

	public static void setFileWriter(FileWriter fileWriter) {
		Crawler.fileWriter = fileWriter;
	}

	public static void setHtmlWriter(XMLFileWriter htmlWriter) {
		Crawler.htmlWriter = htmlWriter;
	}

	public static void setRegex(String regex) {
		Crawler.regex = regex;
	}

	public static void setQregex(String qregex) {
		Crawler.qregex = qregex;
	}

	public static void setInterval(int _int){
		interval = _int;
	}
	
	public static void setDepth(int _depth){
		depth = _depth;
	}
	public static void setHtmlParser(HtmlParser _parser){
		parser = _parser;
	}
	private static String getTimestamp(){
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(time);
	}
	
	/*visit函数定制访问每个页面时所需进行的操作*/
    @Override
    public void visit(Page page) {
    	//String qregex="^http://sports.sina.com.cn/.+/20[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}/[0-9]+.shtml$";
    	if(Pattern.matches(qregex, page.getUrl())){
            
            HtmlContent htmlcnt = parser.parser(page.getUrl(),page.getHtml());
            if( htmlcnt != null ){
	            if(fileWriter.write2file(htmlcnt.getFileId(), page.getHtml(),charset))
	            	addHtml(htmlcnt);
            }
        }
    }
    
    public static synchronized void addHtml(HtmlContent htmlcnt){
    	htmlList.add(htmlcnt);
 
    	if( htmlList.size() != 0 && htmlList.size() % interval == 0 ){
    		String filename = getTimestamp();
			logger.info("writing data into file "+ filename +"...");
			htmlWriter.dumpHtml2File(htmlList, filename);
			logger.info("end writing file "+ filename +".");
			htmlList.clear();
    	}
    }
 
    public static void outputRes(){
    	 if( htmlList.size() != 0 && htmlList.size() % interval == 0 ){
     		String filename = getTimestamp();
 			logger.info("writing data into file "+ filename +"...");
 			htmlWriter.dumpHtml2File(htmlList, filename);
 			logger.info("end writing file "+ filename +".");
 			htmlList.clear();
     	}
    }
    /*启动爬虫*/
    public static void run(String seed) throws Exception{  
    	Crawler crawler=new Crawler();
        crawler.addSeed(seed);
        crawler.addRegex(regex);//"^http://sports.sina.com.cn/.*$");
        
        crawler.start(depth);  
    }
 
}
