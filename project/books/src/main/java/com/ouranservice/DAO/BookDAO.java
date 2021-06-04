package com.ouranservice.DAO;

import java.util.List;

import com.ouranservice.entity.Book;
import com.ouranservice.entity.BookToType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BookDAO {

	//根据bookId获取书籍信息		OK
	Book getBriefById(int bookId);		//简略版

	Book getDetailById(int bookId);		//详细版

	//获取全部书籍				OK		//简略版
	List<Book> getAllBooks(@Param("pageNumber")int pageNumber);

	//根据要求获取对应书籍信息		OK		//简略版
	List<Book> getBookByRequest(@Param("typeId") int typeId,
							   @Param("area") String area,
							   @Param("startTime") String startTime,
							   @Param("endTime") String endTime,
							   @Param("pageNumber")int pageNumber);
	
	//book信息修改				OK
	int updateBook(Book book);
	
	//book信息删除				OK
	int deleteBook(int bookId);

	//根据typeId获取book信息		OK
	List<Book> getBookByTypeId(int typeId);

    List<Book> getWeekly(@Param("startTime")String startTime,
						 @Param("endTime")String endTime);

    //获取书籍数量
    int getNumber();

    //根据BookListId获取book信息

	//获取表格中书籍Id最大值
	int getMaxId();

	//book信息添加				OK
	int addBook(Book book);
	//book信息回收
    int recycle(Book book);
	//bookType添加
	int addBookType(BookToType bookToType);
	//bookType回收
    int recycleType(BookToType bookToType);

	int deleteBookType(int bookId);
}
