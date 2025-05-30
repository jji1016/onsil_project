package com.onsil.onsil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnsilApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnsilApplication.class, args);
    }

}
