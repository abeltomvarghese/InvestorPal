package com.InvestorPal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories()
@SpringBootApplication
public class InvestorPalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestorPalApplication.class, args);
	}

}
