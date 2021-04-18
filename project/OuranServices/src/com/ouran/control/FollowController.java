package com.ouran.control;

import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Follow;
import com.ouran.model.User;
import com.ouran.service.FollowService;
import com.ouran.service.UserService;;

public class FollowController extends Controller {
	FollowService service = new FollowService();
	UserService userService=new UserService();
	/**
	 * �õ��û��ķ�˿����
	 * ����json�ַ������ͻ���
	 * */
	public void findFens() {
		if (null != getParaToInt("userid")) {
			renderJson("fensdata", service.getFen(getParaToInt("userid")));
		} else {
			renderJson("fensdata", "false");
		}
	}
	/**
	 *�õ��û��Ĺ�ע����
	 * ����json�ַ������ͻ���
	 * */
	public void findFollows() {
		if (null != getParaToInt("userid")) {
			List<User> list= service.getFollows(getParaToInt("userid"));
			setAttr("userdate", list);
			setAttr("tuwendate", service.getUserTuwenList(list, getParaToInt("pagenum")));
//			System.out.print(service.getUserTuwenList(list, getParaToInt("pagenum")).isEmpty());
			if(service.getUserTuwenList(list, getParaToInt("pagenum")).isEmpty()) {
				renderText("");
			}
			else {
				renderJson();
			}
		} else {
			renderJson("followsdata", "false");
		}
	}
	/**
	 * �û���ע
	 * �û���follownum+1
	 * ����ע���û�fennum+1
	 * */
	public void addFollow() {
		if(null!=getParaToInt("userid")&&null!=getParaToInt("followerid")) {
			Follow follow=new Follow();
			follow.setFollowerid(getParaToInt("followerid"));
			follow.setUserid(getParaToInt("userid"));
			if(service.addFollow(follow)) {
				userService.addFellowNum(getParaToInt("userid"));
				userService.addFenNum(getParaToInt("followerid"));
				renderText("true");
			}else {
				renderText("false");
			}
		}else {
			renderText("false");
		}
	}
	/**
	 * �û�ȡ����ע
	 * �û���follownum-1
	 * ����ע���û�fennum-1
	 * */
	public void minusFollow() {
		if(null!=getParaToInt("userid")&&null!=getParaToInt("followerid")) {
			Follow follow=new Follow();
			follow.setFollowerid(getParaToInt("followerid"));
			follow.setUserid(getParaToInt("userid"));
			if(service.minusFollow(follow)) {
				userService.minusFellowNum(getParaToInt("userid"));
				userService.minusFenNum(getParaToInt("followerid"));
				renderText("true");
			}else {
				renderText("false");
			}
		}else {
			renderText("false");
		}
	}
	
}
