package com.ouranservice.controller;

import com.ouranservice.entity.Book;
import com.ouranservice.service.BookService;
import com.ouranservice.service.BookTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller("/book")
@RequestMapping("/book")
public class BookController {

	final BookService bookService;
	final BookTypesService bookTypesService;

	@Autowired
	public BookController(BookService bookService, BookTypesService bookTypesService) {
		this.bookService = bookService;
		this.bookTypesService = bookTypesService;
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

	//根据筛选类型条件查询书籍
	@GetMapping("/byRequest")
	@ResponseBody
	public Set<Book> getBookByRequest(@RequestParam("type")String type,
									  @RequestParam("area")String area,
									  @RequestParam("year")String year,
									  @RequestParam("month")String month){
		//type属性判空
		int typeId = -1;
		if (type!=null && !type.equals("")){
			typeId = bookTypesService.getIdByName(type);
		}
		//年月判空
		String startTime = "",endTime = "";
		if(year != null && !year.equals("")){		//判断年份是否为空
			if (month!=null && !month.equals("")){	//判断月份是否为空 若不为空 则开始时间为当前年当前月1日,结束市价哪位当前年当前月31日
				startTime = year+"-"+month+"-01";
				endTime   = year+"-"+month+"-28";
			}else{				//如果月份为空 则默认开始时间为当前年1月1日,结束时间为当前年12月31日
				startTime = year+"-01-01";
				endTime   = year+"-12-31";
			}
		}
		return bookService.getBookByRequest(typeId, area,startTime,endTime);
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
