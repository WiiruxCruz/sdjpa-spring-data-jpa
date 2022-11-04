package guru.springframework.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.jdbc.dao.BookDao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJPATest {
	@Autowired
	@Qualifier("bookDaoImpl")
	BookDao bd;
	
	@Test
	void testFindAllBooksPage1_sortByTitle() {
		List<Book> books = bd.findAllBooksSortByTitle(
			PageRequest.of(
				0,
				10,
				Sort.by(
					Sort.Order.desc("title")
				)
			)
		);
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooksPage1_pageable() {
		List<Book> books = bd.findAllBooks(PageRequest.of(0, 10));
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooksPage2_pageable() {
		List<Book> books = bd.findAllBooks(PageRequest.of(1, 10));
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooksPage10_pageable() {
		List<Book> books = bd.findAllBooks(PageRequest.of(10, 10));
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(0);
	}
	
	@Test
	void testFindAllBooksPage1() {
		List<Book> books = bd.findAllBooks(10, 0);
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooksPage2() {
		List<Book> books = bd.findAllBooks(10, 10);
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooksPage10() {
		List<Book> books = bd.findAllBooks(10, 100);
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(0);
	}
	
	@Test
	void testFindAllBooks() {
		List<Book> books = bd.findAllBooks();
		assertThat(books).isNotNull();
		assertThat(books.size()).isGreaterThan(5);
	}
	
	@Test
	void getById() {
		Book book = bd.getById(4L);
		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	void findBookByTitle() {
		Book book = bd.findByTitle("Clean Code");
		assertThat(book).isNotNull();
	}
	
	@Test
	void testUpdateBook() {
		Book book = new Book();
		book.setIsbn("ISBN");
		book.setPublisher("PUBLISHER");
		book.setTitle("Titulo");
		
		Author author = new Author();
		author.setId(3L);
		
		book.setAuthorId(author.getId());
		
		Book saved = bd.saveNewBook(book);
		saved.setPublisher("PUBLICISTA");
		Book updated = bd.updateBook(saved);
		
		assertThat(updated.getPublisher()).isEqualTo("PUBLICISTA");
	}
	
	@Test
	void testSaveBook() {
		Book book = new Book();
		book.setIsbn("ISBN2");
		book.setPublisher("PUBLISHER2");
		book.setTitle("Titulo2");
		
		Author author = new Author();
		author.setId(3L);
		
		book.setAuthorId(author.getId());
		Book saved = bd.saveNewBook(book);
		
		assertThat(saved).isNotNull();
		
	}
	
	@Test
	void testDeleteBook() {
		Book book = new Book();
		book.setIsbn("test");
		book.setPublisher("test2");
		book.setTitle("test3");
		
		Book saved = bd.saveNewBook(book);
		
		bd.deleteBookById(saved.getId());
		
		assertThrows(
			JpaObjectRetrievalFailureException.class,
			() -> {
				//bd.get(saved.getId());
				bd.getById(saved.getId());
			}
		);
	}
}
