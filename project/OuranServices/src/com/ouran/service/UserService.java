package com.ouran.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.ouran.model.User;

public class UserService {
	private static final User dao=User.dao;
	public List<User> getAll(){
		return dao.findAll();
	}
	public Boolean delect(int id){
		return dao.deleteById(id);
	}
	public List<User> findUser(int pageNumber,String name) {
		Page<User> page=dao.paginate(pageNumber, 10, "select * ","from user where name like '%" + name + "%'");
		return page.getList();
	}
	public User findUser(int id) {
		return dao.findById(id);
	}
	public void update(int id,String name,String pas,int age,String sex,String sign) {
		dao.findById(id).setName(name).setAge(age).setPassword(pas).setSex(sex).setPicname(name+".jpg").setSign(sign).update();
	}
	
	public boolean add(User user) {
		return user.save();
	}
	public void addFenNum(int id) {
		User user=dao.findById(id);
		user.setFennum(user.getFennum()+1);
		user.update();
	}
	public void addFellowNum(int id) {
		User user=dao.findById(id);
		user.setFollownum(user.getFollownum()+1);
		user.update();
	}
	public void minusFenNum(int id) {
		User user=dao.findById(id);
		user.setFennum(user.getFennum()-1);
		user.update();
	}
	public void minusFellowNum(int id) {
		User user=dao.findById(id);
		user.setFollownum(user.getFollownum()-1);
		user.update();
	}
	public List<User> findAlluser() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
}
