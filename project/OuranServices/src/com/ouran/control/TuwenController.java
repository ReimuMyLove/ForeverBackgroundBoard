package com.ouran.control;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.jfinal.core.Controller;
import com.ouran.model.Tuwen;
import com.ouran.service.TuwenService;


public class TuwenController extends Controller {
	TuwenService service = new TuwenService();
	//增删改查
	public void find() {
		if(null!=getParaToInt("id")) {
			renderJson("date", service.findTuwen(getParaToInt("id")));
		}else {
			renderJson("date",service.getAll());
		}
	}
	public void add() {
		Tuwen tuwen = new Tuwen();
		if (null != getPara("title")) {
			String title = getPara("title");
			tuwen.setTitle(title);
		}
		if (null != getPara("writer")) {
			String writer = getPara("writer");
			tuwen.setWriter(writer);
		}
		int userid = getParaToInt("userid");
		int typeid = getParaToInt("typeid");
		int likes = getParaToInt("likes");
		int isoriginal = getParaToInt("isoriginal");
		String text = getPara("text");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = formatter.format(date);
		tuwen.setUserid(userid);
		tuwen.setTypeid(typeid);
		tuwen.setLikes(likes);
		try {
			//直接生成的date对象只能得到年月日得不到时分秒
			tuwen.setTime(formatter.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tuwen.setIsoriginal(isoriginal);
		tuwen.setText(text);
		if(service.add(tuwen)) {
			renderText("true");
		}else {
			renderText("false");
		}
		
	}
}
