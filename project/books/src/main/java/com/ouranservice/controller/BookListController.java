package com.ouranservice.controller;

import com.ouranservice.entity.BookList;
import com.ouranservice.service.BookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("/bookList")
@RequestMapping("/bookList")
public class BookListController {
    final BookListService bookListService;

    @Autowired
    public BookListController(BookListService bookListService) {
        this.bookListService = bookListService;
    }

    //根据Id查询书单具体信息
    @GetMapping("/byId")
    @ResponseBody
    public BookList getBookListById(@RequestParam("bookListId") int bookListId){
        return bookListService.getBookListById(bookListId);
    }

    //获取初始书单(5个)
    @GetMapping("/newList")
    @ResponseBody
    public List<BookList> getNewList(){
        return bookListService.getNewList();
    }

    //分页查询全部书单信息(按照ID倒序)
    @GetMapping("/getAll")
    @ResponseBody
    public List<BookList> getAll(@RequestParam("pageNumber") int pageNumber){
        return bookListService.getAll(pageNumber);
    }
}
