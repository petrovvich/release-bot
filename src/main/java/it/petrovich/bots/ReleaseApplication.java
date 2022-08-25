package it.petrovich.bots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReleaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReleaseApplication.class, args);
    }

}
