package com.example.userapi.controller;

import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void testGetUser() throws Exception {
        // Mock du UserService
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();

        // Injection du UserService dans UserController avec reflection
        Field userServiceField = UserController.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userController, userService);

        // Simuler la réponse du service
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setUserName("Jane Doe");
        userResponseDTO.setCountryOfResidence("France");

        when(userService.getUserById(1L)).thenReturn(Optional.of(userResponseDTO));

        // Appeler la méthode et vérifier le résultat
        ResponseEntity<UserResponseDTO> response = userController.getUser(1L);

        assertEquals(1L, response.getBody().getId());
        assertEquals("Jane Doe", response.getBody().getUserName());
        assertEquals("France", response.getBody().getCountryOfResidence());
        verify(userService, times(1)).getUserById(1L);
    }
}
