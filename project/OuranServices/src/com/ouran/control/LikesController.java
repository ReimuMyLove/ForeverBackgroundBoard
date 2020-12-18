package com.ouran.control;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Likes;
import com.ouran.model.Tuwen;
import com.ouran.service.CheatService;
import com.ouran.service.LikeService;
import com.ouran.service.MusicService;
import com.ouran.service.TuwenService;

public class LikesController extends Controller {
	LikeService service = new LikeService();
	TuwenService tuwenService = new TuwenService();
	CheatService cheatService = new CheatService();
	MusicService musicService=new MusicService();
	/**
	 * 给图文或话题或音乐或评论点赞
	 */
	public void addlike() {
		if (null != getParaToInt("userid") && null != getParaToInt("tuwenid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setTuwenid(getParaToInt("tuwenid"));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				like.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (service.addlike(like)) {
				tuwenService.addlike(getParaToInt("tuwenid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("topicdetialid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setTopicdetialid(getParaToInt("topicdetialid"));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				like.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (service.addlike(like)) {
				musicService.addlike(getParaToInt("musicid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("cheatid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setCheatid(getParaToInt("cheatid"));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				like.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (service.addlike(like)) {
				cheatService.addlike(getParaToInt("cheatid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("musicid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setMusicid(getParaToInt("musicid"));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				like.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (service.addlike(like)) {
				musicService.addlike(like.getMusicid());
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

	/**
	 * 取消赞
	 */
	public void minuslike() {
		if (null != getParaToInt("userid") && null != getParaToInt("tuwenid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setTuwenid(getParaToInt("tuwenid"));
			if (service.minuslike(like) > 0) {
				tuwenService.minuslike(getParaToInt("tuwenid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("topicdetialid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setTopicdetialid(getParaToInt("topicdetialid"));
			if (service.minuslike(like) > 0) {
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("cheatid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setCheatid(getParaToInt("cheatid"));
			if (service.addlike(like)) {
				cheatService.minuslike(getParaToInt("cheatid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("musicid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setMusicid(getParaToInt("musicid"));
			if (service.minusmusiclike(like)>0) {
				musicService.minuslike(like.getMusicid());
				renderText("true");
			} else {
				renderText("false");
			}
		}
	}

	/**
	 * 陈列出所有给该话题或图文点赞的用户
	 * 
	 */
	public void findlike() {
		if (null != getParaToInt("tuwenid")) {
			renderJson("tuwenlike", service.findtuwenlike(getParaToInt("tuwenid")));
		} else if (null != getParaToInt("topicdetialid")) {
			renderJson("topicdata", service.findtuwenlike(getParaToInt("topicdetalid")));
		} else {
			renderText("false");
		}
	}
	/**
	 * 查找某用户曾经赞过的文章
	 * 
	 * 
	 * */
	public void finduserlike() {
		if (null != getParaToInt("userid")) {
			List<Likes> likes= service.finduserliketuwen(getParaToInt("userid"));
			List<Tuwen> tuwens = new ArrayList<Tuwen>();

			for (Likes like : likes) {
				if (like.getTuwenid() != null) {
					Tuwen tuwen = tuwenService.findTuwen(like.getTuwenid());
					tuwens.add(tuwen);
				} else if (like.getTopicdetialid() != null) {

				} else if (like.getMusicid() != null) {

				}
			}
			setAttr("likedate", likes);
			setAttr("tuwendate", tuwens);
			setAttr("userdate", tuwenService.getUserList(tuwens));
			renderJson();
		}else {
			renderJson("false");
		}
	}
}
