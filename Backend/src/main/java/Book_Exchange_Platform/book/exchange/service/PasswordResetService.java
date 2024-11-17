package Book_Exchange_Platform.book.exchange.Service;

import Book_Exchange_Platform.book.exchange.Repository.PasswordResetTokenRepository;
import Book_Exchange_Platform.book.exchange.Resources.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    // This method is invoked when the forgot password flow starts
    public void sendResetEmail(String email, String token) {
        // Send the reset email logic
        String resetLink = "http://yourapp.com/reset-password?token=" + token;
        sendEmail(email, resetLink); // Your email sending logic

        // Schedule the token deletion after 2 seconds
        deleteTokenAfterDelay(token);
    }

    // Function to send email (implement your email logic here)
    private void sendEmail(String email, String resetLink) {
        // Your email sending logic here
    }

    // Use Timer to schedule the deletion of the token after 2 seconds
    private void deleteTokenAfterDelay(String token) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Optional<PasswordResetToken> resetTokenOpt = passwordResetTokenRepository.findByToken(token);
                if (resetTokenOpt.isPresent()) {
                    PasswordResetToken resetToken = resetTokenOpt.get();
                    passwordResetTokenRepository.delete(resetToken);
                    System.out.println("Password reset token deleted after delay.");
                }
            }
        }, 2000); // 2 seconds delay
    }
}
