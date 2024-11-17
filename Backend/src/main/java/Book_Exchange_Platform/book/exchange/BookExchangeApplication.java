package Book_Exchange_Platform.book.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("book_Exchange_Platform.book.exchange.Repository") // Specify repository package if different
@EntityScan("Book_Exchange_Platform.book.exchange.Resources") // Specify entity package if different
public class BookExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookExchangeApplication.class, args);
	}

}
