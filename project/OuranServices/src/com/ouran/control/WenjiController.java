package com.ouran.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.ouran.model.Tuwen;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;
import com.ouran.service.FollowService;
import com.ouran.service.LikeService;
import com.ouran.service.TuwenService;
import com.ouran.service.WenjiDetialService;
import com.ouran.service.WenjiService;

public class WenjiController extends Controller {

	WenjiService service = new WenjiService();
	TuwenService tuwenService=new TuwenService();
	WenjiDetialService detialService=new WenjiDetialService();
	FollowService followService=new FollowService();
	LikeService likeService=new LikeService();
	public void findwenji() {
		renderJson(service.find(getParaToInt("userid")));
	}
	public void findwenjiByUser() {
		renderJson(service.findByUser(getParaToInt("userid")));
	}
	
	public void findwenjiByWenjiid() {
		List<Tuwen>tuwens=new ArrayList<Tuwen>();
		List<WenjiDetail>details= detialService.find(getParaToInt("wenjiid"));
		setAttr("detial", details);
		for (WenjiDetail wenjiDetail : details) {
			Tuwen tuwen=new Tuwen();
			tuwen=tuwenService.findShortTuwen(wenjiDetail.getTuwenId());
			tuwens.add(tuwen);
			System.out.print(""+tuwen);
		}
		setAttr("followdate", followService.getFollows(getParaToInt("userid")));
		setAttr("likedate", likeService.finduserlike(getParaToInt("userid")));
		setAttr("userdate", tuwenService.getUserList(tuwens));
		setAttr("tuwendate", tuwens);
		renderJson();
	}
	public void addwenji() {
		Wenji wenji = new Wenji();
		wenji.setUserid(getParaToInt("userid"));
		wenji.setName(getPara("name"));
		if (getPara("haspic") != null) {
			UploadFile file = getFile();
			String str = file.getFileName();
			if (str != null) {
				wenji.setPic(str);
			}
		}
		wenji.setTuwenNum(0);
		if (service.add(wenji)) {
			List<Wenji>list=service.findByUser(getParaToInt("userid"));
			renderJson(list.get(list.size()-1));
		} else {
			renderText("false");
		}
	}
	/**
	 * 删除文集，文集下面对应的文集detial也被删除
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
				// 获取本地输出流
				String path = getRequest().getServletContext().getRealPath("/imgs/tuwen/");
				OutputStream out = new FileOutputStream(path + name);
				// 循环读写，保存图片
				int len = -1;
				byte[] bytes = new byte[1024];
				while ((len = in.read(bytes)) != -1) {
					out.write(bytes, 0, len);
				}

				System.out.println("接收图片成功");
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
