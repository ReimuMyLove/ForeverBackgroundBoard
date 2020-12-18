package com.ouran.control;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Follow;
import com.ouran.model.User;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;
import com.ouran.service.FollowService;
import com.ouran.service.LikeService;
import com.ouran.service.UserService;
import com.ouran.service.WenjiDetialService;
import com.ouran.service.WenjiService;;

public class FollowController extends Controller {
	FollowService service = new FollowService();
	UserService userService = new UserService();
	LikeService likeService = new LikeService();
	WenjiDetialService detialService = new WenjiDetialService();
	WenjiService wenjiService = new WenjiService();

	/**
	 * �õ��û��ķ�˿���� ����json�ַ������ͻ���
	 */
	public void findFens() {
		if (null != getParaToInt("userid")) {
			renderJson("fensdata", service.getFen(getParaToInt("userid")));
		} else {
			renderJson("fensdata", "false");
		}
	}
	/**
	 * �û��Ĺ�ע�б���Ϣ
	 * */
	public void findUserFens() {
		List<Follow> fen=new ArrayList<Follow>();
		List<User>users=new ArrayList<User>();
		fen=service.getFensList(getParaToInt("userid"));
		users=service.getFen(getParaToInt("userid"));
		setAttr("fendate", fen);
		setAttr("userdate", users);
		renderJson();
	}
	/**
	 * �õ��û��Ĺ�ע���� ����json�ַ������ͻ���
	 */
	public void findFollows() {
		if (null != getParaToInt("userid")) {
			List<User> list = service.getFollows(getParaToInt("userid"));
			List<Wenji> list1 = wenjiService.findByUser(getParaToInt("userid"));
			List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
			for (Wenji wenji : list1) {
				List<WenjiDetail> list2 = detialService.find(wenji.getId());
				collectdate.addAll(list2);
			}
			/**
			 * �ҵ��û������ļ� �õ��ļ����ղص�����
			 */
			service.removeDuplicate(collectdate);
			setAttr("collectdate", collectdate);
			setAttr("userdate", list);
			setAttr("tuwendate", service.getUserTuwenList(list, getParaToInt("pagenum")));
			setAttr("likedate", likeService.finduserlike(getParaToInt("userid")));
//			System.out.print(service.getUserTuwenList(list, getParaToInt("pagenum")).isEmpty());
			if (service.getUserTuwenList(list, getParaToInt("pagenum")).isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}
		} else {
			renderJson("followsdata", "false");
		}
	}

	public void findUserFollows() {
		if (null != getParaToInt("userid")) {
			List<User> list = service.getFollows(getParaToInt("userid"));
			setAttr("userdate", list);
//			System.out.print(service.getUserTuwenList(list, getParaToInt("pagenum")).isEmpty());
			renderJson();
		} else {
			renderJson("false");
		}
	}

	/**
	 * �û���ע �û���follownum+1 ����ע���û�fennum+1
	 */
	public void addFollow() {
		if (null != getParaToInt("userid") && null != getParaToInt("followerid")) {
			Follow follow = new Follow();
			follow.setFollowerid(getParaToInt("followerid"));
			follow.setUserid(getParaToInt("userid"));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				follow.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (service.addFollow(follow)) {
				userService.addFellowNum(getParaToInt("userid"));
				userService.addFenNum(getParaToInt("followerid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

	/**
	 * �û�ȡ����ע �û���follownum-1 ����ע���û�fennum-1
	 */
	public void minusFollow() {
		if (null != getParaToInt("userid") && null != getParaToInt("followerid")) {
			Follow follow = new Follow();
			follow.setFollowerid(getParaToInt("followerid"));
			follow.setUserid(getParaToInt("userid"));
			if (service.minusFollow(follow)) {
				userService.minusFellowNum(getParaToInt("userid"));
				userService.minusFenNum(getParaToInt("followerid"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

}
