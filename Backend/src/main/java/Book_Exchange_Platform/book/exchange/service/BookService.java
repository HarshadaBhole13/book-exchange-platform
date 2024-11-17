package Book_Exchange_Platform.book.exchange.Service;


import Book_Exchange_Platform.book.exchange.Exception.ResourceNotFoundException;
import Book_Exchange_Platform.book.exchange.Repository.BookRepository;
import Book_Exchange_Platform.book.exchange.Repository.UserRepository;
import Book_Exchange_Platform.book.exchange.Resources.Book;
import Book_Exchange_Platform.book.exchange.Resources.BookDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Transactional
    public Book addBookToUser(Long userId, Book book) {
        return userRepository.findById(userId).map(user -> {
            book.setUser(user);
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }


    public List<BookDTO> getUserBooks(Long userId) {
        List<Book> books = bookRepository.findByUserId(userId);
        return books.stream()
                .map(book -> new BookDTO(
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getBookCondition(), // Updated
                        book.isAvailable(),
                        book.getCreatedAt(),
                        book.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        bookRepository.delete(book);
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setBookCondition(updatedBook.getBookCondition()); // Updated
        book.setAvailable(updatedBook.isAvailable());
      //  book.setBookImage(updatedBook.getBookImage()); // Updated

        return bookRepository.save(book);
    }

    public List<Book> getAllAvailableBooks() {
        return bookRepository.findAllAvailableBooks();
    }

}

