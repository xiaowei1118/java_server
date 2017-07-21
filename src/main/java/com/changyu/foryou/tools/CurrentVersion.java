package com.changyu.foryou.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CurrentVersion {
	private String android_version;
	private String ios_version;

	private static String getFilePath(){
		String path=CurrentVersion.class.getClass().getResource("/").getPath();
		//截去一些前面6个无用的字符  
		path=path.substring(6,path.length());  
		//将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）  
		path=path.replaceAll("%20", " ");  
		//查找“WEB-INF”在该字符串的位置  
		int num = path.indexOf("WEB-INF");  
		//截取即可 
		path=path.substring(0, num+"WEB-INF".length());
		return path;
	}
	
	public static String readAndroidVerson(){

		String path=getFilePath()+"/android-version.txt";
		File file = new File(path);

		BufferedReader reader = null;
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			StringBuffer contentBuffer=new StringBuffer();


			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				contentBuffer.append(tempString);
			}
			reader.close();
			return contentBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
	}

	public static boolean writeIosVersion(){

		return false;
	}

	public static boolean readIosVerson(){
		return false;
	}
}
