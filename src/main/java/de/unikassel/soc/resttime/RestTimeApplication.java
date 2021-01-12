package de.unikassel.soc.resttime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestTimeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(RestTimeApplication.class, args);
	}

}
