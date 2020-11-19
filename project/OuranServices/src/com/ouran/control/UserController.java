package com.ouran.control;

import com.jfinal.core.Controller;
import com.ouran.service.UserService;

public class UserController extends Controller {
	UserService service = new UserService();
	public void find() {
		if (null != getParaToInt("id")) {
			renderJson("data", service.findUser(getParaToInt("id")));
		} else {
			renderJson("data", service.getAll());
		}
	}
	public void delect() {
		if (null != getParaToInt("id")) {
			if(service.delect(getParaToInt("id"))) {
				renderText("true");
			}else {
				renderText("false");
			}
		}else {
			renderText("false");
		}
	}
	//?age=20&name=ls&password=ls&sex=ÄÐ&sign=ìªìª
	public void update() {
		int id=getParaToInt("id");
		int age=getParaToInt("age");
		String name=getPara("name");
		String pas =getPara("password");
		String sex=getPara("sex");
		String sign=getPara("sign");
		service.update(id, name, pas, age, sex, sign);
	}
	public void add() {
		int age=getParaToInt("age");
		String name=getPara("name");
		String pas =getPara("password");
		String sex=getPara("sex");
		String sign=getPara("sign");
		System.out.print(age+name);
		System.out.print(sex+pas);
		System.out.print(sign);
		service.add(name, pas, age, sex, sign);
	}
}
