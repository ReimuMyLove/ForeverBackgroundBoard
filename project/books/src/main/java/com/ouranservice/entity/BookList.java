package com.ouranservice.entity;

import java.util.List;

public class BookList {
    private int bookListId;                 //书单Id
    private String bookListName;            //书单名字
    private String bookListIntroduction;    //书单简介
    private List<Book> bookList;            //书籍内容

    /**
     * getter and setter
     */
    public int getBookListId() {
        return bookListId;
    }
    public void setBookListId(int bookListId) {
        this.bookListId = bookListId;
    }

    public String getBookListName() {
        return bookListName;
    }
    public void setBookListName(String bookListName) {
        this.bookListName = bookListName;
    }

    public String getBookListIntroduction() {
        return bookListIntroduction;
    }
    public void setBookListIntroduction(String bookListIntroduction) {
        this.bookListIntroduction = bookListIntroduction;
    }

    public List<Book> getBookList() {
        return bookList;
    }
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * Constructor
     */
    //全参构造
    public BookList(int bookListId, String bookListName, String bookListIntroduction, List<Book> bookList) {
        this.bookListId = bookListId;
        this.bookListName = bookListName;
        this.bookListIntroduction = bookListIntroduction;
        this.bookList = bookList;
    }

    //自动生成构造
    public BookList(String bookListName, String bookListIntroduction, List<Book> bookList) {
        this.bookListName = bookListName;
        this.bookListIntroduction = bookListIntroduction;
        this.bookList = bookList;
    }

    //无参构造
    public BookList() {
    }

    /**
     * toString()
     */
    @Override
    public String toString() {
        return "BookList{" +
                "bookListId=" + bookListId +
                ", bookListName='" + bookListName + '\'' +
                ", bookListIntroduction='" + bookListIntroduction + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
