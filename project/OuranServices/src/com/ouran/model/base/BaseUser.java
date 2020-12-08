package com.ouran.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setPicname(java.lang.String picname) {
		set("picname", picname);
		return (M)this;
	}
	
	public java.lang.String getPicname() {
		return getStr("picname");
	}

	public M setAge(java.lang.Integer age) {
		set("age", age);
		return (M)this;
	}
	
	public java.lang.Integer getAge() {
		return getInt("age");
	}

	public M setSex(java.lang.String sex) {
		set("sex", sex);
		return (M)this;
	}
	
	public java.lang.String getSex() {
		return getStr("sex");
	}

	public M setSign(java.lang.String sign) {
		set("sign", sign);
		return (M)this;
	}
	
	public java.lang.String getSign() {
		return getStr("sign");
	}

	public M setFennum(java.lang.Integer fennum) {
		set("fennum", fennum);
		return (M)this;
	}
	
	public java.lang.Integer getFennum() {
		return getInt("fennum");
	}

	public M setFollownum(java.lang.Integer follownum) {
		set("follownum", follownum);
		return (M)this;
	}
	
	public java.lang.Integer getFollownum() {
		return getInt("follownum");
	}

	public M setBackgroundpic1(java.lang.String backgroundpic1) {
		set("backgroundpic1", backgroundpic1);
		return (M)this;
	}
	
	public java.lang.String getBackgroundpic1() {
		return getStr("backgroundpic1");
	}

	public M setBackgroundpic2(java.lang.String backgroundpic2) {
		set("backgroundpic2", backgroundpic2);
		return (M)this;
	}
	
	public java.lang.String getBackgroundpic2() {
		return getStr("backgroundpic2");
	}

}
