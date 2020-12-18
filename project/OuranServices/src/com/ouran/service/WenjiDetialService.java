package com.ouran.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;

public class WenjiDetialService {
	WenjiDetail dao=new WenjiDetail();
	private WenjiService wenjiService=new WenjiService();
	public boolean add(WenjiDetail wenjiDetail) {
		return wenjiDetail.save();
	}
	public int delete(int userid,int tuwenid) {
		List<Wenji>wenjis=wenjiService.findByUser(userid);
		for (Wenji wenji : wenjis) {
			return Db.delete("delete  from wenji_detail where wenjiid="+wenji.getId()+" && tuwen_id="+tuwenid);
		}
		return -1;
	}
	/**
	 * 查找文集下的图文收藏
	 * */
	public List<WenjiDetail> find(int wenjiid) {
		String sql="select *from wenji_detail where wenjiid="+wenjiid +"&& tuwen_id is not null";
		return dao.find(sql);
	}
	public List<WenjiDetail> getusercollectmusic(int userid) {
		return dao.find("SELECT * FROM wenji_detail where wenjiid in ( SELECT id from wenji where userid ="+userid+" && music_id is not null)");
	}
	/**
	 * 
	 * 查询文集下的所有数据，包括音乐
	 * */
	public List<WenjiDetail> findAll(int wenjiid) {
		String sql="select *from wenji_detail where wenjiid="+wenjiid ;
		return dao.find(sql);
	}
	/**
	 * 通过tuwen_id查找收藏
	 * */
	
	public List<WenjiDetail> findBytuwen(int tuwenid) {
		String sql="select *from wenji_detail where tuwen_id="+tuwenid;
		return dao.find(sql);
	}
	/**
	 * 当某篇文章被删除其所有点赞所有收藏都会被删除
	 * */
	public int deleteTuwen(int tuwenid) {
		return Db.delete("delete from wenji_detail where tuwen_id="+tuwenid);
	}
	/**
	 * 删除文集，文集下面对应的文集detial也被删除
	 * */
	public int deletedetial(int wenjiid) {
		return Db.delete("delete from wenji_detail where wenjiid="+wenjiid);
	}
}
