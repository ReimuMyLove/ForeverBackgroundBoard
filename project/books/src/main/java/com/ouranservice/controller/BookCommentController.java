package com.ouranservice.controller;

import java.util.List;

import com.ouranservice.entity.BookComment;
import com.ouranservice.service.BookCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("/comment")
@RequestMapping("/comment")
public class BookCommentController {

	final BookCommentService bookCommentService;

	@Autowired
	public BookCommentController(BookCommentService bookCommentService) {
		this.bookCommentService = bookCommentService;
	}

	//查询全部书评
	@GetMapping("/getAll")
	@ResponseBody
	public List<BookComment> getAllComment() {
		return bookCommentService.getAllComment();
	}

	//根据BookId查询书评
	@GetMapping("/byBookId")
	@ResponseBody
	public List<BookComment> getCommentByBookId(@RequestParam("bookId")int bookId){
		return bookCommentService.getCommentByBookId(bookId);
	}

	//根据userId查询书评
	@GetMapping("/byUserId")
	@ResponseBody
	public List<BookComment> getCommentByUserId(@RequestParam("userId")int userId){
		return bookCommentService.getCommentByUserId(userId);
	}

	//添加书评
	@PostMapping("/add")
	@ResponseBody
	public String addComment(@RequestParam("bookComment")BookComment bookComment){
		int count = bookCommentService.addComment(bookComment);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}

	//根据commentId删除书评
	@PostMapping("/delete")
	@ResponseBody
	public String deleteComment(@RequestParam("commentId")int commentId){
		int count = bookCommentService.deleteComment(commentId);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}

	//根据commentId点赞书评
	@GetMapping("/commentLike")
	@ResponseBody
	public String commentLike(@RequestParam("commentId")int commentId){
		int count = bookCommentService.commentLike(commentId);
		if(count == 1){
			return "OK";
		}else {
			return "ERROR";
		}
	}
}
