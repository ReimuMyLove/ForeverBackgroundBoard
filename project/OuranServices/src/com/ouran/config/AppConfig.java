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
import com.ouran.control.UserController;
import com.ouran.model.Cheat;
import com.ouran.model.Fen;
import com.ouran.model.Follow;
import com.ouran.model.Likes;
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
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/user", UserController.class);
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost:3306/ouran_db?useUnicode=true&characterEncoding=utf-8", "root", "");
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(true);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.addMapping("cheat", "id", Cheat.class);
		// Composite Primary Key order: fenid,userid
		arp.addMapping("fen", "fenid,userid", Fen.class);
		// Composite Primary Key order: followerid,userid
		arp.addMapping("follow", "followerid,userid", Follow.class);
		arp.addMapping("likes", "id", Likes.class);
		arp.addMapping("topic", "id", Topic.class);
		// Composite Primary Key order: topicid,userid
		arp.addMapping("topic_detial", "topicid,userid", TopicDetial.class);
		arp.addMapping("tuwen", "id", Tuwen.class);
		arp.addMapping("types", "type", Types.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("wenji", "id", Wenji.class);
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
