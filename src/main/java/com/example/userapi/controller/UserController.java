package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    private ResponseEntity<?> registerUser(@Valid @RequestBody User user){

        if (!userService.isAdult(user.getBirthDate())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit être majeur");
        }

        if (!userService.isResidentInFrance(user.getCountryOfResidence())) {
            return ResponseEntity.badRequest().body("L'utilisateur doit résider en France");
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUser(@PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }
}
