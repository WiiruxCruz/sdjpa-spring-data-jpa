package guru.springframework.jdbc.dao;

import java.util.List;

import guru.springframework.jdbc.domain.Book;

public interface BookDao {
	List<Book> findAllBooks();
	Book findByTitle(String title);
	Book getById(Long id);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	void deleteBookById(Long id);
	
}
