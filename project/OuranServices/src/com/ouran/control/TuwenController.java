package com.ouran.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.ouran.model.Tuwen;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;
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

	// 增删改查
	/**
	 * 通过id查找特定文章 通过title模糊查找 查找所有文章
	 */
	public void findByid() {
		// 通过id/title查找
		if (null != getParaToInt("id")) {
			renderJson(service.findTuwen(getParaToInt("id")));
		}}
	public void find() {
		// 通过id/title查找
		if (null != getParaToInt("id")) {
			renderJson("tuwendate", service.findTuwen(getParaToInt("id")));
		} else if (null != getPara("title")) {
			List<Tuwen> list = service.findTuwen(getParaToInt("pagenum"), getPara("title"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			renderJson();
		} else if (null != getParaToInt("typeid")) {
			List<Tuwen> list = service.findTuwen(getParaToInt("pagenum"), getParaToInt("typeid"));
			setAttr("tuwendate", list);
			setAttr("userdate", service.getUserList(list));
			if (service.getUserList(list).isEmpty()) {
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
		if(null!=getParaToInt("pagenum")&&null!=getParaToInt("userid")&&null!=getParaToInt("typeid")) {
			renderJson("date",service.findTuwenByuser(getParaToInt("pagenum"),getParaToInt("userid"),getParaToInt("typeid")));
		}else {
			renderText("null");
		}
	}

	/**
	 * 热门文章在前 显示近一个月的热门文章
	 */
	public void findByhot() {
		List<Tuwen> list = service.findByHot(getParaToInt("pagenum"));
		setAttr("hotdate", list);
		setAttr("userdate", service.getUserList(list));
		if (list.isEmpty()) {
			renderText("");
		} else {
			renderJson();
		}
	}

	/**
	 * 查找tag标签的文章
	 */
	public void findByTag() {
		List<Tuwen> list = service.findByTag(getParaToInt("pagenum"), getPara("tag"));
		setAttr("tagdate", list);
		setAttr("userdate", service.getUserList(list));
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

	public void name() {
		renderFile("002.png");
	}
	public void getRecommendTuwen() {
		renderJson(service.findRecommendTuwen());
	}
	/**
	 * 上传文章
	 */
public void add() {// 添加
		
		Tuwen tuwen = new Tuwen();
		UploadFile file=getFile();
		String str=file.getFileName();
		if (str!=null) {
			tuwen.setPic(str);
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time1 = formatter1.format(date);
		String text = getPara("text");
		String title = getPara("title");
		tuwen.setTitle(title);
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
		tuwen.setText(date + "/" + time1 + title + ".txt");
		try {
			// 获取本地输出流
			String path1 = getRequest().getServletContext().getRealPath("/article/");
			String articlepath = path1 + date;
			File myPath = new File(articlepath);
			if (!myPath.exists()) {// 若此目录不存在，则创建之// 这个东西只能简历一级文件夹，两级是无法建立的。。。。。
				myPath.mkdir();
				System.out.println("创建文件夹路径为：" + articlepath);
			}
			OutputStream out1 = new FileOutputStream(path1 + date + "/" + time1 + title + ".txt");
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
}

