package guru.springframework.jdbc.dao.impl;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

/**
 * Created by jt on 8/28/21.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
	
	private final AuthorRepository ar;
	
	public AuthorDaoImpl(AuthorRepository ar) {
		this.ar = ar;
	}
	
    @Override
    public Author getById(Long id) {
        return ar.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return ar.findAuthorByFirstNameAndLastName(firstName, lastName)
        		.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return ar.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
    	Author foundAuthor = ar.getById(author.getId());
    	foundAuthor.setFirstName(author.getFirstName());
    	foundAuthor.setLastName(author.getLastName());
        return ar.save(foundAuthor);
    }

    @Override
    public void deleteAuthorById(Long id) {
    	ar.deleteById(id);
    }
}
