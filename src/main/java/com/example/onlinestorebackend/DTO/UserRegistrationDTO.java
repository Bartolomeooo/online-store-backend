package com.example.onlinestorebackend.DTO;

public class UserRegistrationDTO {

    private String username;
    private String email;
    private String password;

    public UserRegistrationDTO() {}

    // Konstruktor z argumentami
    public UserRegistrationDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Gettery i settery
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
