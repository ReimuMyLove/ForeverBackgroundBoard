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
	
	public  boolean addcheat(Cheat cheat) {
		return cheat.save();
	}
	public Cheat findByid(int id) {
		return dao.findById(id);
	}
	public List<Cheat> find(int pagenum,int id) {
		Page<Cheat> page=dao.paginate(pagenum, 10, "select *"," from cheat where tu_id="+id);
		return page.getList();
	}
	public boolean deletecheat(int id) {
		return dao.deleteById(id);
	}
	
	public void addlike(int id) {
		Cheat cheat = dao.findById(id);
		cheat.setLikes(cheat.getLikes() + 1);
		cheat.update();
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
	public List getUserList(List<Cheat> list) {
		List<User> list2 = new ArrayList<User>();
		for (Cheat cheat : list) {
			int id = cheat.getUser1id();
			User user = userService.findUser(id);
			list2.add(user);
		}
		return removeDuplicate(list2);
	}
	public int getleastid(int userid) {
		List<Cheat> list=dao.find("select *from cheat where user1id="+userid);
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
