package com.book.api.bookstore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Book addBook(@RequestBody Book book) {
		return bookService.save(book);
	}

	@RequestMapping(value = "/add/image", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			String fileName = id + ".png";

			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
					"C://personnel/e-commerce/Trunk/app-back/api/src/main/resources/static/image/book/" + fileName)));
			stream.write(bytes);
			stream.close();
			return new ResponseEntity<String>("upload success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("upload failed !", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/update/image", method = RequestMethod.POST)
	public ResponseEntity<String> updateImagePost(@RequestParam("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			String fileName = id + ".png";
			Files.deleteIfExists(Paths.get(
					"C://personnel/e-commerce/Trunk/app-back/api/src/main/resources/static/image/book/" + fileName));
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
					"C://personnel/e-commerce/Trunk/app-back/api/src/main/resources/static/image/book/" + fileName)));
			stream.write(bytes);
			stream.close();
			return new ResponseEntity<String>("upload success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("upload failed !", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping("/bookList")
	public List<Book> getBookList() {
		return bookService.findAll();
	}

	@RequestMapping("/{id}")
	public Book getBook(@PathVariable("id") Long id) {
		return bookService.findOne(id);
	}

	@RequestMapping("/update")
	public Book updateBook(@RequestBody Book book) {
		// hibernate update directly the book using the id of entity
		return bookService.save(book);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ResponseEntity<String> remove(@RequestBody String id) throws IOException {

		String fileName = id + ".png";
		Files.deleteIfExists(Paths
				.get("C://personnel/e-commerce/Trunk/app-back/api/src/main/resources/static/image/book/" + fileName));
		bookService.removeOne(Long.parseLong(id));
		return new ResponseEntity<String>("Remove Sucess", HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchBook",method=RequestMethod.POST)
	public List<Book> searchBook(@RequestBody String keyword){
		return bookService.findAllByTitle(keyword);
	}
	
	
	
}
