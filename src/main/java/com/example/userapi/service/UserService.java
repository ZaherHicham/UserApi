package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * Service pour gérer la logique métier liée aux utilisateurs.
 * Cette classe encapsule les règles métiers et interagit avec la couche de dépôt (UserRepository).
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Enregistre un utilisateur dans la base de données.
     *
     * @param user L'utilisateur à enregistrer.
     * @return L'utilisateur enregistré avec un identifiant généré.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id Identifiant unique de l'utilisateur.
     * @return Un objet Optional contenant l'utilisateur s'il est trouvé, ou vide sinon.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Vérifie si un utilisateur est adulte (18 ans ou plus).
     *
     * @param birthDate La date de naissance de l'utilisateur.
     * @return true si l'utilisateur a 18 ans ou plus, false sinon ou si la date est nulle.
     */
    public boolean isAdult(LocalDate birthDate) {
        if ( birthDate == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears() >= 18;
    }

    /**
     * Vérifie si un utilisateur réside en France.
     *
     * @param countryOfResidence Le pays de résidence fourni.
     * @return true si le pays est "France" (insensible à la casse), false sinon.
     */
    public boolean isResidentInFrance(String countryOfResidence) {
        return countryOfResidence.equalsIgnoreCase("france");
    }
}
