package com.ouran.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.ouran.model.Likes;
import com.ouran.model.Tuwen;
import com.ouran.model.User;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;
import com.ouran.service.CheatService;
import com.ouran.service.FollowService;
import com.ouran.service.LikeService;
import com.ouran.service.TuwenService;
import com.ouran.service.UserService;
import com.ouran.service.WenjiDetialService;
import com.ouran.service.WenjiService;

public class TuwenController extends Controller {
	TuwenService service = new TuwenService();
	LikeService likeService = new LikeService();
	WenjiDetialService detialService = new WenjiDetialService();
	UserService userService = new UserService();
	WenjiService wenjiService = new WenjiService();
	FollowService followService = new FollowService();
	CheatService cheatService = new CheatService();

	public void recommendbytime() {
		String time = getPara("time");
		String time2 = time.substring(0, time.length() - 1)
				+ (Integer.parseInt(String.valueOf(time.charAt(time.length() - 1))) + 1);
		renderJson(service.findtuwenBytime(time, time2));
	}

//	public void getMessage() {
//		/**
//		 * 得到对用户发表的图文的点赞，收藏，和对用户的关注
//		 */
//		int userid = getParaToInt("userid");
//		List<Tuwen> tuwens = service.findTuwenByuser(userid);
////		List<Likes> likes = new ArrayList<Likes>();
////		List<WenjiDetail> wenjiDetails = new ArrayList<WenjiDetail>();
//		List<Cheat> cheats = new ArrayList<Cheat>();
//		List<User> users = new ArrayList<User>();
////		List<Follow> follows = followService.getFensList(userid);
//		for (Tuwen tuwen : tuwens) {
////			List<Likes> likes1 = likeService.getLikes(tuwen.getId());
////			likes.addAll(likes1);
////			List<WenjiDetail> details = detialService.findBytuwen(tuwen.getId());
////			wenjiDetails.addAll(details);
//			List<Cheat> cList = cheatService.find(tuwen.getId());
//			cheats.addAll(cList);
//		}
////		users.addAll(followService.getFen(userid));
////		System.out.print(likes.toString());
////		for (Likes likes2 : likes) {
////			System.out.print(likes2.toString());
////			User user = userService.findUser(likes2.getUserid());
////			users.add(user);
////		}
////		wenjiDetails.
////		List<Wenji> wList = new ArrayList<Wenji>();
////		for (WenjiDetail detail : wenjiDetails) {
////			wList.add(wenjiService.find(detail.getWenjiid()));
////		}
////		for (Wenji wenji : wList) {
////			User user = userService.findUser(wenji.getUserid());
////			users.add(user);
////		}
//		for(Cheat cheat:cheats) {
//			users.add(userService.findUser(cheat.getUser1id()));
//		}
//		service.removeDuplicate(users);
////		service.removeDuplicate(wList);
////		setAttr("follow", follows);
////		setAttr("like", likes);
////		setAttr("collect", wenjiDetails);
////		setAttr("wenji", wList);
//		setAttr("cheat", cheats);
//		setAttr("user", users);
//		renderJson();
//
//	}
	
	public void getfen() {
		// TODO Auto-generated method stub
		int userid = getParaToInt("userid");
		List<User> follows = followService.getFen(userid);
		renderJson(follows);
	}
	


