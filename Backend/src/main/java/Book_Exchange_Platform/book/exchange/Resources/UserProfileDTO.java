package Book_Exchange_Platform.book.exchange.Resources;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class UserProfileDTO {
    private String username;
    private String email;
    private String readingPreferences;
    private String favoriteGenres;
    private String bookwish;
    private Long userId;
    // Getters and setters

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // Use "user_id" here
    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReadingPreferences() {
        return readingPreferences;
    }

    public void setReadingPreferences(String readingPreferences) {
        this.readingPreferences = readingPreferences;
    }

    public String getFavoriteGenres() {
        return favoriteGenres;
    }

    public void setFavoriteGenres(String favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }

    public String getBookwish() {
        return bookwish;
    }

    public void setBookwish(String bookwish) {
        this.bookwish = bookwish;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
