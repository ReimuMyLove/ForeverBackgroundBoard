package com.ouran.service;

import java.util.List;

import com.ouran.model.Wenji;

public class WenjiService {
	Wenji dao = new Wenji().dao();

	public boolean add(Wenji wenji) {
		return wenji.save();
	}

	public boolean delete(int id) {
		return dao.deleteById(id);
	}
/**
 * 存储图片时注意图片重名问题
 * 
 * */
	public boolean update(Wenji wenji) {
		if (null != wenji.getPic()) {
			return wenji.findById(wenji.getId()).setName(wenji.getName()).setPic(wenji.getPic()).update();
		}else {
			return wenji.findById(wenji.getId()).setName(wenji.getName()).update();
		}
	}
	public List<Wenji> findByUser(int userid) {
		return dao.find("select * from wenji where userid="+userid);
	}
	public Wenji find(int wenjiid) {
		return dao.findById(wenjiid);
	}
}
