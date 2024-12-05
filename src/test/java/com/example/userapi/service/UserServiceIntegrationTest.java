package com.example.userapi.service;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(UserService.class)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        // Préparer un UserRequestDTO
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("John Doe");
        userRequestDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        userRequestDTO.setCountryOfResidence("France");
        userRequestDTO.setPhoneNumber("+33123456789");
        userRequestDTO.setGender("homme");

        // Sauvegarder l'utilisateur
        UserResponseDTO savedUser = userService.saveUser(userRequestDTO);

        // Vérifications
        assertNotNull(savedUser.getId(), "L'ID de l'utilisateur doit être généré");
        assertEquals("John Doe", savedUser.getUserName());
        assertEquals("France", savedUser.getCountryOfResidence());
    }

    @Test
    void testGetUserById() {
        // Préparer un UserRequestDTO
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("Jane Doe");
        userRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        userRequestDTO.setCountryOfResidence("France");
        userRequestDTO.setPhoneNumber("+33765432189");
        userRequestDTO.setGender("femme");

        // Sauvegarder l'utilisateur
        UserResponseDTO savedUser = userService.saveUser(userRequestDTO);

        // Récupérer l'utilisateur par son ID
        Optional<UserResponseDTO> foundUser = userService.getUserById(savedUser.getId());

        // Vérifications
        assertTrue(foundUser.isPresent(), "L'utilisateur doit être trouvé");
        assertEquals("Jane Doe", foundUser.get().getUserName());
        assertEquals("France", foundUser.get().getCountryOfResidence());
    }
}
