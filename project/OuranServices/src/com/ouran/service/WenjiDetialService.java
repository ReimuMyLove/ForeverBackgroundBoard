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
	public List<WenjiDetail> find(int wenjiid) {
		String sql="select *from wenji_detail where wenjiid="+wenjiid;
		return dao.find(sql);
	}
	/**
	 * ��ĳƪ���±�ɾ�������е��������ղض��ᱻɾ��
	 * */
	public int deleteTuwen(int tuwenid) {
		return Db.delete("delete from wenji_detail where tuwen_id="+tuwenid);
	}
	/**
	 * ɾ���ļ����ļ������Ӧ���ļ�detialҲ��ɾ��
	 * */
	public int deletedetial(int wenjiid) {
		return Db.delete("delete from wenji_detail where wenjiid="+wenjiid);
	}
}
