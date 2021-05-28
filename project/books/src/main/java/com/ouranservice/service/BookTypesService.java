package com.ouranservice.service;

import com.ouranservice.DAO.BookTypeDAO;
import com.ouranservice.entity.Book;
import com.ouranservice.entity.BookTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookTypesService {

	final BookTypeDAO bookTypeDAO;

	@Autowired
	public BookTypesService(BookTypeDAO bookTypeDAO) {
		this.bookTypeDAO = bookTypeDAO;
	}

	//根据bookId获取Types
    public Set<BookTypes> getTypesByBookId(int bookId) {
		return bookTypeDAO.getTypesByBookId(bookId);
    }

	//根据typeName查询对应typeId
	public int getIdByName(String typeName) {
		BookTypes bookTypes = bookTypeDAO.getIdByName(typeName);
		return bookTypes.getTypeId();
	}

	//根据typeId获取对应全部信息
	public BookTypes getTypeByTypeId(int typeId) {
		return bookTypeDAO.getTypeByTypeId(typeId);
	}

    public List<BookTypes> getAll() {
		return bookTypeDAO.getAll();
    }
}
