/**
 * 常用文件或目录操作类
 */
package com.ict.util;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;


public class FileDirUtil {

	static Logger logger = Logger.getLogger(FileDirUtil.class.getName());
	/**
	 * 判断路径是否存在
	 * @param path 需要判断的路径
	 * @return 如果路径存在返回true 否则返回false
	 */
	public static boolean PathIsExist(String path){
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 *逐层创建路径对应的目录
	 * @param path 需要创建的目录
	 * @return 如果创建成功或本来指定的目录存在 返回true 否则返回false
	 */
	public static boolean CreateDirectory(String path){
		
		File file = new File(path);
		if (! file.exists()) {
			return file.mkdirs();
		}
		else {
			return true;
		}
	}
	
	/**
	 * 创建路径对应的文件 如果文件所在目录不存在则循环创建目录然后创建文件
	 * 如果文件对应的目录存在则直接创建文件
	 * 如果文件存在则返回ture
	 * @param path 需要创建路径对应的文件
	 * @return 如果文件存在或文件创建成功返回true 如果文件所在的目录创建失败或文件创建失败则返回false
	 */
	public static boolean CreateFile(String path){
		boolean retVal = false;
		File file = new File(path);
		if (! file.exists()) {
			if (!file.getParentFile().exists()) {
				if (file.getParentFile().mkdirs()) {
					return false;
				}
			}

			try {		
				retVal = file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block	
				retVal = false;
				e.printStackTrace();
			}
			return retVal;

		}
		else {
			return true;
		}
	}
	
	/**
	 * 追加一行或几行字符串到对应的文件中
	 * @param file_path 需要追加的文件目录
	 * @param strings 需要追加的字符串列表
	 * @return 如果路径不合法或追加的文件不存在或写入异常则返回false 写入成功返回true
	 */
	public static boolean Append2FileLine(String file_path,ArrayList<String> strings){
				
		if (null == file_path) {
			logger.error("路径为空");
			return false;
		}	
		if (0 == strings.size()) {
			logger.info("待写入文件的字符串列表为空");
			return true;
		}
		
		RandomAccessFile randomAccessFile = null;
		
		try {
			randomAccessFile = new RandomAccessFile(file_path, "rw");
			long fileLength = randomAccessFile.length();
			randomAccessFile.seek(fileLength);
			
			for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {			
				String urlString = (String) iterator.next();
				randomAccessFile.writeBytes(urlString+"\n");
			}
			randomAccessFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			logger.info("需要追加的文件不存在");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("追加文件异常");
			return false;
		}
		
		return true; 
	} //end Write2FileLine
	
	
	
	
}
