package com.example.onlinestorebackend;

import com.example.onlinestorebackend.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineStoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreBackendApplication.class, args);

    }

}
