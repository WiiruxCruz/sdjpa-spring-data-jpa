package guru.springframework.jdbc.dao.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
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
	public List<Book> findAllBooksSortByTitle(Pageable pageable) {
		String sql = "SELECT * FROM Book order by title " + pageable.getSort().getOrderFor("title").getDirection().name()
				+ " limit ? offset ?";
		
		System.out.println("******************************");
		System.out.println(sql);
		
		return jdbcTemplate.query(
			sql,
			getBookMapper(),
			pageable.getPageSize(),
			pageable.getOffset()
		);
	}
	
	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		return jdbcTemplate.query("SELECT * FROM Book limit ? offset ?",
			getBookMapper(),
			pageable.getPageSize(),
			pageable.getOffset()
		);
	}
	
	@Override
	public List<Book> findAllBooks(int pageSize, int offSet) {
		return jdbcTemplate.query("SELECT * FROM book limit ? offset ?",
			getBookMapper(),
			pageSize,
			offSet
		);
	}
	
	@Override
	public List<Book> findAllBooks() {
		
		return jdbcTemplate.query("SELECT * FROM Book", getBookMapper());
	}

	@Override
	public Book findByTitle(String title) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(
			"SELECT * FROM book where title = ?",
			getBookMapper(),
			title
		);
	}

	@Override
	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(
			"SELECT * FROM book where id = ?",
			getBookMapper(),
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

	private RowMapper<Book> getBookMapper() {
		return new BookMapper();
	}
}
