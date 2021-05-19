package com.ouranservice.DAO;

import com.ouranservice.entity.BookList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookListDAO {

    //根据Id查询书单具体信息
    BookList getBookListById(int bookListId);

    //获取初始书单(5个)
    List<BookList> getNewList();

    //分页查询全部书单信息(按照ID倒序)
    List<BookList> getAll(int pageNumber);
}
