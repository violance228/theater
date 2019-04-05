package com.epam.theater.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * created by user violence
 * created on 05.04.2019
 * class created for project theater
 */

@Configuration
@Aspect
public class AOPLog {
    private Logger logger = LoggerFactory.getLogger(AOPLog.class);

    @Before("execution(* com.epam.theater.service..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("start execution method " + joinPoint.toShortString());
        logger.info("with args " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.epam.theater.service..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("finished execution method " + joinPoint.toShortString());
    }

    @AfterReturning(value = "execution(* com.epam.theater.service..*.*(..))",
                    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.info("{} returned with value {}", joinPoint, result);
    }
}
