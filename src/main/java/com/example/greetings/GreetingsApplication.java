package com.example.greetings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication()
//@ComponentScan({"com.example.greetings.GreetingRepository"})
public class GreetingsApplication {


	public static void main(String[] args) {

		SpringApplication.run(GreetingsApplication.class, args);
	}


}
