package com.ouranservice.controller;

import com.ouranservice.entity.Book;
import com.ouranservice.entity.BookTypes;
import com.ouranservice.service.BookTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller("/types")
@RequestMapping("/types")
public class BookTypesController {

	final BookTypesService bookTypesService;

	@Autowired
	public BookTypesController(BookTypesService bookTypesService) {
		this.bookTypesService = bookTypesService;
	}

	//根据bookId查询bookTypes
	@GetMapping("/byBookId")
	@ResponseBody
	public Set<BookTypes> getTypesByBookId(@RequestParam("bookId")int bookId){
		return bookTypesService.getTypesByBookId(bookId);
	}

	//根据typeName查询对应type信息
	@GetMapping("/getBookByTypeName")
	@ResponseBody
	public BookTypes getTypeByTypeName(@RequestParam("typeName")String typeName){
		//通过typeName查询对应typeId
		int typeId = bookTypesService.getIdByName(typeName);

		//根据查找到的typeId获取对应的bookTypes信息
		return bookTypesService.getTypeByTypeId(typeId);
	}
}
