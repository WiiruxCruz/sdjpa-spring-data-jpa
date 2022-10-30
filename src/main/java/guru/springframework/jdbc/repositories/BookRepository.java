package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findBookByTitle(String title);
	Book readByTitle(String title);
	
	@Nullable
	Book getByTitle(@Nullable String title);
	
}
