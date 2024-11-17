package Book_Exchange_Platform.book.exchange.Controller;//package Book_Exchange_Platform.book.exchange.Controller;

import Book_Exchange_Platform.book.exchange.Exception.ResourceNotFoundException;
import Book_Exchange_Platform.book.exchange.Repository.UserPreferencesRepository;
import Book_Exchange_Platform.book.exchange.Repository.UserRepository;
import Book_Exchange_Platform.book.exchange.Resources.*;
import Book_Exchange_Platform.book.exchange.Service.UserPreferencesService;
import Book_Exchange_Platform.book.exchange.Service.UserService;
import Book_Exchange_Platform.book.exchange.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPreferencesService preferencesService;

    @Autowired
    private UserPreferencesService userPreferencesService;
    @Autowired
    UserPreferencesRepository userPreferencesRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (!userOptional.isPresent() ||
                !new BCryptPasswordEncoder().matches(request.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOptional.get();
        System.out.println("User authenticated successfully: " + user.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Successful");
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("Email",user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username or Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserDetails(@RequestParam("user_id") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            UserDTO userDTO = new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @GetMapping("/preferences")
    public ResponseEntity<UserPreferences> getUserPreferences(@RequestParam Long userId) {
        Optional<UserPreferences> userPreferences = userPreferencesRepository.findByUserId(userId);
        if (userPreferences.isPresent()) {
            UserPreferences foundUser = userPreferences.get();
            UserPreferences userPreferences1 = new UserPreferences(foundUser.getId(), foundUser.getReadingPreferences(), foundUser.getFavoriteGenres(), foundUser.getBookwish());
            return ResponseEntity.ok(userPreferences1);
        }else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDTO userProfile) {
        try {
            // Update User table
            Optional<User> userOpt = userRepository.findById(userProfile.getUserId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setUsername(userProfile.getUsername());
                user.setEmail(userProfile.getEmail());
                userRepository.save(user);
            }

            // Update UserPreferences table
            Optional<UserPreferences> prefsOpt = userPreferencesRepository.findByUserId(userProfile.getUserId());
            if (prefsOpt.isPresent()) {
                UserPreferences prefs = prefsOpt.get();
                prefs.setReadingPreferences(userProfile.getReadingPreferences());
                prefs.setFavoriteGenres(userProfile.getFavoriteGenres());
                prefs.setBookwish(userProfile.getBookwish());
                userPreferencesRepository.save(prefs);
            } else {
                // Create new preferences if they don't exist
                UserPreferences newPrefs = new UserPreferences();
                newPrefs.setReadingPreferences(userProfile.getReadingPreferences());
                newPrefs.setFavoriteGenres(userProfile.getFavoriteGenres());
                newPrefs.setBookwish(userProfile.getBookwish());
                userPreferencesRepository.save(newPrefs);
            }

            return ResponseEntity.ok("Profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile");
        }
    }

        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@RequestParam String email) {
            try {
                userService.initiatePasswordReset(email);
                return ResponseEntity.ok("Password reset email sent.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to send reset email.");
            }
        }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Provide meaningful error messages
        }
    }

}




