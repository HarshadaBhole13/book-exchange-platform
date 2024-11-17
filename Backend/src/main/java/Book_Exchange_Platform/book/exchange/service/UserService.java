package Book_Exchange_Platform.book.exchange.Service;

import Book_Exchange_Platform.book.exchange.Exception.ResourceNotFoundException;
import Book_Exchange_Platform.book.exchange.Repository.PasswordResetTokenRepository;
import Book_Exchange_Platform.book.exchange.Repository.UserRepository;
import Book_Exchange_Platform.book.exchange.Resources.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // Change to PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use PasswordEncoder interface

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private PasswordResetService passwordResetService;

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create a new token and save it
        PasswordResetToken resetToken = new PasswordResetToken(user);
        tokenRepository.save(resetToken);

        // Send reset email and handle token deletion after 2 seconds
        passwordResetService.sendResetEmail(user.getEmail(), resetToken.getToken());  // Call sendResetEmail from PasswordResetService
    }


    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // Invalidate token after use
    }

}

