package com.ouran.config;

import java.io.*;

import java.net.URL;

import java.net.URLConnection;

import com.jfinal.json.FastJson;
import com.jfinal.json.JFinalJson;
import com.jfinal.kit.JsonKit;

/**
 * @author test
 */
public class Test {
	public static void main(String[] args) throws IOException {
		// 网络图片链接地址
		String webURL="https://api.imjad.cn/cloudmusic/?type=detail&id=1421337352";
		// 本地保存地址
//		downLoadJpg(webURL);
		//		FastJson.getJson().parse(webURL, null);
	//	downLoadJpg(webURL, "D:xx.txt");
		FileReader r;
		String str="";
		try {
			r = new FileReader("D:xx.txt");
			char[] ch=new char[100];
			int len=0;
			while((len=r.read(ch))!=-1) {
				str+=new String(ch,0,len);
			}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(str);
	}

	public static void downLoadJpg(String webURL, String localPath) {
		try {
			// 网络URL
			URL url = new URL(webURL);
			// 打开网络连接
			URLConnection connection = url.openConnection();
			// 输入流
			InputStream inputStream = connection.getInputStream();
			// 输出文件流
			OutputStream outputStream = new FileOutputStream(new File(localPath));
			// 缓冲区对象
			
			byte[] b = new byte[1024];
			// 读取计数器
			int len;
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
			// 关闭输入流操作
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 关闭输出流操作
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 监听下载成功
		} catch (Exception e) {
			// 监听下载失败
			e.printStackTrace();
		}
	}
	
	public static void downLoadJpg(String webURL) {
		try {
			// 网络URL
			URL url = new URL(webURL);
			// 打开网络连接
			URLConnection connection = url.openConnection();
			// 输入流
			InputStream inputStream = connection.getInputStream();
			
			// 输出文件流
			// 缓冲区对象
			String string="";
			byte[] b = new byte[1024];
			// 读取计数器
			int len;
			while ((len = inputStream.read(b)) != -1) {
				string+=new String(b,0,len, "utf-8");
			}
			System.out.print(string);
			// 关闭输入流操作
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 关闭输出流操作
			// 监听下载成功
		} catch (Exception e) {
			// 监听下载失败
			e.printStackTrace();
		}
	}
}