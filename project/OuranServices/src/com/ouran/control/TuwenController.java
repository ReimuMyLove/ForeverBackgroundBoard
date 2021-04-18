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

	// ��ɾ�Ĳ�
	/**
	 * ͨ��id�����ض����� ͨ��titleģ������ ������������
	 */
	public void findByid() {
		// ͨ��id/title����
		if (null != getParaToInt("id")) {
			renderJson(service.findTuwen(getParaToInt("id")));
		}}
	public void find() {
		// ͨ��id/title����
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
	 * ����������ǰ ��ʾ��һ���µ���������
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
	 * ����tag��ǩ������
	 */
	public void findByTag() {
		List<Tuwen> list = service.findByTag(getParaToInt("pagenum"), getPara("tag"));
		setAttr("tagdate", list);
		setAttr("userdate", service.getUserList(list));
	}

	/**
	 * ɾ������ ��Ҫ�����µ�id ɾ������ʱ��Ҫ���ǲ���ɾ�����»�Ҫɾ�����¶�Ӧ��ϲ�� �ղ� �ʹ����ڱ��ص�txt�ļ�ͼƬ����ɾ��
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
	 * �ϴ�����
	 */
public void add() {// ���
		
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
			// ֱ�����ɵ�date����ֻ�ܵõ������յò���ʱ����
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
			// ��ȡ���������
			String path1 = getRequest().getServletContext().getRealPath("/article/");
			String articlepath = path1 + date;
			File myPath = new File(articlepath);
			if (!myPath.exists()) {// ����Ŀ¼�����ڣ��򴴽�֮// �������ֻ�ܼ���һ���ļ��У��������޷������ġ���������
				myPath.mkdir();
				System.out.println("�����ļ���·��Ϊ��" + articlepath);
			}
			OutputStream out1 = new FileOutputStream(path1 + date + "/" + time1 + title + ".txt");
			// ѭ����д������ͼƬ
			byte[] br = text.getBytes();
			out1.write(br);
			System.out.println("�������³ɹ�");
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �ͻ���ͨ������hispic����÷�������ж��û��Ƿ��ϴ�ͼƬ
		if (null != getParaToInt("haspic")) {
			String pic = time1 + userid;
			tuwen.setPic(pic);
			try {
				InputStream in;
				in = getRequest().getInputStream();
				// ��ȡ���������
				String path = getRequest().getServletContext().getRealPath("/imgs/tuwen/");

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

