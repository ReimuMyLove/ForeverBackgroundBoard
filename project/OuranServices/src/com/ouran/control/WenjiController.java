package com.ouran.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.jfinal.core.Controller;
import com.ouran.model.Wenji;
import com.ouran.service.WenjiDetialService;
import com.ouran.service.WenjiService;

public class WenjiController extends Controller {

	WenjiService service = new WenjiService();
	WenjiDetialService detialService=new WenjiDetialService();
	public void findwenji() {
		renderJson(service.find(getParaToInt("userid")));
	}
	public void addwenji() {
		Wenji wenji = new Wenji();
		wenji.setUserid(getParaToInt("userid"));
		wenji.setName(getPara("name"));
		if (getPara("haspic") != null) {
			wenji.setPic(wenji.getName());
			try {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String time1 = formatter1.format(date);
				InputStream in;
				in = getRequest().getInputStream();
				// ��ȡ���������
				String path = getRequest().getServletContext().getRealPath("/imgs/tuwen/");
				OutputStream out = new FileOutputStream(path + time1+wenji.getName());
				// ѭ����д������ͼƬ
				int len = -1;
				byte[] bytes = new byte[1024];
				while ((len = in.read(bytes)) != -1) {
					out.write(bytes, 0, len);
				}

				System.out.println("����ͼƬ�ɹ�");
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (service.add(wenji)) {
			renderText("true");
		} else {
			renderText("false");
		}
	}
	/**
	 * ɾ���ļ����ļ������Ӧ���ļ�detialҲ��ɾ��
	 * */
	public void deletewenji() {
		if (service.delete(getParaToInt("wenjiid"))) {
			detialService.deletedetial(getParaToInt("wenjiid"));
			renderText("true");
		} else {
			renderText("false");
		}
	}

	public void updatewenji() {
		Wenji wenji = new Wenji();
		int wenjiid=getParaToInt("wenjiid");
		String name=getPara("name");
		wenji.setId(wenjiid);
		wenji.setName(name);
		if (getPara("haspic") != null) {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			wenji.setPic(time1+wenji.getName());
			try {
				InputStream in;
				in = getRequest().getInputStream();
				// ��ȡ���������
				String path = getRequest().getServletContext().getRealPath("/imgs/tuwen/");
				OutputStream out = new FileOutputStream(path + name);
				// ѭ����д������ͼƬ
				int len = -1;
				byte[] bytes = new byte[1024];
				while ((len = in.read(bytes)) != -1) {
					out.write(bytes, 0, len);
				}

				System.out.println("����ͼƬ�ɹ�");
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (service.update(wenji)) {
			renderText("true");
		} else {
			renderText("false");
		}
	}
}
