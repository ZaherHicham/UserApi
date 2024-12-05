package com.example.userapi.service;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
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
        // Préparer un UserRequestDTO
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("John Doe");
        userRequestDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        userRequestDTO.setCountryOfResidence("France");
        userRequestDTO.setPhoneNumber("+33123456789");
        userRequestDTO.setGender("homme");

        // Simuler l'entité sauvegardée
        User user = new User();
        user.setId(1L);
        user.setUserName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Appeler la méthode
        UserResponseDTO responseDTO = userService.saveUser(userRequestDTO);

        // Vérifications
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("John Doe", responseDTO.getUserName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        // Simuler l'entité existante
        User user = new User();
        user.setId(1L);
        user.setUserName("Jane Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Appeler la méthode
        Optional<UserResponseDTO> responseDTO = userService.getUserById(1L);

        // Vérifications
        assertTrue(responseDTO.isPresent());
        assertEquals("Jane Doe", responseDTO.get().getUserName());
        verify(userRepository, times(1)).findById(1L);
    }
}
