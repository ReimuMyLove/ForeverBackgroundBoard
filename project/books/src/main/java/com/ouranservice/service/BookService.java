package com.ouranservice.service;


import com.ouranservice.DAO.BookDAO;
import com.ouranservice.DAO.BookTypeDAO;
import com.ouranservice.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookService {
	final BookDAO bookDAO;
	final BookTypeDAO bookTypeDAO;

	@Autowired
	public BookService(BookDAO bookDAO, BookTypeDAO bookTypeDAO) {
		this.bookDAO = bookDAO;
		this.bookTypeDAO = bookTypeDAO;
	}

	//获取简略信息
	public Book getBriefById(int bookId) {
		return bookDAO.getBriefById(bookId);
	}
	//获取详细信息
	public Book getDetailById(int bookId) {
		return bookDAO.getDetailById(bookId);
	}
	//获取全部书籍
    public Set<Book> getAll() {
		return bookDAO.getAllBooks();
    }
	//添加书籍
	public int addBook(Book book) {
		return bookDAO.addBook(book);
	}
	//删除书籍
	public int deleteBook(int bookId) {
		return bookDAO.deleteBook(bookId);
	}
	//修改书籍
	public int updateBook(Book book) {
		return bookDAO.updateBook(book);
	}

    public Set<Book> getBookByRequest(int typeId, String area, String startTime, String endTime) {
		return bookDAO.getBookByRequest(typeId,area,startTime,endTime);
    }
}
