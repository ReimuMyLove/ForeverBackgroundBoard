package com.ouran.service;

import java.util.List;

import com.ouran.model.User;

public class UserService {
	private static final User dao=User.dao;
	public List<User> getAll(){
		return dao.findAll();
	}
	public Boolean delect(int id){
		return dao.deleteById(id);
	}
	public User findUser(int id) {
		return dao.findById(id);
	}
	public void update(int id,String name,String pas,int age,String sex,String sign) {
		dao.findById(id).setName(name).setAge(age).setPassword(pas).setSex(sex).setPicname(name+".jpg").setSign(sign).update();
	}
	
	public void add(String name,String pas,Integer age,String sex,String sign) {
		new User().set("name",name).set("password",pas).set("age", age).set("sex",sex).set("sign", sign).set("picname", name+".jpg").save();
	}
}
