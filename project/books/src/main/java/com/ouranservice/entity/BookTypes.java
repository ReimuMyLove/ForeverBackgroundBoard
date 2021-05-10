package com.ouranservice.entity;

import java.util.List;

public class BookTypes {
	int typeId;			//类型Id
	String typeName;	//类型名
	List<Book> books;	//包含书籍

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public BookTypes(int typeId, String typeName, List<Book> books) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.books = books;
	}

	public BookTypes(String typeName, List<Book> books) {
		super();
		this.typeName = typeName;
		this.books = books;
	}

	public BookTypes() {
	}

	@Override
	public String toString() {
		return "BookTypes [typeId=" + typeId + ", typeName=" + typeName + ", books=" + books.toString() + "]";
	}
	
}
