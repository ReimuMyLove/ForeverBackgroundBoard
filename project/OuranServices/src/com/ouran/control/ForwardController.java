package com.ouran.control;

import com.jfinal.core.Controller;
import com.ouran.model.Forward;
import com.ouran.service.ForwardService;
import com.ouran.service.TuwenService;


public class ForwardController extends Controller{
	ForwardService service =new ForwardService();
	TuwenService service2=new TuwenService();
	public void add() {
		Forward forward=new Forward();
		forward.setTuwenid(getParaToInt("tuwenid"));
		forward.setUserid(getParaToInt("userid"));
		if(service.add(forward)) {
			renderText("true");
			service2.addforward(getParaToInt("tuwenid"));
		}else {
			renderText("false");
		}
	}
	public void find() {
		service.getforwad(getParaToInt("tuwenid"));
//		service2.addforward(getParaToInt("tuwenid"));
	}
}
