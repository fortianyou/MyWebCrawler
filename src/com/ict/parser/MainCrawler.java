package com.ict.parser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ict.crawler.Crawler;
import com.ict.entity.ConfigInfo;
import com.ict.entity.HtmlContent;
import com.ict.util.ConfigReader;
import com.ict.util.FileWriter;
import com.ict.util.XMLFileWriter;

public class MainCrawler {
	private static Logger logger = Logger.getLogger(MainCrawler.class);
	
	private static String getTimestamp(){
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(time);
	}
	
	public static void main1(String args[]) throws MalformedURLException{
		HupuParser parser = new HupuParser();
		URL url = new URL("http://voice.hupu.com/soccer/1792525.html");
		HtmlContent htmlcnt = parser.parser(url, "utf-8");
		FileWriter fileWriter = new FileWriter("D:/研一课程/hupu_pages");
		fileWriter.write2file(htmlcnt.getColumn()+"_"+htmlcnt.getHtmlId(), parser.getHtml(),"utf-8");
		//logger.warn(html);
	}
	
	public static void hupurun(){
		File file=new File(".");
		String configPath = file.getAbsolutePath();
		ConfigReader configReader = new ConfigReader(configPath+"/config.ini");
		ConfigInfo config = configReader.ReadConfig();
		if( config == null )
		{
			logger.info("读取配置文件出现问题，程序退出");
			return;
		}
		String DNS = config.getDNS();
		String col = config.getCol();
		int htmlid = config.getHtmlid();
		String charset = config.getCharset();
		String path = config.getDocPath();
		FileWriter fileWriter = new FileWriter(config.getHtmlPath());
		
//		String DNS = "http://voice.hupu.com/";
//		String col = "soccer";
//		int htmlid = 1624153;
//		String charset = "utf-8";
//		String path = "D:/研一课程/hupu";
//		FileWriter fileWriter = new FileWriter("D:/研一课程/hupu_pages");
		
		XMLFileWriter htmlWriter = new XMLFileWriter(path);
		HtmlParser parser = new HupuParser();
		
		
		List<HtmlContent> htmlList = new ArrayList<HtmlContent>();
		String filename = null;
		while( htmlid > 0 ){
			
			String urlstr = DNS + col +"/" + htmlid + ".html";
			HtmlContent htmlcnt = null;
			try {
				htmlcnt = parser.parser(new URL(urlstr), charset);
				if( htmlcnt != null ){
					htmlList.add(htmlcnt);
					if( !htmlcnt.getUrl().equals(urlstr) && !htmlcnt.getColumn().equals("") ){
						col = htmlcnt.getColumn();
						logger.info("column has been changed to: "+ col +", for "+htmlcnt.getHtmlId());
					}
					fileWriter.write2file(htmlcnt.getColumn()+"_"+htmlcnt.getHtmlId(), parser.getHtml(),charset);
				}else{
					logger.warn("Page "+ htmlid + ".html haven't been crawled for some reason.");
				}
				htmlid -- ;
			} catch (IOException e) {
				logger.warn(e.getMessage(),e);
			}
			
			if( htmlList.size() != 0 && htmlList.size() % 1000 == 0 ){
				
				filename = getTimestamp();
				logger.info("writing data into file "+ filename +"...");
				htmlWriter.dumpHtml2File(htmlList, filename);
				logger.info("end writing file "+ filename +".");
				htmlList.clear();
			}
		}
		filename = getTimestamp();
		htmlWriter.dumpHtml2File(htmlList, filename);
		htmlList.clear();

	}
	public static void sinarun() throws Exception{
		
		String str = "20141106";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date myDate = formatter.parse(str);
	    Calendar c = Calendar.getInstance();
	   
	    Crawler.setHtmlParser( new SinaParser());
	    Crawler.setCharset("gb2312");
	    //原网页输出地址，普通输出
	    Crawler.setFileWriter(new FileWriter("D:/研一课程/sports.sina.com.cn/"));
	    //解析得到的文档XML输出地址
	    Crawler.setHtmlWriter(new XMLFileWriter("D:/研一课程/sina/"));
	    Crawler.setRegex("^http://sports.sina.com.cn/.*$");//设置可以网页递归的正则表达
	  //设置要进行内同解析提取的网页url正则表达
	    Crawler.setQregex("^http://sports.sina.com.cn/.+/20[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}/[0-9]+.shtml$");
	    
	    Crawler.setInterval(1000);
		while( true){
			c.setTime(myDate);
			c.add(Calendar.DATE, -1);
		    myDate = c.getTime();
			Crawler.run("http://sports.sina.com.cn/head/sports"+formatter.format(myDate)+"am.shtml");
			Crawler.run("http://sports.sina.com.cn/head/sports"+formatter.format(myDate)+"pm.shtml");
		}
	}
	
	public static void tencentrun() throws Exception{
	 	Crawler.setCharset("gb2312");
	    //原网页输出地址，普通输出
	    Crawler.setFileWriter(new FileWriter("D:/研一课程/sports.qq.com/"));
	    //解析得到的文档XML输出地址
	    Crawler.setHtmlWriter(new XMLFileWriter("D:/研一课程/qq/"));
	    Crawler.setRegex("^http://sports.qq.com/.*$");//设置可以网页递归的正则表达
	  //设置要进行内同解析提取的网页url正则表达
	    Crawler.setQregex("^http://sports.qq.com/.+/[0-9]{8}/[0-9]+\\.(htm|shtml|html)$");
	    Crawler.setInterval(1000);
	    Crawler.setDepth(7);
	    Crawler.setHtmlParser(new TencentParser());
	    Crawler.run("http://sports.qq.com/");
	}
	
	public static void main(String []args) throws Exception{
		tencentrun();
	}
	
}

