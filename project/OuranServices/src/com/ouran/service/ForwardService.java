package com.ouran.service;

import java.util.List;

import com.ouran.model.Forward;

public class ForwardService {
	private static final Forward dao=Forward.dao;
	
	public List<Forward> getforwad(int id) {
		String sql="select * from forward where tuwenid='"+id+"' or topicdetialid ='"+id+"'";
		return dao.find(sql);
	}
	public boolean add(Forward forward) {
		System.out.print(forward.toString());
		return forward.save();
	}
}
