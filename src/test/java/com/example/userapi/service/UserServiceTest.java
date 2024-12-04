package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence("France");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getUserName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUserName("Jane Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getUserName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testIsAdult_True() {
        LocalDate birthDate = LocalDate.of(2000, 1, 1);

        boolean result = userService.isAdult(birthDate);

        assertTrue(result, "User born in 2000 should be an adult");
    }

    @Test
    void testIsAdult_False() {
        LocalDate birthDate = LocalDate.now().minusYears(17);

        boolean result = userService.isAdult(birthDate);

        assertFalse(result, "User born 17 years ago should not be an adult");
    }

    @Test
    void testIsResidentInFrance_True() {
        String country = "France";

        boolean result = userService.isResidentInFrance(country);

        assertTrue(result, "User residing in 'France' should return true");
    }

    @Test
    void testIsResidentInFrance_False() {
        String country = "Germany";

        boolean result = userService.isResidentInFrance(country);

        assertFalse(result, "User residing in 'Germany' should return false");
    }
}
