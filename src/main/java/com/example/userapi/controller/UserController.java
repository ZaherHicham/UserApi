package com.example.userapi.controller;

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
     * @param user Objet utilisateur validé depuis le corps de la requête.
     * @return Une réponse HTTP indiquant le statut de l'enregistrement.
     */
    @PostMapping
    private ResponseEntity<?> registerUser(@Valid @RequestBody User user){

        if (!userService.isAdult(user.getBirthDate())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit être majeur");
        }

        if (!userService.isResidentInFrance(user.getCountryOfResidence())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit résider en France");
        }

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Récupère les informations d'un utilisateur par son identifiant.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return Une réponse HTTP contenant les informations de l'utilisateur si trouvé, sinon un statut 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
