package guru.springframework.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.jdbc.dao.impl.BookDaoImpl;
import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
//@Import({BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
	@Autowired
	BookRepository br;
	
	@Test
	void testBookStream() {
		AtomicInteger count = new AtomicInteger();
		
		br.findAllByTitleNotNull().forEach(
			book -> {
				count.incrementAndGet();
		});
		
		assertThat(count.get()).isGreaterThan(5);
	}
	
	@Test
	void testEmptyResultException() {
		assertThrows(EmptyResultDataAccessException.class, () -> {
			Book book = br.readByTitle("foobar4");
		} );
	}
	
	@Test
	void testNullParam() {
		//Book b = br.getByTitle(null);
		assertNull(br.getByTitle(null));
	}
	
	@Test
	void testNoException() {
		assertNull(br.getByTitle("foo"));
	}
}
