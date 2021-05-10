package com.ouranservice.controller;

import com.ouranservice.entity.Book;
import com.ouranservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller("/book")
@RequestMapping("/book")
public class BookController {

	final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	//根据bookId查询简略书籍信息
	@GetMapping("/briefById")
	@ResponseBody
	public Book getBriefById(@RequestParam("bookId")int bookId){
		return bookService.getBriefById(bookId);
	}

	//根据bookId查询简略书籍信息
	@GetMapping("/detailById")
	@ResponseBody
	public Book getDetailById(@RequestParam("bookId")int bookId){
		return bookService.getDetailById(bookId);
	}

	//查询全部书籍
	@GetMapping("/getAll")
	@ResponseBody
	public Set<Book> getAll(){
		return bookService.getAll();
	}

	//添加书籍
	@PostMapping("/add")
	@ResponseBody
	public String addBook(@RequestParam("book") Book book){
		int count = bookService.addBook(book);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}

	//修改书籍信息
	@PostMapping("/update")
	@ResponseBody
	public String updateBook(@RequestParam("book") Book book){
		int count = bookService.updateBook(book);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}

	//删除书籍信息
	@PostMapping("/delete")
	@ResponseBody
	public String deleteBook(@RequestParam("bookId")int bookId){
		int count = bookService.deleteBook(bookId);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}
}
