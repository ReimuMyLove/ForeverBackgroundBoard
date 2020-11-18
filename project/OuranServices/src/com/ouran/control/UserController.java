package com.ouran.control;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.ouran.model.User;
import com.ouran.service.UserService;

public class UserController extends Controller{
	UserService service=new UserService();
	public void test() {
		renderJson(Ret.ok("data", service.getAll()));		
	}


	
}
