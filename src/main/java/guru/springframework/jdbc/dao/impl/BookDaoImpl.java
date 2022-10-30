package guru.springframework.jdbc.dao.impl;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class BookDaoImpl implements BookDao {
	
	BookRepository br;
	
	public BookDaoImpl(BookRepository br) {
		this.br = br;
	}

	@Override
	public Book findByTitle(String title) {
		// TODO Auto-generated method stub
		return br.findBookByTitle(title)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return br.getById(id);
	}

	@Override
	public Book saveNewBook(Book book) {
		// TODO Auto-generated method stub
		return br.save(book);
	}
	
	@Transactional
	@Override
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		Book foundBook = br.getById(book.getId());
		foundBook.setIsbn(book.getIsbn());
		foundBook.setPublisher(book.getPublisher());
		foundBook.setAuthorId(book.getAuthorId());
		foundBook.setTitle(book.getTitle());
		
		return br.save(foundBook);
	}

	@Override
	public void deleteBookById(Long id) {
		// TODO Auto-generated method stub
		br.deleteById(id);
	}

}
