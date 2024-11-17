package Book_Exchange_Platform.book.exchange.Controller;

import Book_Exchange_Platform.book.exchange.Exception.ResourceNotFoundException;
import Book_Exchange_Platform.book.exchange.Repository.BookRepository;
import Book_Exchange_Platform.book.exchange.Resources.Book;
import Book_Exchange_Platform.book.exchange.Resources.BookDTO;
import Book_Exchange_Platform.book.exchange.Service.BookService;
import Book_Exchange_Platform.book.exchange.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private final UserService userService;

    public BookController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestParam("user_id") Long id, @RequestBody Book book) {
      //  book.setBookImage(book.getBookImage()); // Include bookImage
        Book savedBook = bookService.addBookToUser(id, book);
        return ResponseEntity.ok(savedBook);
    }


    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getUserBooks(@RequestParam("user_id") Long userId) {
        List<BookDTO> books = bookService.getUserBooks(userId);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully."); // Change from 204 to 200
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the book.");
        }
    }

    @PutMapping("/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
       // updatedBook.setBookImage(updatedBook.getBookImage()); // Include bookImage
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean available
    ) {
        // Log the received query parameters
        System.out.println("Received query parameters:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Available: " + available);

        // Fetch the books based on the criteria
        List<Book> books = bookRepository.findByCriteria(genre, author, title, available);

        // Log the result size
        System.out.println("Books found: " + books.size());

        return books;
    }


}

