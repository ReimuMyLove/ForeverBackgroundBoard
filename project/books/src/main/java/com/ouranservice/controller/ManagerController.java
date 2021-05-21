package com.ouranservice.controller;

import com.ouranservice.service.BookCommentService;
import com.ouranservice.service.BookListService;
import com.ouranservice.service.BookService;
import com.ouranservice.service.BookTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/")
    public String toWelcome(){
        return "/welcome";
    }
}
