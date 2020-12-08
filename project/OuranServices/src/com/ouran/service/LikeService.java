package com.ouran.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ouran.model.Likes;

public class LikeService {
	private static final Likes dao=Likes.dao;
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
	public List<Record> findtuwenlike(int id) {
		return Db.find("select *from user where id=( select userid from likes where tuwenid="+id+")");
	}
	public List<Likes> findtopiclike(int id) {
		return dao.find("select * from likes where topicdetialid="+id);
	}
	public List<Likes> finduserlike(int id) {
		return dao.find("select *from likes where userid="+id);
	}
	/**
	 * 当某篇文章被删除其所有点赞所有收藏都会被删除
	 * */
	public int deletelike(int tuwenid) {
		return Db.delete("delete from likes where tuwenid ="+tuwenid);
	}
}
