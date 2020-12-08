package com.ouran.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.ouran.model.Tuwen;
import com.ouran.model.User;

public class TuwenService {
	// 已经提交的文章不支持修改操作
	private static final Tuwen dao = Tuwen.dao;
	private UserService userService=new UserService();
	private FileReader  r;
	private String str;
	private String path = "C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\article";

	

	
	/**
	 * jfinal自带数据库分页
	 * page包括totalRow，pageNumber，lastPage，firstPage，totalPage，pageSize，list七个属性，如下
	 * {"tuwendate":{"totalRow":3,"pageNumber":1,"lastPage":true,
	 * "firstPage":true,"totalPage":1,"pageSize":10,"list":
	 * */
	
	
	
	/**
	 * 将文章存在服务器
	 * 通过io流读取文章
	 * @return 
	 * */
	public int getleastid(int userid) {
		List<Tuwen> list=dao.find("select *from tuwen where userid="+userid);
		return list.get(list.size()-1).getId();
	}
	
	public int getAllPageNum() {
		return dao.paginate(1, 10, "select *", "from tuwen").getTotalPage();
	}
	
	public List<Tuwen> getAll(int pagenum) {
		Page<Tuwen> list =dao.paginate(pagenum, 10, "select *", "from tuwen");
		return GetLocalTxt(list.getList());
	}

	public List<Tuwen> findByHot(int pageNumber) {
		Page<Tuwen> list = dao.paginate(pageNumber, 10,"select *" ," from tuwen ORDER BY likes  DESC");
		return GetLocalTxt(list.getList());
	}

	public List<Tuwen> findByTag(int pageNumber,String tag) {
		Page<Tuwen> list = dao.paginate(pageNumber, 10, "select * ","from tuwen where tag like '%" + tag + "%'");
		return GetLocalTxt(list.getList());
	}
	/**
	
	*/
	public Tuwen findRealPathTuwen(int id) {
		return dao.findById(id);
	}
	public Tuwen findTuwen(int id) {
		Tuwen tuwen =dao.findById(id);
		String str="";
		try {
			r = new FileReader(path + "\\" + tuwen.getText());
			char[] ch=new char[100];
			int len=0;
			while((len=r.read(ch))!=-1) {
				str+=new String(ch,0,len);
			}
			r.close();
			tuwen.setText(str);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tuwen;
	}

	public List<Tuwen> findTuwen(int pageNumber,String name) {
		Page<Tuwen> page= dao.paginate(pageNumber, 10, "select * ","from tuwen where title like '%" + name + "%'");
		return GetLocalTxt(page.getList());
	}
	public List<Tuwen> findTuwenByuser(int pageNumber,int userid) {
		Page<Tuwen> page= dao.paginate(pageNumber, 10, "select * ","from tuwen where userid=" + userid);
		return GetLocalTxt(page.getList());
	}
	
	public List<Tuwen> findTuwen(int pageNumber,int typeid) {
		Page<Tuwen> page= dao.paginate(pageNumber, 10, "select * ","from tuwen where typeid=" + typeid);
		return GetLocalTxt(page.getList());
	}
	public Boolean delect(int id) {
		return dao.deleteById(id);
	}

	public Boolean add(Tuwen tuwen) {
		if (tuwen != null) {
			tuwen.save();
//			System.out.print(tuwen.getId());
			return true;
		} else {
			return false;
		}
	}

	public void addlike(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setLikes(tuwen.getLikes() + 1);
		tuwen.update();
	}

	public void minuslike(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setLikes(tuwen.getLikes() - 1);
		tuwen.update();
	}
	public void addcollect(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setCollectnum(tuwen.getCollectnum() + 1);
		tuwen.update();
	}

	public void minuscollect(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setCollectnum(tuwen.getCollectnum() - 1);
		tuwen.update();
	}
	
	public void addforward(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setForwardnum(tuwen.getForwardnum() + 1);
		tuwen.update();
	}
	
	public void addcheat(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setCheatnum(tuwen.getCheatnum() + 1);
		tuwen.update();
	}

	public void minuscheat(int id) {
		Tuwen tuwen = dao.findById(id);
		tuwen.setCheatnum(tuwen.getCheatnum() - 1);
		tuwen.update();
	}
	
	private List<Tuwen> GetLocalTxt(List<Tuwen> list) {
		// TODO Auto-generated method stub
		for (Tuwen tuwen : list) {
			str="";
			try {
				if(tuwen.getTypeid()!=0) {
				r = new FileReader(path + "\\" + tuwen.getText());
				char[] ch=new char[100];
				int len=0;
				while((len=r.read(ch))!=-1) {
					str+=new String(ch,0,len);
				}
				r.close();
				tuwen.setText(str);}
				else {
					r = new FileReader(path + "\\" + tuwen.getText());
					char[] ch=new char[100];
					int len=0;
					while((len=r.read(ch))!=-1&&str.length()<=50) {
						str+=new String(ch,0,len);
					}
					r.close();
					tuwen.setText(str);
//					System.out.println(""+str);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println(""+list.toString());
		return list;
	}
	/**
	 * list	去重
	 * */
	public  List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	} 
	
	public List getUserList(List<Tuwen> list) {
		List<User> list2 = new ArrayList<User>();
		for (Tuwen tuwen : list) {
			int id = tuwen.getUserid();
			User user = userService.findUser(id);
			list2.add(user);
		}
		return removeDuplicate(list2);
	}

	public List<Tuwen> findTuwenByuser(int pageNumber,int userid,int typeid) {
		Page<Tuwen> page= dao.paginate(pageNumber, 10, "select * ","from tuwen where userid=" + userid+" && typeid="+typeid);
		return GetLocalTxt(page.getList());
	}
	public List<Tuwen> findRecommendTuwen() {
		List<Tuwen> page= dao.find("select * "+"from tuwen where typeid=5 && TO_DAYS(NOW()) = TO_DAYS(time)"  );
		return GetLocalTxt(page);
	}
}
