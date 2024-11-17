package Book_Exchange_Platform.book.exchange.Repository;

import Book_Exchange_Platform.book.exchange.Resources.Message;
import Book_Exchange_Platform.book.exchange.Resources.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(User receiver);
    List<Message> findMessagesByReceiverId(Long receiverId);
}
