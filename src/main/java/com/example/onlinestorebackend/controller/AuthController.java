package com.example.onlinestorebackend.controller;

import com.example.onlinestorebackend.DTO.JwtResponseDTO;
import com.example.onlinestorebackend.DTO.LoginRequestDTO;
import com.example.onlinestorebackend.DTO.UserRegistrationDTO;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.security.JwtUtils;
import com.example.onlinestorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            if (userService.existsByUsername(registrationDTO.getUsername())) {
                return ResponseEntity.badRequest().body("Username already taken.");
            }
            if (userService.existsByEmail(registrationDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already taken.");
            }
            userService.registerUser(registrationDTO);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().getName());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", user.getRole().getName() // Dodanie roli do odpowiedzi
        ));
    }

}
