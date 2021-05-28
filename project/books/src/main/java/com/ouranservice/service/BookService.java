package com.ouranservice.service;


import com.ouranservice.DAO.BookDAO;
import com.ouranservice.DAO.BookTypeDAO;
import com.ouranservice.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
    public List<Book> getAll(int pageNumber) {
		return bookDAO.getAllBooks(pageNumber);
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
	//按分类分页查找
    public List<Book> getBookByRequest(int typeId, String area, String startTime, String endTime, int pageNumber) {
		return bookDAO.getBookByRequest(typeId,area,startTime,endTime,pageNumber);
    }
	//查询最近7天新出版书籍
    public List<Book> getWeekly(String startTime,String endTime) {
		return bookDAO.getWeekly(startTime,endTime);
	}

    public int getNumber() {
		return bookDAO.getNumber();
    }

    //获取书籍Id最大值
	public int getMaxId(){
		return bookDAO.getMaxId();
	}
}
