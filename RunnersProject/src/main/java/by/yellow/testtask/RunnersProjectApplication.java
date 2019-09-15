package by.yellow.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "by.yellow.testtask.repository")
@SpringBootApplication
public class RunnersProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunnersProjectApplication.class, args);
	}

}
