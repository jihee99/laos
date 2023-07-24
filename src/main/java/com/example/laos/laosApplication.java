package com.example.laos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class laosApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(laosApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
