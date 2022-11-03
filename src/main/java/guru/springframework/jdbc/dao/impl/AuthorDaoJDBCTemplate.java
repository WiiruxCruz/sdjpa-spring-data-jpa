package guru.springframework.jdbc.dao.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.extractor.AuthorExtractor;
import guru.springframework.jdbc.dao.mapper.AuthorMapper;
import guru.springframework.jdbc.domain.Author;

public class AuthorDaoJDBCTemplate implements AuthorDao {
private final JdbcTemplate jdbcTemplate;
	
	public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Author getById(Long id) {
		// TODO Auto-generated method stub
		String sql = 
		"select\r\n"
		+ "	author.id as id, first_name, last_name,\r\n"
		+ "    book.id as book_id, book.isbn, book.publisher,\r\n"
		+ "    book.title\r\n"
		+ "from author\r\n"
		+ "left outer join book on author.id = book.author_id\r\n"
		+ "where\r\n"
		+ "author.id = ?\r\n"
		;
		
		//return jdbcTemplate.queryForObject("SELECT * FROM author where id = ?", getRowMapper(), id);
		
		return jdbcTemplate.query(sql, new AuthorExtractor(), id);
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
		return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? and last_name = ?",
				getRowMapper(),
				firstName,
				lastName
			);
	}
	
	@Override
	public List<Author> findAllAuthorByLastName(String lastName, Pageable pageable) {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM Author WHERE last_name = ? ");
		
		if(pageable.getSort().getOrderFor("first_name") != null) {
			sb.append("ORDER BY first_name ").append(pageable.getSort().getOrderFor("first_name").getDirection().name());
		}
		
		sb.append(" limit ? offset ?");
		System.out.println("********************************");
		System.out.println(sb.toString());
		
		return jdbcTemplate.query(
			sb.toString(),
			getRowMapper(),
			lastName,
			pageable.getPageSize(),
			pageable.getOffset()
		);
	}

	@Override
	public Author saveNewAuthor(Author author) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"INSERT INTO author(first_name, last_name) values (?, ?)",
			author.getFirstName(),
			author.getLastName()
		);
		
		Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		
		return this.getById(createdId);
	}

	@Override
	public Author updateAuthor(Author author) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"UPDATE author SET first_name = ?, last_name = ? WHERE id = ? ",
			author.getFirstName(),
			author.getLastName(),
			author.getId()
		);
		
		return this.getById(author.getId());
	}

	@Override
	public void deleteAuthorById(Long id) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
			"DELETE FROM author where id = ?",
			id
		);
	}
	
	private RowMapper<Author> getRowMapper(){
		return new AuthorMapper();
	}
}
