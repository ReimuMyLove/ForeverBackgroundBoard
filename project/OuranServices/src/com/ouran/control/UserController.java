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
	 * @author Admin ��ͨ��id���������� �ͻ��˴���ʱ�ɴ�name��Ҳ�ɴ�id����˽����ж�
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
	 * @author Admin ��½ʱ����û����������Ƿ���ȷ
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
	 * �û�ʵ��ע������
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

	// ?age=20&name=ls&password=ls&sex=��&sign=��
	/**
	 * �û�ʵ�ָ��¸������Ϲ��� Ҫ�����˰����в�������
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
	 * �û�ע�Ṧ�� ���ֺ�����Ϊ����ֵ ����ֵ����ͻ���ϵͳ����Ĭ����Ϣ
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
			// �ͻ���ͨ������hispic����÷�������ж��û��Ƿ��ϴ�ͼƬ
			if (getParaToInt("haspic") != null) {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String time1 = formatter1.format(date);
				String pic = time1 + name;
				user.setPicname(pic);
				InputStream in;
				try {
					in = getRequest().getInputStream();
					// ��ȡ���������
					String path = getRequest().getServletContext().getRealPath("/imgs/user/");
					OutputStream out = new FileOutputStream(path + pic);
					// ѭ����д������ͼƬ
					int len = -1;
					byte[] bytes = new byte[1024];
					while ((len = in.read(bytes)) != -1) {
						out.write(bytes, 0, len);
					}

					System.out.println("����ͼƬ�ɹ�");
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
	 * �û���ע ��Ҫ�����û�id���û���ע��followid �û���follownum+1 ����ע��follow fennum+1
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
	 * �����û���Ϣ
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
	 * �û�ȡ����ע ��Ҫ�����û�id���û���ע��followid �û���follownum-1 ����ע��follow fennum-1
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
			// ��ȡ���������
			String path = getRequest().getServletContext().getRealPath("/imgs/background/");
			OutputStream out = new FileOutputStream(path + getPara("userid") + "backpic01.png");
			// ѭ����д������ͼƬ
			int len = -1;
			byte[] bytes = new byte[1024];
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}

			System.out.println("����ͼƬ�ɹ�");
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
			// ��ȡ���������
			String path = getRequest().getServletContext().getRealPath("/imgs/background/");
			OutputStream out = new FileOutputStream(path + getPara("userid") + "backpic02.png");
			// ѭ����д������ͼƬ
			int len = -1;
			byte[] bytes = new byte[1024];
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}

			System.out.println("����ͼƬ�ɹ�");
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * �޸�����ӿ� �Ҹ����û���ID/������/������ �㷵��true/false/keyError �ֱ��ǳɹ� ʧ�� �������
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
