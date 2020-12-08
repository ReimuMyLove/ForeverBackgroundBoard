package com.ouran.control;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.ouran.model.Tuwen;
import com.ouran.model.WenjiDetail;
import com.ouran.service.TuwenService;
import com.ouran.service.WenjiDetialService;

public class WenjiDetialController extends Controller {
	WenjiDetialService service = new WenjiDetialService();
	TuwenService tuwenService = new TuwenService();

	public void add() {
		WenjiDetail wenjiDetail = new WenjiDetail();
		wenjiDetail.setWenjiid(getParaToInt("wenjiid"));
		if (null != getParaToInt("tuwen_id")) {
			tuwenService.addcollect(getParaToInt("tuwen_id"));
			wenjiDetail.setTuwenId(getParaToInt("tuwen_id"));
			service.add(wenjiDetail);
			renderText("true");
		} else if (null != getParaToInt("music_id")) {
			wenjiDetail.setMusicId(getParaToInt("music_id"));
			service.add(wenjiDetail);
			renderText("true");
		} else if (null != getParaToInt("topic_detial_id")) {
			wenjiDetail.setTopicDetailId(getParaToInt("topic_detial_id"));
			service.add(wenjiDetail);
			renderText("true");
		}

	}

	public void delete() {
		if (service.delete(getParaToInt("userid"),getParaToInt("tuwen_id"))>0) {
			tuwenService.minuscollect(getParaToInt("tuwen_id"));
			renderText("true");
		} else {
			renderText("false");
		}
	}

	public void find() {
		List<Tuwen> tuwens = new ArrayList<Tuwen>();

		List<WenjiDetail> details = service.find(getParaToInt("wenjiid"));
		for (WenjiDetail wenjiDetail : details) {
			if (wenjiDetail.getTuwenId() != null) {
				Tuwen tuwen = tuwenService.findTuwen(wenjiDetail.getTuwenId());
				tuwens.add(tuwen);
			} else if (wenjiDetail.getTopicDetailId() != null) {

			} else if (wenjiDetail.getMusicId() != null) {

			}
		}
		setAttr("tuwendate", tuwens);
		renderJson();
		// renderJson(details);
	}
}
