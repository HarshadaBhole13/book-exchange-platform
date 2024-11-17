package Book_Exchange_Platform.book.exchange.Service;

import Book_Exchange_Platform.book.exchange.Repository.MessageRepository;
import Book_Exchange_Platform.book.exchange.Resources.Message;
import Book_Exchange_Platform.book.exchange.Resources.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Send message from one user to another
    public void sendMessage(User sender, User receiver, String content,String deliveryMethod, String duration) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setDeliveryMethod(deliveryMethod);
        message.setDuration(duration);
        message.setSentAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    // Get messages for a specific receiver
    public List<Message> getMessagesForReceiver(Long receiverId) {
        return messageRepository.findMessagesByReceiverId(receiverId);
    }

    // Get messages for a specific user
    public List<Message> getMessagesForUser(User receiver) {
        return messageRepository.findByReceiver(receiver);
    }
}
