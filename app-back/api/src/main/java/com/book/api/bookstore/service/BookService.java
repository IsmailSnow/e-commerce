package com.book.api.bookstore.service;

import java.util.List;

import com.book.api.bookstore.entity.Book;

public interface BookService {
	
	List<Book> findAll();
	
	Book findOne(Long id);
	
	Book save(Book book);
	
	List<Book> findAllByTitle(String title);
	
	void removeOne(Long id);
}
