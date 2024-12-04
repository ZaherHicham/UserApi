package com.example.userapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entité représentant un utilisateur dans l'application.
 * Cette classe est annotée pour être gérée par JPA (en tant que table dans la base de données)
 * et pour valider les données entrantes via les annotations Jakarta Validation.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    /**
     * Identifiant unique de l'utilisateur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de l'utilisateur.
     * Ne peut pas être vide ou nul.
     */
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String userName;

    /**
     * Date de naissance de l'utilisateur.
     * Doit être une date passée pour valider que l'utilisateur est né.
     */
    @Past(message = "La date de naissance doit être antérieure")
    private LocalDate birthDate;

    /**
     * Pays de résidence de l'utilisateur.
     * Ne peut pas être vide ou nul.
     */
    @NotBlank(message = "Le pays de résidence est obligatoire")
    private String countryOfResidence;

    /**
     * Numéro de téléphone de l'utilisateur.
     * Ce champ est facultatif.
     */
    private String phoneNumber;

    /**
     * Genre de l'utilisateur.
     * Doit être l'une des valeurs suivantes : homme, femme, autre.
     */
    @Pattern(regexp = "^(homme|femme|autre)$", message = "Le genre doit être homme, femme ou autre")
    private String gender;



}
