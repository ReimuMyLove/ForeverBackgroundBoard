package com.ouran.control;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Likes;
import com.ouran.model.Tuwen;
import com.ouran.service.CheatService;
import com.ouran.service.LikeService;
import com.ouran.service.TuwenService;

public class LikesController extends Controller {
	LikeService service = new LikeService();
	TuwenService tuwenService = new TuwenService();
	CheatService cheatService = new CheatService();

	/**
	 * ��ͼ�Ļ�������ֻ����۵���
	 */
	public void addlike() {
		if (null != getParaToInt("userid") && null != getParaToInt("tuwenid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setTuwenid(getParaToInt("tuwenid"));
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
			if (service.addlike(like)) {
				renderText("true");
			} else {
				renderText("false");
			}
		} else if (null != getParaToInt("userid") && null != getParaToInt("cheatid")) {
			Likes like = new Likes();
			like.setUserid(getParaToInt("userid"));
			like.setCheatid(getParaToInt("cheatid"));
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
			if (service.addlike(like)) {
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

	/**
	 * ȡ����
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
			if (service.addlike(like)) {
				renderText("true");
			} else {
				renderText("false");
			}
		}
	}

	/**
	 * ���г����и��û����ͼ�ĵ��޵��û�
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
	 * ����ĳ�û������޹�������
	 * 
	 * 
	 * */
	public void finduserlike() {
		if (null != getParaToInt("userid")) {
			List<Likes> likes= service.finduserlike(getParaToInt("userid"));
			List<Tuwen> tuwens = new ArrayList<Tuwen>();

			for (Likes like : likes) {
				if (like.getTuwenid() != null) {
					Tuwen tuwen = tuwenService.findTuwen(like.getTuwenid());
					tuwens.add(tuwen);
				} else if (like.getTopicdetialid() != null) {

				} else if (like.getMusicid() != null) {

				}
			}
			setAttr("tuwendate", tuwens);
			renderJson();
		}else {
			renderJson("false");
		}
	}
}
