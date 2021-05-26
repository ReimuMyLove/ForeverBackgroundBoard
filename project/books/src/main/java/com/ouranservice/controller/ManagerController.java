package com.ouranservice.controller;

import com.ouranservice.service.BookCommentService;
import com.ouranservice.service.BookListService;
import com.ouranservice.service.BookService;
import com.ouranservice.service.BookTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage")
public class ManagerController {

    private final BookService bookService;
    private final BookTypesService bookTypesService;
    private final BookListService bookListService;
    private final BookCommentService bookCommentService;

    @Autowired
    public ManagerController(BookService bookService, BookTypesService bookTypesService, BookListService bookListService, BookCommentService bookCommentService) {
        this.bookService = bookService;
        this.bookTypesService = bookTypesService;
        this.bookListService = bookListService;
        this.bookCommentService = bookCommentService;
    }

    //跳转至登录页面
    @GetMapping("/welcome")
    public String toWelcome(){
        return "/welcome";
    }

    //跳转至登录页面
    @GetMapping("/")
    public String toWelcome2(){
        return "/welcome";
    }

    //跳转至管理页面首页
    @GetMapping("/bookData")
    public String toBookData(@RequestParam("pageNumber") int pageNumber){
        return "book/bookData";
    }

    //跳转至书籍信息管理
    @GetMapping("/bookManage")
    public String toBookManage(@RequestParam("bookId") int bookId,
                               HttpServletRequest request){
        request.setAttribute("bookId",bookId);
        return "book/bookManage";
    }

    //跳转至书籍添加页面
    @GetMapping("/bookAdd")
    public String toBookAdd(){
        return "book/bookAdd";
    }

    //跳转至书单展示页面
    @GetMapping("/bookListData")
    public String toBookListData(){
        return "/bookList/bookListData";
    }
}
