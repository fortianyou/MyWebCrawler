/**
 * �����ļ���Ŀ¼������
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
	 * �ж�·���Ƿ����
	 * @param path ��Ҫ�жϵ�·��
	 * @return ���·�����ڷ���true ���򷵻�false
	 */
	public static boolean PathIsExist(String path){
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 *��㴴��·����Ӧ��Ŀ¼
	 * @param path ��Ҫ������Ŀ¼
	 * @return ��������ɹ�����ָ����Ŀ¼���� ����true ���򷵻�false
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
	 * ����·����Ӧ���ļ� ����ļ�����Ŀ¼��������ѭ������Ŀ¼Ȼ�󴴽��ļ�
	 * ����ļ���Ӧ��Ŀ¼������ֱ�Ӵ����ļ�
	 * ����ļ������򷵻�ture
	 * @param path ��Ҫ����·����Ӧ���ļ�
	 * @return ����ļ����ڻ��ļ������ɹ�����true ����ļ����ڵ�Ŀ¼����ʧ�ܻ��ļ�����ʧ���򷵻�false
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
	 * ׷��һ�л����ַ�������Ӧ���ļ���
	 * @param file_path ��Ҫ׷�ӵ��ļ�Ŀ¼
	 * @param strings ��Ҫ׷�ӵ��ַ����б�
	 * @return ���·�����Ϸ���׷�ӵ��ļ������ڻ�д���쳣�򷵻�false д��ɹ�����true
	 */
	public static boolean Append2FileLine(String file_path,ArrayList<String> strings){
				
		if (null == file_path) {
			logger.error("·��Ϊ��");
			return false;
		}	
		if (0 == strings.size()) {
			logger.info("��д���ļ����ַ����б�Ϊ��");
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
			logger.info("��Ҫ׷�ӵ��ļ�������");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("׷���ļ��쳣");
			return false;
		}
		
		return true; 
	} //end Write2FileLine
	
	
	
	
}
