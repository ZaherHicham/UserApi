package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser_Success() throws Exception {
        // Préparer une requête valide
        User user = new User();
        user.setUserName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("homme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.countryOfResidence").value("France"));
    }

    @Test
    void testRegisterUser_Failure_NotAdult() throws Exception {
        // Préparer une requête invalide (utilisateur mineur)
        User user = new User();
        user.setUserName("Jane Doe");
        user.setBirthDate(LocalDate.now().minusYears(17)); // Moins de 18 ans
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33765432189");
        user.setGender("femme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("L'utilisateur doit être majeur"));
    }

    @Test
    void testRegisterUser_Failure_NotResidentInFrance() throws Exception {
        // Préparer une requête invalide (utilisateur hors de France)
        User user = new User();
        user.setUserName("Mark Twain");
        user.setBirthDate(LocalDate.of(1980, 3, 25));
        user.setCountryOfResidence("Germany"); // Réside hors de France
        user.setPhoneNumber("+491234567890");
        user.setGender("homme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("L'utilisateur doit résider en France"));
    }

    @Test
    void testGetUser_Success() throws Exception {
        // Enregistrer un utilisateur directement dans la base de données
        User user = new User();
        user.setUserName("Jane Smith");
        user.setBirthDate(LocalDate.of(1985, 6, 15));
        user.setCountryOfResidence("France");
        user.setPhoneNumber("+33123456789");
        user.setGender("femme");

        user = userRepository.save(user);

        // Récupérer l'utilisateur avec MockMvc
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Jane Smith"))
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    void testGetUser_NotFound() throws Exception {
        // Récupérer un utilisateur inexistant
        mockMvc.perform(get("/api/users/99999")) // ID inexistant
                .andExpect(status().isNotFound());
    }
}
