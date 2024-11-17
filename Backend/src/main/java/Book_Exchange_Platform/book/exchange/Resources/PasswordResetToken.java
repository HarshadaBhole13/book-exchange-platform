package Book_Exchange_Platform.book.exchange.Resources;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // Updated to match User's column name
    private User user;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    // Constructor to generate a token and set the expiration date automatically
    public PasswordResetToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now().plusMinutes(30); // Set token expiration to 30 minutes
    }

    // Default constructor
    public PasswordResetToken() {}

    // Getters and setters
    public String getToken() { return token; }
    public User getUser() { return user; }
    public LocalDateTime getExpirationDate() { return expirationDate; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }
}
