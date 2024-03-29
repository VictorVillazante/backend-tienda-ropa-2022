package ucb.edu.bo.storebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class StoreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreBackendApplication.class, args);
	}

}
