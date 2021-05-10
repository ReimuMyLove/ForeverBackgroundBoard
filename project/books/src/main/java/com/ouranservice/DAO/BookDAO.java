package com.ouranservice.DAO;

import java.util.Set;

import com.ouranservice.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BookDAO {

	//根据bookId获取书籍信息		OK
	Book getBriefById(int bookId);		//简略版

	Book getDetailById(int bookId);		//详细版

	//获取全部书籍				OK
	Set<Book> getAllBooks();

	//根据要求获取对应书籍信息
	Set<Book> getBookByRequest(@Param("typeId") int typeId,
							   @Param("area") String area,
							   @Param("startTime") String startTime,
							   @Param("endTime") String endTime);
	
	//book信息添加				OK
	int addBook(Book book);
	
	//book信息修改				OK
	int updateBook(Book book);
	
	//book信息删除				OK
	int deleteBook(int bookId);

	//根据typeId获取book信息		OK
	Set<Book> getBookByTypeId(int typeId);

}
