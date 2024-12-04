package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser.getId(), "L'ID de l'utilisateur doit être généré");
        assertEquals("John Doe", savedUser.getUserName());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUserName("Jane Doe");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33765432189");
        user.setGender("femme");

        User savedUser = userService.saveUser(user);

        Optional<User> foundUser = userService.getUserById(savedUser.getId());

        assertTrue(foundUser.isPresent(), "L'utilisateur doit être trouvé");
        assertEquals("Jane Doe", foundUser.get().getUserName());
    }

    @Test
    void testIsAdult() {
        LocalDate adultBirthDate = LocalDate.of(2000, 1, 1);
        LocalDate nonAdultBirthDate = LocalDate.now().minusYears(17);

        assertTrue(userService.isAdult(adultBirthDate), "L'utilisateur doit être considéré comme adulte");
        assertFalse(userService.isAdult(nonAdultBirthDate), "L'utilisateur ne doit pas être considéré comme adulte");
    }

    @Test
    void testIsResidentInFrance() {
        String resident = "France";
        String nonResident = "Germany";

        assertTrue(userService.isResidentInFrance(resident), "L'utilisateur doit être résident en France");
        assertFalse(userService.isResidentInFrance(nonResident), "L'utilisateur ne doit pas être résident en France");
    }
}

