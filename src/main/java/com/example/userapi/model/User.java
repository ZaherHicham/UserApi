package com.example.userapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String userName;

    @NotBlank(message = "La date de naissance est obligatoire")
    private LocalDate birthDate;

    @NotBlank(message = "Le pays de résidence est obligatoire")
    private String countryOfResidence;

    private String phoneNumber;

    @Pattern(regexp = "^(homme|femme|autre)$", message = "Le genre doit être homme, femme ou autre")
    private String gender;



}
