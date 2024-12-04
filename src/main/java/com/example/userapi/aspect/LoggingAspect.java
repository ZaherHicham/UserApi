package com.example.userapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Classe d'aspect pour enregistrer les appels de service, y compris les arguments,
 * le résultat et le temps d'exécution des méthodes.
 * Utilise Spring AOP pour intercepter les appels de méthode.
 */
@Aspect
@Component
public class LoggingAspect {

    // Logger pour enregistrer les messages de débogage et d'informations
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Intercepte tous les appels aux méthodes de la classe UserService.
     *
     * @param joinPoint Représente le point d'exécution de la méthode interceptée.
     * @return Le résultat de la méthode interceptée.
     * @throws Throwable Si une exception est levée dans la méthode interceptée.
     */
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
