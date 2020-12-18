package com.ouran.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.ouran.model.Likes;
import com.ouran.model.User;

public class LikeService {
	private static final Likes dao=Likes.dao;
	private UserService service;
	public List<Likes> getLikes(int id) {
		String sql="select * from likes where tuwenid='"+id+"' or topicdetialid ='"+id+"'";
		return dao.find(sql);
	}
	
	public boolean addlike(Likes likes) {
		return likes.save();
	}
	public int minuslike(Likes likes) {
		return Db.delete("DELETE from likes where userid="+likes.getUserid()+" && tuwenid="+likes.getTuwenid());
	}
	
	public int minusmusiclike(Likes likes) {
		return Db.delete("DELETE from likes where userid="+likes.getUserid()+" && musicid="+likes.getMusicid());
	}
	/**
	 * �õ��û����޹����Լ�������
	 * */
	public List<Likes> getMyselfLike(int id) {
		return dao.find("select *from likes where userid="+id +" && tuwenid is not null");
	}
	/**
	 * �õ�ϲ����ƪ���µ�user
	 * */
	public List<User> findtuwenlike(int id) {
		return service.find("select *from user where id=( select userid from likes where tuwenid="+id+")");
	}
	public List<Likes> findtopiclike(int id) {
		return dao.find("select * from likes where topicdetialid="+id);
	}
	/**
	 * �ҵ��û����޹�����������
	 * */
	public List<Likes> finduserlike(int id) {
		return dao.find("select *from likes where userid="+id);
	}
	/**
	 * �ҵ��û����޹�����������
	 * */
	public List<Likes> findusermusiclike(int id) {
		return dao.find("select *from likes where userid ="+id +"&& musicid is not null");
	}	
	/**
	 * ֻ�ҵ��û����޹��Ĵ��ڵ�ͼ��
	 * */
	public List<Likes> finduserliketuwen(int id) {
		return dao.find("select *from likes where userid="+id+" && tuwenid in (SELECT id from tuwen )");
	}
	/**
	 * ��ĳƪ���±�ɾ�������е��������ղض��ᱻɾ��
	 * */
	public int deletelike(int tuwenid) {
		return Db.delete("delete from likes where tuwenid ="+tuwenid);
	}
}
