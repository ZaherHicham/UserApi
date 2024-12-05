package com.example.userapi.controller;

import com.example.userapi.dto.UserRequestDTO;
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
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("John Doe");
        userRequestDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        userRequestDTO.setCountryOfResidence("France");
        userRequestDTO.setPhoneNumber("0123456789");
        userRequestDTO.setGender("homme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.countryOfResidence").value("France"));
    }

    @Test
    void testRegisterUser_Failure_NotAdult() throws Exception {
        // Préparer une requête invalide (utilisateur mineur)
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("Jane Doe");
        userRequestDTO.setBirthDate(LocalDate.now().minusYears(17)); // Moins de 18 ans
        userRequestDTO.setCountryOfResidence("France");
        userRequestDTO.setPhoneNumber("0765432189");
        userRequestDTO.setGender("femme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("L'utilisateur doit être majeur"));
    }

    @Test
    void testRegisterUser_Failure_NotResidentInFrance() throws Exception {
        // Préparer une requête invalide (utilisateur hors de France)
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("Mark Twain");
        userRequestDTO.setBirthDate(LocalDate.of(1980, 3, 25));
        userRequestDTO.setCountryOfResidence("Germany"); // Réside hors de France
        userRequestDTO.setPhoneNumber("01234567890");
        userRequestDTO.setGender("homme");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
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
        user.setPhoneNumber("0123456789");
        user.setGender("femme");

        // Sauvegarder l'utilisateur
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

    @Test
    void testRegisterUser_PartialData() throws Exception {
        // Création d'un utilisateur avec des données partielles (utilisateur trop jeune)
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("Partial User");
        userRequestDTO.setBirthDate(LocalDate.of(2020, 6, 15));  // L'utilisateur n'est pas majeur (2024 - 2020 = 4 ans)
        userRequestDTO.setCountryOfResidence("France"); // Obligatoire pour éviter d'autres erreurs de validation

        // Effectuer la requête POST
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))  // Sérialisation de l'objet UserRequestDTO
                .andExpect(status().isBadRequest())  // Vérification du code de statut HTTP 400 (Bad Request)
                .andExpect(content().string("L'utilisateur doit être majeur"));  // Vérifier que le corps contient le message d'erreur
    }
}
