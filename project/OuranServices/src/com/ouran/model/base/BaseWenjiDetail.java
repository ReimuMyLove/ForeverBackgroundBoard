package com.ouran.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWenjiDetail<M extends BaseWenjiDetail<M>> extends Model<M> implements IBean {

	public M setWenjiid(java.lang.Integer wenjiid) {
		set("wenjiid", wenjiid);
		return (M)this;
	}
	
	public java.lang.Integer getWenjiid() {
		return getInt("wenjiid");
	}

	public M setTopicDetailId(java.lang.Integer topicDetailId) {
		set("topic_detail_id", topicDetailId);
		return (M)this;
	}
	
	public java.lang.Integer getTopicDetailId() {
		return getInt("topic_detail_id");
	}

	public M setTuwenId(java.lang.Integer tuwenId) {
		set("tuwen_id", tuwenId);
		return (M)this;
	}
	
	public java.lang.Integer getTuwenId() {
		return getInt("tuwen_id");
	}

}
