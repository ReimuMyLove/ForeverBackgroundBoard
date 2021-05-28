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

	private final BookCommentService bookCommentService;

	@Autowired
	public BookCommentController(BookCommentService bookCommentService) {
		this.bookCommentService = bookCommentService;
	}

	//查询全部书评
	@GetMapping("/getAll")
	@ResponseBody
	public List<BookComment> getAllComment(int pageNumber) {
		return bookCommentService.getAllComment(pageNumber);
	}

	//根据BookId查询书评
	@GetMapping("/byBookId")
	@ResponseBody
	public List<BookComment> getCommentByBookId(@RequestParam("bookId")int bookId,
												@RequestParam("pageNumber") int pageNumber){
		//pageNumber - 1 ; 得到limit语句实际页数(例如第一页是limit(0,10))
		pageNumber -= 1 ;
		//pageNumber * 10; 得到limit 开始查询行数
		pageNumber *= 10;
		return bookCommentService.getCommentByBookId(bookId,pageNumber);
	}

	//根据userId查询书评
	@GetMapping("/byUserId")
	@ResponseBody
	public List<BookComment> getCommentByUserId(@RequestParam("userId")int userId,
												@RequestParam("pageNumber") int pageNumber){
		//pageNumber - 1 ; 得到limit语句实际页数(例如第一页是limit(0,10)
		pageNumber -= 1 ;
		//pageNumber * 10; 得到limit 开始查询行数
		pageNumber *= 10;
		return bookCommentService.getCommentByUserId(userId, pageNumber);
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
