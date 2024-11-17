package Book_Exchange_Platform.book.exchange.Service;

import Book_Exchange_Platform.book.exchange.Repository.UserPreferencesRepository;
import Book_Exchange_Platform.book.exchange.Repository.UserRepository;
import Book_Exchange_Platform.book.exchange.Resources.User;
import Book_Exchange_Platform.book.exchange.Resources.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository preferencesRepository;

    @Autowired
    private UserRepository userRepository;

}
