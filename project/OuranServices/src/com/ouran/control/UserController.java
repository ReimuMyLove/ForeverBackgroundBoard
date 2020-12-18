package com.ouran.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.ouran.model.User;
import com.ouran.service.UserService;

public class UserController extends Controller {
	UserService service = new UserService();

	/**
	 * @author Admin 可通过id，姓名查找 客户端传参时可传name；也可传id服务端进行判断
	 * 
	 */
	public void find() {
		if (null != getPara("name")) {
			renderJson("userdata", service.findUser(getParaToInt("pagenum"), getPara("name")));
		} else if (null != getParaToInt("id")) {
			renderJson(service.findUser(getParaToInt("id")));
		} else {
			renderJson("userdata", service.getAll());
		}
	}

	/**
	 * @author Admin 登陆时检查用户名和密码是否正确
	 * 
	 */
	public void check() {
		if (null != getPara("name") && null != getPara("password")) {
			List<User> users = new ArrayList<User>();
			users = service.getAll();
			for (User user : users) {
				if (user.getName().equals(getPara("name")) && user.getPassword().equals(getPara("password"))) {
					renderJson(user);
					break;
				} else {
					renderText("false");
				}
			}
		} else {
			renderText("false");
		}
	}

	/**
	 * 用户实现注销功能
	 */
	public void delete() {
		if (null != getParaToInt("id")) {
			if (service.delect(getParaToInt("id"))) {
				renderText("true");
			} else {
				renderText("false");
			}
		} else {
			renderText("false");
		}
	}

	// ?age=20&name=ls&password=ls&sex=男&sign=飒飒
	/**
	 * 用户实现更新个人资料功能 要求服务端把所有参数传入
	 * 
	 */
	public void update() {
		int id = getParaToInt("id");
		int age = getParaToInt("age");
		String name = getPara("name");
		String pas = getPara("password");
		String sex = getPara("sex");
		String sign = getPara("sign");

		if (service.update(id, age, sex, sign)) {
			renderText("true");
		} else {
			renderText("false");
		}
	}

	/**
	 * 用户注册功能 名字和密码为必须值 其它值不填客户端系统填入默认信息
	 */
	public void add() {
		Boolean flagBoolean = false;
		String path1 = getRequest().getServletContext().getRealPath("/imgs/");
		System.out.print(path1);
		if (null != getPara("name") && null != getPara("password")) {
			List<User> users = service.findAlluser();
			for (User user : users) {
				if (getPara("name").equals(user.getName())) {
					flagBoolean = true;
				}
			}
			User user = new User();
			if (null != getParaToInt("age")) {
				int age = getParaToInt("age");
				user.setAge(age);
			}
			String name = getPara("name");
			String pas = getPara("password");
			user.setName(name);
			user.setPassword(pas);
			// 客户端通过传输hispic标记让服务端来判断用户是否上传图片
			if (getParaToInt("haspic") != null) {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String time1 = formatter1.format(date);
				String pic = time1 + name;
				user.setPicname(pic);
				InputStream in;
				try {
					in = getRequest().getInputStream();
					// 获取本地输出流
					String path = getRequest().getServletContext().getRealPath("/imgs/user/");
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
			user.setFennum(0);
			user.setFollownum(0);
			if (null != getPara("sex")) {
				String sex = getPara("sex");
				user.setSex(sex);
			}
			if (null != getPara("sign")) {
				String sign = getPara("sign");
				user.setSign(sign);
			}
			if (!flagBoolean) {
				if (service.add(user)) {
					renderText("TRUE");
				} else {
					renderText("FALSE");
				}
			} else {
				renderText("ERROR");
			}
		} else {
			renderText("FALSE");
		}

	}

	/**
	 * 用户关注 需要传入用户id和用户关注的followid 用户的follownum+1 被关注的follow fennum+1
	 */
	public void changeuserimage() {
		User user = service.findUser(getParaToInt("userid"));
		String oldname = user.getPicname();
		UploadFile file = getFile();
		String str = file.getFileName();
		if (str != null) {
			user.setPicname(str);
			File file1 = new File(
					"C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\imgs\\tuwen"
							+ oldname);
			if(file1.exists()) {
				file1.delete();
			}
			service.update(user.getId(), str);
			renderText(str);
		} else {
			renderText("false");
		}

	}

	public void changeback1image() {
		User user = service.findUser(getParaToInt("userid"));
		String oldname = user.getBackgroundpic1();
		UploadFile file = getFile();
		String str = file.getFileName();
		if (str != null) {
			user.setPicname(str);
			File file1 = new File(
					"C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\imgs\\tuwen"
							+ oldname);
			if(file1.exists()) {
				file1.delete();
			}
			service.updateback1(user.getId(), str);
			renderText(str);
		} else {
			renderText("false");
		}
		
	}

	/**
	 * 更改用户信息
	 */

	public void addFellowNum() {
		if (null != getParaToInt("userid") && null != getParaToInt("followerid")) {
			service.addFellowNum(getParaToInt("id"));
			service.addFenNum(getParaToInt("followid"));
			renderText("true");
		} else {
			renderText("false");
		}
	}

	/**
	 * 用户取消关注 需要传入用户id和用户关注的followid 用户的follownum-1 被关注的follow fennum-1
	 */
	public void minusFellowNum() {
		if (null != getParaToInt("userid") && null != getParaToInt("followerid")) {
			service.minusFellowNum(getParaToInt("id"));
			service.minusFenNum(getParaToInt("fllowid"));
			renderText("true");
		} else {
			renderText("false");
		}
	}

	public void backgroundpic1() {
		User user = service.findUser(getParaToInt("userid"));
		user.setBackgroundpic1(getPara("userid") + "backpic01.png");
		user.update();
		InputStream in;
		try {
			in = getRequest().getInputStream();
			// 获取本地输出流
			String path = getRequest().getServletContext().getRealPath("/imgs/background/");
			OutputStream out = new FileOutputStream(path + getPara("userid") + "backpic01.png");
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

	public void backgroundpic2() {
		User user = service.findUser(getParaToInt("userid"));
		user.setBackgroundpic2(getPara("userid") + "backpic02.png");
		user.update();
		InputStream in;
		try {
			in = getRequest().getInputStream();
			// 获取本地输出流
			String path = getRequest().getServletContext().getRealPath("/imgs/background/");
			OutputStream out = new FileOutputStream(path + getPara("userid") + "backpic02.png");
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

	/**
	 * 修改密码接口 我给你用户名ID/旧密码/新密码 你返回true/false/keyError 分别是成功 失败 密码错误
	 */

	public void changepassward() {
		if (null != getParaToInt("id") && null != getPara("oldpassward") && null != getPara("newpassward")) {

			User user = service.findUser(getParaToInt("id"));
			if (user.getPassword().equals(getPara("oldpassward"))) {
				if (service.changepassward(getParaToInt("id"), getPara("newpassward"))) {
					renderText("true");
				}

			} else {
				renderText("keyError");
			}
		} else {
			renderText("false");
		}
	}
}
