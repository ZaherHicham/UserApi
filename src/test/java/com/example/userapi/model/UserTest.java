package com.example.userapi.model;

import com.example.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "Aucun problème de validation ne doit être détecté");
    }

    @Test
    void testUserNameIsBlank() {
        User user = new User();
        user.setUserName(""); // Invalide
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Le nom d'utilisateur est obligatoire", violation.getMessage());
    }

    @Test
    void testBirthDateIsFuture() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.now().plusDays(1)); // Invalide
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("La date de naissance doit être antérieure", violation.getMessage());
    }

    @Test
    void testCountryOfResidenceIsBlank() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence(""); // Invalide
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Le pays de résidence est obligatoire", violation.getMessage());
    }

    @Test
    void testInvalidGender() {
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("invalid"); // Invalide

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Le genre doit être homme, femme ou autre", violation.getMessage());
    }
}
