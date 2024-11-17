package Book_Exchange_Platform.book.exchange.Repository;

import Book_Exchange_Platform.book.exchange.Resources.Book;
import Book_Exchange_Platform.book.exchange.Resources.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserId(Long userId);

    // Query to fetch books with available = true
    @Query("SELECT b FROM Book b WHERE b.available = true")
    List<Book> findAllAvailableBooks();


    @Query("SELECT b FROM Book b WHERE " +
            "(:genre IS NULL OR b.genre = :genre) AND " +
            "(:author IS NULL OR b.author = :author) AND " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " + // Use LIKE for partial match
            "(:available IS NULL OR b.available = :available)")
    List<Book> findByCriteria(
            @Param("genre") String genre,
            @Param("author") String author,
            @Param("title") String title,
            @Param("available") Boolean available
    );



}

