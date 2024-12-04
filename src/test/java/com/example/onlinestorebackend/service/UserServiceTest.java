package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.DTO.UserRegistrationDTO;
import com.example.onlinestorebackend.entity.Role;
import com.example.onlinestorebackend.entity.User;
import com.example.onlinestorebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;
        userService.roleService = roleService;
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean exists = userService.existsByUsername("testuser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDTO dto = new UserRegistrationDTO("testuser", "test@test.com", "password123");
        Role role = new Role();
        role.setName("CUSTOMER");

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(roleService.findByName("CUSTOMER")).thenReturn(Optional.of(role));

        userService.registerUser(dto);

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("testuser") &&
                        user.getEmail().equals("test@test.com") &&
                        user.getPassword().equals("encodedPassword")
        ));
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        verify(roleService, times(1)).findByName("CUSTOMER");
    }
}
