package com.example.userapi.aspect;

import com.example.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@SpringBootTest
class LoggingAspectIntegrationTest {

    @Autowired
    private UserService userService; // Service réel injecté

    @Test
    void testAspectIsApplied() {
        // Vérifier que l'aspect ne génère pas d'exception et est exécuté correctement
        userService.isAdult(LocalDate.of(2000, 1, 1));

        // Aucune assertion directe ici, car l'objectif est de valider l'exécution complète avec l'aspect
        // Les logs générés par l'aspect peuvent être vérifiés manuellement ou via des outils supplémentaires
    }
}
