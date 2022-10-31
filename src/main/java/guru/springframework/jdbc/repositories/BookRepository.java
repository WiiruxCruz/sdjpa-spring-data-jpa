package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("SELECT b FROM Book b WHERE title = :title")
	Book findBookByTitleWithQueryNamed(@Param("title") String title);
	
	@Query("SELECT b FROM Book b WHERE title = ?1")
	Book findBookBytitleWithQuery(String title);
	
	Optional<Book> findBookByTitle(String title);
	Book readByTitle(String title);
	
	@Nullable
	Book getByTitle(@Nullable String title);
	
	Stream<Book> findAllByTitleNotNull();
	
	@Async
	Future<Book> queryByTitle(String title);
	
}
