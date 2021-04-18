package com.ouran.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ouran.model.Follow;
import com.ouran.model.Tuwen;
import com.ouran.model.User;

public class FollowService {
	private static final Follow dao = Follow.dao;
	private static final User userdao = User.dao;
	private TuwenService tuwenService=new TuwenService();
	private List<User> fens = new ArrayList<User>();
	private List<User> follows = new ArrayList<User>();

	/**
	 * jfinal使用sql语句查询时总是在占位符那报错 [{followerid:3, userid:2}, {followerid:3,
	 * userid:4}]2 Sql: select * from `user` where `id` = ? 明明可以得到uersid的值为2
	 * 但sql语句总查询不到占位符的值 jfinal官方推荐直接使用sql语句查询
	 */
	public List<User> getFollows(int id) {
		String sql = "select * from follow where userid=" + id;
		List<Follow> followsid = dao.find(sql);
		for (Follow follow : followsid) {
			String sql1 = "select * from user where id=" + follow.getFollowerid();
			User user = userdao.find(sql1).get(0);
			follows.add(user);
		}
		return follows;
	}

	public List<User> getFen(int id) {
		String sql = "select * from follow where followerid=" + id;
		List<Follow> fensid = dao.find(sql);
		for (Follow follow : fensid) {
			String sql1 = "select * from user where id=" + follow.getUserid();
			User user = userdao.find(sql1).get(0);
			fens.add(user);
		}
		return fens;
	}

	public boolean addFollow(Follow follow) {
		return follow.save();
	}

	public boolean minusFollow(Follow follow) {
		return follow.delete();
	}

	public List<Tuwen> getUserTuwenList(List<User> list, int pageNumber) {
		List<Tuwen> list2 = new ArrayList<Tuwen>();
		for (User user : list) {
			int id = user.getId();
			List<Tuwen> tuwen = tuwenService.findTuwenByuser(pageNumber, id);
			list2.addAll(tuwen);
		}

		Collections.sort(list2, new Comparator<Tuwen>() {
			@Override
			public int compare(Tuwen o1, Tuwen o2) {
				// TODO Auto-generated method stub
				if (o1.getTime().compareTo(o2.getTime()) > 0) {
					return 1;
				} else if (o1.getTime().compareTo(o2.getTime()) == 0) {
					return 0;
				}
				return -1;
			}
		});
		return (list2);
	}
}
