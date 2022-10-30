package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

public interface BookDao {
	Book findByTitle(String title);
	Book getById(Long id);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	void deleteBookById(Long id);
	
}
