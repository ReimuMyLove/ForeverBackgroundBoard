package com.ouranservice.entity;

import java.sql.Timestamp;

public class BookComment {
	private int commentId;			//Id
	private int bookId;				//书籍Id
	private String bookName;		//书籍名
	private int userId;				//评论者Id
	private String userName;		//评论者名
	private Timestamp commentTime;	//评论时间
	private String detail;			//内容
	private int commentLikeCount;	//点赞数

	/**
	 * 	getter and setter
	 */

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCommentLikeCount() {
		return commentLikeCount;
	}
	public void setCommentLikeCount(int commentLikeCount) {
		this.commentLikeCount = commentLikeCount;
	}

	/**
	 * 	无参构造 and 有参构造
	 */
	public BookComment(int commentId, int bookId, String bookName,
					   int userId, String userName, Timestamp commentTime,
					   String detail, int commentLikeCount) {
		this.commentId = commentId;
		this.bookId = bookId;
		this.bookName = bookName;
		this.userId = userId;
		this.userName = userName;
		this.commentTime = commentTime;
		this.detail = detail;
		this.commentLikeCount = commentLikeCount;
	}

	public BookComment(int bookId, String bookName, int userId,
					   String userName, Timestamp commentTime,
					   String detail, int commentLikeCount) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.userId = userId;
		this.userName = userName;
		this.commentTime = commentTime;
		this.detail = detail;
		this.commentLikeCount = commentLikeCount;
	}

	public BookComment() {
	}

	/**
	 * 	toString()重写
	 */

	@Override
	public String toString() {
		return "BookComment{" +
				"commentId=" + commentId +
				", bookId=" + bookId +
				", bookName='" + bookName + '\'' +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", detail='" + detail + '\'' +
				", commentLikeCount=" + commentLikeCount +
				'}';
	}
}
