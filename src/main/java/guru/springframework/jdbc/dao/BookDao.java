package guru.springframework.jdbc.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.jdbc.domain.Book;

public interface BookDao {
	List<Book> findAllBooksSortByTitle(Pageable pageable);
	List<Book> findAllBooks(Pageable pageable);
	List<Book> findAllBooks(int pageSize, int offSet);
	List<Book> findAllBooks();
	Book findByTitle(String title);
	Book getById(Long id);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	void deleteBookById(Long id);
	
}
