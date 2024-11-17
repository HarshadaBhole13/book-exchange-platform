package Book_Exchange_Platform.book.exchange.Resources;

import jakarta.persistence.*;

@Entity
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserPreferences() {
    }

    public UserPreferences(Long id, String readingPreferences, String favoriteGenres, String bookwish) {
        this.id = id;
        this.readingPreferences = readingPreferences;
        this.favoriteGenres = favoriteGenres;
        this.bookwish = bookwish;
    }

    private String readingPreferences;
    private String favoriteGenres;
    private String bookwish;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // Use "user_id" here
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
