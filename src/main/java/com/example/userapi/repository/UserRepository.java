package com.example.userapi.repository;

import com.example.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de dépôt pour gérer les opérations sur l'entité User.
 * Étend JpaRepository pour bénéficier de fonctionnalités CRUD prédéfinies
 * et d'autres méthodes utiles pour manipuler les données de l'entité User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Cette interface hérite automatiquement de toutes les méthodes CRUD de JpaRepository.
    // Par exemple : save(), findById(), findAll(), deleteById(), etc.

    /**
     * JpaRepository fournit également la possibilité de définir des méthodes de requêtes personnalisées
     * en suivant les conventions de Spring Data JPA. Exemple :
     *
     * Optional<User> findByUserName(String userName);
     *
     * Cela permet de rechercher un utilisateur par son nom sans écrire de SQL manuellement.
     */
}
