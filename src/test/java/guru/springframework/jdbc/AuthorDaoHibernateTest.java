package guru.springframework.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.impl.AuthorDaoHibernate;
import guru.springframework.jdbc.dao.impl.AuthorDaoJDBCTemplate;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManagerFactory;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
//@Import({AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoHibernateTest {
	@Autowired
    EntityManagerFactory emf;

    AuthorDao authorDao;
    
    @BeforeEach
	void setup() {
		authorDao = new AuthorDaoHibernate(emf); 
	}

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());
        	
		Author deleted = authorDao.getById(saved.getId());
		assertThat(deleted).isNull();

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");

    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }
    
    
    @Test
	void testFindAllAuthorsByLastName() {
		List<Author> authors = authorDao.findAllAuthorByLastName(
			"Smith",
			PageRequest.of(
				0,
				10
			)
		);
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isEqualTo(10);
	}
    
    @Test
   	void testFindAllAuthorsByLastNameAllRecs() {
   		List<Author> authors = authorDao.findAllAuthorByLastName(
   			"Smith",
   			PageRequest.of(
   				0,
   				100
   			)
   		);
   		assertThat(authors).isNotNull();
   		assertThat(authors.size()).isEqualTo(50);
   	}
    
    @Test
   	void testFindAllAuthorsByLastNameSortLastNameDesc() {
   		List<Author> authors = authorDao.findAllAuthorByLastName(
   			"Smith",
   			PageRequest.of(
   				0,
   				10,
   				Sort.by(
   					Sort.Order.desc("first_name")
   				)
   			)
   		);
   		assertThat(authors).isNotNull();
   		assertThat(authors.size()).isEqualTo(10);
   		assertThat(authors.get(0).getFirstName()).isEqualTo("Craig01");
   	}
    
    @Test
   	void testFindAllAuthorsByLastNameSortLastNameAsc() {
   		List<Author> authors = authorDao.findAllAuthorByLastName(
   			"Smith",
   			PageRequest.of(
   				0,
   				10,
   				Sort.by(
   					Sort.Order.asc("first_name")
   				)
   			)
   		);
   		assertThat(authors).isNotNull();
   		assertThat(authors.size()).isEqualTo(10);
   		assertThat(authors.get(0).getFirstName()).isEqualTo("Craig01");
   	}

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }
}
