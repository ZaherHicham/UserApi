package com.example.userapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String userName;

    @Past(message = "La date de naissance doit être antérieure")
    private LocalDate birthDate;

    @NotBlank(message = "Le pays de résidence est obligatoire")
    private String countryOfResidence;

    @Pattern(regexp = "(^$|\\d{10,15})", message = "Le numéro de téléphone doit être valide")
    private String phoneNumber;

    @Pattern(regexp = "(^$|^(homme|femme|autre)$)", message = "Le genre doit être homme, femme ou autre")
    private String gender;


    // Getters et setters
}
