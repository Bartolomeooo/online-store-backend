package com.example.onlinestorebackend.service;

import com.example.onlinestorebackend.entity.Role;
import com.example.onlinestorebackend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName() {
        String roleName = "ADMIN";

        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findByName(roleName);

        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getName());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    @Test
    void testFindByNameNotFound() {
        String roleName = "NON_EXISTENT";

        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findByName(roleName);

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findByName(roleName);
    }
}
