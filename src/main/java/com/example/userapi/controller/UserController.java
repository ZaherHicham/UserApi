package com.example.userapi.controller;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Contrôleur REST pour gérer les opérations liées aux utilisateurs.
 * Il expose des endpoints pour enregistrer un utilisateur et récupérer les informations d'un utilisateur.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * @param userRequestDTO Les informations de l'utilisateur à enregistrer.
     * @return Une réponse HTTP contenant les informations de l'utilisateur enregistré si l'enregistrement a réussi,
     *         sinon un statut 400 avec un message d'erreur.
     */
    @PostMapping
    private ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){

        if (!userService.isAdult(userRequestDTO.getBirthDate())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit être majeur");
        }

        if (!userService.isResidentInFrance(userRequestDTO.getCountryOfResidence())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit résider en France");
        }

        UserResponseDTO responseDTO = userService.saveUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Récupère les informations d'un utilisateur par son identifiant.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return Une réponse HTTP contenant les informations de l'utilisateur si trouvé, sinon un statut 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        Optional<UserResponseDTO> responseDTO = userService.getUserById(id);
        return responseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
