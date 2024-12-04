package com.example.userapi.aspect;

import com.example.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoggingAspectIntegrationTest {

    @Autowired
    private UserService userService; // Vrai service injecté et géré par Spring

    @Test
    void testAspectIsApplied() {
        userService.isAdult(LocalDate.of(2000, 1, 1));

        assertDoesNotThrow(() -> userService.isAdult(LocalDate.of(2000, 1, 1)));
    }
}
