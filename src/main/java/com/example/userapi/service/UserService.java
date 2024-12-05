package com.example.userapi.service;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
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
     * Enregistre un nouvel utilisateur dans le système.
     *
     * @param userRequestDTO Les informations de l'utilisateur à enregistrer.
     * @return Les informations de l'utilisateur enregistré.
     */
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUserName(userRequestDTO.getUserName());
        user.setBirthDate(userRequestDTO.getBirthDate());
        user.setCountryOfResidence(userRequestDTO.getCountryOfResidence());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setGender(userRequestDTO.getGender());

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setUserName(savedUser.getUserName());
        responseDTO.setCountryOfResidence(savedUser.getCountryOfResidence());
        responseDTO.setBirthDate(savedUser.getBirthDate());
        responseDTO.setPhoneNumber(savedUser.getPhoneNumber());
        responseDTO.setGender(savedUser.getGender());

        return responseDTO;
    }


    /**
     * Récupère les informations d'un utilisateur par son identifiant.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return Les informations de l'utilisateur si trouvé, vide sinon.
     */
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            // Mapper l'entité vers un DTO de réponse
            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setUserName(user.getUserName());
            responseDTO.setCountryOfResidence(user.getCountryOfResidence());
            responseDTO.setBirthDate(user.getBirthDate());
            responseDTO.setPhoneNumber(user.getPhoneNumber());
            responseDTO.setGender(user.getGender());
            return responseDTO;
        });
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
