package com.example.userapi.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {
    public boolean isAdult(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears() >= 18;
    }

    public boolean isResidentInFrance(String countryOfResidence) {
        return countryOfResidence.equalsIgnoreCase("france");
    }
}
