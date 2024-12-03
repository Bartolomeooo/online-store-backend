package com.example.onlinestorebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineStoreBackendApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "tak";
        String hashedPassword = encoder.encode(rawPassword);

        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println(hashedPassword);
        System.out.println("Password matches (manual test): " + matches);
        SpringApplication.run(OnlineStoreBackendApplication.class, args);
    }

}
