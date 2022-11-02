package guru.springframework.jdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import guru.springframework.jdbc.domain.Book;

public class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setId(rs.getLong("id"));
		book.setIsbn(rs.getString("isbn"));
		book.setPublisher(rs.getString("publisher"));
		book.setTitle(rs.getString("title"));
		book.setAuthorId(rs.getLong("author_id"));
		return book;
	}

}
