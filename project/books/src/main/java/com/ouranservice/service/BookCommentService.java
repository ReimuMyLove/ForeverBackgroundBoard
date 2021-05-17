package com.ouranservice.service;

import java.util.List;
import java.util.Set;

import com.ouranservice.DAO.BookCommentDAO;
import com.ouranservice.entity.BookComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCommentService {

	final BookCommentDAO bookCommentDAO;

	@Autowired
	public BookCommentService(BookCommentDAO bookCommentDAO) {
		this.bookCommentDAO = bookCommentDAO;
	}


	//获取全部评论
	public List<BookComment> getAllComment() {
		return bookCommentDAO.getAllComment();
	}

	//根据bookId获取评论
    public List<BookComment> getCommentByBookId(int bookId, int pageNumber) {
		return bookCommentDAO.getCommentByBookId(bookId, pageNumber);
    }

    //根据userId获取评论
	public List<BookComment> getCommentByUserId(int userId, int pageNumber) {
		return bookCommentDAO.getCommentByUserId(userId, pageNumber);
	}

	//添加书评
	public int addComment(BookComment bookComment){
		return bookCommentDAO.addComment(bookComment);
	}

	public int commentLike(int commentId) {
		return bookCommentDAO.commentLike(commentId);
	}

	public int deleteComment(int commentId) {
		return bookCommentDAO.deleteComment(commentId);
	}
}
