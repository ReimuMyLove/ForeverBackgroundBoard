package com.ouranservice.service;

import com.ouranservice.DAO.BookListDAO;
import com.ouranservice.entity.BookList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookListService {

    private final BookListDAO bookListDAO;
    @Autowired
    public BookListService(BookListDAO bookListDAO) {
        this.bookListDAO = bookListDAO;
    }

    //根据Id查询书单具体信息
    BookList getBookListById(int bookListId){
        return bookListDAO.getBookListById(bookListId);
    }

    //获取初始书单(5个)
    List<BookList> getNewList(){
        return bookListDAO.getNewList();
    }

    //分页查询全部书单信息(按照ID倒序)
    List<BookList> getAll(int pageNumber){
        return bookListDAO.getAll(pageNumber);
    }
}
