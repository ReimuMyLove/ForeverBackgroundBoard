package com.ouran.control;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Cheat;
import com.ouran.service.CheatService;
import com.ouran.service.TuwenService;

public class CheatController extends Controller {
	CheatService service = new CheatService();
	TuwenService tuwenService=new TuwenService();
	
	public void addcheat() {
		Date date=new Date();
		Cheat cheat = new Cheat();
		cheat.setTime(date);
		cheat.setLikes(0);
		cheat.setUser1id(getParaToInt("user1id"));
		if (null != getParaToInt("replyid")) {
			cheat.setReplyid(getParaToInt("replyid"));
		}
		cheat.setText(getPara("text"));
		if (null != getParaToInt("topic_detial_id")) {
			cheat.setTopicDetialId(getParaToInt("topic_detial_id"));
		}
		if (null != getParaToInt("tu_id")) {
			tuwenService.addcheat(getParaToInt("tu_id"));
			cheat.setTuId(getParaToInt("tu_id"));
		}
		if (null != getParaToInt("music_id")) {
			cheat.setMusicid(getParaToInt("tu_id"));
		}
		if (service.addcheat(cheat)) {
			renderJson(service.findByid(service.getleastid(cheat.getUser1id())));
		} else {
			renderText("false");
		}
	}

	public void deletecheat() {
		Cheat cheat=service.findByid(getParaToInt("id"));
		if(cheat.getTuId()!=null) {
			tuwenService.minuscheat(cheat.getTuId());
		}
		if (service.deletecheat(getParaToInt("id"))) {
			renderText("true");
		} else {
			renderText("false");
		}
	}

	public void find() {
		if (null != getParaToInt("tu_id")) {
			List<Cheat> list=service.find(getParaToInt("pagenum"),getParaToInt("tu_id"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			renderJson();
		}else if (null!=getParaToInt("musicid")) {
//			renderJson("cheatdate", service.find(getParaToInt("musicid")));
		}else if (null!=getParaToInt("topic_detial_id")) {
//			renderJson("cheatdate", service.find(getParaToInt("topic_detial_id")));
		}else {
			renderText("false");
		}
	}
	public void findbyhot() {
		
	}
}
