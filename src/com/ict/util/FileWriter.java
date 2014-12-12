package com.ict.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.io.OutputFormat;

public class FileWriter {
	private Logger logger = Logger.getLogger(FileWriter.class);
	private String path;
	
	public FileWriter(String _path){
		if( _path.endsWith("/"))
			path = _path;
		else
			path = _path +"/";
	}
	
	public boolean write2file(String filename,String html,String charset){
		
		boolean ans = true;
		String path = this.path + filename;
		int idx = path.lastIndexOf("/");
		filename = path.substring(idx,path.length() );
		path = path.substring(0,idx + 1);
		
		if (! FileDirUtil.PathIsExist(path)) {
			
			if ( ! FileDirUtil.CreateDirectory(path)) {
				logger.error("创建文件夹失败");
				return ans = false;
			}
		}
		
		FileOutputStream fOStream = null;
		try {
			fOStream = new FileOutputStream(new File(path + filename ));
			fOStream.write(html.getBytes(charset));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			ans = false;
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			ans = false;
		} finally{
			if( fOStream != null )
				try {
					fOStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					ans = false;
				}
		}
		
		return ans;
	}
}
