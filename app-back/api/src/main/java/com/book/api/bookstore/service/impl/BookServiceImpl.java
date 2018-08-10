package com.book.api.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.entity.repository.BookRepository;
import com.book.api.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private final static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll().stream().filter(a -> a.isActive() == true).collect(Collectors.toList());
	}

	@Override
	public Book findOne(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			return book.get();
		} else {
			throw new RuntimeException("book not found");
		}
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> findAllByTitle(String keyword) {
		// TODO Auto-generated method stub
		return bookRepository.findByTitleContaining(keyword).stream().filter(a -> a.isActive() == true)
				.collect(Collectors.toList());
	}

	@Override
	public void removeOne(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			bookRepository.delete(book.get());
		} else {
			logger.info("l'operation de suppression a echoué car l entite ne trouve pas");
		}
	}
	

	
	

}
