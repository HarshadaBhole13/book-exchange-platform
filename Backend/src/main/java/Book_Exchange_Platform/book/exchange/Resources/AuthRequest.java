package Book_Exchange_Platform.book.exchange.Resources;

// AuthRequest.java
public class AuthRequest {
    private String Username; // Can be username or email
    private String password;

    // Constructors
    public AuthRequest() {}


    public AuthRequest(String username, String password) {
        Username = username;
        this.password = password;
    }

    // getters and setters
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
