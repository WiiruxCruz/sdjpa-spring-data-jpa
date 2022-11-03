package guru.springframework.jdbc.dao.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

@Component
public class AuthorDaoHibernate implements AuthorDao {
	
	EntityManagerFactory emf;
	
	public AuthorDaoHibernate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		return em.find(Author.class, id);
		
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		EntityManager em = getEntityManager();
    	//syntax of the entity en java 
    	TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a "
    			+ "WHERE a.firstName = :first_name and a.lastName = :last_name", Author.class);
    	//TypedQuery<Author> query = em.createNamedQuery("find_by_name", Author.class);
    	
    	query.setParameter("first_name", firstName);
    	query.setParameter("last_name", lastName);
    	
    	Author author = query.getSingleResult();
    	em.close();
        
    	return author;
	}

	@Override
	public List<Author> findAllAuthorByLastName(String lastName, Pageable pageable) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		
		try {
			String hql = "SELECT a FROM Author a where a.lastName = :lastName";
			
			if(pageable.getSort().getOrderFor("firstname") != null) {
				hql = hql + " order by a.firstName "
						+ pageable.getSort().getOrderFor("firstname").getDirection().name();
			}
			
			TypedQuery<Author> query = em.createQuery(hql, Author.class);
			
			query.setParameter("lastName", lastName);
			query.setFirstResult(Math.toIntExact(pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			
			return query.getResultList();
		} finally {
			em.close();
		}
		
	}

	@Override
	public Author saveNewAuthor(Author author) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
    	//em.joinTransaction();
    	em.getTransaction().begin();
    	em.persist(author);
    	em.flush();
    	em.getTransaction().commit();
    	
        return author;
	}

	@Override
	public Author updateAuthor(Author author) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
    	em.joinTransaction();
    	em.merge(author);
    	em.flush();
    	em.clear();
        return em.find(Author.class, author.getId());
	}

	@Override
	public void deleteAuthorById(Long id) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
    	
    	em.getTransaction().begin();
    	Author author = em.find(Author.class, id);
    	em.remove(author);
    	em.flush();
    	em.getTransaction().commit();
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
