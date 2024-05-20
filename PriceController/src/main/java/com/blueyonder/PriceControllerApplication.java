package com.blueyonder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class PriceControllerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

	        // Set system properties
	        System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
	        System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USERNAME"));
	        System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));
		SpringApplication.run(PriceControllerApplication.class, args);
	}

}
