package com.ouran.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ouran.model.Music;
import com.ouran.model.User;
import com.ouran.model.Wenji;


public class MusicService {
	private static final Music dao = Music.dao;
	
	private UserService userService=new UserService();
	private WenjiService wenjiService=new WenjiService();
	/**
	 * 下载音乐，txt信息
	 * */
	
	public void addlike(int id) {
		Music music = dao.findById(id);
		music.setLikes(music.getLikes() + 1);
		music.update();
	}
	
	public void minuslike(int id) {
		Music music = dao.findById(id);
		music.setLikes(music.getLikes() - 1);
		music.update();
	}
	
	public void addcollect(int id) {
		Music music = dao.findById(id);
		music.setLikes(music.getCollectnum() + 1);
		music.update();
	}
	public void minuscollect(int id) {
		Music music = dao.findById(id);
		music.setLikes(music.getCollectnum() - 1);
		music.update();
	}
	public List<Music> findmusic(int pageNumber) {
		return dao.paginate(pageNumber, 10, "select *"," from music order by time desc").getList();
	}
	public void downLoadJpg(String webURL, String localPath) {
		try {
			// 网络URL
			URL url = new URL(webURL);
			// 打开网络连接
			URLConnection connection = url.openConnection();
			// 输入流
			InputStream inputStream = connection.getInputStream();
			// 输出文件流
			OutputStream outputStream = new FileOutputStream(new File(localPath));
			// 缓冲区对象
			
			byte[] b = new byte[1024];
			// 读取计数器
			int len;
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
			// 关闭输入流操作
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 关闭输出流操作
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 监听下载成功
		} catch (Exception e) {
			// 监听下载失败
			e.printStackTrace();
		}
	}
	public boolean addmusic(Music music) {
		return music.save();
	}
	public  Music getinfo(String fileName)  
	{     
		System.out.print(fileName);
		Music music=new Music();
	    String fileContent = "";     
	    try   
	    {       
	        File f = new File(fileName);      
	        if(f.isFile()&&f.exists())  
	        {       
	            InputStreamReader read = new InputStreamReader(new FileInputStream(f),"utf-8");       
	            BufferedReader reader=new BufferedReader(read);       
	            String line;       
	            while ((line = reader.readLine()) != null)   
	            {        
	                fileContent += line;       
	            }         
	            read.close();      
	        }     
	    } catch (Exception e)   
	    {         
	        e.printStackTrace();     
	    }     
	    System.out.print(""+fileContent);
	    net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(fileContent);
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
		music.setPic(pic);
		music.setName(name);
		music.setSinger(songer);
	    return music;   
	}  
	
	public List getUserList(List<Music> list) {
		List<User> list2 = new ArrayList<User>();
		System.out.print(list);
		for (Music tuwen : list) {
			int id = tuwen.getUserid();
			User user = userService.findUser(id);
			list2.add(user);
		}
		return removeDuplicate(list2);
	}
	public  List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	} 
	/**
	 * 通过id删除音乐
	 * */
	public boolean deleteByid(int musicid) {
		return dao.deleteById(musicid);
	}
	/**
	 * 通过userid查找音乐
	 * */
	public List<Music> getMusicByuser(int userid) {
		return dao.find("select * from music where userid = "+userid);
	}
	public List<Wenji> getwenji(int userid) {
		return wenjiService.findByUser(userid);
	}
}
