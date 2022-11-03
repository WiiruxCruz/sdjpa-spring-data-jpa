package guru.springframework.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import net.bytebuddy.utility.RandomString;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateTest {
	@Autowired
	BookDao bd;
	
	@Test
	void findAllBooksSortByTitle() {
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
	void findAllBooks() {
		List<Book> books = bd.findAllBooks(PageRequest.of(0, 10));
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBooks() {
		
	}
	
	@Test
	void testGetBook() {
		Book book = bd.getById(4L);
		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	void testGetBookByName() {
		Book book = bd.findByTitle("Clean Code");
		assertThat(book).isNotNull();
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
		assertThat(saved.getId()).isNotNull();
		
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
	void testDeleteBook() {
		Book book = new Book();
		book.setIsbn("test");
		book.setPublisher("test2");
		book.setTitle("test3");
		
		Book saved = bd.saveNewBook(book);
		
		bd.deleteBookById(saved.getId());
		
		Book deleted = bd.getById(saved.getId());
		assertThat(deleted).isNull();
		assertThat(bd.getById(saved.getId()));
	}
}
