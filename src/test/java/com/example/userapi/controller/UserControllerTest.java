package com.example.userapi.controller;

import com.example.userapi.controller.UserController;
import com.example.userapi.model.User;
import com.example.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void testRegisterUser() throws Exception {
        // Mock UserService
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();

        // Inject UserService into UserController using reflection
        Field userServiceField = UserController.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userController, userService);

        // Prepare a User object
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence("France");

        // Mock UserService methods
        when(userService.isAdult(user.getBirthDate())).thenReturn(true);
        when(userService.isResidentInFrance(user.getCountryOfResidence())).thenReturn(true);
        when(userService.saveUser(user)).thenReturn(user);

        // Call private registerUser method using reflection
        Method registerUserMethod = UserController.class.getDeclaredMethod("registerUser", User.class);
        registerUserMethod.setAccessible(true);

        ResponseEntity<?> response = (ResponseEntity<?>) registerUserMethod.invoke(userController, user);

        // Assert response status
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testGetUser() throws Exception {
        // Mock UserService
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();

        // Inject UserService into UserController using reflection
        Field userServiceField = UserController.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userController, userService);

        // Prepare a User object
        User user = new User();
        user.setId(1L);

        // Mock UserService method
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Call public getUser method
        ResponseEntity<User> response = userController.getUser(1L);

        // Assert response body
        response.getBody();
        assertEquals(1L, (long) response.getBody().getId());
    }
}
