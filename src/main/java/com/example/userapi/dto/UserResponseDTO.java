package com.example.userapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String userName;
    private String countryOfResidence;
    private LocalDate birthDate;
    private String phoneNumber;
    private String gender;

}
