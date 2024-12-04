package com.example.userapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.userapi.service.UserService.*(..))")
    public Object logServiceCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Entrée dans : {}", joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            LOGGER.info("Arguments :");
            for (Object arg : args) {
                LOGGER.info(" - {}", arg);
            }
        } else {
            LOGGER.info("Aucun argument passé.");
        }

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error("Erreur lors de l'exécution de la méthode : {}", throwable.getMessage());
            throw throwable;
        }
        long executionTime = System.currentTimeMillis() - startTime;

        LOGGER.info("Sortie de : {}", joinPoint.getSignature());
        LOGGER.info("Résultat : {}", result);
        LOGGER.info("Temps d'exécution : {} ms", executionTime);

        return result;
    }


}
