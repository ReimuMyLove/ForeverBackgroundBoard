package com.ouran.control;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Music;
import com.ouran.model.Wenji;
import com.ouran.service.FollowService;
import com.ouran.service.LikeService;
import com.ouran.service.MusicService;
import com.ouran.service.WenjiDetialService;
import com.ouran.service.WenjiService;

public class MusicController extends Controller {
	MusicService service = new MusicService();
	FollowService followService = new FollowService();
	LikeService likeService = new LikeService();
	private WenjiService wenjiService = new WenjiService();
	WenjiDetialService detialService = new WenjiDetialService();

	public void addmusic() {
		/**
		 * 下载音乐
		 */
		String filePth = "C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\music\\music\\"
				+ getPara("id") + ".mp3";
		File file = new File(filePth);
		if (!file.exists()) {
			// 文件不存在
			service.downLoadJpg("https://v1.hitokoto.cn/nm/redirect/music/" + getPara("id"),
					"C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\music\\music\\"
							+ getPara("id") + ".mp3");
			service.downLoadJpg("https://v1.hitokoto.cn/nm/detail/" + getPara("id"),
					"C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\music\\info\\"
							+ getPara("id") + ".txt");
		}
		/**
		 * 下载音乐信息
		 */
		if (file.exists()) {
			Music music = service.getinfo(
					"C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\music\\info\\"
							+ getPara("id") + ".txt");
			music.setPath(
					"http://49.232.217.140:8080/OuranServices/music/music/"+ getPara("id") + ".mp3");
			music.setLikes(0);
			music.setCheatnum(0);
			music.setCollectnum(0);
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time1 = formatter1.format(date);
			try {
				music.setTime(formatter1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			music.setUserid(getParaToInt("userid"));
			if (getPara("text") != null) {
				music.setText(getPara("text"));
			} else {
				music.setText("");
			}
			if(music.getUserid()!=-1) {
			if (service.addmusic(music)) {
				renderJson(music);
			}else {
				renderText("false");
			}}else {
				renderJson(music);
			}
		} else {
			renderText("Error");
		}
	}
	public void delete() {
		if (null!=getParaToInt("musicid")) {
			renderText(service.deleteByid(getParaToInt("musicid"))+"");
		}
	}
	
	public void findByuser() {
		if(null != getParaToInt("userid")) {
			List<Music>musics=service.getMusicByuser(getParaToInt("userid"));
			setAttr("musicdate", musics);
			setAttr("likedate", likeService.findusermusiclike(getParaToInt("userid")));
			renderJson();
		}
	}
	public void getmusic() {
		if (null != getParaToInt("userid")) {
			List<Music> list = service.findmusic(getParaToInt("pagenum"));
			setAttr("musicdate", list);
			setAttr("userdate", service.getUserList(list));
			setAttr("followdate", followService.getFollows(getParaToInt("userid")));
			//返回用户点赞过的音乐数据
			setAttr("likedate", likeService.findusermusiclike(getParaToInt("userid")));
			setAttr("collectdate", detialService.getusercollectmusic(getParaToInt("userid")));
			// List<WenjiDetail> collectdate = new ArrayList<WenjiDetail>();
//			for (Wenji wenji : list1) {
//				List<WenjiDetail> list2 = detialService.find(wenji.getId());
//				collectdate.addAll(list2);
//			}
//			/**
//			 * 找到用户所有文集 得到文集里收藏的文章
//			 */
//			service.removeDuplicate(collectdate);
//			setAttr("collectdate", collectdate);
			if (service.getUserList(list).isEmpty()) {
				renderText("");
			} else {
				renderJson();
			}

		}
	}

}
