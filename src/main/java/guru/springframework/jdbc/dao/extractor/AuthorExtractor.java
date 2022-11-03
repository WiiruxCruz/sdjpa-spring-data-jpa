package guru.springframework.jdbc.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import guru.springframework.jdbc.dao.mapper.AuthorMapper;
import guru.springframework.jdbc.domain.Author;

public class AuthorExtractor implements ResultSetExtractor<Author> {

	@Override
	public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		rs.next();
		return new AuthorMapper().mapRow(rs, 0);
	}

}
