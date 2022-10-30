package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findBookByTitle(String title);
}
