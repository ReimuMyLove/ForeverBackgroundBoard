package com.ouran.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.ouran.model.Cheat;
import com.ouran.model.Tuwen;
import com.ouran.model.User;

public class CheatService {
	private static final Cheat dao = Cheat.dao;
	private UserService userService=new UserService();
	private TuwenService tuwenService=new TuwenService();
	public  boolean addcheat(Cheat cheat) {
		return cheat.save();
	}
	public Cheat findByid(int id) {
		return dao.findById(id);
	}
	/**
	 * 通过图文得到用户进行过的评论
	 * */
	public List<Cheat> find(int pagenum,int id) {
		Page<Cheat> page=dao.paginate(pagenum, 10, "select *"," from cheat where tu_id="+id);
		return page.getList();
	}
	public boolean deletecheat(int id) {
		return dao.deleteById(id);
	}
	/**
	 * 得到对用户发表过的所有文章进行的评论 (注意:不加载自己对自己的评论)
	 * */
	public List<Cheat> getAllCheatsByUser(int pagenum,int userid) {
		Page<Cheat> page=dao.paginate(pagenum, 10, "select* ","from cheat where tu_id in (select id from tuwen where userid="+userid+" ) && user1id != "+userid+" ORDER BY time DESC");
		return page.getList();
	}
	/**
	 * 得到用户进行过的评论
	 * */
	public List<Cheat> getUserCheat(int userid) {
		List<Cheat> page=dao.find("select* "+"from cheat where user1id= "+userid+" ORDER BY time DESC");
		return page;
	}
	/**
	 * 通过图文得到用户进行过的评论
	 * */
	public List<Cheat> find(int id) {
		List<Cheat> page=dao.find("select *"+" from cheat where tu_id="+id);
		return page;
	}
	public void addlike(int id) {
		Cheat cheat = dao.findById(id);
		cheat.setLikes(cheat.getLikes() + 1);
		cheat.update();
	}
	/**
	 * 通过cheat得到图文列表
	 * */
	public List<Tuwen> geTuwens(List<Cheat>cheats) {
		List<Tuwen>tuwens=new ArrayList<Tuwen>();
		for (Cheat cheat : cheats) {
			Tuwen tuwen=tuwenService.findShortTuwen(cheat.getTuId());
			tuwens.add(tuwen);
		}
		return tuwens;
	}
	public void minuslike(int id) {
		Cheat cheat = dao.findById(id);
		cheat.setLikes(cheat.getLikes() - 1);
		cheat.update();
	}
	/**
	 * 当某篇文章被删除其所有点赞所有收藏都会被删除
	 * */
	public int deleteallcheat(int tuwenid) {
		return Db.delete("delete from likes where tu_id ="+tuwenid);
	}
	/**
	 * 得到评论的用户
	 * */
	public List getUserList(List<Cheat> list) {
		List<User> list2 = new ArrayList<User>();
		for (Cheat cheat : list) {
			int id = cheat.getUser1id();
			User user = userService.findUser(id);
			list2.add(user);
		}
		return removeDuplicate(list2);
	}
	/**
	 * 得到用户最后一条评论
	 * */
	public int getleastid(int tuwenid,int userid) {
		List<Cheat> list=dao.find("select *from cheat where user1id="+userid+" && tu_id="+tuwenid);
		return list.get(list.size()-1).getId();
	}
	/**
	 * list	去重
	 * */
	public  List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	} 
}
