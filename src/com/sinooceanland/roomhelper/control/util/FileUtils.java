package com.sinooceanland.roomhelper.control.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import android.os.Environment;

public class FileUtils {


	/**
	 * 根据文件名与文件夹名创建文件
	 * @param dirUrl
	 * @param fileName
	 * @return
	 */
	public static File getOrNewImageFile(String dirUrl, String fileName) {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 判断是否存在SD卡
			return null;
		}
		File dirFile = new File(dirUrl);// 创建存储目录
		if (!dirFile.exists()) {// 存储目录是否存在，不存在则穿件
			dirFile.mkdirs();
		}
		File platCarFile = new File(dirFile + fileName);
		return platCarFile;
	}
	/**
	 * 往车辆平台文件中写入json数据
	 */
	public static void write2file(File file,byte[] responseBody) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(file));
			out.print(responseBody);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (out != null) {
			out.close();
		}
	}

	/**
	 * 往车辆平台文件中写入json数据
	 */
	public static void write2PlatCarFile(File platCarFile, String json) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(platCarFile));
			out.print(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (out != null) {
			out.close();
		}
	}
	//删除文件和目录
	  public static void clearFiles(String workspaceRootPath){
	     File file = new File(workspaceRootPath);
	       if(file.exists()){
	          deleteFile(file);
	     }
	 }
	 private static void deleteFile(File file){
	      if(file.isDirectory()){
	           File[] files = file.listFiles();
	           for(int i=0; i<files.length; i++){
	                deleteFile(files[i]);
	           }
	      }
	      file.delete();
	 }
	@SuppressWarnings("resource")
	public static String getPlatFormCarFileContent(File platCarFile) {
		FileInputStream inputStream;
		byte[] buffer;
		String str = "";
		try {
			inputStream = new FileInputStream(platCarFile);
			buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			str = new String(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	
}