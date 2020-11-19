package com.ouran.service;

import java.util.List;

import com.ouran.model.Tuwen;

public class TuwenService {
	// �Ѿ��ύ�����²�֧���޸Ĳ���
	private static final Tuwen dao = Tuwen.dao;

	public List<Tuwen> getAll() {
		return dao.findAll();
	}

	public Tuwen findTuwen(int id) {
		return dao.findById(id);
	}

	public Boolean delect(int id) {
		return dao.deleteById(id);
	}

	public Boolean add(Tuwen tuwen) {
		if (tuwen != null) {
			return tuwen.save();
		} else {
			return false;
		}
	}

}
