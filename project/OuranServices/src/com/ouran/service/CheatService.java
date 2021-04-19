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
	 * ͨ��ͼ�ĵõ��û����й�������
	 * */
	public List<Cheat> find(int pagenum,int id) {
		Page<Cheat> page=dao.paginate(pagenum, 10, "select *"," from cheat where tu_id="+id);
		return page.getList();
	}
	public boolean deletecheat(int id) {
		return dao.deleteById(id);
	}
	/**
	 * �õ����û���������������½��е����� (ע��:�������Լ����Լ�������)
	 * */
	public List<Cheat> getAllCheatsByUser(int pagenum,int userid) {
		Page<Cheat> page=dao.paginate(pagenum, 10, "select* ","from cheat where tu_id in (select id from tuwen where userid="+userid+" ) && user1id != "+userid+" ORDER BY time DESC");
		return page.getList();
	}
	/**
	 * �õ��û����й�������
	 * */
	public List<Cheat> getUserCheat(int userid) {
		List<Cheat> page=dao.find("select* "+"from cheat where user1id= "+userid+" ORDER BY time DESC");
		return page;
	}
	/**
	 * ͨ��ͼ�ĵõ��û����й�������
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
	 * ͨ��cheat�õ�ͼ���б�
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
	 * ��ĳƪ���±�ɾ�������е��������ղض��ᱻɾ��
	 * */
	public int deleteallcheat(int tuwenid) {
		return Db.delete("delete from likes where tu_id ="+tuwenid);
	}
	/**
	 * �õ����۵��û�
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
	 * �õ��û����һ������
	 * */
	public int getleastid(int tuwenid,int userid) {
		List<Cheat> list=dao.find("select *from cheat where user1id="+userid+" && tu_id="+tuwenid);
		return list.get(list.size()-1).getId();
	}
	/**
	 * list	ȥ��
	 * */
	public  List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	} 
}
