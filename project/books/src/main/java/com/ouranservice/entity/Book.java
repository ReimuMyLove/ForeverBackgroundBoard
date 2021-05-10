package com.ouranservice.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class Book {
	private int bookId;						//Id
	private String bookName;				//书名
	private String bookWriter;				//作者
	private Timestamp bookPublishTime;		//出版时间		筛选类型-1
	private String bookPublishArea;			//出版地			筛选类型-2
	private String bookPic;					//封面
	private String bookIntroduction;		//简介
	private float bookStar;					//评分
	private String bookDownAddress;			//下载地址
	private int bookLikeCount;				//点赞数
	private Set<BookTypes> bookTypes;		//书籍类型		筛选类型-3
	private List<BookComment> bookComments; //书评

	/**
	 * 	getter and setter
	 */
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

	public String getBookWriter() {
		return bookWriter;
	}
	public void setBookWriter(String bookWriter) {
		this.bookWriter = bookWriter;
	}

	public Timestamp getBookPublishTime() {
		return bookPublishTime;
	}
	public void setBookPublishTime(Timestamp bookPublishTime) {
		this.bookPublishTime = bookPublishTime;
	}

	public String getBookPublishArea() {
		return bookPublishArea;
	}
	public void setBookPublishArea(String bookPublishArea) {
		this.bookPublishArea = bookPublishArea;
	}

	public String getBookPic() {
		return bookPic;
	}
	public void setBookPic(String bookPic) {
		this.bookPic = bookPic;
	}

	public String getBookIntroduction() {
		return bookIntroduction;
	}
	public void setBookIntroduction(String bookIntroduction) {
		this.bookIntroduction = bookIntroduction;
	}

	public float getBookStar() {
		return bookStar;
	}
	public void setBookStar(float bookStar) {
		this.bookStar = bookStar;
	}

	public String getBookDownAddress() {
		return bookDownAddress;
	}
	public void setBookDownAddress(String bookDownAddress) {
		this.bookDownAddress = bookDownAddress;
	}

	public int getBookLikeCount() {
		return bookLikeCount;
	}
	public void setBookLikeCount(int bookLikeCount) {
		this.bookLikeCount = bookLikeCount;
	}

	public Set<BookTypes> getBookTypes() {
		return bookTypes;
	}
	public void setBookTypes(Set<BookTypes> bookTypes) {
		this.bookTypes = bookTypes;
	}

	public List<BookComment> getBookComments() {
		return bookComments;
	}
	public void setBookComments(List<BookComment> bookComments) {
		this.bookComments = bookComments;
	}

	/**
	 * 	无参构造 and 有参构造
	 */
	public Book(int bookId, String bookName, String bookWriter, Timestamp bookPublishTime, String bookPublishArea, String bookPic, String bookIntroduction, float bookStar, String bookDownAddress, int bookLikeCount, Set<BookTypes> bookTypes, List<BookComment> bookComments) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookWriter = bookWriter;
		this.bookPublishTime = bookPublishTime;
		this.bookPublishArea = bookPublishArea;
		this.bookPic = bookPic;
		this.bookIntroduction = bookIntroduction;
		this.bookStar = bookStar;
		this.bookDownAddress = bookDownAddress;
		this.bookLikeCount = bookLikeCount;
		this.bookTypes = bookTypes;
		this.bookComments = bookComments;
	}

	public Book(String bookName, String bookWriter, Timestamp bookPublishTime, String bookPublishArea, String bookPic, String bookIntroduction, float bookStar, String bookDownAddress, int bookLikeCount, Set<BookTypes> bookTypes, List<BookComment> bookComments) {
		this.bookName = bookName;
		this.bookWriter = bookWriter;
		this.bookPublishTime = bookPublishTime;
		this.bookPublishArea = bookPublishArea;
		this.bookPic = bookPic;
		this.bookIntroduction = bookIntroduction;
		this.bookStar = bookStar;
		this.bookDownAddress = bookDownAddress;
		this.bookLikeCount = bookLikeCount;
		this.bookTypes = bookTypes;
		this.bookComments = bookComments;
	}

	public Book() {
	}

	/**
	 * 	toString()重写
	 */
	
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookType=" + bookTypes.toString() + ", bookName=" + bookName + ", bookWriter="
				+ bookWriter + ", bookPublishTime=" + bookPublishTime + ", bookPic=" + bookPic + ", bookIntroduction="
				+ bookIntroduction + ", BookLikeCount=" + bookLikeCount + "]";
	}
	
}
