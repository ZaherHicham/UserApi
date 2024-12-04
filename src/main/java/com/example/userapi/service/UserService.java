package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public boolean isAdult(LocalDate birthDate) {
        if ( birthDate == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears() >= 18;
    }

    public boolean isResidentInFrance(String countryOfResidence) {
        return countryOfResidence.equalsIgnoreCase("france");
    }
}
