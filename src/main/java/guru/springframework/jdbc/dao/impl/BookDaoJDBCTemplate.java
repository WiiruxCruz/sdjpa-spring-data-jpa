package guru.springframework.jdbc.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.mapper.BookMapper;
import guru.springframework.jdbc.domain.Book;

public class BookDaoJDBCTemplate implements BookDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public BookDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Book findByTitle(String title) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(
			"SELECT * FROM book where title = ?",
			getRowMapper(),
			title
		);
	}

	@Override
	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(
			"SELECT * FROM book where id = ?",
			getRowMapper(),
			id
		);
	}

	@Override
	public Book saveNewBook(Book book) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"INSERT INTO book(title, isbn, publisher, author_id) values(?, ?, ?, ?)",
			book.getTitle(),
			book.getIsbn(),
			book.getPublisher(),
			book.getAuthorId()
		);
		
		Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		
		return this.getById(createdId);
	}

	@Override
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ?",
			book.getTitle(),
			book.getIsbn(),
			book.getPublisher(),
			book.getAuthorId()
		);
		
		return this.getById(book.getId());
	}

	@Override
	public void deleteBookById(Long id) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"DELETE FROM book where id = ?",
			id
		);
	}

	private RowMapper<Book> getRowMapper() {
		return new BookMapper();
	}
}
