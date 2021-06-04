package com.ouranservice.entity;

public class BookToType {
    private int Id;
    private int bookId;
    private int typeId;

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public BookToType(int id, int bookId, int typeId) {
        Id = id;
        this.bookId = bookId;
        this.typeId = typeId;
    }

    public BookToType(int bookId, int typeId) {
        this.bookId = bookId;
        this.typeId = typeId;
    }

    public BookToType() {
    }

    @Override
    public String toString() {
        return "BookToType{" +
                "Id=" + Id +
                ", bookId=" + bookId +
                ", typeId=" + typeId +
                '}';
    }
}
