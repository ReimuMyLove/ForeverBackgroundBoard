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
	 * 得到用户点赞过的自己的文章
	 * */
	public List<Likes> getMyselfLike(int id) {
		return dao.find("select *from likes where userid="+id +" && tuwenid is not null");
	}
	/**
	 * 得到喜欢这篇文章的user
	 * */
	public List<User> findtuwenlike(int id) {
		return service.find("select *from user where id=( select userid from likes where tuwenid="+id+")");
	}
	public List<Likes> findtopiclike(int id) {
		return dao.find("select * from likes where topicdetialid="+id);
	}
	/**
	 * 找到用户点赞过的所有数据
	 * */
	public List<Likes> finduserlike(int id) {
		return dao.find("select *from likes where userid="+id);
	}
	/**
	 * 找到用户点赞过的音乐数据
	 * */
	public List<Likes> findusermusiclike(int id) {
		return dao.find("select *from likes where userid ="+id +"&& musicid is not null");
	}	
	/**
	 * 只找到用户点赞过的存在的图文
	 * */
	public List<Likes> finduserliketuwen(int id) {
		return dao.find("select *from likes where userid="+id+" && tuwenid in (SELECT id from tuwen )");
	}
	/**
	 * 当某篇文章被删除其所有点赞所有收藏都会被删除
	 * */
	public int deletelike(int tuwenid) {
		return Db.delete("delete from likes where tuwenid ="+tuwenid);
	}
}
