package Book_Exchange_Platform.book.exchange.Repository;

import Book_Exchange_Platform.book.exchange.Resources.Book;
import Book_Exchange_Platform.book.exchange.Resources.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    Optional<UserPreferences> findByUserId(Long userId);
}
