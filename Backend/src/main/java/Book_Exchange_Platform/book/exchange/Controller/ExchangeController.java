package Book_Exchange_Platform.book.exchange.Controller;

import Book_Exchange_Platform.book.exchange.Repository.UserRepository;
import Book_Exchange_Platform.book.exchange.Resources.Book;
import Book_Exchange_Platform.book.exchange.Resources.ExchangeRequestDTO;
import Book_Exchange_Platform.book.exchange.Resources.Message;
import Book_Exchange_Platform.book.exchange.Resources.User;
import Book_Exchange_Platform.book.exchange.Service.BookService;
import Book_Exchange_Platform.book.exchange.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ExchangeController {

    @Autowired
    private BookService bookService; // Assuming you have a BookService
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/exchange")
    public ResponseEntity<String> sendExchangeRequest(@RequestBody ExchangeRequestDTO exchangeRequest) {
        // Get the sender (current logged-in user)
        User sender = userRepository.findById(exchangeRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Get the book the user wants to exchange
        Book book = bookService.getBookById(exchangeRequest.getBookId());

        // Get the receiver (user who owns the book)
        User receiver = book.getUser();

        // Create a message content
        String messageContent = "User " + sender.getUsername() + " has requested to exchange the book: "
                + book.getTitle() + " with you. Please review the request.";

        // Send message to the receiver
        messageService.sendMessage(sender, receiver, messageContent,exchangeRequest.getDeliveryMethod(),exchangeRequest.getDuration());

        return ResponseEntity.ok("Exchange request sent.");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessagesForReceiver(@RequestParam Long receiverId) {
        List<Message> messages = messageService.getMessagesForReceiver(receiverId);
        return ResponseEntity.ok(messages);
    }
}
