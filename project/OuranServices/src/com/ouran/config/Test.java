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
		// ����ͼƬ���ӵ�ַ
		String webURL="https://api.imjad.cn/cloudmusic/?type=detail&id=1421337352";
		// ���ر����ַ
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
			// ����URL
			URL url = new URL(webURL);
			// ����������
			URLConnection connection = url.openConnection();
			// ������
			InputStream inputStream = connection.getInputStream();
			// ����ļ���
			OutputStream outputStream = new FileOutputStream(new File(localPath));
			// ����������
			
			byte[] b = new byte[1024];
			// ��ȡ������
			int len;
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
			// �ر�����������
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// �ر����������
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// �������سɹ�
		} catch (Exception e) {
			// ��������ʧ��
			e.printStackTrace();
		}
	}
	
	public static void downLoadJpg(String webURL) {
		try {
			// ����URL
			URL url = new URL(webURL);
			// ����������
			URLConnection connection = url.openConnection();
			// ������
			InputStream inputStream = connection.getInputStream();
			
			// ����ļ���
			// ����������
			String string="";
			byte[] b = new byte[1024];
			// ��ȡ������
			int len;
			while ((len = inputStream.read(b)) != -1) {
				string+=new String(b,0,len, "utf-8");
			}
			System.out.print(string);
			// �ر�����������
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// �ر����������
			// �������سɹ�
		} catch (Exception e) {
			// ��������ʧ��
			e.printStackTrace();
		}
	}
}