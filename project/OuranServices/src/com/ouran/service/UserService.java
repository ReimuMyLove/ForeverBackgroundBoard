package com.ouran.service;

import java.util.List;

import com.ouran.model.User;

public class UserService {
	private static final User dao=User.dao;
	public List<User> getAll(){
		return dao.findAll();
	}
}
