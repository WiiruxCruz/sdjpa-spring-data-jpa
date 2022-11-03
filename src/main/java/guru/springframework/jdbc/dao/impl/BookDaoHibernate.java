package guru.springframework.jdbc.dao.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

@Component
public class BookDaoHibernate implements BookDao {
	
	EntityManagerFactory emf;
	
	public BookDaoHibernate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Book> findAllBooksSortByTitle(Pageable pageable) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		
		try {
			String hql = "SELECT b FROM Book b order by b.title "
					+ pageable.getSort().getOrderFor("title").getDirection().name();
			
			TypedQuery<Book> query = em.createQuery(hql, Book.class);
			query.setFirstResult(Math.toIntExact( pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
			query.setFirstResult(Math.toIntExact( pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Book> findAllBooks(int pageSize, int offSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findAllBooks() {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
			
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Book findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book saveNewBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBookById(Long id) {
		// TODO Auto-generated method stub

	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
