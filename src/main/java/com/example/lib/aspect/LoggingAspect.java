package com.example.lib.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.lib.book..*(..)) || execution(* com.example.lib.patron..*(..)) || execution(* com.example.lib.borrowingRecord..*(..))")
    public void controllerAndServiceMethods() {}

    // Before advice for controller and service methods
    @Before("controllerAndServiceMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Called method: " + methodName + " with args: " + Arrays.toString(args));
    }

    // After throwing advice for controller and service methods
    @AfterThrowing(pointcut = "controllerAndServiceMethods()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Exception in method: " + methodName + " with message: " + ex.getMessage());
    }

    // After returning advice for controller and service methods
    @AfterReturning(pointcut = "controllerAndServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " returns: " + result);
    }

}