	// 增删改查
	/**
	 * 通过id查找特定文章 通过title模糊查找 查找所有文章
	 */
	public void find() {
		// 通过id/title查找
		if (null != getParaToInt("id")) {
			renderJson("tuwendate", service.findTuwen(getParaToInt("id")));
		} else if (null != getPara("title")) {
			List<Tuwen> list = service.findTuwen(getParaToInt("pagenum"), getPara("title"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			renderJson();
		} else if (null != getParaToInt("typeid") && null != getParaToInt("userid")) {
			List<Tuwen> list = service.findTuwen(getParaToInt("pagenum"), getParaToInt("typeid"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			setAttr("followdate", followService.getFollows(getParaToInt("userid")));
			setAttr("likedate", likeService.finduserlike(getParaToInt("userid")));

			List<Wenji> list1 = wenjiService.findByUser(getParaToInt("userid"));
			List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
			for (Wenji wenji : list1) {
				List<WenjiDetail> list2 = detialService.find(wenji.getId());
				collectdate.addAll(list2);
			}
			/**
			 * 找到用户所有文集 得到文集里收藏的文章
			 */
			service.removeDuplicate(collectdate);
			setAttr("collectdate", collectdate);
			if (service.getUserList(list).isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}

		} else if (null != getParaToInt("typeid")) {
			List<Tuwen> list1 = service.findTuwen(getParaToInt("pagenum"), getParaToInt("typeid"));
			setAttr("tuwendate", list1);
			setAttr("userdate", service.getUserList(list1));
			if (service.getUserList(list1).isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}

		} else {
			List<Tuwen> list = service.getAll(getParaToInt("pagenum"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			renderJson();
		}
	}

	public void findUserTuwen() {
		if (null != getParaToInt("pagenum") && null != getParaToInt("userid") && null != getParaToInt("typeid")) {
			renderJson("date",
					service.findTuwenByuser(getParaToInt("pagenum"), getParaToInt("userid"), getParaToInt("typeid")));
		} else {
			renderText("null");
		}
	}

	public void getRecommendTuwen() {
		List<Tuwen> list = service.findRecommendTuwen();
		SimpleDateFormat formatter1 = new SimpleDateFormat("MMM d, yyyy  ");

		for (Tuwen tuwen : list) {
			String time = formatter1.format(tuwen.getTime());
			try {
				tuwen.setTime(formatter1.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(tuwen.getTime());
		}
		renderJson(list);
	}

	/**
	 * 热门文章在前 显示近一个月的热门文章
	 */
	public void findByhot() {
		if (null != getParaToInt("userid")) {
			setAttr("followdate", followService.getFollows(getParaToInt("userid")));
			setAttr("likedate", likeService.finduserlike(getParaToInt("userid")));
			List<Tuwen> list = service.findByHot(getParaToInt("pagenum"));
			setAttr("hotdate", list);
			setAttr("userdate", service.getUserList(list));
			List<Wenji> list1 = wenjiService.findByUser(getParaToInt("userid"));
			List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
			for (Wenji wenji : list1) {
				List<WenjiDetail> list2 = detialService.find(wenji.getId());
				collectdate.addAll(list2);
			}
			/**
			 * 找到用户所有文集 得到文集里收藏的文章
			 */
			service.removeDuplicate(collectdate);
			setAttr("collectdate", collectdate);
			if (list.isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}
		} else {
			List<Tuwen> list = service.findByHot(getParaToInt("pagenum"));
			setAttr("hotdate", list);
			setAttr("userdate", service.getUserList(list));
			if (list.isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}
		}
	}

	/**
	 * 查找tag标签的文章
	 */
	public void findByTag() {

		if (null != getParaToInt("typeid")) {
			List<Tuwen> list = service.findByTag(getParaToInt("pagenum"), getPara("tag"), getParaToInt("typeid"));
			setAttr("tagdate", list);
			List<User> users = followService.getFollows(getParaToInt("userid"));
			setAttr("followdate", users);
			List<Likes> likes = likeService.finduserlike(getParaToInt("userid"));
			setAttr("likedate", likes);
			List<Wenji> list1 = wenjiService.findByUser(getParaToInt("userid"));
			List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
			for (Wenji wenji : list1) {
				List<WenjiDetail> list2 = detialService.find(wenji.getId());
				collectdate.addAll(list2);
			}
			/**
			 * 找到用户所有文集 得到文集里收藏的文章
			 */
			service.removeDuplicate(collectdate);
			setAttr("collectdate", collectdate);

			setAttr("userdate", service.getUserList(list));
			renderJson();
		} else {
			List<Tuwen> list = service.findByTag(getParaToInt("pagenum"), getPara("tag"));
			setAttr("tagdate", list);
			List<User> users = followService.getFollows(getParaToInt("userid"));
			setAttr("followdate", users);
			List<Likes> likes = likeService.finduserlike(getParaToInt("userid"));
			setAttr("likedate", likes);
			List<Wenji> list1 = wenjiService.findByUser(getParaToInt("userid"));
			List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
			for (Wenji wenji : list1) {
				List<WenjiDetail> list2 = detialService.find(wenji.getId());
				collectdate.addAll(list2);
			}
			/**
			 * 找到用户所有文集 得到文集里收藏的文章
			 */
			service.removeDuplicate(collectdate);
			setAttr("collectdate", collectdate);

			setAttr("userdate", service.getUserList(list));
			renderJson();

		}
	}

	/**
	 * 删除文章 需要该文章的id 删除文章时需要考虑不仅删除文章还要删除文章对应的喜欢 收藏 和储存在本地的txt文件图片都需删除
	 */
	public void delect() {
		if (null != getParaToInt("id")) {
			String path = getRequest().getServletContext().getRealPath("/article/")
					+ service.findRealPathTuwen(getParaToInt("id")).getText();
			String path1 = getRequest().getServletContext().getRealPath("/article/")
					+ service.findRealPathTuwen(getParaToInt("id")).getPic();
			System.out.print(path);
			File file = new File(path);
			File file1 = new File(path1);
			if (file.exists()) {
				file.delete();
			}
			if (file1.exists()) {
				file1.delete();
			}
			if (service.delect(getParaToInt("id"))) {
				likeService.deletelike(getParaToInt("id"));
				detialService.deleteTuwen(getParaToInt("id"));
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

	public void name()  {
		String json=(service.name("D:xx.txt"));
		net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(json);
		//得到歌曲信息
		String song=jsonObject.getString("songs");
		net.sf.json.JSONObject jsonObject1= net.sf.json.JSONObject.fromObject(song.substring(1, song.length()-1));
		//得到名字
		String name=jsonObject1.getString("name");
		//得到歌手
		String ar=jsonObject1.getString("ar");
		String al=jsonObject1.getString("al");		
		net.sf.json.JSONObject jsonObject2= net.sf.json.JSONObject.fromObject(ar.substring(1, ar.length()-1));
		net.sf.json.JSONObject jsonObject3= net.sf.json.JSONObject.fromObject(al);
		
		String songer=jsonObject2.getString("name");
		String pic=jsonObject3.getString("picUrl");
		renderText(name+songer+pic);
	}

	/**
	 * 上传文章
	 */
	public void add() {// 添加

		Tuwen tuwen = new Tuwen();
		UploadFile file = getFile();
		String str = file.getFileName();
		if (str != null) {
			tuwen.setPic(str);
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time1 = formatter1.format(date);
		String text = getPara("text");
		if (null != getPara("title")) {
			String title = getPara("title");
			tuwen.setTitle(title);
		}
		tuwen.setCollectnum(0);
		tuwen.setForwardnum(0);
		tuwen.setCheatnum(0);
		if (null != getPara("writer")) {
			String writer = getPara("writer");
			tuwen.setWriter(writer);
		}
		int userid = getParaToInt("userid");
		if (null != getParaToInt("typeid")) {
			int typeid = getParaToInt("typeid");
			tuwen.setTypeid(typeid);
		}
		tuwen.setLikes(0);
		if (null != getParaToInt("likes")) {
			int likes = getParaToInt("likes");
			tuwen.setLikes(likes);
		}
		int isoriginal = getParaToInt("isoriginal");
		tuwen.setUserid(userid);
		try {
			// 直接生成的date对象只能得到年月日得不到时分秒
			tuwen.setTime(formatter1.parse(time1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != getPara("tag")) {
			tuwen.setTag(getPara("tag"));
		}
		tuwen.setIsoriginal(isoriginal);
		tuwen.setText(date + "/" + time1 + userid + ".txt");
		try {
			// 获取本地输出流
			String path1 = getRequest().getServletContext().getRealPath("/article/");
			String articlepath = path1 + date;
			File myPath = new File(articlepath);
			if (!myPath.exists()) {// 若此目录不存在，则创建之// 这个东西只能简历一级文件夹，两级是无法建立的。。。。。
				myPath.mkdir();
				System.out.println("创建文件夹路径为：" + articlepath);
			}
			OutputStream out1 = new FileOutputStream(path1 + date + "/" + time1 + userid + ".txt");
			// 循环读写，保存图片
			byte[] br = text.getBytes();
			out1.write(br);
			System.out.println("接收文章成功");
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 客户端通过传输hispic标记让服务端来判断用户是否上传图片
		if (null != getParaToInt("haspic")) {
			String pic = time1 + userid;
			tuwen.setPic(pic);
			try {
				InputStream in;
				in = getRequest().getInputStream();
				// 获取本地输出流
				String path = getRequest().getServletContext().getRealPath("/imgs/tuwen/");

				OutputStream out = new FileOutputStream(path + pic);
				// 循环读写，保存图片
				int len = -1;
				byte[] bytes = new byte[1024];
				while ((len = in.read(bytes)) != -1) {
					out.write(bytes, 0, len);
				}

				System.out.println("接收图片成功");
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int wenjiid = getParaToInt("wenjiid");
		Wenji wenji = wenjiService.find(userid);
		tuwen.setWenji(wenji.getName());
		if (service.add(tuwen)) {
			WenjiDetail wenjiDetail = new WenjiDetail();
			wenjiDetail.setWenjiid(wenjiid);
			wenjiDetail.setTuwenId(service.getleastid(userid));
			detialService.add(wenjiDetail);
			renderFile(service.getleastid(userid) + tuwen.getPic());
			renderText("true");
		} else {
			renderText("false");
		}

	}

	public void findByid() {
		renderJson(service.findTuwen(getParaToInt("id")));
	}

	public void addtuwen() {// 添加

		Tuwen tuwen = new Tuwen();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time1 = formatter1.format(date);
		String text = getPara("text");
		if (null != getPara()) {
			String title = getPara("title");
			tuwen.setTitle(title);
		}
		tuwen.setCollectnum(0);
		tuwen.setForwardnum(0);
		tuwen.setCheatnum(0);
		if (null != getPara("writer")) {
			String writer = getPara("writer");
			tuwen.setWriter(writer);
		}
		int userid = getParaToInt("userid");
		if (null != getParaToInt("typeid")) {
			int typeid = getParaToInt("typeid");
			tuwen.setTypeid(typeid);
		}
		if (null != getParaToInt("likes")) {
			int likes = getParaToInt("likes");
			tuwen.setLikes(likes);
		}
		int isoriginal = getParaToInt("isoriginal");
		tuwen.setUserid(userid);
		tuwen.setLikes(0);
		try {
			// 直接生成的date对象只能得到年月日得不到时分秒
			tuwen.setTime(formatter1.parse(time1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != getPara("tag")) {
			tuwen.setTag(getPara("tag"));
		}
		tuwen.setIsoriginal(isoriginal);
		tuwen.setText(date + "/" + time1 + userid + ".txt");
		try {
			// 获取本地输出流
			String path1 = getRequest().getServletContext().getRealPath("/article/");
			String articlepath = path1 + date;
			File myPath = new File(articlepath);
			if (!myPath.exists()) {// 若此目录不存在，则创建之// 这个东西只能简历一级文件夹，两级是无法建立的。。。。。
				myPath.mkdir();
				System.out.println("创建文件夹路径为：" + articlepath);
			}
			OutputStream out1 = new FileOutputStream(path1 + date + "/" + time1 + userid + ".txt");
			// 循环读写，保存图片
			byte[] br = text.getBytes();
			out1.write(br);
			System.out.println("接收文章成功");
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 客户端通过传输hispic标记让服务端来判断用户是否上传图片
		if (null != getParaToInt("haspic")) {
			UploadFile file = getFile();
			String str = file.getFileName();
			if (str != null) {
				tuwen.setPic(str);
			}
		}
		int wenjiid = getParaToInt("wenjiid");
		Wenji wenji = wenjiService.find(userid);
		tuwen.setWenji(wenji.getName());
		if (service.add(tuwen)) {
			WenjiDetail wenjiDetail = new WenjiDetail();
			wenjiDetail.setWenjiid(wenjiid);
			wenjiDetail.setTuwenId(service.getleastid(userid));
			detialService.add(wenjiDetail);
			Tuwen tuwen2=service.getleastTuwen(getParaToInt("userid"));
			renderJson(tuwen2);
			} else {
			renderText("false");
		}

	}
}
