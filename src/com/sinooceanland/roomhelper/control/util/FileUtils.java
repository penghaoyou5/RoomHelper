package com.sinooceanland.roomhelper.control.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

	/**
	 * 根据文件名与文件夹名创建文件
	 * 
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
		File platCarFile = new File(dirFile,fileName);
		return platCarFile;
	}

	public static  void saveFile(Bitmap bm,File dirFile) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/**
	 * 写入图片数据
	 */
	public static void write2file(File file, byte[] responseBody) {
		if(true){
			FileOutputStream outputStream = null;

			try{
//				outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
				outputStream = new FileOutputStream(file);
				outputStream.write(responseBody);
				outputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}



		PrintStream out = null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			out = new PrintStream(new FileOutputStream(file));
			out.print(responseBody);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	// 删除文件和目录
	public static void clearFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		if (file.exists()) {
			deleteFile(file);
		}
	}

	private static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
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

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deletefile(String delpath) throws Exception {
		try {

			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + File.separator + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						System.out
								.println(delfile.getAbsolutePath() + "删除文件成功");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + File.separator + filelist[i]);
					}
				}
				System.out.println(file.getAbsolutePath() + "删除成功");
				file.delete();
			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 输出某个文件夹下的所有文件夹和文件路径
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			System.out.println("遍历的路径为：" + file.getAbsolutePath());
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时（即文件夹下有子文件时），返回 true
			if (!file.isDirectory()) {
				System.out.println("该文件的绝对路径：" + file.getAbsolutePath());
				System.out.println("名称：" + file.getName());
			} else if (file.isDirectory()) {
				// 得到目录中的文件和目录
				String[] filelist = file.list();
				if (filelist.length == 0) {
					System.out.println(file.getAbsolutePath()
							+ "文件夹下，没有子文件夹或文件");
				} else {
					System.out
							.println(file.getAbsolutePath() + "文件夹下，有子文件夹或文件");
				}
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + File.separator + filelist[i]);
					System.out.println("遍历的路径为：" + readfile.getAbsolutePath());
					if (!readfile.isDirectory()) {
						System.out.println("该文件的路径："
								+ readfile.getAbsolutePath());
						System.out.println("名称：" + readfile.getName());
					} else if (readfile.isDirectory()) {
						System.out.println("-----------递归循环-----------");
						readfile(filepath + File.separator + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile() Exception:" + e.getMessage());
		}
		return true;
	}

//	public static void main(String[] args) {
//		try {
//			// readfile("D:/file");
//			deletefile("D:/file");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		System.out.println("ok");
//	}

}
