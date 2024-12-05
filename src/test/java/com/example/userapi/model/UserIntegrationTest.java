package com.example.userapi.model;

import com.example.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("0123456789");
        user.setGender("homme");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId(), "L'ID de l'utilisateur doit être généré");
        assertEquals("John Doe", savedUser.getUserName());
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setUserName("Jane Doe");
        user.setBirthDate(LocalDate.of(1985, 6, 15));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("0765432189");
        user.setGender("femme");

        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent(), "L'utilisateur doit être trouvé");
        assertEquals("Jane Doe", foundUser.get().getUserName());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserName("Mark Twain");
        user.setBirthDate(LocalDate.of(1980, 5, 25));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("0612345678");
        user.setGender("homme");

        User savedUser = userRepository.save(user);

        // Modification de l'utilisateur
        savedUser.setUserName("Samuel Clemens");
        User updatedUser = userRepository.save(savedUser);

        assertEquals("Samuel Clemens", updatedUser.getUserName(), "Le nom d'utilisateur doit être mis à jour");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserName("John Smith");
        user.setBirthDate(LocalDate.of(1995, 3, 15));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("0123456789");
        user.setGender("homme");

        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent(), "L'utilisateur doit être supprimé");
    }
}
