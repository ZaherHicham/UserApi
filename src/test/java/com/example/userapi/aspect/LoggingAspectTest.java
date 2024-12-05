package com.example.userapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LoggingAspectTest {

    private LoggingAspect loggingAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loggingAspect = new LoggingAspect();
    }

    @Test
    void testLogServiceCalls_Success() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.toString()).thenReturn("com.example.userapi.service.UserService.someMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"TestArg1", 123});

        // Mock la méthode exécutée
        when(joinPoint.proceed()).thenReturn("TestResult");

        Object result = loggingAspect.logServiceCalls(joinPoint);

        assertEquals("TestResult", result, "La méthode doit retourner le résultat attendu");
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void testLogServiceCalls_Exception() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.toString()).thenReturn("com.example.userapi.service.UserService.someMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        // Simuler une exception pendant l'exécution
        Throwable throwable = new RuntimeException("Test Exception");
        when(joinPoint.proceed()).thenThrow(throwable);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> loggingAspect.logServiceCalls(joinPoint));
        assertEquals("Test Exception", exception.getMessage(), "Le message d'exception doit correspondre");
    }
}
