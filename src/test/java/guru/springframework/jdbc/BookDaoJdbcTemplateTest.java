package guru.springframework.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.impl.BookDaoJDBCTemplate;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJdbcTemplateTest {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	BookDao bd;
	
	@BeforeEach
	void setup() {
		bd = new BookDaoJDBCTemplate(jdbcTemplate); 
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
		
		System.out.println("New id is:" + saved.getId());
		
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
			EmptyResultDataAccessException.class,
			() -> {
				//bd.get(saved.getId());
				bd.getById(saved.getId());
			}
		);
	}
	
}
