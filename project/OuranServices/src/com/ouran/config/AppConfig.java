package com.ouran.config;




import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.ouran.control.CheatController;
import com.ouran.control.FollowController;
import com.ouran.control.ForwardController;
import com.ouran.control.LikesController;
import com.ouran.control.MusicController;
import com.ouran.control.TuwenController;
import com.ouran.control.UserController;
import com.ouran.control.WenjiController;
import com.ouran.control.WenjiDetialController;
import com.ouran.model.Cheat;
import com.ouran.model.Follow;
import com.ouran.model.Forward;
import com.ouran.model.Likes;
import com.ouran.model.Music;
import com.ouran.model.Topic;
import com.ouran.model.TopicDetial;
import com.ouran.model.Tuwen;
import com.ouran.model.Types;
import com.ouran.model.User;
import com.ouran.model.Wenji;
import com.ouran.model.WenjiDetail;

public class AppConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		me.setViewType(ViewType.JSP);
		me.setDevMode(true);
		me.setBaseUploadPath("imgs/tuwen");
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/user", UserController.class);
		me.add("/tuwen",TuwenController.class);
		me.add("/follow",FollowController.class);
		me.add("/cheat",CheatController.class);
		me.add("/likes",LikesController.class);
		me.add("/wenji",WenjiController.class);
		me.add("/wenjidetial",WenjiDetialController.class);
		me.add("/forward",ForwardController.class);
		me.add("/music",MusicController.class);
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost:3306/ouran_db?useUnicode=true&characterEncoding=utf-8", "root", "");
	//	DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost:3306/ouran_db?useUnicode=true&characterEncoding=utf-8", "root", "WRK990424");
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(true);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.addMapping("forward", "id", Forward.class);
		arp.addMapping("cheat", "id", Cheat.class);
		// Composite Primary Key order: fenid,userid
		// Composite Primary Key order: followerid,userid
		arp.addMapping("follow", "followerid,userid", Follow.class);
		arp.addMapping("likes", "id", Likes.class);
		arp.addMapping("topic", "id", Topic.class);
		// Composite Primary Key order: topicid,userid
		arp.addMapping("topic_detial", "topicid,userid", TopicDetial.class);
		arp.addMapping("tuwen", "id", Tuwen.class);
		arp.addMapping("types", "type", Types.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("music", "id", Music.class);
		arp.addMapping("wenji_detail", "wenjiid", WenjiDetail.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

}
