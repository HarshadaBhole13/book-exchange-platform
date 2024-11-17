package Book_Exchange_Platform.book.exchange.Resources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String bookCondition; // Renamed
    private boolean available;
   // private byte[] bookImage; // Added
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public BookDTO(Long id, String title, String author, String genre, String bookCondition, boolean available, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookCondition = bookCondition;
        this.available = available;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public BookDTO(Book book) {
        this.id = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.bookCondition = book.getBookCondition(); // Updated
        this.available = book.isAvailable();
        this.createdAt=book.getCreatedAt();
        this.updatedAt=book.getUpdatedAt();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    }
// Getters and Setters
