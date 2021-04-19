package com.ouran.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Cheat;
import com.ouran.model.Tuwen;
import com.ouran.model.User;
import com.ouran.service.CheatService;
import com.ouran.service.LikeService;
import com.ouran.service.TuwenService;
import com.ouran.service.UserService;

public class CheatController extends Controller {
	CheatService service = new CheatService();
	TuwenService tuwenService=new TuwenService();
	UserService uService=new UserService();
	LikeService likeService=new LikeService();
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
			renderJson(service.findByid(service.getleastid(cheat.getTuId(),cheat.getUser1id())));
		} else {
			renderText("false");
		}
	}
	/**
	 * 所有用户收到的评论
	 * uers的最后一个为他自己
	 * */
	public void getAllCheatByUser() {
		List<Cheat> cheats=service.getAllCheatsByUser(getParaToInt("pagenum"), getParaToInt("userid"));
		List<User>users=new ArrayList<User>();
		List<Tuwen>tuwens=new ArrayList<Tuwen>();
		for (Cheat cheat : cheats) {
			User user=new User();
			user=uService.findUser(cheat.getUser1id());
			Tuwen tuwen=new Tuwen();
			tuwen=tuwenService.findShortTuwen(cheat.getTuId());
			tuwens.add(tuwen);
			users.add(user);
//			System.out.print(b);
		}
		service.removeDuplicate(users);
		service.removeDuplicate(tuwens);
		setAttr("user", users);
		users.add(uService.findUser(getParaToInt("userid")));
		setAttr("tuwen", tuwens);
		setAttr("cheat", cheats);
		setAttr("like", likeService.getMyselfLike(getParaToInt("userid")));
		renderJson();
	}
	/**
	 * 用户进行的所有评论
	 * */
	public void getUserCheat() {
		List<Cheat> cheats=service.getUserCheat( getParaToInt("userid"));
		List<Tuwen>tuwens=service.geTuwens(cheats);
		setAttr("cheatdate", cheats);
		setAttr("tuwendate", tuwens);
		setAttr("userdate", tuwenService.getUserList(tuwens));
		renderJson();
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
